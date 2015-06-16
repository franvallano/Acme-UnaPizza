package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.DeliveryMan;


@Repository
public interface DeliveryManRepository
	extends JpaRepository<DeliveryMan, Integer> {
	
	@Query("select b from DeliveryMan b where b.userAccount.id = ?1")
	DeliveryMan findByPrincipal(int userAccountId);
	
}
