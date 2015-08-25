package services;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Complaint;
import domain.DiscussionMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
//@Transactional
//@TransactionConfiguration(defaultRollback = false)
public class DiscussionMessageServiceTestNegative extends AbstractTest {
	@Autowired
	private ComplaintService complaintService;
	@Autowired
	private DiscussionMessageService discussionMessageService;
	
	// Test agregar mensaje a una queja
	// Error: Queja ya cerrada
	@Test(expected = IllegalArgumentException.class)
	public void testAddDiscussionMessageAlreadyClosed() {
		authenticate("admin1");
		
		Complaint complaint;
		DiscussionMessage discussionMessage;
		Integer totalBefore;
		
		complaint = complaintService.findOneIfAvailable(31);
		
		complaintService.assignComplaint(complaint);
		
		unauthenticate();
		
		authenticate("customer2");
		
		totalBefore = discussionMessageService.findAll().size();
		
		complaint = complaintService.findOneIfOwner(complaint.getId());
		
		discussionMessage = discussionMessageService.create(complaint);
		
		discussionMessage.setMessage("Responderme rápido por favor");
		
		discussionMessageService.save(discussionMessage);
		
		Assert.isTrue(totalBefore + 1 == discussionMessageService.findAll().size());
		
		unauthenticate();
	}
	
	// Test agregar mensaje a una queja
	// Error: No autenticado
	@Test(expected = IllegalArgumentException.class)
	public void testAddDiscussionMessageNotAuth() {
		authenticate(null);
			
		Complaint complaint;
		DiscussionMessage discussionMessage;
		Integer totalBefore;
			
		complaint = complaintService.findOneIfAvailable(32);
			
		complaintService.assignComplaint(complaint);
			
		unauthenticate();
			
		authenticate("customer2");
			
		totalBefore = discussionMessageService.findAll().size();
			
		complaint = complaintService.findOneIfOwner(complaint.getId());
			
		discussionMessage = discussionMessageService.create(complaint);
			
		discussionMessage.setMessage("Responderme rápido por favor");
			
		discussionMessageService.save(discussionMessage);
			
		Assert.isTrue(totalBefore + 1 == discussionMessageService.findAll().size());
			
		unauthenticate();
	}
	
	// Test agregar mensaje a una queja
	// Error: Mensaje vacio
	@Test(expected = IllegalArgumentException.class)
	public void testAddDiscussionMessageEmpty() {
		authenticate(null);
			
		Complaint complaint;
		DiscussionMessage discussionMessage;
		Integer totalBefore;
				
		complaint = complaintService.findOneIfAvailable(32);
				
		complaintService.assignComplaint(complaint);
				
		unauthenticate();
				
		authenticate("customer2");
				
		totalBefore = discussionMessageService.findAll().size();
				
		complaint = complaintService.findOneIfOwner(complaint.getId());
				
		discussionMessage = discussionMessageService.create(complaint);
				
		discussionMessageService.save(discussionMessage);
				
		Assert.isTrue(totalBefore + 1 == discussionMessageService.findAll().size());
				
		unauthenticate();
	}
	
	// Test agregar mensaje a una queja
	// Error: Queja no asignada a un Administrator
	@Test(expected = IllegalArgumentException.class)
	public void testAddDiscussionMessageNotAssign() {
		authenticate("customer1");
		
		Complaint complaint;
		DiscussionMessage discussionMessage;
		
		complaint = complaintService.findOne(32);
			
		discussionMessage = discussionMessageService.create(complaint);
			
		discussionMessage.setMessage("Gracias, estamos atendiéndola");
			
		discussionMessageService.save(discussionMessage);
		
		unauthenticate();
	}
}
