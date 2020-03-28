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
@RequestMapping("/collaborator")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ContactController {

    @Autowired
    private ContactService contactService;

    @RequestMapping(path = "{collaboratorRegistrationNumber}/contact", method = RequestMethod.POST, produces="application/json", consumes="application/json")
    @ResponseBody
    @ApiOperation(value = "Create collaborator profile")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 400, message = "Please, check check all"),
            @ApiResponse(code = 500, message = "Some error occurred"),
    })
    public ResponseEntity<String> add(@Valid @RequestBody Contact contact) {
        return contactService.add(contact);
    }

    @RequestMapping(path = "{collaboratorRegistrationNumber}/contact", method = RequestMethod.GET, produces="application/json")
    @ResponseBody
    @ApiOperation(value = "Find all contacts by collaborator registration number")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get all contacts from the collaborator"),
            @ApiResponse(code = 400, message = "Some field have wrong information"),
            @ApiResponse(code = 500, message = "Some error occurred"),
    })
    public ResponseEntity<List<Contact>> findAllContactsFromCollaborator(@PathVariable("collaboratorRegistrationNumber") Integer collaboratorRegistrationNumber) {
        return contactService.findAllContactsFromCollaborator(collaboratorRegistrationNumber);
    }

    @RequestMapping(path = "{collaboratorRegistrationNumber}/contact/{contactId}", method = RequestMethod.DELETE, produces="application/json")
    @ResponseBody
    @ApiOperation(value = "Delete the contact by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Delete the contact"),
            @ApiResponse(code = 400, message = "Some field have wrong information"),
            @ApiResponse(code = 500, message = "Some error occurred"),
    })
    public ResponseEntity<String> deleteContactById(@PathVariable("contactId") Integer contactId) {
        return contactService.deleteContactById(contactId);
    }
}