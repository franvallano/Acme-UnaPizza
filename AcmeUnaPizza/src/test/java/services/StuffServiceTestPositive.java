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
import domain.Stuff;
import domain.WorkShop;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class StuffServiceTestPositive extends AbstractTest {
	@Autowired
	private StuffService stuffService;
	@Autowired
	private WorkShopService workShopService;
	
	// Test listar articulos
	// Autenticado como Boss
	@Test
	public void testListAllStuffs() {
		authenticate("boss1");
		
		Collection<Stuff> stuffs;

		stuffs = stuffService.findAll();

		Assert.isTrue(stuffs.size() == 6);
		
		unauthenticate();
	}
	
	// Test editar articulo
	// Autenticado como Boss
	@Test
	public void testEditStuff() {
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
	// Autenticado como Boss
	@Test
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
		stuff.setStatus("OK");
		stuff.setWorkShop(workShop);
		
		stuffService.save(stuff);
		
		Assert.isTrue(totalBefore + 1 == stuffService.findAll().size());
		
		unauthenticate();
	}
}
