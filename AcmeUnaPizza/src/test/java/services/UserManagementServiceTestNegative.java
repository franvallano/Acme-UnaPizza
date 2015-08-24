package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionSystemException;
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
//@Transactional
//@TransactionConfiguration(defaultRollback = false)
public class UserManagementServiceTestNegative extends AbstractTest {
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
	// Error: No autenticado como Administrator
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAdministratorNotAdmin() {
		authenticate(null);
		
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
	
	// Test crear Administrator
	// Error: Email repetido
	@Test(expected = DataIntegrityViolationException.class)
	public void testCreateAdministratorSameEmail() {
		authenticate("admin1");
		
		AdministratorForm administratorForm;
		Administrator administrator;
		Integer totalBefore;
		
		administratorForm = new AdministratorForm();
		totalBefore = administratorService.findAll().size();
		
		administratorForm.setUsername("admin3");
		administratorForm.setName("admin3");
		administratorForm.setSurname("suradmin2");
		administratorForm.setEmail("admin2@gmail.com");
		administratorForm.setPassword("12345678");
		administratorForm.setRepeatedPass("12345678");
		
		administrator = administratorService.reconstruct(administratorForm);
		administratorService.save(administrator, administratorForm.getRepeatedPass());
		
		Assert.isTrue(totalBefore + 1 == administratorService.findAll().size());
		
		unauthenticate();
	}
	
	// Test crear Boss
	// Error: Fecha de cumpleaños en futuro
	@Test(expected = TransactionSystemException.class)
	public void testCreateBossBadBirthDate() {
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
		staffForm.setBirthDate(new GregorianCalendar(2017, 5, 20).getTime());
		staffForm.setAddress("Calle de la Juana");
		
		boss = (Boss) staffService.reconstruct(staffForm, Authority.BOSS);
		staffService.save(boss, staffForm.getRepeatedPass());
		
		Assert.isTrue(totalBefore + 1 == bossService.findAll().size());
	
		unauthenticate();
	}
	
	// Test crear Cook
	// Error: Usuario repetido
	@Test(expected = IllegalArgumentException.class)
	public void testCreateCookSameEmail() {
		authenticate("cook1");
		
		StaffForm staffForm;
		Cook cook;
		Integer totalBefore;
		
		staffForm = new StaffForm();
		
		totalBefore = cookService.findAll().size();
		
		staffForm.setUsername("cook1");
		staffForm.setName("cook1");
		staffForm.setSurname("surcook1");
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
	// Error: Fecha de contrato en pasado
	@Test(expected = IllegalArgumentException.class)
	public void testCreateDeliveryManBadEndContractDate() {
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
		staffForm.setContractEndDate(new GregorianCalendar(2010, 5, 20).getTime());
		
		staffForm.setDrivingLicenseNumber("12888678Z");
		staffForm.setMotorbike(motorbike);
		
		deliveryMan = (DeliveryMan) staffService.reconstruct(staffForm, Authority.DELIVERY_MAN);
		staffService.save(deliveryMan, staffForm.getRepeatedPass());
		
		Assert.isTrue(totalBefore + 1 == deliveryManService.findAll().size());
	
		unauthenticate();			
	}
	
	
	// Test crear Customer
	// Error: Tarjeta de credito con numero incorrecto
	@Test(expected = IllegalArgumentException.class)
	public void testCreateCustomerBadCC() {
		CustomerForm customerForm;
		Customer customer;
		Integer totalBefore;
		CreditCard creditCard;

		customerForm = new CustomerForm();
		creditCard = new CreditCard();
		
		totalBefore = customerService.findAll().size();
		
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
		creditCard.setNumber("0000");
		
		customerForm.setCreditCard(creditCard);
		
		customer = customerService.reconstruct(customerForm);
		customerService.save(customer, customerForm.getRepeatedPass(), customerForm.isAgree());
		
		Assert.isTrue(totalBefore + 1 == customerService.findAll().size());
	}
	
	// Test cambiar clave Administrator
	// Error: La clave nueva y su repetida no coinciden
	@Test(expected = IllegalArgumentException.class)
	public void testChangePasswordAdministratorDifferentPassword() {
		authenticate("admin1");
		
		Administrator administrator;
		PasswordForm passwordForm;
		
		passwordForm = new PasswordForm();
		
		passwordForm.setActualPassword("admin1");
		passwordForm.setNewPassword("12345678");
		passwordForm.setRepeatNewPassword("87654321");
		
		administrator = administratorService.reconstructPassword(passwordForm);
		administratorService.saveProfile(administrator);
		
		administrator = administratorService.findByPrincipal();
		
		unauthenticate();
	}
	
	
	// Test cambiar clave Boss
	// Error: No autenticado
	@Test(expected = IllegalArgumentException.class)
	public void testChangePasswordBossNotAuth() {
		authenticate(null);
			
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
	// Error: Clave vacia
	@Test(expected = IllegalArgumentException.class)
	public void testChangePasswordCookEmptyPass() {
		authenticate(null);
				
		Staff staff;
		PasswordForm passwordForm;
		Md5PasswordEncoder encoder;
		String pass = "";
				
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
	
	// Test cambiar clave DeliveryMan
	// Error: Clave previa no coincide
	@Test(expected = IllegalArgumentException.class)
	public void testChangePasswordDeliveryManPasswordNotMatch() {
		authenticate("deliveryman1");
					
		Staff staff;
		PasswordForm passwordForm;
		Md5PasswordEncoder encoder;
		String pass = "12345678";
					
		encoder = new Md5PasswordEncoder();
		passwordForm = new PasswordForm();
			
		passwordForm.setActualPassword("claveerronea");
		passwordForm.setNewPassword(pass);
		passwordForm.setRepeatNewPassword(pass);
					
		staff = staffService.reconstructPassword(passwordForm);
		staffService.saveProfile(staff);
				
		staff = staffService.findByPrincipal();
			
		Assert.isTrue(staff.getUserAccount().getPassword().equals(encoder.encodePassword(pass, null)));
		
		unauthenticate();
	}
	
	// Test cambiar clave Customer
	// Error: Clave demasiado corta
	@Test(expected = IllegalArgumentException.class)
	public void testChangePasswordCustomerShortPassword() {
		authenticate("customer1");
					
		Customer customer1;
		PasswordForm passwordForm;
		Md5PasswordEncoder encoder;
		String pass = "";
						
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
	// Error: No autenticado como Administrator
	@Test(expected = IllegalArgumentException.class)
	public void testEditAdministratorNotAuth() {
		authenticate(null);
		
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
	// Error: Nombre vacio
	@Test(expected = TransactionSystemException.class)
	public void testEditBossEmptyName() {
		authenticate("boss1");
			
		StaffProfileForm staffProfileForm;
		Staff staff;
			
		staff = staffService.findByPrincipal();
			
		staffProfileForm = staffService.desreconstructProfile(staff, Authority.BOSS);
			
		staffProfileForm.setName("");
		staffProfileForm.setSurname("Guerrero");
			
		staff = staffService.reconstructProfile(staffProfileForm, Authority.BOSS);
		staffService.saveProfile(staff);
			
		unauthenticate();
	}
	
	// Test editar perfil Cook
	// Error: Email repetido
	@Test(expected = DataIntegrityViolationException.class)
	public void testEditCookSameEmail() {
		authenticate("cook1");
				
		StaffProfileForm staffProfileForm;
		Staff staff;
		
		staff = staffService.findByPrincipal();
				
		staffProfileForm = staffService.desreconstructProfile(staff, Authority.COOK);
				
		staffProfileForm.setName("Jaime");
		staffProfileForm.setSurname("Guerrero");
		staffProfileForm.setEmail("cook2@gmail.com");
			
		staff = staffService.reconstructProfile(staffProfileForm, Authority.COOK);
		staffService.saveProfile(staff);
				
		staff = staffService.findByPrincipal();
				
		Assert.isTrue(staff.getName().equals("Jaime") && staff.getSurname().equals("Guerrero"));
				
		unauthenticate();
	}
	
	// Test editar perfil DeliveryMan
	// Error: Licencia de conducir vacia
	@Test(expected = TransactionSystemException.class)
	public void testEditDeliveryManEmptyDrivingLicense() {
		authenticate("deliveryman1");
				
		StaffProfileForm staffProfileForm;
		Staff staff;
					
		staff = staffService.findByPrincipal();
					
		staffProfileForm = staffService.desreconstructProfile(staff, Authority.DELIVERY_MAN);
					
		staffProfileForm.setName("Jaime");
		staffProfileForm.setSurname("Guerrero");
		staffProfileForm.setDrivingLicenseNumber("");
				
		staff = staffService.reconstructProfile(staffProfileForm, Authority.DELIVERY_MAN);
		staffService.saveProfile(staff);
					
		staff = staffService.findByPrincipal();
					
		Assert.isTrue(staff.getName().equals("Jaime") && staff.getSurname().equals("Guerrero"));
					
		unauthenticate();
	}
	
	// Test listar todos los Administrator
	// Error: No autenticado como Administrator
	@Test(expected = IllegalArgumentException.class)
	public void testListAdministrators() {
		authenticate(null);

		Collection<Administrator> administrators;

		administrators = administratorService.findAll();

		Assert.isTrue(administrators.size() == 2);

		unauthenticate();
	}

	// Test listar todos los Boss
	// Error: No autenticado como Administrator
	@Test(expected = IllegalArgumentException.class)
	public void testListBosses() {
		authenticate(null);

		Collection<Boss> bosses;

		bosses = bossService.findAll();

		Assert.isTrue(bosses.size() == 3);

		unauthenticate();
	}

	// Test listar todos los Cooks
	// Error: No autenticado como Administrator
	@Test(expected = IllegalArgumentException.class)
	public void testListCooks() {
		authenticate(null);

		Collection<Cook> cooks;

		cooks = cookService.findAll();

		Assert.isTrue(cooks.size() == 2);

		unauthenticate();
	}

	// Test listar todos los DeliveryMen
	// Error: No autenticado como Administrator
	@Test(expected = IllegalArgumentException.class)
	public void testListDeliveryMen() {
		authenticate(null);

		Collection<DeliveryMan> deliveryMen;

		deliveryMen = deliveryManService.findAll();

		Assert.isTrue(deliveryMen.size() == 2);

		unauthenticate();
	}

	// Test listar todos los Customers
	// Error: No autenticado como Administrator
	@Test(expected = IllegalArgumentException.class)
	public void testListCustomers() {
		authenticate(null);

		Collection<Customer> customer;

		customer = customerService.findAll();

		Assert.isTrue(customer.size() == 2);

		unauthenticate();
	}
	
	// Test para cambiar fecha de fin contrato (hacerlo indefinido) de un Staff
	// Error: Atenticado como Boss
	@Test(expected = IllegalArgumentException.class)
	public void testChangeEndDateContractStaffAuthBoss() {
		authenticate("boss1");
		
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
	
	// Test para cambiar fecha de fin contrato (hacerlo indefinido) de un Staff
	// Error: Fecha fin de contrato e inicio vacias
	@Test(expected = TransactionSystemException.class)
	public void testChangeEndDateContractStaff() {
		authenticate("admin1");
		
		ChangeDateContractForm changeDateContractForm;
		Staff staff;
		
		changeDateContractForm = staffService.desreconstructChangeDateContract(38);
		changeDateContractForm.setIdStaff(38);
		
		changeDateContractForm.setContractStartDate(null);
		changeDateContractForm.setContractEndDate(null);
			
		staff = staffService.reconstructChangeDateContract(changeDateContractForm);
		staffService.saveChangeContractDate(staff);
		
		Assert.isTrue(staff.getContractEndDate() == null);
						
		unauthenticate();
	}
	
	// Test para cambiar fecha de inicio y fin de contrato
	// Error: Fecha inicio contrato mayor que la de fin
	@Test(expected = IllegalArgumentException.class)
	public void testChangeStartDateContractStaffBadDate() {
		authenticate("admin1");
			
		ChangeDateContractForm changeDateContractForm;
		Staff staff;
		Date start, end;
			
		changeDateContractForm = staffService.desreconstructChangeDateContract(38);
		changeDateContractForm.setIdStaff(38);
		
		start = new Date(new GregorianCalendar(2017, 9, 28).getTimeInMillis());
		end = new Date(new GregorianCalendar(2016, 8, 28).getTimeInMillis());
			
		changeDateContractForm.setContractStartDate(start);
		changeDateContractForm.setContractEndDate(end);
				
		staff = staffService.reconstructChangeDateContract(changeDateContractForm);
		staffService.saveChangeContractDate(staff);
							
		unauthenticate();
	}
	
	// Test para cambiar fecha de inicio y fin de contrato
	// Error: Fecha fin de contrato en pasado
	@Test(expected = IllegalArgumentException.class)
	public void testChangeStartDateContractStaffEndDatePast() {
		authenticate("admin1");
				
		ChangeDateContractForm changeDateContractForm;
		Staff staff;
		Date start, end;
		Calendar calStart = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();
				
		changeDateContractForm = staffService.desreconstructChangeDateContract(38);
		changeDateContractForm.setIdStaff(38);
			
		start = new Date(new GregorianCalendar(2014, 9, 28).getTimeInMillis());
		end = new Date(new GregorianCalendar(2015, 4, 28).getTimeInMillis());
				
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
	
	// Test para activar un Customer
	// Error: Customer ya activado
	@Test(expected = IllegalArgumentException.class)
	public void testActivateCustomerCustomerAlreadyActivate() {
		authenticate("admin1");
		
		Customer customer;
		
		customer = customerService.findOne(29);
		
		// Activar
		customerService.activate(customer);

		
		unauthenticate();
	}
	
	// Test para desactivar un Customer
	// Error: Customer ya desactivado
	@Test(expected = IllegalArgumentException.class)
	public void testActivateCustomerCustomerAlreadyDeactivate() {
		authenticate("admin1");
		
		Customer customer;
			
		customer = customerService.findOne(29);
			
		// Desactivar
		customerService.deactivate(customer);
		
		// Lo volvemos a desactivar
		customerService.deactivate(customer);
			
		unauthenticate();
	}
	
	// Test para desactivar y activar un Customer
	// Error: No autenticado
	@Test(expected = IllegalArgumentException.class)
	public void testActivateDeactivateCustomerNoAuth() {
		authenticate(null);
			
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