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
import domain.Stuff;
import domain.WorkShop;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
//@Transactional
//@TransactionConfiguration(defaultRollback = false)
public class StuffServiceTestNegative extends AbstractTest {
	@Autowired
	private StuffService stuffService;
	@Autowired
	private WorkShopService workShopService;
	
	// Test editar articulo
	// Error: Autenticado como Cook
	@Test(expected = IllegalArgumentException.class)
	public void testEditStuffBadAuth() {
		authenticate("cook1");
			
		Stuff stuff;
		
		stuff = stuffService.findOne(22);
		
		stuff.setName("Ventilador");
		
		stuffService.save(stuff);
		
		stuff = stuffService.findOne(22);
		
		Assert.isTrue(stuff.getName().equals("Ventilador"));
		
		unauthenticate();
	}
	
	// Test editar articulo
	// Error: No autenticado
	@Test(expected = TransactionSystemException.class)
	public void testEditStuffNoAuth() {
		authenticate("boss1");
				
		Stuff stuff;
			
		stuff = stuffService.findOne(22);
			
		stuff.setName("Ventilador");
			
		stuffService.save(stuff);
			
		stuff = stuffService.findOne(22);
			
		Assert.isTrue(stuff.getName().equals("Ventilador"));
			
		unauthenticate();
	}
	
	// Test crear articulo
	// Error: Ningun taller asignado
	@Test(expected = TransactionSystemException.class)
	public void testCreateWorkShop() {
		authenticate("boss1");
		
		Integer totalBefore;
		Stuff stuff;
		WorkShop workShop;
		
		stuff = stuffService.create();
		workShop = workShopService.findOne(20);

		totalBefore = stuffService.findAll().size();
		
		stuff.setName("Ventilador");
		stuff.setPowerConsumption(6500);
		stuff.setReferenceCode("AAA12345");
		
		stuffService.save(stuff);
		
		Assert.isTrue(totalBefore + 1 == stuffService.findAll().size());
		
		unauthenticate();
	}
	
	// Test crear articulo
	// Error: Codigo invalido
	@Test(expected = TransactionSystemException.class)
	public void testCreateWorkShopBadCode() {
		authenticate("boss1");
			
		Integer totalBefore;
		Stuff stuff;
		WorkShop workShop;
		
		stuff = stuffService.create();
		workShop = workShopService.findOne(20);

		totalBefore = stuffService.findAll().size();
			
		stuff.setName("Ventilador");
		stuff.setPowerConsumption(6500);
		stuff.setReferenceCode("CODIGO-MALO:(");
		stuff.setStatus("OK");
		stuff.setWorkShop(workShop);
			
		stuffService.save(stuff);
			
		Assert.isTrue(totalBefore + 1 == stuffService.findAll().size());
			
		unauthenticate();
	}
}
