package br.com.fiap.controller;

import br.com.fiap.entity.Collaborator;
import br.com.fiap.service.CollaboratorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/collaborator")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CollaboratorController {

    @Autowired
    private CollaboratorService collaboratorService;

    @RequestMapping(path = "/add", method = RequestMethod.POST, produces="application/json", consumes="application/json")
    @ResponseBody
    @ApiOperation(value = "Create new Collaborator")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Add new Collaborator"),
            @ApiResponse(code = 400, message = "Some field have wrong information"),
            @ApiResponse(code = 500, message = "Some error occurred"),
    })
    public ResponseEntity<String> add(@Valid @RequestBody Collaborator collaborator) {
        return collaboratorService.add(collaborator);
    }

    @RequestMapping(path = "/{collaboratorRegistrationNumber}", method = RequestMethod.PATCH, produces="application/json", consumes="application/json")
    @ResponseBody
    @ApiOperation(value = "Update the collaborator")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Update the collaborator"),
            @ApiResponse(code = 400, message = "Some field have wrong information"),
            @ApiResponse(code = 500, message = "Some error occurred"),
    })
    public ResponseEntity<String> updateCollaboratorByRegistrationNumber(@RequestBody Collaborator CollaboratorUpdate,
                                                                           @PathVariable("collaboratorRegistrationNumber") Integer collaboratorRegistrationNumber) {
        return collaboratorService.updateCollaboratorByRegistrationNumber(CollaboratorUpdate,collaboratorRegistrationNumber);
    }


    @RequestMapping(path = "/{collaboratorRegistrationNumber}", method = RequestMethod.DELETE, produces="application/json")
    @ResponseBody
    @ApiOperation(value = "Delete the collaborator")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Delete the collaborator"),
            @ApiResponse(code = 400, message = "Some field have wrong information"),
            @ApiResponse(code = 500, message = "Some error occurred"),
    })
    public ResponseEntity<String> deleteCollaboratorByRegistrationNumber(@PathVariable("collaboratorRegistrationNumber") Integer collaboratorRegistrationNumber) {
        return collaboratorService.deleteCollaboratorByRegistrationNumber(collaboratorRegistrationNumber);
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET, produces="application/json")
    @ResponseBody
    @ApiOperation(value = "Get all registered collaborators")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get all registered collaborators"),
            @ApiResponse(code = 400, message = "Some field have wrong information"),
            @ApiResponse(code = 500, message = "Some error occurred"),
    })
    public Iterable<Collaborator> getAllCollaborators() {
        return collaboratorService.getAllCollaborators();
    }

    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "Search for collaborator by name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search for collaborator by name"),
            @ApiResponse(code = 400, message = "Some field have wrong information"),
            @ApiResponse(code = 500, message = "Some error occurred"),
    })
    public List<Collaborator> findByName(@PathVariable String name) {
        return collaboratorService.findByName(name);
    }


    @RequestMapping(value = "/collaborator_id/{collaboratorRegistrationNumber}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "Search for collaborator by registration number")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search for collaborator by registration number"),
            @ApiResponse(code = 400, message = "Some field have wrong information"),
            @ApiResponse(code = 500, message = "Some error occurred"),
    })
    public Collaborator findByCollaboratorRegistrationNumber(@PathVariable Integer collaboratorRegistrationNumber) {

        return collaboratorService.findByCollaboratorRegistrationNumber(collaboratorRegistrationNumber);
    }

}
