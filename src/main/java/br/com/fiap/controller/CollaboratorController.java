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
@RequestMapping(path = "/Collaborator")
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
    public ResponseEntity<String> add(@Valid @RequestBody Collaborator COLLABORATOR) {
        return collaboratorService.add(COLLABORATOR);
    }

    @RequestMapping(value = "/load_from_csv", method = RequestMethod.POST, produces="application/json")
    @ResponseBody
    @ApiOperation(value = "Create new Collaborators from CSV file")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Add new Collaborators"),
            @ApiResponse(code = 400, message = "Some field have wrong information"),
            @ApiResponse(code = 500, message = "Some error occurred"),
    })
    public ResponseEntity<String> loadFromCsv() {
        return collaboratorService.loadFromCsv();
    }

    @RequestMapping(path = "/{CollaboratorRegistrationNumber}", method = RequestMethod.PATCH, produces="application/json", consumes="application/json")
    @ResponseBody
    @ApiOperation(value = "Update the Collaborator")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Update the Collaborator"),
            @ApiResponse(code = 400, message = "Some field have wrong information"),
            @ApiResponse(code = 500, message = "Some error occurred"),
    })
    public ResponseEntity<String> updateCollaboratorByCollaboratorRegistrationNumber(@RequestBody Collaborator CollaboratorUpdate,
                                                                           @PathVariable("CollaboratorRegistrationNumber") Integer CollaboratorRegistrationNumber) {
        return collaboratorService.updateCollaboratorByCollaboratorRegistrationNumber(CollaboratorUpdate,CollaboratorRegistrationNumber);
    }

    @RequestMapping(path = "/{CollaboratorRegistrationNumber}", method = RequestMethod.DELETE, produces="application/json")
    @ResponseBody
    @ApiOperation(value = "Delete the Collaborator")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Delete the Collaborator"),
            @ApiResponse(code = 400, message = "Some field have wrong information"),
            @ApiResponse(code = 500, message = "Some error occurred"),
    })
    public ResponseEntity<String> deleteCollaboratorByCollaboratorRegistrationNumber(@PathVariable Integer CollaboratorRegistrationNumber) {
        return collaboratorService.deleteCollaboratorByCollaboratorRegistrationNumber(CollaboratorRegistrationNumber);
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET, produces="application/json")
    @ResponseBody
    @ApiOperation(value = "Get all registered Collaborators")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get all registered Collaborators"),
            @ApiResponse(code = 400, message = "Some field have wrong information"),
            @ApiResponse(code = 500, message = "Some error occurred"),
    })
    public Iterable<Collaborator> getAllCollaborators() {
        return collaboratorService.getAllCollaborators();
    }

    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "Search for Collaborator by name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search for Collaborator by name"),
            @ApiResponse(code = 400, message = "Some field have wrong information"),
            @ApiResponse(code = 500, message = "Some error occurred"),
    })
    public List<Collaborator> findByName(@PathVariable String name) {
        return collaboratorService.findByName(name);
    }

    @RequestMapping(value = "/CollaboratorRegistrationNumber/{CollaboratorRegistrationNumber}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "Search for Collaborator by registration number")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search for Collaborator by registration number"),
            @ApiResponse(code = 400, message = "Some field have wrong information"),
            @ApiResponse(code = 500, message = "Some error occurred"),
    })
    public Collaborator findByCollaboratorRegistrationNumber(@PathVariable Integer CollaboratorRegistrationNumber) {
        return collaboratorService.findByCollaboratorRegistrationNumber(CollaboratorRegistrationNumber);
    }

}
