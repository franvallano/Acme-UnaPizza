package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BossRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.PasswordCode;
import domain.Boss;

@Service
@Transactional
public class BossService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private BossRepository bossRepository;

	// Ancillary services -----------------------------------------------------

	@Autowired
	private ActorService actorService;
	
	// Constructor ------------------------------------------------------------
	public BossService(){
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Boss create(){
		Boss newbye;
		
		newbye = new Boss();
		newbye.setUserAccount(createUserAccount());
		
		return newbye;
	}

	public void save( Boss boss ){
		Assert.notNull( boss );
		Assert.isTrue(actorService.isBoss());
		
		if(boss.getId()==0){
			String passwordCoded;
			
			passwordCoded = PasswordCode.encode(boss.getUserAccount().getPassword());
			boss.getUserAccount().setPassword(passwordCoded);
		}
		
		this.bossRepository.save( boss );
	}

	public void delete( Boss entity ){
		Assert.notNull(entity);
		Assert.isTrue( entity.getId()!=0 );
		Assert.isTrue( this.bossRepository.exists(entity.getId() ));
		
		this.bossRepository.delete( entity );
		
		Assert.isTrue( !this.bossRepository.exists(entity.getId() ));
	}
	
	public Boss findOne( int id ){
		Assert.isTrue( id != 0);
		
		Boss res;
		
		res = this.bossRepository.findOne( id );
		
		return res;
	}

	public Collection<Boss> findAll(){
		Collection<Boss> res;
		
		res = bossRepository.findAll();
		
		return res;
	}

	// Other business methods -------------------------------------------------
	
	public UserAccount createUserAccount() {
		UserAccount result;
		Collection<Authority> authorities;
		Authority authority;
		
		authority = new Authority();
		authority.setAuthority("BOSS");
		
		authorities = new ArrayList<Authority>();
		authorities.add(authority);
		
		result = new UserAccount();
		result.setAuthorities(authorities);
		
		return result;
	}
	
	public Boss findByPrincipal() {
	 	Boss boss;
	 	UserAccount userAccount;
	 	
	 	userAccount = LoginService.getPrincipal();
	 	boss = bossRepository.findByPrincipal(userAccount.getId());
	 	
	 	Assert.notNull(boss);
	 	
	 	return boss;
	 }
	
	// Ancillary methods ------------------------------------------------------

}