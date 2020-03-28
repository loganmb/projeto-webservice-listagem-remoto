package br.com.fiap.service;

import br.com.fiap.entity.Collaborator;
import br.com.fiap.entity.Contact;
import br.com.fiap.repository.CollaboratorRepository;
import br.com.fiap.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@RequestMapping("/transaction")
public class TransactionService {

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Transactional
    public ResponseEntity<String> add(Contact contact) {
        try {
            if (contact.getCollaboratorId() == null)
                throw new Exception("\"Collaborator registration number is required\"");

            contact.setCollaborator(collaboratorRepository.findByCollaboratorRegistrationNumber(contact.getCollaboratorId()));

            if (contact.getCollaborator() == null)
                throw new Exception("\"Collaborator registration number not found\"");

            if (contactRepository.existsById(contact.getContactId()))
                throw new Exception("\"Contact ID already exist\"");

            contactRepository.save(contact);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
            String body = "{\"message\":\"Added the contact successfully\"}";

            return new ResponseEntity<>(body, headers, HttpStatus.CREATED);

        } catch (Exception e) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
            String body = "{\"message\":\"An error has occurred\", \"exception\":" + e.getMessage() + "}";

            return new ResponseEntity<>(body, headers, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<Contact>> findAllTransactionsFromCollaborator(Integer CollaboratorRegistrationNumber) {

        Collaborator COLLABORATOR = collaboratorRepository.findByCollaboratorRegistrationNumber(CollaboratorRegistrationNumber);

        List<Contact> contacts = transactionRepository.findAllTransactionsFromCollaborator(COLLABORATOR);

        contacts.forEach(contact -> contact.setCollaboratorId(contact.getCollaborator().getCollaboratorRegistrationNumber()));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());

        return new ResponseEntity<>(contacts, headers, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> deleteTransactionById(Integer transactionId) {

        try {

            Contact contact = contactRepository.findTransactionByTransactionId(transactionId);

            contactRepository.deleteById(contact.getContactId());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
            String body = "{\"message\":\"Deleted the contact successfully\"}";

            return new ResponseEntity<>(body, headers, HttpStatus.OK);

        } catch (Exception e) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
            String body = "{\"message\":\"An error has occurred\", \"exception\":" + e.getMessage() + "}";

            return new ResponseEntity<>(body, headers, HttpStatus.BAD_REQUEST);
        }

    }
}
