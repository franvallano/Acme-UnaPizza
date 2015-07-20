package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Complaint;
import domain.Customer;
import domain.DiscussionMessage;
import domain.PurchaseOrder;
import forms.AdministratorForm;
import forms.AdministratorProfileForm;
import forms.CustomerProfileForm;
import forms.PasswordForm;

@Service
@Transactional
public class AdministratorService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private AdministratorRepository administratorRepository;

	// Ancillary services -----------------------------------------------------
	
	// Constructor ------------------------------------------------------------
	public AdministratorService(){
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Administrator create(){
		Administrator newbye;
		
		findByPrincipal();
		
		newbye = new Administrator();
		newbye.setUserAccount(createUserAccount());
		
		return newbye;
	}

	public void save(Administrator administrator, String rPass){
		String pass;
		Md5PasswordEncoder encoder;
		encoder = new Md5PasswordEncoder();
		
		Assert.notNull(administrator);
		Assert.isTrue(administrator.getUserAccount().getPassword().equals(rPass));
		
		pass = administrator.getUserAccount().getPassword();
		pass = encoder.encodePassword(pass, null);
		administrator.getUserAccount().setPassword(pass);
		
		administratorRepository.save(administrator);
	}
	
	public void saveProfile(Administrator administrator){
		Assert.notNull(administrator);
		Assert.isTrue(administrator.getUserAccount().getUsername().equals(findByPrincipal().getUserAccount().getUsername()));
	
		administratorRepository.save(administrator);
	}
	
	public Administrator findOne( int id ){
		Administrator res;
		
		res = this.administratorRepository.findOne( id );
		
		Assert.notNull(res);
		
		return res;
	}

	public Collection<Administrator> findAll(){
		Collection<Administrator> res;
		
		res = administratorRepository.findAll();
		
		return res;
	}

	// Other business methods -------------------------------------------------
	
	public UserAccount createUserAccount() {
		UserAccount result;
		Collection<Authority> authorities;
		Authority authority;
		
		authority = new Authority();
		authority.setAuthority(Authority.ADMINISTRATOR);
		
		authorities = new ArrayList<Authority>();
		authorities.add(authority);
		
		result = new UserAccount();
		result.setAuthorities(authorities);
		result.setActive(true);
		
		return result;
	}
	
	public Administrator findByPrincipal() {
	 	Administrator administrator;
	 	UserAccount userAccount;
	 	
	 	userAccount = LoginService.getPrincipal();
	 	administrator = administratorRepository.findByPrincipal(userAccount.getId());
	 	
	 	Assert.notNull(administrator);
	 	
	 	return administrator;
	}
	
	//Metodo que recibe un objeto formulario y reconstruye un objeto de dominio
	public Administrator reconstruct(AdministratorForm administratorForm) {
		Assert.notNull(administratorForm);
		Administrator administrator;
		Collection<DiscussionMessage> discussionMessages;
		Collection<PurchaseOrder> purchaseOrders;
		Collection<Complaint> complaints;
		
		administrator = create();
		
		discussionMessages = new ArrayList<DiscussionMessage>();
		purchaseOrders = new ArrayList<PurchaseOrder>();
		complaints = new ArrayList<Complaint>();
		
		administrator.getUserAccount().setUsername(administratorForm.getUsername());
		administrator.getUserAccount().setPassword(administratorForm.getPassword());
		
		administrator.setName(administratorForm.getName());
		administrator.setSurname(administratorForm.getSurname());
		administrator.setEmail(administratorForm.getEmail());
		
		administrator.setDiscussionMessages(discussionMessages);
		administrator.setPurchaseOrders(purchaseOrders);
		administrator.setComplaints(complaints);
		
		return administrator;
		
	}
	
	public Administrator reconstructProfile(AdministratorProfileForm administratorProfileForm) {
		Assert.notNull(administratorProfileForm);
		Administrator administrator;
		
		administrator = findByPrincipal();
		
		Assert.isTrue(administrator.getUserAccount().getUsername().equals(administratorProfileForm.getUsername()));
		
		administrator.setName(administratorProfileForm.getName());
		administrator.setSurname(administratorProfileForm.getSurname());
		administrator.setEmail(administratorProfileForm.getEmail());
		
		return administrator;
		
	}
	
	public AdministratorProfileForm desreconstructProfile(Administrator administrator) {
		Assert.notNull(administrator);
		AdministratorProfileForm administratorProfileForm;
		
		administratorProfileForm = new AdministratorProfileForm();
		
		administratorProfileForm.setUsername(administrator.getUserAccount().getUsername());
		administratorProfileForm.setName(administrator.getName());
		administratorProfileForm.setSurname(administrator.getSurname());
		administratorProfileForm.setEmail(administrator.getEmail());
		
		return administratorProfileForm;
	}
	
	public AdministratorForm desreconstruct(Administrator administrator){
		Assert.notNull(administrator);
		
		AdministratorForm administratorForm = new AdministratorForm();
		
		if(administrator.getId() != 0) {
			administratorForm.setUsername(administrator.getUserAccount().getUsername());
			administratorForm.setPassword(administrator.getUserAccount().getPassword());
			administratorForm.setRepeatedPass(administrator.getUserAccount().getPassword());
			administratorForm.setName(administrator.getName());
			administratorForm.setSurname(administrator.getSurname());
			administratorForm.setEmail(administrator.getEmail());
		}
		
		return administratorForm;
	}
	
	
	public boolean rPassword(AdministratorForm administratorForm) {
		boolean result;
		String pass;
		String rpass;
		
		pass = administratorForm.getPassword();
		rpass = administratorForm.getRepeatedPass();
		result = pass.equals(rpass);
		
		return result;
	}
	
	public Administrator reconstructPassword(PasswordForm passwordForm) {
		Assert.notNull(passwordForm);
		Assert.isTrue(passwordForm.getNewPassword().equals(passwordForm.getRepeatNewPassword()));
		Administrator administrator;
		Md5PasswordEncoder encoder;

		administrator = findByPrincipal();
		
		encoder = new Md5PasswordEncoder();
		
		Assert.isTrue(administrator.getUserAccount().getPassword().equals(encoder.encodePassword(passwordForm.getActualPassword(), null)));
		
		administrator.getUserAccount().setPassword(encoder.encodePassword(passwordForm.getNewPassword(), null));
		
		return administrator;
	}
	
	// Ancillary methods ------------------------------------------------------

}