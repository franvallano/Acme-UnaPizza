package services;

import java.util.Collection;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class ComplaintServiceTestPositive extends AbstractTest {
	@Autowired
	private ComplaintService complaintService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private AdministratorService administratorService;
	
	// Test listar quejas disponibles
	// Autenticado como Administrator
	@Test
	public void testListAllAvailableComplaints() {
		authenticate("admin1");
		
		Collection<Complaint> complaints;

		complaints = complaintService.findAvailables();

		Assert.isTrue(complaints.size() == 1);
		
		unauthenticate();
	}
	
	// Test listar quejas de un Customer
	// Autenticado como Customer
	@Test
	public void testListAllMyComplaints() {
		authenticate("customer1");
			
		Collection<Complaint> complaints;

		complaints = complaintService.findByPrincipal();

		Assert.isTrue(complaints.size() == 1);
			
		unauthenticate();
	}
	
	// Test crear una queja
	// Autenticado como Customer
	@Test
	public void testCreateComplaint() {
		authenticate("customer1");
		
		Complaint complaint;
		Integer totalBefore;
		
		complaint = complaintService.create();
		totalBefore = complaintService.findAll().size();
		
		complaint.setTitle("Ingredientes caducados");
		complaint.setDescription("Vergüenza os debería dar");
		
		complaintService.save(complaint);
		
		Assert.isTrue(totalBefore + 1 == complaintService.findAll().size());
		
		unauthenticate();
	}
	
	// Test asignacion y resolucion de una queja
	// Autenticado como Administrator
	@Test
	public void testAssignAndResolutionComplaint() {
		authenticate("admin1");
		
		Complaint complaint;
		
		complaint = complaintService.findOneIfAvailable(32);
		
		complaint = complaintService.findOne(32);
		
		complaintService.assignComplaint(complaint);
		
		complaint.setResult("Lo sentimos, le compensaremos");
		
		complaintService.addResolution(complaint);
		complaintService.save(complaint);

		Assert.isTrue(complaint.getState().equals("CLOSED") && 
				complaint.getResult().equals("Lo sentimos, le compensaremos"));
		
		unauthenticate();
	}
}
