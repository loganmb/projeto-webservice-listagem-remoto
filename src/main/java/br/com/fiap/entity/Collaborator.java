package br.com.fiap.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Collaborator", uniqueConstraints = {@UniqueConstraint(columnNames = "PERSONAL_REGISTRATION_NUMBER")})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Collaborator implements Serializable {

    public Collaborator(){}

    public Collaborator(Integer collaboratorRegistrationNumber, String name){
        this.collaboratorRegistrationNumber = collaboratorRegistrationNumber;
        this.name = name;
    }

    @Id
    @Column(name = "COLLABORATOR_ID", unique = true, nullable = false)
    @JsonProperty("collaborator_registration_number")
    @NotNull
    @ApiModelProperty(value = "Institution's registration number")
    private Integer collaboratorRegistrationNumber;

    @Column(name = "NAME")
    @JsonProperty("name")
    @NotNull
    @ApiModelProperty(value = "Collaborator's name")
    private String name;

    @Column(name="JOB_ROLE")
    @JsonProperty("job_role")
    @ApiModelProperty(value= "Collaborator's job role in the company")
    private String jobRole;

    @Column(name="ABOUT_ME")
    @JsonProperty("about_me")
    @ApiModelProperty(value= "A little bit about the collaborator")
    private String aboutMe;

    @Column(name="EXPERIENCE")
    @JsonProperty("experience")
    @ApiModelProperty(value= "Some about collaborators experience")
    private String experience;

    @JsonProperty("contacts")
    private List<Contact> contacts;


    public Integer getCollaboratorRegistrationNumber() {
        return collaboratorRegistrationNumber;
    }

    public void setCollaboratorRegistrationNumber(Integer collaboratorRegistrationNumber) {
        this.collaboratorRegistrationNumber = collaboratorRegistrationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}
