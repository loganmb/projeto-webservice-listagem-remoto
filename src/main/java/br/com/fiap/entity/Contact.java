package br.com.fiap.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "CONTACT", uniqueConstraints = {@UniqueConstraint(columnNames = "CONTACT_ID")})
public class Contact implements Serializable {

    public Contact() {
    }


    public Contact(Collaborator collaborator, Integer collaboratorId, @NotNull String DDI,
                   @NotNull String DDD, @NotNull String phoneNumber, String description) {

        this.collaborator = collaborator;
        this.collaboratorId = collaboratorId;
        this.DDI = DDI;
        this.DDD = DDD;
        this.phoneNumber = phoneNumber;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    long id;

    @Id
    @Column(name = "CONTACT_ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("contact_id")
    @NotNull
    @ApiModelProperty(value = "Contact identification")
    private Integer contactId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COLLABORATOR_ID")
    @JsonIgnore
    private Collaborator collaborator;

    @JsonProperty("collaborator_id")
    @Transient
    @ApiModelProperty(value = "Institution's registration number")
    private Integer collaboratorId;

    @Column(name = "IDD")
    @JsonProperty("idd")
    @NotNull
    @ApiModelProperty(value = "International Direct Dialing (DDI in Brazil)")
    private String DDI;

    @Column(name = "NPA")
    @JsonProperty("ddd")
    @NotNull
    @ApiModelProperty(value = "Numbering Plan Administration (DDD in Brazil)")
    private String DDD;

    @Column(name = "PRONE_NUMBER")
    @JsonProperty("phone_number")
    @NotNull
    @ApiModelProperty(value = "Phone number")
    private String phoneNumber;

    @Column(name = "DESCRIPTION")
    @JsonProperty("description")
    @ApiModelProperty(value = "Description of the contact")
    private String description;


    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public Collaborator getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(Collaborator collaborator) {
        this.collaborator = collaborator;
    }

    public Integer getCollaboratorId() {
        return collaboratorId;
    }

    public void setCollaboratorId(Integer collaboratorId) {
        this.collaboratorId = collaboratorId;
    }

    public String getDDI() {
        return DDI;
    }

    public void setDDI(String DDI) {
        this.DDI = DDI;
    }

    public String getDDD() {
        return DDD;
    }

    public void setDDD(String DDD) {
        this.DDD = DDD;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
