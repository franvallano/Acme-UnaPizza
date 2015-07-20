package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Boss;


@Repository
public interface BossRepository
	extends JpaRepository<Boss, Integer> {
	
	@Query("select b from Boss b where b.userAccount.id = ?1")
	Boss findByPrincipal(int userAccountId);
	
	@Query("select b from Boss b join b.salesOrders sO where sO.id = ?1")
	Boss findBossBySalesOrder(int salesOrderId);
}
