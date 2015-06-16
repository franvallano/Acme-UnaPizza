package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Cook;


@Repository
public interface CookRepository
	extends JpaRepository<Cook, Integer> {
	
	@Query("select c from Cook c where c.userAccount.id = ?1")
	Cook findByPrincipal(int userAccountId);
	
}
