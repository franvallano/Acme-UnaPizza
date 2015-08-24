package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
public class MotorbikeServiceTestNegative extends AbstractTest {
	@Autowired
	private MotorbikeService motorbikeService;
	@Autowired
	private GarageService garageService;
	
	// Test editar motorbike
	// Error: No autenticado como Administrator
	@Test(expected = IllegalArgumentException.class)
	public void testEditMotorbikeNotAdmin() {
		authenticate(null);
			
		Motorbike motorbike;
		
		motorbike = motorbikeService.findOne(16);
		
		motorbike.setNumber(99);
		
		motorbikeService.save(motorbike);
		
		motorbike = motorbikeService.findOne(16);
		
		Assert.isTrue(motorbike.getNumber() == 99);
		
		unauthenticate();
	}
	
	// Test editar motorbike
	// Error: Garaje sin asignar
	@Test(expected = TransactionSystemException.class)
	public void testEditMotorbikeNotGarage() {
		authenticate("admin1");
		
		Motorbike motorbike;
			
		motorbike = motorbikeService.findOne(16);
			
		motorbike.setNumber(99);
		motorbike.setGarage(null);
			
		motorbikeService.save(motorbike);
			
		motorbike = motorbikeService.findOne(16);
			
		Assert.isTrue(motorbike.getNumber() == 99);
			
		unauthenticate();
	}
	
	// Test crear motorbike
	// Error: Numero de moto repetido
	@Test(expected = DataIntegrityViolationException.class)
	public void testCreateMotorbike() {
		authenticate("admin1");
		
		Integer totalBefore;
		Motorbike motorbike;
		Garage garage;
		
		motorbike = motorbikeService.create();
		garage = garageService.findOne(15);
		totalBefore = motorbikeService.findAll().size();
		
		motorbike.setGarage(garage);
		motorbike.setNumber(1);
		motorbike.setLicensePlate("5008DFG");
		
		motorbikeService.save(motorbike);
		
		Assert.isTrue(totalBefore + 1 == motorbikeService.findAll().size());
		
		unauthenticate();
	}
	
	// Test para cambiar una moto de garaje
	// Error: Cambiar a un garaje lleno
	@Test(expected = IllegalArgumentException.class)
	public void testChangeGarageFull() {
		authenticate("admin1");
		
		Motorbike motorbike;
		Garage garage;
		
		motorbike = motorbikeService.findOne(16);
		garage = garageService.findOne(13);
		
		motorbike.setGarage(garage);
		
		motorbikeService.save(motorbike);
		
		motorbike = motorbikeService.findOne(16);
		
		Assert.isTrue(motorbike.getGarage().getId() == garage.getId());
		
		unauthenticate();
	}
	
	// Test para eliminar una moto
	// Error: Borrar una moto que tiene un repartidor
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteMotorbikeWithDeliveryMan() {
		authenticate("admin1");
		
		Motorbike motorbike;
		
		motorbike = motorbikeService.findOne(16);
		
		motorbikeService.delete(motorbike);
		
		unauthenticate();
	}
}
