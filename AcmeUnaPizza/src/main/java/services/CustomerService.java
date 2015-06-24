package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.PasswordCode;
import domain.Customer;

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

	public void save( Customer customer ){
		Assert.notNull(customer);
		
		if(customer.getId()==0){
			String passwordCoded;
			
			passwordCoded = PasswordCode.encode(customer.getUserAccount().getPassword());
			customer.getUserAccount().setPassword(passwordCoded);
		}
		
		this.customerRepository.save( customer );
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
		authority.setAuthority("CUSTOMER");
		
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
	
	// Ancillary methods ------------------------------------------------------

}