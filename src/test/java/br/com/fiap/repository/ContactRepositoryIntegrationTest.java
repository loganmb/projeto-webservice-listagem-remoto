package br.com.fiap.repository;

import br.com.fiap.ContactApplication;
import br.com.fiap.config.ProcessorMySqlContainer;
import br.com.fiap.entity.Collaborator;
import br.com.fiap.entity.Contact;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ContactApplication.class)
@ActiveProfiles({"integrationTest"})
public class ContactRepositoryIntegrationTest {

    @ClassRule
    public static MySQLContainer processorMySqlContainer = ProcessorMySqlContainer.getInstance();

    @Autowired
    CollaboratorRepository collaboratorRepository;

    @Autowired
    ContactRepository contactRepository;

    @Before
    @Transactional("contactManager")
    public void insertContacts() {

        List<Collaborator> Collaborators = new ArrayList<>();
        List<Contact> contacts = new ArrayList<>();
        for (int collaboratorIndex = 1; collaboratorIndex < 6; collaboratorIndex++) {
            Collaborators.add(new Collaborator(collaboratorIndex, "Name " + collaboratorIndex));
            for(int contactIndex = 10; contactIndex < 21; contactIndex = contactIndex + 10 ) {
                contacts.add(new Contact(Collaborators.get(collaboratorIndex -1), collaboratorIndex, "" + contactIndex,
                        "" + contactIndex * 2, "9" + contactIndex * 30, ""));
            }
        }
        collaboratorRepository.saveAll(Collaborators);
        contactRepository.saveAll(contacts);
    }

    @Test
    @Transactional
    public void shouldFindAllTransactionsFromStudent() {
        List<Contact> contacts = contactRepository.findAllContactsFromCollaborator(
                new Collaborator(1, "Name 1"));

        assertTrue(contacts.get(0).getContactId() == 11);
        assertTrue(contacts.get(1).getContactId() == 21);
    }

    @Test
    @Transactional
    public void shouldFindTransactionByTransactionId() {
        Contact contact = contactRepository.findContactById(11);

        assertTrue(contact.getContactId() == 11);
    }
}