package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

import security.Authority;
import utilities.AbstractTest;
import domain.Administrator;
import domain.Boss;
import domain.Cook;
import domain.CreditCard;
import domain.Customer;
import domain.DeliveryMan;
import domain.Motorbike;
import domain.Staff;
import forms.AdministratorForm;
import forms.AdministratorProfileForm;
import forms.ChangeDateContractForm;
import forms.CustomerForm;
import forms.PasswordForm;
import forms.StaffForm;
import forms.StaffProfileForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class UserManagementServiceTestPositive extends AbstractTest {
	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private MotorbikeService motorbikeService;
	@Autowired
	private StaffService staffService;
	@Autowired
	private BossService bossService;
	@Autowired
	private CookService cookService;
	@Autowired
	private DeliveryManService deliveryManService;
	@Autowired
	private CustomerService customerService;
	
	// Test crear Administrator
	// Autenticado como Administrator
	@Test
	public void testCreateAdministrator() {
		authenticate("admin1");
		
		AdministratorForm administratorForm;
		Administrator administrator;
		Integer totalBefore;
		
		administratorForm = new AdministratorForm();
		totalBefore = administratorService.findAll().size();
		
		administratorForm.setUsername("admin3");
		administratorForm.setName("admin3");
		administratorForm.setSurname("suradmin3");
		administratorForm.setEmail("admin3@mail.com");
		administratorForm.setPassword("12345678");
		administratorForm.setRepeatedPass("12345678");
		
		administrator = administratorService.reconstruct(administratorForm);
		administratorService.save(administrator, administratorForm.getRepeatedPass());
		
		Assert.isTrue(totalBefore + 1 == administratorService.findAll().size());
		
		unauthenticate();
	}
	
	// Test crear Boss
	// Autenticado como Administrator
	@Test
	public void testCreateBoss() {
		authenticate("admin1");
		
		StaffForm staffForm;
		Boss boss;
		Integer totalBefore;
		
		staffForm = new StaffForm();
		
		totalBefore = bossService.findAll().size();
		
		staffForm.setUsername("boss4");
		staffForm.setName("boss4");
		staffForm.setSurname("surboss4");
		staffForm.setEmail("boss4@mail.com");
		staffForm.setPassword("12345678");
		staffForm.setRepeatedPass("12345678");
		
		staffForm.setDni("30236999Q");
		staffForm.setSsNumber("AB-1200007890");
		staffForm.setPhone("954121212");
		staffForm.setAccountNumber("12345670000034567890");
		staffForm.setBirthDate(new GregorianCalendar(1980, 5, 20).getTime());
		staffForm.setAddress("Calle de la Juana");
		
		boss = (Boss) staffService.reconstruct(staffForm, Authority.BOSS);
		staffService.save(boss, staffForm.getRepeatedPass());
		
		Assert.isTrue(totalBefore + 1 == bossService.findAll().size());
	
		unauthenticate();
	}
	
	// Test crear Cook
	// Autenticado como Administrator
	@Test
	public void testCreateCook() {
		authenticate("admin1");
		
		StaffForm staffForm;
		Cook cook;
		Integer totalBefore;
		
		staffForm = new StaffForm();
		
		totalBefore = cookService.findAll().size();
		
		staffForm.setUsername("cook3");
		staffForm.setName("cook3");
		staffForm.setSurname("surcook3");
		staffForm.setEmail("cook3@mail.com");
		staffForm.setPassword("12345678");
		staffForm.setRepeatedPass("12345678");
		
		staffForm.setDni("30236999Q");
		staffForm.setSsNumber("AB-1200007890");
		staffForm.setPhone("954121212");
		staffForm.setAccountNumber("12345670000034567890");
		staffForm.setBirthDate(new GregorianCalendar(1980, 5, 20).getTime());
		staffForm.setAddress("Calle de la Juana");
		
		cook = (Cook) staffService.reconstruct(staffForm, Authority.COOK);
		staffService.save(cook, staffForm.getRepeatedPass());
		
		Assert.isTrue(totalBefore + 1 == cookService.findAll().size());
	
		unauthenticate();
	}
	
	// Test crear DeliveryMan
	// Autenticado como Administrator
	@Test
	public void testCreateDeliveryMan() {
		authenticate("admin1");
		
		StaffForm staffForm;
		DeliveryMan deliveryMan;
		Motorbike motorbike;
		Integer totalBefore;
		
		staffForm = new StaffForm();
		motorbike = motorbikeService.findOne(18);
		
		totalBefore = deliveryManService.findAll().size();
		
		staffForm.setUsername("cook3");
		staffForm.setName("cook3");
		staffForm.setSurname("surcook3");
		staffForm.setEmail("cook3@mail.com");
		staffForm.setPassword("12345678");
		staffForm.setRepeatedPass("12345678");
		
		staffForm.setDni("30236999Q");
		staffForm.setSsNumber("AB-1200007890");
		staffForm.setPhone("954121212");
		staffForm.setAccountNumber("12345670000034567890");
		staffForm.setBirthDate(new GregorianCalendar(1980, 5, 20).getTime());
		staffForm.setAddress("Calle de la Juana");
		staffForm.setContractEndDate(new GregorianCalendar(2017, 5, 20).getTime());
		
		staffForm.setDrivingLicenseNumber("12888678Z");
		staffForm.setMotorbike(motorbike);
		
		deliveryMan = (DeliveryMan) staffService.reconstruct(staffForm, Authority.DELIVERY_MAN);
		staffService.save(deliveryMan, staffForm.getRepeatedPass());
		
		Assert.isTrue(totalBefore + 1 == deliveryManService.findAll().size());
	
		unauthenticate();			
	}
	
	// Test crear Customer
	@Test
	public void testCreateCustomer() {
		CustomerForm customerForm;
		Customer customer;
		Integer totalBefore;
		CreditCard creditCard;

		customerForm = new CustomerForm();
		creditCard = new CreditCard();
		
		totalBefore = customerService.findAllCustomers().size();
		
		customerForm.setUsername("customer3");
		customerForm.setName("customer3");
		customerForm.setSurname("surcustomer3");
		customerForm.setEmail("customer3@mail.com");
		customerForm.setPassword("12345678");
		customerForm.setRepeatedPass("12345678");
		
		customerForm.setPhone("954121212");
		customerForm.setBirthDate(new GregorianCalendar(1980, 5, 20).getTime());
		customerForm.setAddress("Calle de la Juana");
		
		customerForm.setAgree(true);
		
		creditCard.setBrandName("Visa");
		creditCard.setCVV(333);
		creditCard.setExpirationMonth(2);
		creditCard.setExpirationYear(2017);
		creditCard.setHolderName("customer3");
		creditCard.setNumber("4303017070539663");
		
		customerForm.setCreditCard(creditCard);
		
		customer = customerService.reconstruct(customerForm);
		customerService.save(customer, customerForm.getRepeatedPass(), customerForm.isAgree());
		
		Assert.isTrue(totalBefore + 1 == customerService.findAllCustomers().size());
	}
	
	// Test cambiar clave Administrator
	// Autenticado como Administrator
	@Test
	public void testChangePasswordAdministrator() {
		authenticate("admin1");
		
		Administrator administrator;
		PasswordForm passwordForm;
		Md5PasswordEncoder encoder;
		String pass = "12345678";
		
		encoder = new Md5PasswordEncoder();
		passwordForm = new PasswordForm();
		
		passwordForm.setActualPassword("admin1");
		passwordForm.setNewPassword(pass);
		passwordForm.setRepeatNewPassword(pass);
		
		administrator = administratorService.reconstructPassword(passwordForm);
		administratorService.saveProfile(administrator);
		
		administrator = administratorService.findByPrincipal();
		
		Assert.isTrue(administrator.getUserAccount().getPassword().equals(encoder.encodePassword(pass, null)));
		
		unauthenticate();
	}
	
	// Test cambiar clave Boss
	// Autenticado como Boss
	@Test
	public void testChangePasswordBoss() {
		authenticate("boss1");
			
		Staff staff;
		PasswordForm passwordForm;
		Md5PasswordEncoder encoder;
		String pass = "12345678";
			
		encoder = new Md5PasswordEncoder();
		passwordForm = new PasswordForm();
			
		passwordForm.setActualPassword("boss1");
		passwordForm.setNewPassword(pass);
		passwordForm.setRepeatNewPassword(pass);
			
		staff = staffService.reconstructPassword(passwordForm);
		staffService.saveProfile(staff);
			
		staff = staffService.findByPrincipal();
			
		Assert.isTrue(staff.getUserAccount().getPassword().equals(encoder.encodePassword(pass, null)));
		
		unauthenticate();
	}
	
	// Test cambiar clave Cook
	// Autenticado como Cook
	@Test
	public void testChangePasswordCook() {
		authenticate("cook1");
				
		Staff staff;
		PasswordForm passwordForm;
		Md5PasswordEncoder encoder;
		String pass = "12345678";
				
		encoder = new Md5PasswordEncoder();
		passwordForm = new PasswordForm();
				
		passwordForm.setActualPassword("cook1");
		passwordForm.setNewPassword(pass);
		passwordForm.setRepeatNewPassword(pass);
				
		staff = staffService.reconstructPassword(passwordForm);
		staffService.saveProfile(staff);
			
		staff = staffService.findByPrincipal();
			
		Assert.isTrue(staff.getUserAccount().getPassword().equals(encoder.encodePassword(pass, null)));
		
		unauthenticate();
	}
	
	// Test cambiar clave DeliveryMan
	// Autenticado como DeliveryMan
	@Test
	public void testChangePasswordDeliveryMan() {
		authenticate("deliveryman1");
					
		Staff staff;
		PasswordForm passwordForm;
		Md5PasswordEncoder encoder;
		String pass = "12345678";
					
		encoder = new Md5PasswordEncoder();
		passwordForm = new PasswordForm();
			
		passwordForm.setActualPassword("deliveryman1");
		passwordForm.setNewPassword(pass);
		passwordForm.setRepeatNewPassword(pass);
					
		staff = staffService.reconstructPassword(passwordForm);
		staffService.saveProfile(staff);
				
		staff = staffService.findByPrincipal();
			
		Assert.isTrue(staff.getUserAccount().getPassword().equals(encoder.encodePassword(pass, null)));
		
		unauthenticate();
	}
	
	// Test cambiar clave Customer
	// Autenticado como Customer
	@Test
	public void testChangePasswordCustomer() {
		authenticate("customer1");
					
		Customer customer1;
		PasswordForm passwordForm;
		Md5PasswordEncoder encoder;
		String pass = "12345678";
						
		encoder = new Md5PasswordEncoder();
		passwordForm = new PasswordForm();
				
		passwordForm.setActualPassword("customer1");
		passwordForm.setNewPassword(pass);
		passwordForm.setRepeatNewPassword(pass);
						
		customer1 = customerService.reconstructPassword(passwordForm);
		customerService.saveProfile(customer1);
					
		customer1 = customerService.findByPrincipal();
				
		Assert.isTrue(customer1.getUserAccount().getPassword().equals(encoder.encodePassword(pass, null)));
			
		unauthenticate();
	}
	
	// Test editar perfil Administrator
	// Autenticado como Administrator
	@Test
	public void testEditAdministrator() {
		authenticate("admin1");
		
		AdministratorProfileForm administratorProfileForm;
		Administrator administrator;
		
		administrator = administratorService.findByPrincipal();
		
		administratorProfileForm = administratorService.desreconstructProfile(administrator);
		
		administratorProfileForm.setName("Jaime");
		administratorProfileForm.setSurname("Guerrero");
		
		administrator = administratorService.reconstructProfile(administratorProfileForm);
		administratorService.saveProfile(administrator);
		
		administrator = administratorService.findByPrincipal();
		
		Assert.isTrue(administrator.getName().equals("Jaime") && administrator.getSurname().equals("Guerrero"));
		
		unauthenticate();
	}
	
	// Test editar perfil Boss
	// Autenticado como Boss
	@Test
	public void testEditBoss() {
		authenticate("boss1");
			
		StaffProfileForm staffProfileForm;
		Staff staff;
			
		staff = staffService.findByPrincipal();
			
		staffProfileForm = staffService.desreconstructProfile(staff, Authority.BOSS);
			
		staffProfileForm.setName("Jaime");
		staffProfileForm.setSurname("Guerrero");
			
		staff = staffService.reconstructProfile(staffProfileForm, Authority.BOSS);
		staffService.saveProfile(staff);
			
		staff = staffService.findByPrincipal();
			
		Assert.isTrue(staff.getName().equals("Jaime") && staff.getSurname().equals("Guerrero"));
			
		unauthenticate();
	}
	
	// Test editar perfil Cook
	// Autenticado como Cook
	@Test
	public void testEditCook() {
		authenticate("cook1");
				
		StaffProfileForm staffProfileForm;
		Staff staff;
				
		staff = staffService.findByPrincipal();
				
		staffProfileForm = staffService.desreconstructProfile(staff, Authority.COOK);
				
		staffProfileForm.setName("Jaime");
		staffProfileForm.setSurname("Guerrero");
			
		staff = staffService.reconstructProfile(staffProfileForm, Authority.COOK);
		staffService.saveProfile(staff);
				
		staff = staffService.findByPrincipal();
				
		Assert.isTrue(staff.getName().equals("Jaime") && staff.getSurname().equals("Guerrero"));
				
		unauthenticate();
	}
	
	// Test editar perfil DeliveryMan
	// Autenticado como DeliveryMan
	@Test
	public void testEditDeliveryMan() {
		authenticate("deliveryman1");
				
		StaffProfileForm staffProfileForm;
		Staff staff;
					
		staff = staffService.findByPrincipal();
					
		staffProfileForm = staffService.desreconstructProfile(staff, Authority.DELIVERY_MAN);
					
		staffProfileForm.setName("Jaime");
		staffProfileForm.setSurname("Guerrero");
				
		staff = staffService.reconstructProfile(staffProfileForm, Authority.DELIVERY_MAN);
		staffService.saveProfile(staff);
					
		staff = staffService.findByPrincipal();
					
		Assert.isTrue(staff.getName().equals("Jaime") && staff.getSurname().equals("Guerrero"));
					
		unauthenticate();
	} 
	
	// Test listar todos los Administrator
	// Autenticado como Administrator
	@Test
	public void testListAdministrators() {
		authenticate("admin1");
		
		Collection<Administrator> administrators;
		
		administrators = administratorService.findAll();
		
		Assert.isTrue(administrators.size() == 2);
		
		unauthenticate();
	}
	
	// Test listar todos los Boss
	// Autenticado como Administrator
	@Test
	public void testListBosses() {
		authenticate("admin1");
			
		Collection<Boss> bosses;
			
		bosses = bossService.findAll();
			
		Assert.isTrue(bosses.size() == 3);
			
		unauthenticate();
	}
	
	// Test listar todos los Cooks
	// Autenticado como Administrator
	@Test
	public void testListCooks() {
		authenticate("admin1");
				
		Collection<Cook> cooks;
				
		cooks = cookService.findAll();
				
		Assert.isTrue(cooks.size() == 2);
				
		unauthenticate();
	}
	
	// Test listar todos los DeliveryMen
	// Autenticado como Administrator
	@Test
	public void testListDeliveryMen() {
		authenticate("admin1");
					
		Collection<DeliveryMan> deliveryMen;
					
		deliveryMen = deliveryManService.findAll();
					
		Assert.isTrue(deliveryMen.size() == 2);
					
		unauthenticate();
	}
	
	// Test listar todos los Customers
	// Autenticado como Administrator
	@Test
	public void testListCustomers() {
		authenticate("admin1");
						
		Collection<Customer> customer;
						
		customer = customerService.findAll();
						
		Assert.isTrue(customer.size() == 2);
						
		unauthenticate();
	}
	
	// Test para cambiar fecha de fin contrato (hacerlo indefinido) de un Staff
	// Autenticado como Administrator
	@Test
	public void testChangeEndDateContractStaff() {
		authenticate("admin1");
		
		ChangeDateContractForm changeDateContractForm;
		Staff staff;
		
		changeDateContractForm = staffService.desreconstructChangeDateContract(38);
		changeDateContractForm.setIdStaff(38);
		
		changeDateContractForm.setContractEndDate(null);
			
		staff = staffService.reconstructChangeDateContract(changeDateContractForm);
		staffService.saveChangeContractDate(staff);
		
		Assert.isTrue(staff.getContractEndDate() == null);
						
		unauthenticate();
	}
	
	// Test para cambiar fecha de inicio y fin de contrato
	// Autenticado como Administrator
	@Test
	public void testChangeStartDateContractStaff() {
		authenticate("admin1");
			
		ChangeDateContractForm changeDateContractForm;
		Staff staff;
		Date start, end;
		Calendar calStart = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();
			
		changeDateContractForm = staffService.desreconstructChangeDateContract(38);
		changeDateContractForm.setIdStaff(38);
		
		start = new Date(new GregorianCalendar(2014, 9, 28).getTimeInMillis());
		end = new Date(new GregorianCalendar(2017, 8, 28).getTimeInMillis());
			
		changeDateContractForm.setContractStartDate(start);
		changeDateContractForm.setContractEndDate(end);
				
		staff = staffService.reconstructChangeDateContract(changeDateContractForm);
		staffService.saveChangeContractDate(staff);
		
		calStart.setTime(staff.getContractStartDate());
		calEnd.setTime(staff.getContractEndDate());
		
		Assert.isTrue(calStart.get(Calendar.YEAR) == 2014 && calStart.get(Calendar.MONTH) ==  9 
				&& calStart.get(Calendar.DAY_OF_MONTH) == 28);
		Assert.isTrue(calEnd.get(Calendar.YEAR) == 2017 && calEnd.get(Calendar.MONTH) ==  8 
				&& calEnd.get(Calendar.DAY_OF_MONTH) == 28);
							
		unauthenticate();
	}
	
	// Test para desactivar y activar un Customer
	// Autenticado como Administrator
	@Test
	public void testActivateDeactivateCustomer() {
		authenticate("admin1");
		
		Customer customer;
		
		customer = customerService.findOne(29);
		
		Assert.isTrue(customer.getUserAccount().getActive());
		
		// Desactivar
		customerService.deactivate(customer);
		
		customer = customerService.findOne(29);
		
		Assert.isTrue(!customer.getUserAccount().getActive());
		
		// Activar
		customerService.activate(customer);
		
		customer = customerService.findOne(29);
		
		Assert.isTrue(customer.getUserAccount().getActive());
		
		unauthenticate();
	}
}