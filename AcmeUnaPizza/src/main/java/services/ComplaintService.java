package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ComplaintRepository;
import domain.Administrator;
import domain.Complaint;
import domain.DiscussionMessage;
import domain.SalesOrder;

@Service
@Transactional
public class ComplaintService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ComplaintRepository complaintRepository;

	// Ancillary services -----------------------------------------------------
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AdministratorService administratorService;
	
	// Constructor ------------------------------------------------------------
	public ComplaintService(){
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Complaint create(){
		Complaint result;
		List<DiscussionMessage> discussionMessages;
		Date creationMoment;
		long milisecond;
		
		Assert.isTrue(actorService.isCustomer());
		
		result = new Complaint();
		discussionMessages = new ArrayList<DiscussionMessage>();
		milisecond = System.currentTimeMillis() - 1000;
		creationMoment = new Date(milisecond);
		
		result.setCreationMoment(creationMoment);
		result.setState("OPEN");
		result.setCustomer(customerService.findByPrincipal());
		result.setDiscussionMessages(discussionMessages);
		result.setAdministrator(null);
		
		return result;
	}

	public void save( Complaint entity ){
		Assert.notNull( entity );
		
		Date creationMoment;
		long milisecond;
		
		milisecond = System.currentTimeMillis() - 1000;
		creationMoment = new Date(milisecond);
		
		entity.setCreationMoment(creationMoment);
		
		this.complaintRepository.save( entity );
	}

	public void delete( Complaint entity ){
		Assert.isTrue( entity.getId()!=0 );
		Assert.isTrue( this.complaintRepository.exists(entity.getId() ));
		Assert.isTrue( actorService.isAdministrator() ); 
		
		this.complaintRepository.delete( entity );
		
		Assert.isTrue( !this.complaintRepository.exists(entity.getId() ));
	}
	
	public Complaint findOne( int id ){
		Assert.isTrue( id != 0);
		
		Complaint res;
		
		res = this.complaintRepository.findOne( id );
		
		Assert.notNull(res);
		
		return res;
	}
	
	public Complaint findOneByPrincipalCustomer( int id ){
		Assert.isTrue( id != 0);
		
		Complaint res;
		
		res = this.complaintRepository.findOne( id );

		Assert.notNull(res);
		
		Assert.isTrue(res.getCustomer().getId() == customerService.findByPrincipal().getId());
		
		return res;
	}

	public Collection<Complaint> findAll(){
		Collection<Complaint> res;
		
		res = complaintRepository.findAll();
		
		return res;
	}

	// Other business methods -------------------------------------------------

	public Collection<Complaint> findAvailables() {
		Collection<Complaint> availables;
		
		Assert.isTrue(actorService.isAdministrator());
		
		availables = complaintRepository.findAvailables();
		
		return availables;
	}
	
	public Complaint findOneIfAvailable(int complaintId) {
		Complaint complaint;
		
		Assert.isTrue(actorService.isAdministrator());
		
		complaint = findOne(complaintId);
		
		Assert.isTrue(complaint.getAdministrator() == null);
		
		return complaint;
	}
	
	public Complaint findOneIfAdministratorOwner(int complaintId) {
		Complaint result;
		
		result = findOne(complaintId);
		
		Assert.isTrue(result.getAdministrator() != null && actorService.findByPrincipal().getId() == result.getAdministrator().getId());
		
		return result;
	}
	
	public void assignComplaint(Complaint complaint) {
		Assert.notNull(complaint);
		Assert.isTrue(actorService.isAdministrator());
		Assert.isTrue(findOneIfAvailable(complaint.getId()) != null);
		
		complaint.setAdministrator((Administrator) actorService.findByPrincipal());
	}
	
	public Collection<Complaint> findByPrincipal() {
		Collection<Complaint> result;
		
		//Assert.isTrue(actorService.isAdministrator() || actorService.isCustomer());
		
		result = complaintRepository.findByPrincipalId(actorService.findByPrincipal().getId());
		
		return result;
	}
	
	public void modifyState(Complaint complaint, String state) {
		Assert.notNull(complaint);
		Assert.isTrue(actorService.isAdministrator());
		Assert.isTrue(actorService.findByPrincipal() == complaint.getAdministrator());
		
		complaint.setState(state);
	}
	
	public void modifyStateOpen(Complaint complaint) {
		Assert.notNull(complaint);
		Assert.isTrue(!complaint.getState().equals("CLOSED"));
		
		modifyState(complaint, "OPEN");
	}
	
	public void modifyStateClosed(Complaint complaint) {
		Assert.notNull(complaint);
		modifyState(complaint, "CLOSED");
	}
	
	public void addResolution(Complaint complaint) {
		Assert.notNull(complaint);
		Assert.isTrue(actorService.isAdministrator());
		Assert.isTrue(actorService.findByPrincipal() == complaint.getAdministrator());
		Assert.isTrue(complaint.getState().equals("OPEN"));
		
		complaint.setState("CLOSED");
	}
	
	public Complaint findOneByAdministrator(int id) {
		Assert.isTrue(id != 0);
		
		Complaint res;
		
		res = this.complaintRepository.findOne(id);
		
		Assert.notNull(res);
		
		if(res.getAdministrator() != null)
			Assert.isTrue(res.getAdministrator().getId() == administratorService.findByPrincipal().getId());
		
		return res;
	}
	
	public Complaint findOneAvailableToResolution(int id) {
		Assert.isTrue(id != 0);
		
		Complaint res;
		
		res = this.complaintRepository.findOne(id);
		
		Assert.notNull(res);
		Assert.notNull(res.getAdministrator());
		Assert.isTrue(res.getAdministrator().getId() == administratorService.findByPrincipal().getId());
		
		Assert.isTrue(!res.getState().equals("CLOSED"));
		
		return res;
	}
	
	public Complaint findOneIfOwner(int complaintId) {
		Complaint result;
		
		result = findOne(complaintId);
		
		Assert.isTrue(actorService.findByPrincipal().getId() == result.getCustomer().getId() &&
				result.getAdministrator() != null);
				
		return result;
	}
	
	// Ancillary methods ------------------------------------------------------

}