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
import utilities.PasswordCode;
import domain.Administrator;
import domain.Complaint;
import domain.DiscussionMessage;
import domain.PurchaseOrder;
import domain.SalesOrder;
import forms.AdministratorForm;
import forms.CustomerForm;

@Service
@Transactional
public class AdministratorService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private AdministratorRepository administratorRepository;

	// Ancillary services -----------------------------------------------------

	@Autowired
	private ActorService actorService;
	
	@Autowired
	private SalesOrderService salesOrderService;
	
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

	public void delete( Administrator entity ){
		Assert.notNull(entity);
		Assert.isTrue( entity.getId()!=0 );
		Assert.isTrue( this.administratorRepository.exists(entity.getId() ));
		
		this.administratorRepository.delete( entity );
		
		Assert.isTrue( !this.administratorRepository.exists(entity.getId() ));
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
	
	// Ancillary methods ------------------------------------------------------

}