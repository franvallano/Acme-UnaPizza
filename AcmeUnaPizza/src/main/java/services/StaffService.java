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
import forms.ChangeDateContractForm;
import forms.PasswordForm;
import forms.StaffForm;
import forms.StaffProfileForm;

@Service
@Transactional
public class StaffService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private StaffRepository staffRepository;
	
	// Supporting services ----------------------------------------------------
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private DeliveryManService deliveryManService;
	
	@Autowired
	private BossService bossService;
	
	@Autowired
	private CookService cookService;
	
	@Autowired
	private MotorbikeService motorbikeService;
	
	// Constructors -----------------------------------------------------------
	
	public StaffService() {
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------

	public Staff create(){
		Staff newbye;
		Administrator administrator;
			
		// Comprobamos que sea un Administrador quien lo registra
		administrator = administratorService.findByPrincipal();
		Assert.notNull(administrator);
			
		newbye = new Staff();
			
		return newbye;
	}
	
	public Staff findOne(int staffId) {
		Staff result = null;
		Boss boss;
		DeliveryMan deliveryMan;
		Cook cook;
		
		boss = bossService.findOneNotNull(staffId);
		
		if(boss == null) {
			deliveryMan = deliveryManService.findOneNotNull(staffId);
			if(deliveryMan == null) {
				cook = cookService.findOneNotNull(staffId);
				
				if(cook != null)
					result = (Cook) cook;
			} else {
				result = (DeliveryMan) deliveryMan;
			}
		} else {
			result = (Boss) boss;
		}
		
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
		Calendar lastHourMinute = Calendar.getInstance();
		
		repairs = new ArrayList<Repair>();
		salesOrders = new ArrayList<SalesOrder>();
		
		staffProvisional = create();
		Assert.notNull(staffProvisional);

		if(staffType.equals(Authority.BOSS)) {
			staffProvisional.setUserAccount(createUserAccount(Authority.BOSS));
			staffProvisional.getUserAccount().setUsername(staffForm.getUsername());
			staffProvisional.getUserAccount().setPassword(staffForm.getPassword());
			staffProvisional.getUserAccount().setActive(true);
			
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
			
			// Si tiene fecha de fin de contrato
			if(staffForm.getContractEndDate() != null) {
				lastHourMinute.setTimeInMillis(staffForm.getContractEndDate().getTime());
				lastHourMinute.set(Calendar.HOUR_OF_DAY, 23);
				lastHourMinute.set(Calendar.MINUTE, 59);
				lastHourMinute.set(Calendar.SECOND, 59);
				
				boss.setContractEndDate(new Date(lastHourMinute.getTimeInMillis()));
				
				Assert.isTrue(boss.getContractStartDate().before(boss.getContractEndDate()));
			}
			
			return boss;
			
		} else if(staffType.equals(Authority.DELIVERY_MAN)) {
			staffProvisional.setUserAccount(createUserAccount(Authority.DELIVERY_MAN));
			staffProvisional.getUserAccount().setUsername(staffForm.getUsername());
			staffProvisional.getUserAccount().setPassword(staffForm.getPassword());
			staffProvisional.getUserAccount().setActive(true);
			
			Assert.notNull(staffForm.getMotorbike());
			
			// Comprobamos que la moto no este en uso
			Assert.isTrue(motorbikeService.findFreeMotorbikes().contains(staffForm.getMotorbike()));

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
			deliveryMan.setMotorbike(staffForm.getMotorbike());
			
			milliseconds = System.currentTimeMillis();
			contractStartDate = new Date(milliseconds - 1);
			deliveryMan.setContractStartDate(contractStartDate);
			deliveryMan.setMotorbike(staffForm.getMotorbike());
			deliveryMan.setSalesOrders(salesOrders);
			
			// Si tiene fecha de fin de contrato
			if(staffForm.getContractEndDate() != null) {
				lastHourMinute.setTimeInMillis(staffForm.getContractEndDate().getTime());
				lastHourMinute.set(Calendar.HOUR_OF_DAY, 23);
				lastHourMinute.set(Calendar.MINUTE, 59);
				lastHourMinute.set(Calendar.SECOND, 59);
						
				deliveryMan.setContractEndDate(new Date(lastHourMinute.getTimeInMillis()));
						
				Assert.isTrue(deliveryMan.getContractStartDate().before(deliveryMan.getContractEndDate()));
			}
			
			return deliveryMan;

		} else if(staffType.equals(Authority.COOK)) {
			staffProvisional.setUserAccount(createUserAccount(Authority.COOK));
			staffProvisional.getUserAccount().setUsername(staffForm.getUsername());
			staffProvisional.getUserAccount().setPassword(staffForm.getPassword());
			staffProvisional.getUserAccount().setActive(true);
			
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
			
			// Si tiene fecha de fin de contrato
			if(staffForm.getContractEndDate() != null) {
				lastHourMinute.setTimeInMillis(staffForm.getContractEndDate().getTime());
				lastHourMinute.set(Calendar.HOUR_OF_DAY, 23);
				lastHourMinute.set(Calendar.MINUTE, 59);
				lastHourMinute.set(Calendar.SECOND, 59);
									
				cook.setContractEndDate(new Date(lastHourMinute.getTimeInMillis()));
									
				Assert.isTrue(cook.getContractStartDate().before(cook.getContractEndDate()));
			}
			
			return cook;
			
		} else {
			Assert.notNull(staffProvisional);
		}
		
		return staffProvisional;			
	}
	
	public Staff reconstructProfile(StaffProfileForm staffProfileForm, String staffType) {
		Assert.notNull(staffProfileForm);
		Staff staffProvisional = null;
		
		if(staffType.equals(Authority.BOSS)) {
			Boss boss;
			
			boss = bossService.findByPrincipal();
			
			Assert.isTrue(boss.getUserAccount().getUsername().equals(staffProfileForm.getUsername()));
			
			boss.setName(staffProfileForm.getName());
			boss.setSurname(staffProfileForm.getSurname());
			boss.setEmail(staffProfileForm.getEmail());
			
			boss.setDni(staffProfileForm.getDni());
			boss.setSsNumber(staffProfileForm.getSsNumber());
			boss.setPhone(staffProfileForm.getPhone());
			boss.setBirthDate(staffProfileForm.getBirthDate());
			boss.setAddress(staffProfileForm.getAddress());
			boss.setAccountNumber(staffProfileForm.getAccountNumber());
			
			return boss;
		} else if(staffType.equals(Authority.DELIVERY_MAN)) {
			DeliveryMan deliveryMan;
			
			deliveryMan = deliveryManService.findByPrincipal();
			
			Assert.isTrue(deliveryMan.getUserAccount().getUsername().equals(staffProfileForm.getUsername()));
			
			deliveryMan.setName(staffProfileForm.getName());
			deliveryMan.setSurname(staffProfileForm.getSurname());
			deliveryMan.setEmail(staffProfileForm.getEmail());
			
			deliveryMan.setDni(staffProfileForm.getDni());
			deliveryMan.setSsNumber(staffProfileForm.getSsNumber());
			deliveryMan.setPhone(staffProfileForm.getPhone());
			deliveryMan.setBirthDate(staffProfileForm.getBirthDate());
			deliveryMan.setAddress(staffProfileForm.getAddress());
			deliveryMan.setAccountNumber(staffProfileForm.getAccountNumber());
			deliveryMan.setDrivingLicenseNumber(staffProfileForm.getDrivingLicenseNumber());
			
			return deliveryMan;
		} else if(staffType.equals(Authority.COOK)) {
			Cook cook;
			
			cook = cookService.findByPrincipal();

			Assert.isTrue(cook.getUserAccount().getUsername().equals(staffProfileForm.getUsername()));
			
			cook.setName(staffProfileForm.getName());
			cook.setSurname(staffProfileForm.getSurname());
			cook.setEmail(staffProfileForm.getEmail());
			
			cook.setDni(staffProfileForm.getDni());
			cook.setSsNumber(staffProfileForm.getSsNumber());
			cook.setPhone(staffProfileForm.getPhone());
			cook.setBirthDate(staffProfileForm.getBirthDate());
			cook.setAddress(staffProfileForm.getAddress());
			cook.setAccountNumber(staffProfileForm.getAccountNumber());
			
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
	
	public Staff reconstructPassword(PasswordForm passwordForm) {
		Assert.notNull(passwordForm);
		Assert.isTrue(passwordForm.getNewPassword().equals(passwordForm.getRepeatNewPassword()));
		Staff staff;
		Md5PasswordEncoder encoder;

		staff = findByPrincipal();
		
		encoder = new Md5PasswordEncoder();
		
		Assert.isTrue(staff.getUserAccount().getPassword().equals(encoder.encodePassword(passwordForm.getActualPassword(), null)));
		
		staff.getUserAccount().setPassword(encoder.encodePassword(passwordForm.getNewPassword(), null));
		
		return staff;
	}
	
	public void saveProfile(Staff staff){
		Assert.notNull(staff);
		Assert.isTrue(staff.getUserAccount().getUsername().equals(findByPrincipal().getUserAccount().getUsername()));
	
		staffRepository.save(staff);
	}
	
	public StaffProfileForm desreconstructProfile(Staff staff, String authority) {
		Assert.notNull(staff);
		StaffProfileForm staffProfileForm;
		
		staffProfileForm = new StaffProfileForm();
		
		staffProfileForm.setUsername(staff.getUserAccount().getUsername());
		staffProfileForm.setName(staff.getName());
		staffProfileForm.setSurname(staff.getSurname());
		staffProfileForm.setEmail(staff.getEmail());
		staffProfileForm.setPhone(staff.getPhone());
		staffProfileForm.setBirthDate(staff.getBirthDate());
		staffProfileForm.setAddress(staff.getAddress());
		staffProfileForm.setDni(staff.getDni());
		staffProfileForm.setSsNumber(staff.getSsNumber());
		staffProfileForm.setAccountNumber(staff.getAccountNumber());
		
		if(authority.equals(Authority.DELIVERY_MAN))
			staffProfileForm.setDrivingLicenseNumber(deliveryManService.findByPrincipal().getDrivingLicenseNumber());
		
		return staffProfileForm;
	}
	
	public void checkHasContract(int userAccountId) {
		Staff staff;
		
		// Busco el staff con contrato en vigor
		staff = staffRepository.checkHasContract(userAccountId);
		
		if(staff == null) {
			// Si no tiene contrato en vigor buscamos para cancelarlo si no esta cancelado ya
			staff = staffRepository.findOneByUserAccount(userAccountId);
			Assert.notNull(staff);
			
			if(staff.getUserAccount().getActive()) {
				staff.getUserAccount().setActive(false);
				
				staffRepository.save(staff);
			}
		}
	}
	
	public Staff findOneByUserAccount(int userAccountId) {
		Staff result;
		
		result = staffRepository.findOneByUserAccount(userAccountId);
		
		return result;
	}
	
	public ChangeDateContractForm desreconstructChangeDateContract(int staffId) {
		ChangeDateContractForm changeDateContractForm;
		Staff staff;
		administratorService.findByPrincipal();
		
		staff = findOne(staffId);
		
		//staff = staffRepository.findOneByUserAccount(staff.);
		
		changeDateContractForm = new ChangeDateContractForm();
		
		changeDateContractForm.setContractStartDate(staff.getContractStartDate());
		if(staff.getContractEndDate() != null)
			changeDateContractForm.setContractEndDate(staff.getContractEndDate());
		else
			changeDateContractForm.setContractEndDate(null);
		
		return changeDateContractForm;
	}
	
	public Staff reconstructChangeDateContract(ChangeDateContractForm changeDateContractForm) {
		Assert.notNull(changeDateContractForm);
		Staff staff;
		
		administratorService.findByPrincipal();
		
		staff = findOne(changeDateContractForm.getIdStaff());
		
		if(changeDateContractForm.getContractEndDate() != null) {
			Calendar endContract = Calendar.getInstance();
			Date dateEndContract;
			
			endContract.setTimeInMillis(changeDateContractForm.getContractEndDate().getTime());
			endContract.set(Calendar.HOUR_OF_DAY, 23);
			endContract.set(Calendar.MINUTE, 59);
			endContract.set(Calendar.SECOND, 59);
			dateEndContract = new Date(endContract.getTimeInMillis());
			
			staff.setContractEndDate(dateEndContract);
			
			Assert.isTrue(staff.getContractEndDate().after(new Date(System.currentTimeMillis())));
			Assert.isTrue(changeDateContractForm.getContractStartDate().before(staff.getContractEndDate()));
		} else {
			staff.setContractEndDate(null);
		}
		
		Assert.notNull(staff);
		
		staff.setContractStartDate(changeDateContractForm.getContractStartDate());
		
		return staff;
	}
	
	public void saveChangeContractDate(Staff staff){
		Assert.notNull(staff);
		
		administratorService.findByPrincipal();
	
		staffRepository.save(staff);
	}
}
