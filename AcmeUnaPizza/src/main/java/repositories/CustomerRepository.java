package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
	//@Query("select c from Customer c where c.totalCost
	//Collection<Customer> findCustomerMoreMoneySpent();
}