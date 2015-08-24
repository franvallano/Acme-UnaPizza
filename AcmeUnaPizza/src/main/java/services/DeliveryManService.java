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
import domain.DeliveryMan;
import domain.Motorbike;
import domain.SalesOrder;

@Service
@Transactional
public class DeliveryManService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private DeliveryManRepository deliveryManRepository;
	

	// Ancillary services -----------------------------------------------------
	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private SalesOrderService salesOrderService;
	
	// Constructor ------------------------------------------------------------
	public DeliveryManService(){
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public DeliveryMan create(){
		DeliveryMan newbye;
		
		administratorService.findByPrincipal();
		
		newbye = new DeliveryMan();
		newbye.setUserAccount(createUserAccount());
		newbye.setMotorbike(new Motorbike());
		
		return newbye;
	}

	public void save(DeliveryMan deliveryMan){
		Assert.notNull(deliveryMan);
		// Lo comento porque lo usaremos para borrar una moto ya que el registro y la edicion se hace
		// desde un Form
		//Assert.isTrue(actorService.isDeliveryMan());
		administratorService.findByPrincipal();
		
		deliveryManRepository.save(deliveryMan);
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
	
	public DeliveryMan findOneNotNull( int id ){
		Assert.isTrue( id != 0);
		
		DeliveryMan res;
		
		res = this.deliveryManRepository.findOne( id );
		
		return res;
	}

	public Collection<DeliveryMan> findAll(){
		Collection<DeliveryMan> res;
		
		administratorService.findByPrincipal();
		
		res = deliveryManRepository.findAll();
		
		return res;
	}

	// Other business methods -------------------------------------------------
	
	public UserAccount createUserAccount() {
		UserAccount result;
		Collection<Authority> authorities;
		Authority authority;
		
		authority = new Authority();
		authority.setAuthority(Authority.DELIVERY_MAN);
		
		authorities = new ArrayList<Authority>();
		authorities.add(authority);
		
		result = new UserAccount();
		result.setAuthorities(authorities);
		result.setActive(true);
		
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
	
	public void finishSalesOrder(int salesOrderId) {
		Assert.isTrue(salesOrderId != 0);
		SalesOrder salesOrder;
		
		salesOrder = salesOrderService.findOneCheckDeliveryMan(salesOrderId, "ONITSWAY");
		
		Assert.notNull(salesOrder);
		Assert.isTrue(salesOrder.getDeliveryMan().getId() == findByPrincipal().getId());
		
		salesOrder.setState("DELIVERED");
		
		salesOrderService.saveByDeliveryMan(salesOrder, true);
	}
	
	public void prepared(int salesOrderId) {
		Assert.isTrue(salesOrderId != 0);
		SalesOrder salesOrder;
		
		salesOrder = salesOrderService.findOneCheckCook(salesOrderId);
		Assert.notNull(salesOrder);
		Assert.isTrue(salesOrder.getCook().getId() == findByPrincipal().getId());
		
		salesOrder.setState("PREPARED");
		
		salesOrderService.saveByCook(salesOrder);
	}
	
	public void onItsWay(int salesOrderId) {
		Assert.isTrue(salesOrderId != 0);
		SalesOrder salesOrder;
		
		salesOrder = salesOrderService.findOneCheckDeliveryMan(salesOrderId, "PREPARED");
		Assert.notNull(salesOrder);
		Assert.isNull(salesOrder.getDeliveryMan());
		
		// Comprobamos no tenga pedidos asignados en estado de ONITSWAY
		Assert.isTrue(salesOrderService.findTotalOnItsWayByDeliveryMan(findByPrincipal().getId()) == 0);
		
		salesOrder.setState("ONITSWAY");
		salesOrder.setDeliveryMan(findByPrincipal());
		salesOrderService.saveByDeliveryMan(salesOrder, false);
	}
	
	// Ancillary methods ------------------------------------------------------

}