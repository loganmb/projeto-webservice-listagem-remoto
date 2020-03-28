package br.com.fiap.repository;

import br.com.fiap.entity.Collaborator;
import br.com.fiap.entity.Contact;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Contact, Integer> {

    @Query("SELECT t FROM Contact t WHERE t.collaborator = :CollaboratorRegistrationNumber")
    public List<Contact> findAllTransactionsFromCollaborator(@Param("CollaboratorRegistrationNumber") Collaborator CollaboratorRegistrationNumber);

    @Query("SELECT t FROM Contact t WHERE t.transactionId = :transactionId")
    public Contact findTransactionByTransactionId(@Param("transactionId") Integer transactionId);
}
