package br.com.fiap.repository;

import br.com.fiap.ProcessorApplication;
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
@SpringBootTest(classes = ProcessorApplication.class)
@ActiveProfiles({"integrationTest"})
public class CollaboratorRepositoryIntegrationTest {

    @ClassRule
    public static MySQLContainer processorMySqlContainer = ProcessorMySqlContainer.getInstance();

    @Autowired
    CollaboratorRepository collaboratorRepository;

    @Before
    @Transactional("studentTransactionManager")
    public void insertStudents() {

        List<Collaborator> Collaborators = new ArrayList<>();
        for (int index = 1; index < 6; index++) {
            Collaborators.add(new Collaborator(index, "Name " + index));
        }
        collaboratorRepository.saveAll(Collaborators);
    }

    @Test
    @Transactional
    public void shouldFindStudentByName() {
        List<Collaborator> Collaborators = collaboratorRepository.findByName("Name 1");

        assertEquals(1, Collaborators.size());
        assertEquals("Name 1", Collaborators.get(0).getName());
    }

    @Test
    @Transactional
    public void shouldFindByStudentRegistrationNumber() {
        Collaborator COLLABORATOR = collaboratorRepository.findByStudentRegistrationNumber(1);

        assertTrue(COLLABORATOR.getStudentRegistrationNumber() == 1);
        assertEquals("Name 1", COLLABORATOR.getName());
    }

}
