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
import domain.Garage;
import domain.Motorbike;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
//@Transactional
//@TransactionConfiguration(defaultRollback = false)
public class GarageServiceTestNegative extends AbstractTest {
	@Autowired
	private GarageService garageService;
	
	// Test listar garajes
	// Error: Autenticado como Boss
	@Test(expected = IllegalArgumentException.class)
	public void testListAllGaragesAuthBoss() {
		authenticate("boss1");
		
		Collection<Garage> garages;

		garages = garageService.findAll();

		Assert.isTrue(garages.size() == 4);
		
		unauthenticate();
	}
	
	// Test editar garaje
	// Error: Autenticado como DeliveryMan
	@Test(expected = IllegalArgumentException.class)
	public void testEditGarageAuthDeliveryMan() {
		authenticate("deliveryman1");
			
		Garage garage;
		
		garage = garageService.findOne(12);
		
		garage.setSize(50);
		
		garageService.save(garage);
		
		garage = garageService.findOne(12);
		
		Assert.isTrue(garage.getSize() == 50);
		
		unauthenticate();
	}
	
	// Test crear garaje
	// Error: Sin numero de plazas
	@Test(expected = TransactionSystemException.class)
	public void testCreateGarageNotSize() {
		authenticate("admin1");
		
		Integer totalBefore;
		Garage garage;
		
		garage = garageService.create();
		totalBefore = garageService.findAll().size();
		
		garage.setLocation("Cualquier sitio");
		
		garageService.save(garage);
		
		Assert.isTrue(totalBefore + 1 == garageService.findAll().size());
		
		unauthenticate();
	}
}
