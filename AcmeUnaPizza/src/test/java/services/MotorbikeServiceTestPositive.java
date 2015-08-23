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
import domain.Garage;
import domain.Motorbike;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class MotorbikeServiceTestPositive extends AbstractTest {
	@Autowired
	private MotorbikeService motorbikeService;
	@Autowired
	private GarageService garageService;
	
	// Test listar motos
	// Autenticado como Administrator
	@Test
	public void testListAllMotorbikes() {
		authenticate("admin1");
		
		Collection<Motorbike> motorbikes;

		motorbikes = motorbikeService.findAll();

		Assert.isTrue(motorbikes.size() == 4);
		
		unauthenticate();
	}
	
	// Test editar motorbike
	// Autenticado como Administrator
	@Test
	public void testEditMotorbike() {
		authenticate("admin1");
			
		Motorbike motorbike;
		
		motorbike = motorbikeService.findOne(16);
		
		motorbike.setNumber(99);
		
		motorbikeService.save(motorbike);
		
		motorbike = motorbikeService.findOne(16);
		
		Assert.isTrue(motorbike.getNumber() == 99);
		
		unauthenticate();
	}
	
	// Test crear motorbike
	// Autenticado como Administrator
	@Test
	public void testCreateMotorbike() {
		authenticate("admin1");
		
		Integer totalBefore;
		Motorbike motorbike;
		Garage garage;
		
		motorbike = motorbikeService.create();
		garage = garageService.findOne(15);
		totalBefore = motorbikeService.findAll().size();
		
		motorbike.setGarage(garage);
		motorbike.setNumber(99);
		motorbike.setLicensePlate("5688DFG");
		
		motorbikeService.save(motorbike);
		
		Assert.isTrue(totalBefore + 1 == motorbikeService.findAll().size());
		
		unauthenticate();
	}
	
	// Test para cambiar una moto de garaje
	// Autenticado como Administrator
	@Test
	public void testChangeGarage() {
		authenticate("admin1");
		
		Motorbike motorbike;
		Garage garage;
		
		motorbike = motorbikeService.findOne(16);
		garage = garageService.findOne(15);
		
		Assert.isTrue(motorbike.getGarage().getId() == 12);
		
		motorbike.setGarage(garage);
		
		motorbikeService.save(motorbike);
		
		motorbike = motorbikeService.findOne(16);
		
		Assert.isTrue(motorbike.getGarage().getId() == garage.getId());
		
		unauthenticate();
	}
	
	// Test para eliminar una moto
	// Autenticado como Administrator
	@Test
	public void testDeleteMotorbike() {
		authenticate("admin1");
		
		Motorbike motorbike;
		Integer totalBefore;
		
		motorbike = motorbikeService.findOne(19);
		totalBefore = motorbikeService.findAll().size();
		
		motorbikeService.delete(motorbike);
		
		Assert.isTrue(totalBefore - 1 == motorbikeService.findAll().size());
		
		unauthenticate();
	}
}
