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
        collaboratorRepository.save(mockCollaborator());
    }

    @Test
    public void shouldAddCollaboratorSuccessfully() {

        ResponseEntity<String> response = collaboratorService.add(new Collaborator(111000, "New Collaborator Name"));

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void shouldUpdateCollaboratorSuccessfully() {

        ResponseEntity<String> response = collaboratorService.updateCollaboratorByRegistrationNumber(new Collaborator(
                mockCollaborator().getCollaboratorRegistrationNumber(),
                "New Name"
        ), mockCollaborator().getCollaboratorRegistrationNumber());

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void shouldGetAllCollaboratorsSuccessfully() {

        List<Collaborator> Collaborators = (List<Collaborator>) collaboratorService.getAllCollaborators();

        assertTrue(Collaborators.size() == 1);
    }

    @Test
    public void shouldFindCollaboratorByNameSuccessfully() {

        List<Collaborator> Collaborators = collaboratorService.findByName(mockCollaborator().getName());

        assertEquals(mockCollaborator().getName(), Collaborators.get(0).getName());
    }

    @Test
    public void shouldFindCollaboratorByRegistrantionNumberSuccessfully() {

        Collaborator COLLABORATOR = collaboratorService.findByCollaboratorRegistrationNumber(mockCollaborator().getCollaboratorRegistrationNumber());

        assertEquals(mockCollaborator().getCollaboratorRegistrationNumber(), COLLABORATOR.getCollaboratorRegistrationNumber());
    }

    @Test
    public void shouldDeleteCollaboratorSuccessfully() {

        ResponseEntity<String> response = collaboratorService.deleteCollaboratorByRegistrationNumber(mockCollaborator().getCollaboratorRegistrationNumber());

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    private Collaborator mockCollaborator() {
        return new Collaborator(333000, "Collaborator Name");
    }
}
