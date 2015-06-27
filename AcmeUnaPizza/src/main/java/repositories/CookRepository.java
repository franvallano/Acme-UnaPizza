package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Cook;
import domain.Customer;


@Repository
public interface CookRepository
	extends JpaRepository<Cook, Integer> {
	
	@Query("select c from Cook c where c.userAccount.id = ?1")
	Cook findByPrincipal(int userAccountId);
	
	//Cook que más pedidos ha realizado.
	@Query("select c from Cook c where c.salesOrders.size = (select max(coo.salesOrders.size) from Cook coo)")
	Collection<Cook> findCookMoreOrders();
}
