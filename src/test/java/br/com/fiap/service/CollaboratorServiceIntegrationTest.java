package br.com.fiap.service;

import br.com.fiap.ProcessorApplication;
import br.com.fiap.config.ProcessorMySqlContainer;
import br.com.fiap.entity.Collaborator;
import br.com.fiap.repository.CollaboratorRepository;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProcessorApplication.class)
@ActiveProfiles({"integrationTest"})
public class CollaboratorServiceIntegrationTest {

    @ClassRule
    public static MySQLContainer processorMySqlContainer = ProcessorMySqlContainer.getInstance();

    @Autowired
    private CollaboratorService collaboratorService;

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Before
    @Transactional("transactionManager")
    public void setUp() {
        collaboratorRepository.save(mockStudent());
    }

    @Test
    public void shouldAddStudentSuccessfully() {

        ResponseEntity<String> response = collaboratorService.add(new Collaborator(111000, "New Collaborator Name"));

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void shouldUpdateStudentSuccessfully() {

        ResponseEntity<String> response = collaboratorService.updateStudentByStudentRegistrationNumber(new Collaborator(
                mockStudent().getStudentRegistrationNumber(),
                "New Name"
        ), mockStudent().getStudentRegistrationNumber());

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void shouldGetAllStudentsSuccessfully() {

        List<Collaborator> Collaborators = (List<Collaborator>) collaboratorService.getAllStudents();

        assertTrue(Collaborators.size() == 1);
    }

    @Test
    public void shouldFindStudentByNameSuccessfully() {

        List<Collaborator> Collaborators = collaboratorService.findByName(mockStudent().getName());

        assertEquals(mockStudent().getName(), Collaborators.get(0).getName());
    }

    @Test
    public void shouldFindStudentByRegistrantionNumberSuccessfully() {

        Collaborator COLLABORATOR = collaboratorService.findByStudentRegistrationNumber(mockStudent().getStudentRegistrationNumber());

        assertEquals(mockStudent().getStudentRegistrationNumber(), COLLABORATOR.getStudentRegistrationNumber());
    }

    @Test
    public void shouldDeleteStudentSuccessfully() {

        ResponseEntity<String> response = collaboratorService.deleteStudentByStudentRegistrationNumber(mockStudent().getStudentRegistrationNumber());

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    private Collaborator mockStudent() {
        return new Collaborator(333000, "Collaborator Name");
    }
}
