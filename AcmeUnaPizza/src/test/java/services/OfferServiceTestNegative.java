package services;

import java.sql.Date;
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
import domain.Offer;
import domain.Product;
import forms.OfferForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
//@Transactional
//@TransactionConfiguration(defaultRollback = false)
public class OfferServiceTestNegative extends AbstractTest {
	@Autowired
	private OfferService offerService;
	
	// Test crear nueva oferta
	// Error: No autenticado como Administrator
	@Test(expected = IllegalArgumentException.class)
	public void testCreateOfferNotAdmin() {
		authenticate(null);
		
		Integer totalBefore;
		Offer offer;
		OfferForm offerForm;
		
		totalBefore = offerService.findAll().size();
		offerForm = new OfferForm();
		
		offerForm.setDiscount(20);
		offerForm.setName("Super oferta");
		offerForm.setRangee("VIP");
		offerForm.setStartDate(new Date(System.currentTimeMillis()+ 3600000));
		
		offer = offerService.reconstruct(offerForm);
		
		offerService.save(offer);
		
		Assert.isTrue(totalBefore + 1 == offerService.findAll().size());
		
		unauthenticate();
	}
	
	// Test crear nueva oferta
	// Error: Fecha comienzo en pasado
	@Test(expected = IllegalArgumentException.class)
	public void testCreateOfferDateBefore() {
		authenticate("admin1");
		
		Integer totalBefore;
		Offer offer;
		OfferForm offerForm;
			
		totalBefore = offerService.findAll().size();
		offerForm = new OfferForm();
			
		offerForm.setDiscount(20);
		offerForm.setName("Super oferta");
		offerForm.setRangee("VIP");
		offerForm.setStartDate(new Date(System.currentTimeMillis()- 90000000));
		
		offer = offerService.reconstruct(offerForm);
			
		offerService.save(offer);
		
		Assert.isTrue(totalBefore + 1 == offerService.findAll().size());
		
		unauthenticate();
	}
	
	// Test editar producto
	// Error: Descuento negativo
	@Test(expected = TransactionSystemException.class)
	public void testEditOfferNegativeDiscount() {
		authenticate("admin1");
			
		Offer offer;
		OfferForm offerForm;
		
		offer = offerService.findOne(42);
		
		offerForm = offerService.desreconstruct(offer);
		
		offerForm.setName("Super oferta buena");
		offerForm.setDiscount(-55);
		offerForm.setStartDate(new Date(System.currentTimeMillis()));
		
		offer = offerService.reconstruct(offerForm);
		
		offerService.save(offer);
		
		unauthenticate();
	}
	
	// Test editar producto
	// Error: Nombre vacio
	@Test(expected = TransactionSystemException.class)
	public void testEditOfferNameEmpty() {
		authenticate("admin1");
				
		Offer offer;
		OfferForm offerForm;
			
		offer = offerService.findOne(42);
			
		offerForm = offerService.desreconstruct(offer);
			
		offerForm.setName("");
		offerForm.setDiscount(50);
		offerForm.setStartDate(new Date(System.currentTimeMillis()));
			
		offer = offerService.reconstruct(offerForm);
			
		offerService.save(offer);
			
		unauthenticate();
	}
}
