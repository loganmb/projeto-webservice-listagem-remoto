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
@RequestMapping(path = "/student")
public class StudentController {

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
    @ApiOperation(value = "Create new students from CSV file")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Add new students"),
            @ApiResponse(code = 400, message = "Some field have wrong information"),
            @ApiResponse(code = 500, message = "Some error occurred"),
    })
    public ResponseEntity<String> loadFromCsv() {
        return collaboratorService.loadFromCsv();
    }

    @RequestMapping(path = "/{studentRegistrationNumber}", method = RequestMethod.PATCH, produces="application/json", consumes="application/json")
    @ResponseBody
    @ApiOperation(value = "Update the student")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Update the student"),
            @ApiResponse(code = 400, message = "Some field have wrong information"),
            @ApiResponse(code = 500, message = "Some error occurred"),
    })
    public ResponseEntity<String> updateStudentByStudentRegistrationNumber(@RequestBody Collaborator CollaboratorUpdate,
                                                                           @PathVariable("studentRegistrationNumber") Integer studentRegistrationNumber) {
        return collaboratorService.updateStudentByStudentRegistrationNumber(CollaboratorUpdate,studentRegistrationNumber);
    }

    @RequestMapping(path = "/{studentRegistrationNumber}", method = RequestMethod.DELETE, produces="application/json")
    @ResponseBody
    @ApiOperation(value = "Delete the student")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Delete the student"),
            @ApiResponse(code = 400, message = "Some field have wrong information"),
            @ApiResponse(code = 500, message = "Some error occurred"),
    })
    public ResponseEntity<String> deleteStudentByStudentRegistrationNumber(@PathVariable Integer studentRegistrationNumber) {
        return collaboratorService.deleteStudentByStudentRegistrationNumber(studentRegistrationNumber);
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET, produces="application/json")
    @ResponseBody
    @ApiOperation(value = "Get all registered students")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get all registered students"),
            @ApiResponse(code = 400, message = "Some field have wrong information"),
            @ApiResponse(code = 500, message = "Some error occurred"),
    })
    public Iterable<Collaborator> getAllStudents() {
        return collaboratorService.getAllStudents();
    }

    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "Search for student by name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search for student by name"),
            @ApiResponse(code = 400, message = "Some field have wrong information"),
            @ApiResponse(code = 500, message = "Some error occurred"),
    })
    public List<Collaborator> findByName(@PathVariable String name) {
        return collaboratorService.findByName(name);
    }

    @RequestMapping(value = "/studentRegistrationNumber/{studentRegistrationNumber}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "Search for student by registration number")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search for student by registration number"),
            @ApiResponse(code = 400, message = "Some field have wrong information"),
            @ApiResponse(code = 500, message = "Some error occurred"),
    })
    public Collaborator findByStudentRegistrationNumber(@PathVariable Integer studentRegistrationNumber) {
        return collaboratorService.findByStudentRegistrationNumber(studentRegistrationNumber);
    }

}
