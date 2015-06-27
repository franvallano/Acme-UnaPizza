package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Customer;


@Repository
public interface AdministratorRepository
	extends JpaRepository<Administrator, Integer> {
	
	@Query("select a from Administrator a where a.userAccount.id = ?1")
	Administrator findByPrincipal(int userAccountId);
	
	//Dinero invertido en productos.
	@Query("select SUM(totalCost) from PurchaseOrder pO")
	Double findInvestedMoney();
	
	//Dinero ganado en las ventas de productos.
	@Query("select SUM(totalCost) from SalesOrder sO")
	Double findSalesMoney();
	
	//Importe medio de los pedidos.
	@Query("select AVG(totalCost) from SalesOrder sO")
	Double findAvgOrders();
	
	// select c from Customer c join c.salesOrders sO GROUP BY (c) HAVING SUM(sO.totalCost) >= ALL (select SUM(salOr.totalCost) from SalesOrder salOr GROUP BY (salOr.customer));
	
	
	//Pizza m�s y menos vendida.
	//Bebida m�s y menos vendida.
	//Postre m�s y menos vendido.
	//Complemento m�s y menos vendido.
	//Pedidos realizados a proveedores.
	//Importe de pedidos no entregados.

}
