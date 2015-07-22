package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.DiscussionMessageRepository;
import domain.Administrator;
import domain.Complaint;
import domain.Customer;
import domain.DiscussionMessage;

@Service
@Transactional
public class DiscussionMessageService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private DiscussionMessageRepository discussionMessageRepository;

	// Ancillary services -----------------------------------------------------

	@Autowired
	private ActorService actorService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AdministratorService administratorService;
	
	// Constructor ------------------------------------------------------------
	public DiscussionMessageService(){
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public DiscussionMessage create(Complaint complaint){
		DiscussionMessage newbye;
		Date moment;
		//Customer customer;
		long miliseconds;
		
		//customer = customerService.findByPrincipal();
		
		checkPrincipal(complaint);
		
		miliseconds = System.currentTimeMillis() - 1000;
		moment = new Date(miliseconds);
		
		newbye = new DiscussionMessage();
		newbye.setMoment(moment);
		newbye.setCustomer(complaint.getCustomer());
		newbye.setAdministrator(complaint.getAdministrator());
		newbye.setComplaint(complaint);
		
		return newbye;
	}

	public void save( DiscussionMessage entity ){
		Assert.notNull(entity);
		
		this.discussionMessageRepository.save( entity );
	}

	public void delete( DiscussionMessage entity ){
		Assert.isTrue( entity.getId()!=0 );
		Assert.isTrue( this.discussionMessageRepository.exists(entity.getId() ));
		
		this.discussionMessageRepository.delete( entity );
		
		Assert.isTrue( !this.discussionMessageRepository.exists(entity.getId() ));
	}
	
	public DiscussionMessage findOne( int id ){
		Assert.isTrue( id != 0);
		
		DiscussionMessage res;
		
		res = this.discussionMessageRepository.findOne( id );
		
		Assert.notNull(res);
		
		return res;
 }

	public Collection<DiscussionMessage> findAll(){
		Collection<DiscussionMessage> res;
		
		res = discussionMessageRepository.findAll();
		
		return res;
	}

	// Other business methods -------------------------------------------------

	public void checkPrincipal(Complaint complaint) {
		Customer customer;
		Administrator administrator;

		if (actorService.isCustomer()) {
			customer = customerService.findByPrincipal();
			Assert.isTrue(customer.getId() == complaint.getCustomer().getId());
			Assert.isTrue(complaint.getState().compareTo("OPEN") == 0);
		} else if (actorService.isAdministrator()) {
			administrator = administratorService.findByPrincipal();
			Assert.isTrue(complaint.getAdministrator() != null);
			Assert.isTrue(administrator.getId() == complaint.getAdministrator().getId());
			Assert.isTrue(complaint.getState().compareTo("OPEN") == 0);
		}

	}
	// Ancillary methods ------------------------------------------------------

}