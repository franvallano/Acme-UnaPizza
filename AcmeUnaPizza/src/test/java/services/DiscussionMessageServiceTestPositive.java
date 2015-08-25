package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Complaint;
import domain.DiscussionMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class DiscussionMessageServiceTestPositive extends AbstractTest {
	@Autowired
	private ComplaintService complaintService;
	@Autowired
	private DiscussionMessageService discussionMessageService;
	
	// Test agregar mensaje a una queja
	// Autenticado como Customer
	// NOTA: Primero la asignamos a un Administrator, ya que no hay 
	// ningun ejemplo en la base de datos
	@Test
	public void testAddDiscussionMessageCustomer() {
		authenticate("admin1");
		
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
	// Autenticado como Administrator
	@Test
	public void testAddDiscussionMessageAdministrator() {
		authenticate("admin1");
			
		// Primero la asignamos al Administrator, ya que no hay 
		// ningun ejemplo en la base de datos
		Complaint complaint;
		DiscussionMessage discussionMessage;
		Integer totalBefore;
		
		complaint = complaintService.findOneIfAvailable(32);
			
		complaintService.assignComplaint(complaint);

		totalBefore = discussionMessageService.findAll().size();
			
		complaint = complaintService.findOneByAdministrator(complaint.getId());
			
		discussionMessage = discussionMessageService.create(complaint);
			
		discussionMessage.setMessage("Gracias, estamos atendiéndola");
			
		discussionMessageService.save(discussionMessage);
			
		Assert.isTrue(totalBefore + 1 == discussionMessageService.findAll().size());
		
		unauthenticate();
	}
}
