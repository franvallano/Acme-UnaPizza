package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.DeliveryManRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.PasswordCode;
import domain.DeliveryMan;
import domain.Garage;
import domain.Motorbike;

@Service
@Transactional
public class DeliveryManService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private DeliveryManRepository deliveryManRepository;

	// Ancillary services -----------------------------------------------------

	@Autowired
	private ActorService actorService;
	
	// Constructor ------------------------------------------------------------
	public DeliveryManService(){
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public DeliveryMan create(){
		DeliveryMan newbye;
		
		newbye = new DeliveryMan();
		newbye.setUserAccount(createUserAccount());
		newbye.setMotorbike(new Motorbike());
		
		return newbye;
	}

	public void save( DeliveryMan deliveryMan ){
		Assert.notNull( deliveryMan );
		Assert.isTrue(actorService.isDeliveryMan());
		
		if(deliveryMan.getId()==0){
			String passwordCoded;
			
			passwordCoded = PasswordCode.encode(deliveryMan.getUserAccount().getPassword());
			deliveryMan.getUserAccount().setPassword(passwordCoded);
		}
		
		this.deliveryManRepository.save( deliveryMan );
	}

	public void delete( DeliveryMan entity ){
		Assert.notNull(entity);
		Assert.isTrue( entity.getId()!=0 );
		Assert.isTrue( this.deliveryManRepository.exists(entity.getId() ));
		
		this.deliveryManRepository.delete( entity );
		
		Assert.isTrue( !this.deliveryManRepository.exists(entity.getId() ));
	}
	
	public DeliveryMan findOne( int id ){
		Assert.isTrue( id != 0);
		
		DeliveryMan res;
		
		res = this.deliveryManRepository.findOne( id );
		
		Assert.notNull(res);
		
		return res;
	}

	public Collection<DeliveryMan> findAll(){
		Collection<DeliveryMan> res;
		
		res = deliveryManRepository.findAll();
		
		return res;
	}

	// Other business methods -------------------------------------------------
	
	public UserAccount createUserAccount() {
		UserAccount result;
		Collection<Authority> authorities;
		Authority authority;
		
		authority = new Authority();
		authority.setAuthority("DELIVERYMAN");
		
		authorities = new ArrayList<Authority>();
		authorities.add(authority);
		
		result = new UserAccount();
		result.setAuthorities(authorities);
		
		return result;
	}
	
	public DeliveryMan findByPrincipal() {
	 	DeliveryMan deliveryMan;
	 	UserAccount userAccount;
	 	
	 	userAccount = LoginService.getPrincipal();
	 	deliveryMan = deliveryManRepository.findByPrincipal(userAccount.getId());
	 	
	 	Assert.notNull(deliveryMan);
	 	
	 	return deliveryMan;
	}
	
	public Collection<DeliveryMan> findDeliveryManMoreOrders() {
		Collection<DeliveryMan> result;
		
		result = deliveryManRepository.findDeliveryManMoreOrders();
		
		Assert.notNull(result);
		
		return result;
	}
	
	public DeliveryMan findDeliveryManByMotorbike(int motorbikeId) {
		DeliveryMan result;
		
		result = deliveryManRepository.findDeliveryManByMotorbike(motorbikeId);
		
		return result;
	}
	
	// Ancillary methods ------------------------------------------------------

}