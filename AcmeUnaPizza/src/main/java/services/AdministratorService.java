package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.PasswordCode;
import domain.Administrator;
import forms.RegistrationAdministratorForm;

@Service
@Transactional
public class AdministratorService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private AdministratorRepository administratorRepository;

	// Ancillary services -----------------------------------------------------

	@Autowired
	private ActorService actorService;
	
	// Constructor ------------------------------------------------------------
	public AdministratorService(){
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Administrator create(){
		Administrator newbye;
		
		newbye = new Administrator();
		newbye.setUserAccount(createUserAccount());
		
		return newbye;
	}

	public void save( Administrator administrator ){
		Assert.notNull( administrator );
		Assert.isTrue(actorService.isAdministrator());
		
		if(administrator.getId()==0){
			String passwordCoded;
			
			passwordCoded = PasswordCode.encode(administrator.getUserAccount().getPassword());
			administrator.getUserAccount().setPassword(passwordCoded);
		}
		
		this.administratorRepository.save( administrator );
	}

	public void delete( Administrator entity ){
		Assert.notNull(entity);
		Assert.isTrue( entity.getId()!=0 );
		Assert.isTrue( this.administratorRepository.exists(entity.getId() ));
		
		this.administratorRepository.delete( entity );
		
		Assert.isTrue( !this.administratorRepository.exists(entity.getId() ));
	}
	
	public Administrator findOne( int id ){
		Assert.isTrue( id != 0);
		
		Administrator res;
		
		res = this.administratorRepository.findOne( id );
		
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
		authority.setAuthority("ADMINISTRATOR");
		
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
	
	public Administrator convertToAdministrator(Administrator administrator,RegistrationAdministratorForm registrationAdminForm) {
		Assert.notNull(registrationAdminForm);
		Assert.notNull(administrator);

		administrator.setName(registrationAdminForm.getName());
		administrator.setSurname(registrationAdminForm.getSurname());
		administrator.setEmail(registrationAdminForm.getEmail());

		administrator.getUserAccount().setUsername(registrationAdminForm.getUsername());
		
		administrator.getUserAccount().setPassword(registrationAdminForm.getPassword());

		return administrator;
	}
	
	
	// Ancillary methods ------------------------------------------------------

}