package br.com.fiap.service;

import java.util.List;

import br.com.fiap.entity.Collaborator;
import br.com.fiap.repository.CollaboratorRepository;
import br.com.fiap.utils.NameFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CollaboratorService {

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Transactional
    public ResponseEntity<String> add(Collaborator collaborator) {

        try {
            collaborator.setName(NameFormatter.capitalizeName(collaborator.getName()));
            collaboratorRepository.save(collaborator);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
            String body = "{\"message\":\"Added the collaborator successfully\"}";

            return new ResponseEntity<>(body, headers, HttpStatus.CREATED);

        } catch (Exception e) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
            String body = "{\"message\":\"An error has occurred\", \"exception\":" + e.getMessage() + "}";

            return new ResponseEntity<>(body, headers, HttpStatus.BAD_REQUEST);
        }

    }

    @Transactional

    public ResponseEntity<String> updateCollaboratorByRegistrationNumber(Collaborator CollaboratorUpdate, Integer CollaboratorRegistrationNumber) {

        try {

            Collaborator CollaboratorDatabase = collaboratorRepository.findByCollaboratorRegistrationNumber(CollaboratorRegistrationNumber);


            CollaboratorDatabase.setName(CollaboratorUpdate.getName() == null || CollaboratorUpdate.getName().isEmpty()
                    ? NameFormatter.capitalizeName(CollaboratorDatabase.getName())
                    : NameFormatter.capitalizeName(CollaboratorUpdate.getName()));

            collaboratorRepository.save(CollaboratorDatabase);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
            String body = "{\"message\":\"Updated the collaborator successfully\"}";

            return new ResponseEntity<>(body, headers, HttpStatus.OK);

        } catch (Exception e) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
            String body = "{\"message\":\"An error has occurred\", \"exception\":" + e.getMessage() + "}";

            return new ResponseEntity<>(body, headers, HttpStatus.BAD_REQUEST);
        }

    }

    @Transactional

    public ResponseEntity<String> deleteCollaboratorByRegistrationNumber(Integer CollaboratorRegistrationNumber) {

        try {

            Collaborator collaborator = collaboratorRepository.findByCollaboratorRegistrationNumber(CollaboratorRegistrationNumber);


            collaboratorRepository.deleteById(collaborator.getCollaboratorRegistrationNumber());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
            String body = "{\"message\":\"Deleted the collaborator successfully\"}";

            return new ResponseEntity<>(body, headers, HttpStatus.OK);

        } catch (Exception e) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
            String body = "{\"message\":\"An error has occurred\", \"exception\":" + e.getMessage() + "}";

            return new ResponseEntity<>(body, headers, HttpStatus.BAD_REQUEST);
        }

    }

    @Transactional(readOnly = true)
    public Iterable<Collaborator> getAllCollaborators() {
        return collaboratorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Collaborator> findByName(String name) {
        return collaboratorRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public Collaborator findByCollaboratorRegistrationNumber(Integer CollaboratorRegistrationNumber) {
        return collaboratorRepository.findByCollaboratorRegistrationNumber(CollaboratorRegistrationNumber);

    }

}
