package services;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.StaffRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Staff;

@Service
@Transactional
public class StaffService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private StaffRepository staffRepository;
	
	// Supporting services ----------------------------------------------------
	
	// Constructors -----------------------------------------------------------
	
	public StaffService() {
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	public Staff findOne(int actorId) {
		Staff result;
		 
		result = staffRepository.findOne(actorId);
		 
		return result;
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

}
