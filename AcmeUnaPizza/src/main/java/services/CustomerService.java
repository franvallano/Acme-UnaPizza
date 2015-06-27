package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Complaint;
import domain.Customer;
import domain.SalesOrder;
import forms.CustomerForm;

@Service
@Transactional
public class CustomerService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CustomerRepository customerRepository;

	// Ancillary services -----------------------------------------------------

	// Constructor ------------------------------------------------------------
	public CustomerService(){
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Customer create(){
		Customer newbye;
		
		newbye = new Customer();
		newbye.setUserAccount(createUserAccount());
		
		return newbye;
	}

	public void save(Customer customer, String rPass, Boolean agree){
		String pass;
		Md5PasswordEncoder encoder;
		encoder = new Md5PasswordEncoder();
		
		Assert.notNull(customer);
		Assert.isTrue(customer.getUserAccount().getPassword().equals(rPass));
		Assert.isTrue(agree);
		
		pass = customer.getUserAccount().getPassword();
		pass = encoder.encodePassword(pass, null);
		customer.getUserAccount().setPassword(pass);
		
		customerRepository.save(customer);
	}

	public void delete( Customer entity ){
		Assert.notNull(entity);
		Assert.isTrue( entity.getId()!=0 );
		Assert.isTrue( this.customerRepository.exists(entity.getId() ));
		
		this.customerRepository.delete( entity );
		
		Assert.isTrue( !this.customerRepository.exists(entity.getId() ));
	}
	
	public Customer findOne( int id ){
		Assert.isTrue( id != 0);
		
		Customer res;
		
		res = this.customerRepository.findOne( id );
		
		return res;
 }

	public Collection<Customer> findAll(){
		Collection<Customer> res;
		
		res = customerRepository.findAll();
		
		return res;
	}

	// Other business methods -------------------------------------------------

	public UserAccount createUserAccount() {
		UserAccount result;
		Collection<Authority> authorities;
		Authority authority;
		
		authority = new Authority();
		authority.setAuthority(Authority.CUSTOMER);
		
		authorities = new ArrayList<Authority>();
		authorities.add(authority);
		
		result = new UserAccount();
		result.setAuthorities(authorities);
		
		return result;
	}
	
	public Customer findByPrincipal() {
	 	Customer customer;
	 	UserAccount userAccount;
	 	
	 	userAccount = LoginService.getPrincipal();
	 	customer = customerRepository.findByPrincipal(userAccount.getId());
	 	
	 	Assert.notNull(customer);
	 	
	 	return customer;
	 }
	
	//Metodo que recibe un objeto formulario y reconstruye un objeto de dominio
	public Customer reconstruct(CustomerForm customerForm) {
		Customer customer;
		Collection<SalesOrder> salesOrders;
		Collection<Complaint> complaints;
		UserAccount userAccount;
		Authority authority;
		
		customer = create();
		userAccount = new UserAccount();

		salesOrders = new ArrayList<SalesOrder>();
		complaints = new ArrayList<Complaint>();
		userAccount.setUsername(customerForm.getUsername());
		userAccount.setPassword(customerForm.getPassword());
		
		authority = new Authority();

		authority.setAuthority(Authority.CUSTOMER);
		userAccount.addAuthority(authority);
		
		customer.setUserAccount(userAccount);
		customer.setName(customerForm.getName());
		customer.setSurname(customerForm.getSurname());
		customer.setEmail(customerForm.getEmail());
		customer.setCreditCard(null);
		customer.setPhone(customerForm.getPhone());
		customer.setBirthDate(customerForm.getBirthDate());
		customer.setAddress(customerForm.getAddress());
		customer.setSalesOrders(salesOrders);
		customer.setComplaints(complaints);
		customer.setRangee("STANDARD");
		
		return customer;
	}
	
	public boolean rPassword(CustomerForm customerForm) {
		boolean result;
		String pass;
		String rpass;
		
		pass = customerForm.getPassword();
		rpass = customerForm.getRepeatedPass();
		result = pass.equals(rpass);
		
		return result;
	}
	
	public Collection<Customer> findCustomerMoreComplaints() {
		Collection<Customer> result;
		
		result = customerRepository.findCustomerMoreComplaints();
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Customer> findCustomerMoreOrders() {
		Collection<Customer> result;
		
		result = customerRepository.findCustomerMoreOrders();
		
		Assert.notNull(result);
		
		return result;
	}
	
	// Ancillary methods ------------------------------------------------------

}