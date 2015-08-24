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
import domain.Boss;
import domain.SalesOrder;

@Service
@Transactional
public class BossService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private BossRepository bossRepository;

	// Ancillary services -----------------------------------------------------
	
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private SalesOrderService salesOrderService;
	
	// Constructor ------------------------------------------------------------
	public BossService(){
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Boss create(){
		Boss newbye;
		
		administratorService.findByPrincipal();
		
		newbye = new Boss();
		newbye.setUserAccount(createUserAccount());
		
		return newbye;
	}

	public void save(Boss boss){
		Assert.notNull(boss);
		
		this.bossRepository.save(boss);
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
		
		Assert.notNull(res);
		
		return res;
	}
	
	public Boss findOneNotNull( int id ){
		Assert.isTrue( id != 0);
		
		Boss res;
		
		res = this.bossRepository.findOne( id );
		
		return res;
	}

	public Collection<Boss> findAll(){
		Collection<Boss> res;
		
		administratorService.findByPrincipal();
		
		res = bossRepository.findAll();
		
		return res;
	}

	// Other business methods -------------------------------------------------
	
	public UserAccount createUserAccount() {
		UserAccount result;
		Collection<Authority> authorities;
		Authority authority;
		
		authority = new Authority();
		authority.setAuthority(Authority.BOSS);
		
		authorities = new ArrayList<Authority>();
		authorities.add(authority);
		
		result = new UserAccount();
		result.setAuthorities(authorities);
		result.setActive(true);
		
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
	
	public void assignBoss(int salesOrderId) {
		Assert.isTrue(salesOrderId != 0);
		SalesOrder salesOrder;
		
		salesOrder = salesOrderService.findOneCheckBoss(salesOrderId);
		Assert.notNull(salesOrder);
		Assert.isNull(salesOrder.getBoss());
		Assert.isTrue(salesOrder.getId() == salesOrderId);
		Assert.isTrue(salesOrder.getState().equals("OPEN"));
		
		salesOrder.setBoss(findByPrincipal());
		
		salesOrderService.saveAssignBoss(salesOrder);
	}
	
	public Boss findBossBySalesOrder(int salesOrderId) {
		Assert.isTrue(salesOrderId != 0);
		Boss result;
		
		result = bossRepository.findBossBySalesOrder(salesOrderId);
		
		return result;
	}
	
	// Ancillary methods ------------------------------------------------------

}