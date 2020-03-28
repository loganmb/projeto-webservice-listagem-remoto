package br.com.fiap.repository;

import br.com.fiap.ProcessorApplication;
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
@SpringBootTest(classes = ProcessorApplication.class)
@ActiveProfiles({"integrationTest"})
public class ContactRepositoryIntegrationTest {

    @ClassRule
    public static MySQLContainer processorMySqlContainer = ProcessorMySqlContainer.getInstance();

    @Autowired
    CollaboratorRepository collaboratorRepository;

    @Autowired
    ContactRepository contactRepository;

    @Before
    @Transactional("transactionTransactionManager")
    public void insertTransactions() {

        List<Collaborator> Collaborators = new ArrayList<>();
        List<Contact> contacts = new ArrayList<>();
        for (int studentIndex = 1; studentIndex < 6; studentIndex++) {
            Collaborators.add(new Collaborator(studentIndex, "Name " + studentIndex));
            for(int transactionIndex = 10; transactionIndex < 21; transactionIndex = transactionIndex + 10 ) {
                contacts.add(new Contact(transactionIndex + studentIndex, Collaborators.get(studentIndex-1), Collaborators.get(studentIndex-1).getStudentRegistrationNumber(), "123" + transactionIndex, 0.99 + transactionIndex, "transaction " + transactionIndex + studentIndex));
            }
        }
        collaboratorRepository.saveAll(Collaborators);
        contactRepository.saveAll(contacts);
    }

    @Test
    @Transactional
    public void shouldFindAllTransactionsFromStudent() {
        List<Contact> contacts = contactRepository.findAllTransactionsFromStudent(new Collaborator(1, "Name 1"));

        assertTrue(contacts.get(0).getTransactionId() == 11);
        assertTrue(contacts.get(1).getTransactionId() == 21);
    }

    @Test
    @Transactional
    public void shouldFindTransactionByTransactionId() {
        Contact contact = contactRepository.findTransactionByTransactionId(11);

        assertTrue(contact.getTransactionId() == 11);
    }
}
