package br.com.fiap.controller;

import br.com.fiap.entity.Contact;
import br.com.fiap.service.ContactService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @RequestMapping(path = "/add", method = RequestMethod.POST, produces="application/json", consumes="application/json")
    @ResponseBody
    @ApiOperation(value = "Create new contact for the collaborator")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Add new contact"),
            @ApiResponse(code = 400, message = "Some field have wrong information"),
            @ApiResponse(code = 500, message = "Some error occurred"),
    })
    public ResponseEntity<String> add(@Valid @RequestBody Contact contact) {
        return contactService.add(contact);
    }

    @RequestMapping(path = "/Collaborator/{collaboratorRegistrationNumber}", method = RequestMethod.GET, produces="application/json")
    @ResponseBody
    @ApiOperation(value = "Find all contacts by collaborator registration number")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get all contacts from the collaborator"),
            @ApiResponse(code = 400, message = "Some field have wrong information"),
            @ApiResponse(code = 500, message = "Some error occurred"),
    })
<<<<<<< Updated upstream:src/main/java/br/com/fiap/controller/TransactionController.java
<<<<<<< HEAD
    public ResponseEntity<List<Contact>> findAllTransactionsFromStudent(@PathVariable Integer studentRegistrationNumber) {
        return contactService.findAllTransactionsFromStudent(studentRegistrationNumber);
=======
    public ResponseEntity<List<Contact>> findAllTransactionsFromCollaborator(@PathVariable Integer CollaboratorRegistrationNumber) {
        return transactionService.findAllTransactionsFromCollaborator(CollaboratorRegistrationNumber);
>>>>>>> 8d0c044dbaf1bf7239cfa69b92347bd0f054c5d5
=======
    public ResponseEntity<List<Contact>> findAllContactsFromCollaborator(@PathVariable("collaboratorRegistrationNumber") Integer collaboratorRegistrationNumber) {
        return contactService.findAllContactsFromCollaborator(collaboratorRegistrationNumber);
>>>>>>> Stashed changes:src/main/java/br/com/fiap/controller/ContactController.java
    }

    @RequestMapping(path = "/{contactId}", method = RequestMethod.DELETE, produces="application/json")
    @ResponseBody
    @ApiOperation(value = "Delete the contact by transaction ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Delete the contact"),
            @ApiResponse(code = 400, message = "Some field have wrong information"),
            @ApiResponse(code = 500, message = "Some error occurred"),
    })
<<<<<<< Updated upstream:src/main/java/br/com/fiap/controller/TransactionController.java
    public ResponseEntity<String> deleteTransactionById(@PathVariable Integer transactionId) {
        return contactService.deleteTransactionById(transactionId);
=======
    public ResponseEntity<String> deleteTransactionById(@PathVariable("contactId") Integer contactId) {
        return contactService.deleteTransactionById(contactId);
>>>>>>> Stashed changes:src/main/java/br/com/fiap/controller/ContactController.java
    }
}
