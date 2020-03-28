package br.com.fiap.repository;

import br.com.fiap.entity.Collaborator;
import br.com.fiap.entity.Contact;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Integer> {

    @Query("SELECT t FROM Contact t WHERE t.student = :CollaboratorRegistrationNumber")
    public List<Contact> findAllContactsFromCollaborator(@Param("CollaboratorRegistrationNumber") Collaborator CollaboratorRegistrationNumber);

    @Query("SELECT t FROM Contact t WHERE t.transactionId = :transactionId")
    public Contact findContactByContactId(@Param("transactionId") Integer transactionId);
}
