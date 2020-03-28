package br.com.fiap.repository;

import br.com.fiap.ContactApplication;
import br.com.fiap.config.ProcessorMySqlContainer;
import br.com.fiap.entity.Collaborator;
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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ContactApplication.class)
@ActiveProfiles({"integrationTest"})
public class CollaboratorRepositoryIntegrationTest {

    @ClassRule
    public static MySQLContainer processorMySqlContainer = ProcessorMySqlContainer.getInstance();

    @Autowired
    CollaboratorRepository collaboratorRepository;

    @Before
    @Transactional("collaboratorContactManager")
    public void insertCollaborators() {

        List<Collaborator> Collaborators = new ArrayList<>();
        for (int index = 1; index < 6; index++) {
            Collaborators.add(new Collaborator(index, "Name " + index));
        }
        collaboratorRepository.saveAll(Collaborators);
    }

    @Test
    @Transactional
    public void shouldFindCollaboratorByName() {
        List<Collaborator> Collaborators = collaboratorRepository.findByName("Name 1");

        assertEquals(1, Collaborators.size());
        assertEquals("Name 1", Collaborators.get(0).getName());
    }

    @Test
    @Transactional
    public void shouldFindByCollaboratorRegistrationNumber() {
        Collaborator collaborator = collaboratorRepository.findByCollaboratorRegistrationNumber(1);

        assertTrue(collaborator.getCollaboratorRegistrationNumber() == 1);
        assertEquals("Name 1", collaborator.getName());
    }

}
