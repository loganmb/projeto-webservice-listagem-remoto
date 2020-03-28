package br.com.fiap.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
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
    public ResponseEntity<String> add(Collaborator COLLABORATOR) {

        try {
            COLLABORATOR.setName(NameFormatter.capitalizeName(COLLABORATOR.getName()));
            collaboratorRepository.save(COLLABORATOR);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
            String body = "{\"message\":\"Added the Collaborator successfully\"}";

            return new ResponseEntity<>(body, headers, HttpStatus.CREATED);

        } catch (Exception e) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
            String body = "{\"message\":\"An error has occurred\", \"exception\":" + e.getMessage() + "}";

            return new ResponseEntity<>(body, headers, HttpStatus.BAD_REQUEST);
        }

    }

    @Transactional()
    public ResponseEntity<String> loadFromCsv() {

        List<Collaborator> Collaborators = new ArrayList<>();

        try {

            BufferedReader csvReader = new BufferedReader(new FileReader("./files/lista_alunos.csv"));
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(";");
                Collaborator COLLABORATOR = new Collaborator(Integer.parseInt(data[1]), NameFormatter.capitalizeName(data[0]));

                Collaborators.add(COLLABORATOR);
            }
            csvReader.close();

            collaboratorRepository.saveAll(Collaborators);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
            String body = "{\"message\":\"Added all the Collaborators successfully\"}";

            return new ResponseEntity<>(body, headers, HttpStatus.CREATED);

        } catch (Exception e) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
            String body = "{\"message\":\"An error has occurred\", \"exception\":" + e.getMessage() + "}";

            return new ResponseEntity<>(body, headers, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<String> updateStudentByStudentRegistrationNumber(Collaborator CollaboratorUpdate, Integer studentRegistrationNumber) {

        try {

            Collaborator CollaboratorDatabase = collaboratorRepository.findByStudentRegistrationNumber(studentRegistrationNumber);

            CollaboratorDatabase.setName(CollaboratorUpdate.getName() == null || CollaboratorUpdate.getName().isEmpty()
                    ? NameFormatter.capitalizeName(CollaboratorDatabase.getName())
                    : NameFormatter.capitalizeName(CollaboratorUpdate.getName()));

            collaboratorRepository.save(CollaboratorDatabase);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
            String body = "{\"message\":\"Updated the student successfully\"}";

            return new ResponseEntity<>(body, headers, HttpStatus.OK);

        } catch (Exception e) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
            String body = "{\"message\":\"An error has occurred\", \"exception\":" + e.getMessage() + "}";

            return new ResponseEntity<>(body, headers, HttpStatus.BAD_REQUEST);
        }

    }

    @Transactional
    public ResponseEntity<String> deleteStudentByStudentRegistrationNumber(Integer studentRegistrationNumber) {

        try {

            Collaborator COLLABORATOR = collaboratorRepository.findByStudentRegistrationNumber(studentRegistrationNumber);

            collaboratorRepository.deleteById(COLLABORATOR.getStudentRegistrationNumber());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
            String body = "{\"message\":\"Deleted the Collaborator successfully\"}";

            return new ResponseEntity<>(body, headers, HttpStatus.OK);

        } catch (Exception e) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
            String body = "{\"message\":\"An error has occurred\", \"exception\":" + e.getMessage() + "}";

            return new ResponseEntity<>(body, headers, HttpStatus.BAD_REQUEST);
        }

    }

    @Transactional(readOnly = true)
    public Iterable<Collaborator> getAllStudents() {
        return collaboratorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Collaborator> findByName(String name) {
        return collaboratorRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public Collaborator findByStudentRegistrationNumber(Integer studentRegistrationNumber) {
        return collaboratorRepository.findByStudentRegistrationNumber(studentRegistrationNumber);
    }

}
