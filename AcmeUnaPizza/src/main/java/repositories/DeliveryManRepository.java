package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;
import domain.DeliveryMan;


@Repository
public interface DeliveryManRepository
	extends JpaRepository<DeliveryMan, Integer> {
	
	@Query("select b from DeliveryMan b where b.userAccount.id = ?1")
	DeliveryMan findByPrincipal(int userAccountId);
	
	//Delivery Man que más pedidos han repartido.
	@Query("select dm from DeliveryMan dm where dm.salesOrders.size = (select max(delm.salesOrders.size) from DeliveryMan delm)")
	Collection<DeliveryMan> findDeliveryManMoreOrders();
}
