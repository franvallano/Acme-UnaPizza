package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CookRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.PasswordCode;
import domain.Cook;
import domain.SalesOrder;

@Service
@Transactional
public class CookService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CookRepository cookRepository;

	// Ancillary services -----------------------------------------------------
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private SalesOrderService salesOrderService;

	@Autowired
	private ActorService actorService;
	
	// Constructor ------------------------------------------------------------
	public CookService(){
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Cook create(){
		Cook newbye;
		
		administratorService.findByPrincipal();
		
		newbye = new Cook();
		newbye.setUserAccount(createUserAccount());
		
		return newbye;
	}

	public void save( Cook cook ){
		Assert.notNull( cook );
		Assert.isTrue(actorService.isCook());
		administratorService.findByPrincipal();
		
		if(cook.getId()==0){
			String passwordCoded;
			
			passwordCoded = PasswordCode.encode(cook.getUserAccount().getPassword());
			cook.getUserAccount().setPassword(passwordCoded);
		}
		
		this.cookRepository.save( cook );
	}

	public void delete( Cook entity ){
		Assert.notNull(entity);
		Assert.isTrue( entity.getId()!=0 );
		Assert.isTrue( this.cookRepository.exists(entity.getId() ));
		
		this.cookRepository.delete( entity );
		
		Assert.isTrue( !this.cookRepository.exists(entity.getId() ));
	}
	
	public Cook findOne( int id ){
		Assert.isTrue( id != 0);
		
		Cook res;
		
		res = this.cookRepository.findOne( id );
		
		Assert.notNull(res);
		
		return res;
	}
	
	public Cook findOneNotNull( int id ){
		Assert.isTrue( id != 0);
		
		Cook res;
		
		res = this.cookRepository.findOne( id );
		
		return res;
	}

	public Collection<Cook> findAll(){
		Collection<Cook> res;
		
		administratorService.findByPrincipal();
		
		res = cookRepository.findAll();
		
		return res;
	}

	// Other business methods -------------------------------------------------
	
	public UserAccount createUserAccount() {
		UserAccount result;
		Collection<Authority> authorities;
		Authority authority;
		
		authority = new Authority();
		authority.setAuthority(Authority.COOK);
		
		authorities = new ArrayList<Authority>();
		authorities.add(authority);
		
		result = new UserAccount();
		result.setAuthorities(authorities);
		result.setActive(true);
		
		return result;
	}
	
	public Cook findByPrincipal() {
	 	Cook cook;
	 	UserAccount userAccount;
	 	
	 	userAccount = LoginService.getPrincipal();
	 	cook = cookRepository.findByPrincipal(userAccount.getId());
	 	
	 	Assert.notNull(cook);
	 	
	 	return cook;
	 }
	
	public Collection<Cook> findCookMoreOrders() {
		Collection<Cook> result;
		
		result = cookRepository.findCookMoreOrders();
		
		Assert.notNull(result);
		
		return result;
	}
	
	public void assignAndCooking(int salesOrderId) {
		Assert.isTrue(salesOrderId != 0);
		SalesOrder salesOrder;
		
		salesOrder = salesOrderService.findOneCheckCook(salesOrderId);
		Assert.notNull(salesOrder);
		Assert.isNull(salesOrder.getCook());
		Assert.isTrue(salesOrder.getId() == salesOrderId);
		Assert.isTrue(salesOrder.getState().equals("OPEN"));
		
		salesOrder.setCook(findByPrincipal());
		salesOrder.setState("COOKING");
		
		salesOrderService.saveByCook(salesOrder);
	}
	
	public void prepared(int salesOrderId) {
		Assert.isTrue(salesOrderId != 0);
		SalesOrder salesOrder;
		
		salesOrder = salesOrderService.findOneCheckCook(salesOrderId);
		Assert.notNull(salesOrder);
		Assert.isTrue(salesOrder.getCook().getId() == findByPrincipal().getId());
		Assert.isTrue(salesOrder.getId() == salesOrderId);
		Assert.isTrue(salesOrder.getState().equals("COOKING"));
		
		salesOrder.setState("PREPARED");
		
		salesOrderService.saveByCook(salesOrder);
	}
	
	// Ancillary methods ------------------------------------------------------

}