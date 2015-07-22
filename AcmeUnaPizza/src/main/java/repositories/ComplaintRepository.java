package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer>{
	
	@Query("select c from Complaint c where c.administrator is null")
	Collection<Complaint> findAvailables();
	
	@Query("select c from Complaint c where c.administrator.id = ?1 or c.customer.id = ?1")
	Collection<Complaint> findByPrincipalId(int actorId);
	
	@Query("select c from Complaint c order by c.state")
	Collection<Complaint> findGroupByStatus();
}