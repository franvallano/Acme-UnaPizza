package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.PurchaseOrder;


@Repository
public interface PurchaseOrderRepository
	extends JpaRepository<PurchaseOrder, Integer> {
	
	
}
