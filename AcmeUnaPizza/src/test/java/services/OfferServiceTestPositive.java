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
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Offer;
import domain.Product;
import forms.OfferForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class OfferServiceTestPositive extends AbstractTest {
	@Autowired
	private OfferService offerService;
	
	// Test listar todas las ofertas y las actuales
	// Autenticado como Administrator
	@Test
	public void testListAllOffers() {
		authenticate("admin1");
		
		Collection<Offer> offers;
		Collection<Offer> currentOffers;
		
		offers = offerService.findAll();
		currentOffers = offerService.findCurrentOffers();
		
		Assert.isTrue(offers.size() == 4);
		Assert.isTrue(currentOffers.size() == 3);
		
		unauthenticate();
	}
	
	// Test crear nueva oferta
	// Autenticado como Administrator
	@Test
	public void testCreateOffer() {
		authenticate("admin1");
		
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
	
	// Test editar producto
	// Autenticado como Administrator
	@Test
	public void testEditOffer() {
		authenticate("admin1");
			
		Offer offer;
		OfferForm offerForm;
		
		offer = offerService.findOne(42);
		
		offerForm = offerService.desreconstruct(offer);
		
		offerForm.setName("Super oferta buena");
		offerForm.setDiscount(99);
		offerForm.setStartDate(new Date(System.currentTimeMillis()));
		
		offer = offerService.reconstruct(offerForm);
		
		offerService.save(offer);
		
		offer = offerService.findOne(42);
		
		Assert.isTrue(offer.getName().equals("Super oferta buena") && offer.getDiscount() == 99);
		
		unauthenticate();
	}
}
