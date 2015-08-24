package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

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
import forms.CustomerProfileForm;
import forms.PasswordForm;

@Service
@Transactional
public class CustomerService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CustomerRepository customerRepository;

	// Ancillary services -----------------------------------------------------
	@Autowired
	private AdministratorService administratorService;

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
	
	public void save(Customer customer){
		Assert.notNull(customer);
		
		customerRepository.save(customer);
	}
	
	public void deactivate(Customer customer){
		Assert.notNull(customer);
		Assert.isTrue(customer.getUserAccount().getActive());
		
		administratorService.findByPrincipal();
		
		customer.getUserAccount().setActive(false);
		
		customerRepository.save(customer);
	}
	
	public void activate(Customer customer){
		Assert.notNull(customer);
		Assert.isTrue(!customer.getUserAccount().getActive());
		
		administratorService.findByPrincipal();
		
		customer.getUserAccount().setActive(true);
		
		customerRepository.save(customer);
	}
	
	public void saveProfile(Customer customer){
		Assert.notNull(customer);
		Assert.isTrue(customer.getUserAccount().getUsername().equals(findByPrincipal().getUserAccount().getUsername()));
	
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
		
		Assert.notNull(res);
		
		return res;
 }

	public Collection<Customer> findAll(){
		Collection<Customer> res;
		
		administratorService.findByPrincipal();
		
		res = customerRepository.findAll();
		
		return res;
	}
	
	public Collection<Customer> findAllCustomers(){
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
		result.setActive(true);
		
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
		Assert.notNull(customerForm);
		Customer customer;
		Collection<SalesOrder> salesOrders;
		Collection<Complaint> complaints;
		Calendar calendar = Calendar.getInstance();
		
		customer = create();

		salesOrders = new ArrayList<SalesOrder>();
		complaints = new ArrayList<Complaint>();
		calendar.setTimeInMillis(System.currentTimeMillis());
		
		customer.getUserAccount().setUsername(customerForm.getUsername());
		customer.getUserAccount().setPassword(customerForm.getPassword());
		
		customer.setName(customerForm.getName());
		customer.setSurname(customerForm.getSurname());
		customer.setEmail(customerForm.getEmail());
		if(customerForm.getCreditCard() != null) {
			customer.setCreditCard(customerForm.getCreditCard());
			
			Assert.isTrue(customerForm.getCreditCard().getExpirationYear() >= calendar.get(Calendar.YEAR));
			
			if(customerForm.getCreditCard().getExpirationYear() == calendar.get(Calendar.YEAR))
				Assert.isTrue(customerForm.getCreditCard().getExpirationMonth() >= (calendar.get(Calendar.MONTH) + 1));
		}
		customer.setPhone(customerForm.getPhone());
		customer.setBirthDate(customerForm.getBirthDate());
		customer.setAddress(customerForm.getAddress());
		customer.setSalesOrders(salesOrders);
		customer.setComplaints(complaints);
		customer.setRangee("STANDARD");
		
		return customer;
	}
	
	public Customer reconstructProfile(CustomerProfileForm customerProfileForm) {
		Assert.notNull(customerProfileForm);
		Customer customer;
		Calendar calendar = Calendar.getInstance();
		
		customer = findByPrincipal();
		
		Assert.isTrue(customer.getUserAccount().getUsername().equals(customerProfileForm.getUsername()));
		
		calendar.setTimeInMillis(System.currentTimeMillis());
		
		customer.setName(customerProfileForm.getName());
		customer.setSurname(customerProfileForm.getSurname());
		customer.setEmail(customerProfileForm.getEmail());
		if(customerProfileForm.getCreditCard() != null) {
			Assert.isTrue(customerProfileForm.getCreditCard().getExpirationYear() >= calendar.get(Calendar.YEAR));
			
			if(customerProfileForm.getCreditCard().getExpirationYear() == calendar.get(Calendar.YEAR))
				Assert.isTrue(customerProfileForm.getCreditCard().getExpirationMonth() >= (calendar.get(Calendar.MONTH) + 1));
			
			customer.setCreditCard(customerProfileForm.getCreditCard());
		} else
			customer.setCreditCard(null);
		customer.setPhone(customerProfileForm.getPhone());
		customer.setBirthDate(customerProfileForm.getBirthDate());
		customer.setAddress(customerProfileForm.getAddress());
		
		return customer;
	}
	
	public Customer reconstructPassword(PasswordForm passwordForm) {
		Assert.notNull(passwordForm);
		Assert.isTrue(passwordForm.getNewPassword().equals(passwordForm.getRepeatNewPassword()));
		Assert.isTrue(!passwordForm.getNewPassword().equals("") && !passwordForm.getRepeatNewPassword().equals(""));
		Assert.isTrue(passwordForm.getNewPassword().length() >= 5 && passwordForm.getRepeatNewPassword().length() >= 5);
		Customer customer;
		Md5PasswordEncoder encoder;

		customer = findByPrincipal();
		
		encoder = new Md5PasswordEncoder();
		
		Assert.isTrue(customer.getUserAccount().getPassword().equals(encoder.encodePassword(passwordForm.getActualPassword(), null)));
		
		customer.getUserAccount().setPassword(encoder.encodePassword(passwordForm.getNewPassword(), null));
		
		return customer;
	}
	
	public CustomerForm desreconstruct(Customer customer) {
		Assert.notNull(customer);
		CustomerForm customerForm;
		
		customerForm = new CustomerForm();
		
		customerForm.setUsername(customer.getUserAccount().getUsername());
		customerForm.setPassword(customer.getUserAccount().getPassword());
		customerForm.setRepeatedPass(customer.getUserAccount().getPassword());
		customerForm.setName(customer.getName());
		customerForm.setSurname(customer.getSurname());
		customerForm.setEmail(customer.getEmail());
		customerForm.setPhone(customer.getPhone());
		customerForm.setBirthDate(customer.getBirthDate());
		customerForm.setAddress(customer.getAddress());
		customerForm.setAgree(true);
		
		if(customer.getCreditCard() != null) {
			customerForm.setCheckBoxCreditCard(true);
			customerForm.setCreditCard(customer.getCreditCard());
		} else {
			customerForm.setCheckBoxCreditCard(false);
		}
		
		return customerForm;
	}
	
	public CustomerProfileForm desreconstructProfile(Customer customer) {
		Assert.notNull(customer);
		CustomerProfileForm customerProfileForm;
		
		customerProfileForm = new CustomerProfileForm();
		
		customerProfileForm.setUsername(customer.getUserAccount().getUsername());
		customerProfileForm.setName(customer.getName());
		customerProfileForm.setSurname(customer.getSurname());
		customerProfileForm.setEmail(customer.getEmail());
		customerProfileForm.setPhone(customer.getPhone());
		customerProfileForm.setBirthDate(customer.getBirthDate());
		customerProfileForm.setAddress(customer.getAddress());
		
		if(customer.getCreditCard() != null) {
			customerProfileForm.setCheckBoxCreditCard(true);
			customerProfileForm.setCreditCard(customer.getCreditCard());
		} else {
			customerProfileForm.setCheckBoxCreditCard(false);
		}
		
		return customerProfileForm;
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
	
	public Collection<Customer> findCustomerMoreMoneySpent() {
		Collection<Customer> result;
		
		result = customerRepository.findCustomerMoreMoneySpent();
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Integer findTotalNumberOrders() {
		Integer result;
		Customer customer;
		
		customer = findByPrincipal();
		
		result = customerRepository.findTotalNumberOrders(customer.getId());
		
		return result;
	}
	
	public Collection<Date> findDateLastOrder() {
		Collection<Date> result;
		Customer customer;
		
		customer = findByPrincipal();
		
		result = customerRepository.findDateLastOrder(customer.getId());
		
		return result;
	}
	
	public Customer findCustomerBySalesOrder(int salesOrderId) {
		Assert.isTrue(salesOrderId != 0);
		Customer result;
		
		result = customerRepository.findCustomerBySalesOrder(salesOrderId);
		
		return result;
	}
	
	public void checkRangeCustomer(Customer customer, boolean newSalesOrder) {
		Assert.notNull(customer);
		// El +1 es por el nuevo pedido que vamo
		int totalSalesOrder = customer.getSalesOrders().size();
		String range = "";
		
		// Si es nuevo pedido sumamos 1 y si no lo restamos
		if(newSalesOrder)
			totalSalesOrder += 1;
		else
			totalSalesOrder -= 1;
		
		if(totalSalesOrder >= 0 && totalSalesOrder <= 3)
			range = "STANDARD";
		else if(totalSalesOrder >= 4 && totalSalesOrder <= 7)
			range = "SILVER";
		else if(totalSalesOrder >= 8 && totalSalesOrder <= 12)
			range = "GOLD";
		else 
			range = "VIP";
			
		// Si tiene un nuevo rango lo asignamos y guardamos
		if(!customer.getRangee().equals(range)) {
			customer.setRangee(range);
			
			customerRepository.save(customer);
		}
	}
	
	// Ancillary methods ------------------------------------------------------
	
}