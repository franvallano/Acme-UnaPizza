package services;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.StaffRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Boss;
import domain.Cook;
import domain.DeliveryMan;
import domain.Repair;
import domain.SalesOrder;
import domain.Staff;
import domain.Stuff;
import forms.StaffForm;

@Service
@Transactional
public class StaffService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private StaffRepository staffRepository;
	
	// Supporting services ----------------------------------------------------
	@Autowired
	private AdministratorService administratorService;
	
	// Constructors -----------------------------------------------------------
	
	public StaffService() {
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------

	public Staff create(String staffType){
		Staff newbye;
		Administrator administrator;
			
		// Comprobamos que sea un Administrador quien lo registra
		administrator = administratorService.findByPrincipal();
		Assert.notNull(administrator);
			
		newbye = new Staff();
		newbye.setUserAccount(createUserAccount(staffType));
			
		return newbye;
	}
	
	public Staff findOne(int staffId) {
		Staff result;
		 
		result = staffRepository.findOne(staffId);
		
		Assert.notNull(result);
		 
		return result;
	}
	
	public void save(Staff staff, String rPass){
		String pass;
		Md5PasswordEncoder encoder;
		encoder = new Md5PasswordEncoder();
		
		Assert.notNull(staff);
		Assert.isTrue(staff.getUserAccount().getPassword().equals(rPass));
		administratorService.findByPrincipal();
		
		pass = staff.getUserAccount().getPassword();
		pass = encoder.encodePassword(pass, null);
		staff.getUserAccount().setPassword(pass);
		
		staffRepository.save(staff);
	}
	
	public Collection<Staff> findAll(){
		Collection<Staff> result;
		
		result = staffRepository.findAll();
		
		return result;
	}
	// Other business methods -------------------------------------------------
	
	private boolean checkRole(String role) {
		boolean result;
		Collection<Authority> authorities;
		
		result = false;
		authorities = LoginService.getPrincipal().getAuthorities();
		for(Authority a : authorities){
			result= result || a.getAuthority().equals(role);
		}
		
		return result;
	}
	
	public boolean isCook() {
		boolean result;
		
		result = checkRole(Authority.COOK);
				
		return result;
	}
	
	public boolean isDeliveryMan() {
		boolean result;
		
		result = checkRole(Authority.DELIVERY_MAN);
				
		return result;
	}
	
	public boolean isBoss() {
		boolean result;
		
		result = checkRole(Authority.BOSS);
				
		return result;
	}
	
	public boolean isCustomer() {
		boolean result;
		
		result = checkRole(Authority.CUSTOMER);
				
		return result;
	}
	
	//Metodo que recibe un objeto formulario y reconstruye un objeto de dominio
	public Staff reconstruct(StaffForm staffForm, String staffType) {
		Assert.notNull(staffForm);
		Staff staffProvisional = null;
		long milliseconds;
		Date contractStartDate;
		Collection<Repair> repairs;
		Collection<SalesOrder> salesOrders;
		
		repairs = new ArrayList<Repair>();
		salesOrders = new ArrayList<SalesOrder>();
			

		if(staffType.equals("boss")) {
			staffProvisional = create(Authority.BOSS);
			Assert.notNull(staffProvisional);
			
			staffProvisional.getUserAccount().setUsername(staffForm.getUsername());
			staffProvisional.getUserAccount().setPassword(staffForm.getPassword());
			
			Collection<Stuff> stuffs;
			
			stuffs = new ArrayList<Stuff>();
				
			Boss boss = new Boss();
			
			boss.setUserAccount(staffProvisional.getUserAccount());
			boss.setName(staffForm.getName());
			boss.setSurname(staffForm.getSurname());
			boss.setEmail(staffForm.getEmail());
			
			boss.setDni(staffForm.getDni());
			boss.setSsNumber(staffForm.getSsNumber());
			boss.setAccountNumber(staffForm.getAccountNumber());
			boss.setPhone(staffForm.getPhone());
			boss.setBirthDate(staffForm.getBirthDate());
			boss.setAddress(staffForm.getAddress());
			
			milliseconds = System.currentTimeMillis();
			contractStartDate = new Date(milliseconds - 1);
			boss.setContractStartDate(contractStartDate);
			boss.setRepairs(repairs);	
			boss.setSalesOrders(salesOrders);
			boss.setStuffs(stuffs);
			
			return boss;
			
		} else if(staffType.equals("deliveryMan")) {
			staffProvisional = create(Authority.DELIVERY_MAN);
			Assert.notNull(staffProvisional);
			
			staffProvisional.getUserAccount().setUsername(staffForm.getUsername());
			staffProvisional.getUserAccount().setPassword(staffForm.getPassword());

			DeliveryMan deliveryMan = new DeliveryMan();
			
			deliveryMan.setUserAccount(staffProvisional.getUserAccount());
			deliveryMan.setName(staffForm.getName());
			deliveryMan.setSurname(staffForm.getSurname());
			deliveryMan.setEmail(staffForm.getEmail());
			
			deliveryMan.setDni(staffForm.getDni());
			deliveryMan.setSsNumber(staffForm.getSsNumber());
			deliveryMan.setAccountNumber(staffForm.getAccountNumber());
			deliveryMan.setPhone(staffForm.getPhone());
			deliveryMan.setBirthDate(staffForm.getBirthDate());
			deliveryMan.setAddress(staffForm.getAddress());
			deliveryMan.setDrivingLicenseNumber(staffForm.getDrivingLicenseNumber());
			
			milliseconds = System.currentTimeMillis();
			contractStartDate = new Date(milliseconds - 1);
			deliveryMan.setContractStartDate(contractStartDate);
			deliveryMan.setMotorbike(staffForm.getMotorbike());
			deliveryMan.setSalesOrders(salesOrders);
			
			return deliveryMan;

		} else if(staffType.equals("cook")) {
			staffProvisional = create(Authority.COOK);
			Assert.notNull(staffProvisional);
			
			staffProvisional.getUserAccount().setUsername(staffForm.getUsername());
			staffProvisional.getUserAccount().setPassword(staffForm.getPassword());
			
			Cook cook = new Cook();
			
			cook.setUserAccount(staffProvisional.getUserAccount());
			cook.setName(staffForm.getName());
			cook.setSurname(staffForm.getSurname());
			cook.setEmail(staffForm.getEmail());
			
			cook.setDni(staffForm.getDni());
			cook.setSsNumber(staffForm.getSsNumber());
			cook.setAccountNumber(staffForm.getAccountNumber());
			cook.setPhone(staffForm.getPhone());
			cook.setBirthDate(staffForm.getBirthDate());
			cook.setAddress(staffForm.getAddress());
			
			milliseconds = System.currentTimeMillis();
			contractStartDate = new Date(milliseconds - 1);
			cook.setContractStartDate(contractStartDate);
			cook.setSalesOrders(salesOrders);
			
			return cook;
			
		} else {
			Assert.notNull(staffProvisional);
		}
		
		return staffProvisional;			
	}
	
	public Staff findByPrincipal() {
		Staff result;
	 	UserAccount userAccount;
	 	
	 	userAccount = LoginService.getPrincipal();
	 	result = staffRepository.findByPrincipal(userAccount.getId());
	 	
	 	Assert.notNull(result);
	 	
	 	return result;
	 }
	
	public Collection<Staff> findAllExceptMe() {
		Collection<Staff> result;
		
		Assert.isTrue(findByPrincipal() != null);
		
		result = staffRepository.findAll();
		result.remove(findByPrincipal());
		
		return result;
	}

	public boolean rPassword(StaffForm staffForm) {
		boolean result;
		String pass;
		String rpass;
		
		pass = staffForm.getPassword();
		rpass = staffForm.getRepeatedPass();
		result = pass.equals(rpass);
		
		return result;
	}
	
	public UserAccount createUserAccount(String staffType) {
		UserAccount result;
		Collection<Authority> authorities;
		Authority authority;
		
		authority = new Authority();
		authority.setAuthority(staffType);
		
		authorities = new ArrayList<Authority>();
		authorities.add(authority);
		
		result = new UserAccount();
		result.setAuthorities(authorities);
		
		return result;
	}
}
