package br.com.fiap.repository;

import java.util.List;

import br.com.fiap.entity.Collaborator;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CollaboratorRepository extends CrudRepository<Collaborator, Integer> {

	@Query("SELECT s FROM Collaborator s WHERE s.name LIKE %:name%")
	public List<Collaborator> findByName(@Param("name") String name);
	
	@Query("SELECT s FROM Collaborator s WHERE s.studentRegistrationNumber = :studentRegistrationNumber")
	public Collaborator findByCollaboratorRegistrationNumber(@Param("studentRegistrationNumber") Integer studentRegistrationNumber);
	
}
