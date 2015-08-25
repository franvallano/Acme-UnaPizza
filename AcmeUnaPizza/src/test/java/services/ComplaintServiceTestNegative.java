package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Complaint;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
//@Transactional
//@TransactionConfiguration(defaultRollback = false)
public class ComplaintServiceTestNegative extends AbstractTest {
	@Autowired
	private ComplaintService complaintService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private AdministratorService administratorService;
	
	// Test crear una queja
	// Error: No autenticado
	@Test(expected = IllegalArgumentException.class)
	public void testCreateComplaintNoAuth() {
		authenticate(null);
		
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
	
	// Test crear una queja
	// Error: Titulo vacio
	@Test(expected = TransactionSystemException.class)
	public void testCreateComplaintEmptyTitle() {
		authenticate("customer1");
			
		Complaint complaint;
		Integer totalBefore;
			
		complaint = complaintService.create();
		totalBefore = complaintService.findAll().size();
			
		complaint.setDescription("Sois unos ruineros");
			
		complaintService.save(complaint);
			
		Assert.isTrue(totalBefore + 1 == complaintService.findAll().size());
			
		unauthenticate();
	}
	
	// Test asignacion y resolucion de una queja
	// Error: Autenticado como Customer
	@Test(expected = IllegalArgumentException.class)
	public void testAssignAndResolutionComplaintNoAuth() {
		authenticate("customer1");
		
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
	
	// Test asignacion y resolucion de una queja
	// Error: Administrator no asignado a la queja
	@Test(expected = IllegalArgumentException.class)
	public void testAssignAndResolutionComplaintNotAssigned() {
		authenticate("admin1");
		
		Complaint complaint;
			
		complaint = complaintService.findOneIfAvailable(31);
			
		complaint.setResult("Lo sentimos, le compensaremos");
			
		complaintService.addResolution(complaint);
		complaintService.save(complaint);

		Assert.isTrue(complaint.getState().equals("CLOSED") && 
			complaint.getResult().equals("Lo sentimos, le compensaremos"));
			
		unauthenticate();
	}
	
	// Test asignacion y resolucion de una queja
	// Error: Resolucion vacia
	@Test(expected = IllegalArgumentException.class)
	public void testAssignAndResolutionComplaintEmptyResolution() {
		authenticate("admin1");
		
		Complaint complaint;
		
		complaint = complaintService.findOneIfAvailable(31);
		
		complaint = complaintService.findOne(31);
		
		complaint.setResult("Lo sentimos, le compensaremos");
		
		complaintService.assignComplaint(complaint);
		
		complaintService.addResolution(complaint);
		complaintService.save(complaint);

		Assert.isTrue(complaint.getState().equals("CLOSED") && 
				complaint.getResult().equals("Lo sentimos, le compensaremos"));
		
		unauthenticate();
	}
	
	// Test asignacion y resolucion de una queja
	// Error: Resolucion no pertenece al Administrator
	@Test(expected = IllegalArgumentException.class)
	public void testAssignAndResolutionComplaintNotMine() {
		authenticate("admin2");
			
		Complaint complaint;
			
		complaint = complaintService.findOneIfAvailable(32);
			
		complaint = complaintService.findOne(32);
			
		complaintService.assignComplaint(complaint);
			
		complaintService.addResolution(complaint);
		complaintService.save(complaint);

		Assert.isTrue(complaint.getState().equals("CLOSED") && 
				complaint.getResult().equals("Lo sentimos, le compensaremos"));
			
		unauthenticate();
	}
	
	// Test ver una queja 
	// Error: La queja no es de dicho Customer
	@Test(expected = IllegalArgumentException.class)
	public void showComplaintCustomerNotMine() {
		authenticate("customer1");
		
		Complaint complaint;
		
		complaint = complaintService.findOneByPrincipalCustomer(32);
		
		unauthenticate();
	}
	
	// Test ver una queja 
	// Error: La queja no es de dicho Administrator
	@Test(expected = IllegalArgumentException.class)
	public void showComplaintAdministratorNotMine() {
		authenticate("admin2");
			
		Complaint complaint;
			
		complaint = complaintService.findOneByPrincipalCustomer(31);
			
		unauthenticate();
	}
}
