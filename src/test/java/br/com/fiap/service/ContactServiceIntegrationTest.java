package br.com.fiap.service;

import br.com.fiap.ContactApplication;
import br.com.fiap.config.ProcessorMySqlContainer;
import br.com.fiap.entity.Collaborator;
import br.com.fiap.entity.Contact;
import br.com.fiap.repository.CollaboratorRepository;
import br.com.fiap.repository.ContactRepository;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ContactApplication.class)
@ActiveProfiles({"integrationTest"})
public class ContactServiceIntegrationTest {

    @ClassRule
    public static MySQLContainer processorMySqlContainer = ProcessorMySqlContainer.getInstance();

    @Autowired
    private ContactService contactService;

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Before
    @Transactional("contactManager")
    public void setUp() {

        Contact contact = mockContact();

        collaboratorRepository.save(contact.getCollaborator());
        contactRepository.save(contact);
    }

    @Test
    public void shouldAddTransactionSuccessfully() {
        Contact contact = new Contact(
                new Collaborator(123, ""),
                123,
                "1",
                "52",
                "444224142",
                "Contact description"
        );
        ResponseEntity<String> response = contactService.add(contact);

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void shouldThrowExceptionForTransactionAlreadyExist() {
        ResponseEntity<String> response = contactService.add(mockContact());

        assertTrue(response.getStatusCode().is4xxClientError());
        assertTrue(response.getBody().contains("Contact ID already exist"));
    }

    @Test
    public void shouldThrowExceptionForNonStudent() {
        Contact contact = new Contact(
                new Collaborator(123, ""),
                123,
                "1",
                "52",
                "444224142",
                "Contact description"
        );

        ResponseEntity<String> response = contactService.add(contact);

        assertTrue(response.getStatusCode().is4xxClientError());
        assertTrue(response.getBody().contains("Collaborator registration number not found"));
    }

    @Test
    public void shouldFindAllTransactionsFromStudent() {
        ResponseEntity<List<Contact>> response = contactService.findAllContactsFromCollaborator(mockContact().getCollaboratorId());

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void shouldDeleteTheTransaction() {
        ResponseEntity<String> response = contactService.deleteContactById(mockContact().getContactId());

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    private Collaborator mockCollaborator() {
        return new Collaborator(333000, "Collaborator Name");
    }

    private Contact mockContact() {
        return new Contact(
                new Collaborator(123, ""),
                123,
                "1",
                "52",
                "444224142",
                "Contact description"
        );
    }
}
