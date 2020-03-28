package br.com.fiap.service;

import br.com.fiap.ProcessorApplication;
import br.com.fiap.config.ProcessorMySqlContainer;
import br.com.fiap.entity.Collaborator;
import br.com.fiap.entity.Contact;
import br.com.fiap.repository.CollaboratorRepository;
import br.com.fiap.repository.TransactionRepository;
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
@SpringBootTest(classes = ProcessorApplication.class)
@ActiveProfiles({"integrationTest"})
public class ContactServiceIntegrationTest {

    @ClassRule
    public static MySQLContainer processorMySqlContainer = ProcessorMySqlContainer.getInstance();

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Before
    @Transactional("transactionManager")
    public void setUp() {

        Contact contact = mockTransaction();

        collaboratorRepository.save(contact.getCOLLABORATOR());
        transactionRepository.save(contact);
    }

    @Test
    public void shouldAddTransactionSuccessfully() {
        Contact contact = new Contact(
                2000,
                mockStudent(),
                333000,
                "4532",
                1.00,
                "Contact description"
        );
        ResponseEntity<String> response = transactionService.add(contact);

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void shouldThrowExceptionForTransactionAlreadyExist() {
        ResponseEntity<String> response = transactionService.add(mockTransaction());

        assertTrue(response.getStatusCode().is4xxClientError());
        assertTrue(response.getBody().contains("Contact ID already exist"));
    }

    @Test
    public void shouldThrowExceptionForNonStudent() {
        Contact contact = new Contact(
                1000,
                new Collaborator(222000, "Name 3"),
                222000,
                "4532",
                1.00,
                "Contact description"
        );

        ResponseEntity<String> response = transactionService.add(contact);

        assertTrue(response.getStatusCode().is4xxClientError());
        assertTrue(response.getBody().contains("Collaborator registration number not found"));
    }

    @Test
    public void shouldFindAllTransactionsFromStudent() {
        ResponseEntity<List<Contact>> response = transactionService.findAllTransactionsFromStudent(mockTransaction().getStudentRegistrationNumber());

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void shouldDeleteTheTransaction() {
        ResponseEntity<String> response = transactionService.deleteTransactionById(mockTransaction().getTransactionId());

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    private Collaborator mockStudent() {
        return new Collaborator(333000, "Collaborator Name");
    }

    private Contact mockTransaction() {
        return new Contact(
                1000,
                mockStudent(),
                333000,
                "4532",
                1.00,
                "Contact description"
        );
    }
}
