package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Boss;
import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	
	@Query("select c from Customer c where c.userAccount.id = ?1")
	Customer findByPrincipal(int userAccountId);
	
	// Customers que más quejas han realizado.
	@Query("select c from Customer c where c.complaints.size = (select max(cust.complaints.size) from Customer cust)")
	Collection<Customer> findCustomerMoreComplaints();
	
	//Customers que más pedidos han realizado.
	@Query("select c from Customer c where c.salesOrders.size = (select max(cust.salesOrders.size) from Customer cust)")
	Collection<Customer> findCustomerMoreOrders();
	
	//Customers que más dinero han gastado.
	@Query("select c from Customer c join c.salesOrders sO GROUP BY (c) HAVING SUM(sO.totalCost) >= ALL (select SUM(salOr.totalCost) from SalesOrder salOr GROUP BY (salOr.customer))")
	Collection<Customer> findCustomerMoreMoneySpent();
	
	// Total number of orders 
	@Query("select COUNT(sO) from SalesOrder sO where sO.customer.id = ?1")
	Integer findTotalNumberOrders(int customerId);
	
	// Fecha último pedido realizado
	@Query("select sO.creationMoment from SalesOrder sO where sO.customer.id = ?1 ORDER BY sO.creationMoment DESC")
	Collection<Date> findDateLastOrder(int customerId);
	
	@Query("select c from Customer c join c.salesOrders sO where sO.id = ?1")
	Customer findCustomerBySalesOrder(int salesOrderId);
}