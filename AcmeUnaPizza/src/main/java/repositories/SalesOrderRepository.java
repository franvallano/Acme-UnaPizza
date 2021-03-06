/* 
* Autogenerated interface code 
* Variables (text between %) must have been replaced when code is autogenerated
*/
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;
import domain.SalesOrder;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Integer>{
	
	@Query("select count(sO) from SalesOrder sO where sO.cook.id = ?1")
	Integer findTotalSalesOrderByCook(int cookId);
	
	@Query("select count(sO) from SalesOrder sO")
	Integer findTotalSalesOrder();
	
	@Query("select count(sO) from SalesOrder sO where sO.deliveryMan.id = ?1")
	Integer findTotalSalesOrderByDeliveryMan(int deliveryManId);
	
	//Dinero ganado en las ventas de productos.
	@Query("select SUM(totalCost) from SalesOrder sO where sO.state != 'UNDELIVERED'")
	Double findSalesMoney();
		
	//Importe medio de los pedidos.
	@Query("select AVG(totalCost) from SalesOrder sO")
	Double findAvgOrders();
		
	//Importe de pedidos no entregados.
	@Query("select SUM(sO.totalCost) from SalesOrder sO WHERE sO.state = 'UNDELIVERED'")
	Double findTotalMoneyUndeliveredOrders();
	
	// Importe del pedido mas caro
	@Query("select MAX(sO.totalCost) from SalesOrder sO")
	Double findMoreExpensiveSalesOrder();
	
	// Importe del pedido mas barato
	@Query("select MIN(sO.totalCost) from SalesOrder sO")
	Double findLessExpensiveSalesOrder();
	
	// Importe del pedido mas caro por delivery man
	@Query("select MAX(sO.totalCost) from SalesOrder sO where sO.deliveryMan.id = ?1")
	Double findMoreExpensiveSalesOrderByDeliveryMan(int deliveryManId);
	
	// Importe del pedido mas caro por cook
	@Query("select MAX(sO.totalCost) from SalesOrder sO where sO.cook.id = ?1")
	Double findMoreExpensiveSalesOrderByCook(int cookId);
	
	// Importe del pedido mas barato por delivery man
	@Query("select MIN(sO.totalCost) from SalesOrder sO where sO.deliveryMan.id = ?1")
	Double findLessExpensiveSalesOrderByDeliveryMan(int deliveryManId);
		
	// Importe del pedido mas barato por cook
	@Query("select MIN(sO.totalCost) from SalesOrder sO where sO.cook.id = ?1")
	Double findLessExpensiveSalesOrderByCook(int cookId);
	
	// Importe medio de los pedidos
	@Query("select AVG(sO.totalCost) from SalesOrder sO")
	Double findAvgSalesOrder();
	
	// Importe medio de los pedidos por delivery man
	@Query("select AVG(sO.totalCost) from SalesOrder sO where sO.deliveryMan.id = ?1")
	Double findAvgSalesOrderByDeliveryMan(int deliveryManId);
	
	// Importe medio de los pedidos por cook
	@Query("select AVG(sO.totalCost) from SalesOrder sO where sO.cook.id = ?1")
	Double findAvgSalesOrderByCook(int cookId);
	
	//Pedido que m�s se ha tardado en repartir
	@Query("select sO from SalesOrder sO where sO.drivingTime=(select max(s.drivingTime) from SalesOrder s)")
	Collection<SalesOrder> findSalesOrderMaxDrivingTime();
	
	//Pedido que menos se ha tardado en repartir
	@Query("select sO from SalesOrder sO where sO.drivingTime=(select min(s.drivingTime) from SalesOrder s where s.drivingTime>0)")
	Collection<SalesOrder> findSalesOrderMinDrinvingTime();
	
	@Query("select sO from SalesOrder sO where sO.customer.id = ?1")
	Collection<SalesOrder> findAllByCustomerId(int customerId);
	
	@Query("select sO from SalesOrder sO where sO.state = 'UNDELIVERED'")
	Collection<SalesOrder> findAllSalesOrderUndelivered();
	
	@Query("select sO from SalesOrder sO where sO.state = 'DELIVERED'")
	Collection<SalesOrder> findAllSalesOrderDelivered();
	
	@Query("select sO from SalesOrder sO where sO.state = 'OPEN'")
	Collection<SalesOrder> findAllSalesOrderOpened();
	
	@Query("select sO from SalesOrder sO where sO.boss.id = ?1")
	Collection<SalesOrder> findMySalesOrdersBoss(int bossId);
	
	@Query("select sO from SalesOrder sO where sO.deliveryMan.id = ?1")
	Collection<SalesOrder> findMySalesOrdersDeliveryMan(int deliveryManId);
	
	@Query("select sO from SalesOrder sO where sO.cook.id = ?1")
	Collection<SalesOrder> findMySalesOrdersCook(int cookId);
	
	@Query("select sO from SalesOrder sO where sO.state = 'COOKING' OR sO.state = 'PREPARED' OR sO.state = 'ONITSWAY'")
	Collection<SalesOrder> findAllSalesOrderInProcess();
	
	@Query("select sO from SalesOrder sO where sO.state = 'OPEN' and sO.boss is not null")
	Collection<SalesOrder> findAllForCooking();
	
	@Query("select sO from SalesOrder sO where sO.state = 'COOKING' and sO.cook.id = ?1")
	Collection<SalesOrder> findAllForPrepared(int cookId);
	
	@Query("select sO from SalesOrder sO where sO.state = 'PREPARED' and sO.deliveryMan is null")
	Collection<SalesOrder> findAllOnItsWay();
	
	@Query("select sO from SalesOrder sO where sO.state = 'ONITSWAY' and sO.deliveryMan.id = ?1")
	Collection<SalesOrder> findOneToFinish(int deliveryManId);
	
	@Query("select count(sO) from SalesOrder sO where sO.deliveryMan.id = ?1 AND sO.state = 'ONITSWAY'")
	Integer findTotalOnItsWayByDeliveryMan(int deliveryManId);
} 
