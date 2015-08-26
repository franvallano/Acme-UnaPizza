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
import domain.WorkShop;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class WorkShopServiceTestPositive extends AbstractTest {
	@Autowired
	private WorkShopService workShopService;
	
	// Test listar talleres
	// Autenticado como Boss
	@Test
	public void testListAllWorkShops() {
		authenticate("boss1");
		
		Collection<WorkShop> workShops;

		workShops = workShopService.findAll();

		Assert.isTrue(workShops.size() == 2);
		
		unauthenticate();
	}
	
	// Test editar taller
	// Autenticado como Boss
	@Test
	public void testEditWorkShop() {
		authenticate("boss1");
			
		WorkShop workShop;
		
		workShop = workShopService.findOne(20);
		
		workShop.setCompany("La mejor");
		
		workShopService.save(workShop);
		
		workShop = workShopService.findOne(20);
		
		Assert.isTrue(workShop.getCompany().equals("La mejor"));
		
		unauthenticate();
	}
	
	// Test crear taller
	// Autenticado como Boss
	@Test
	public void testCreateWorkShop() {
		authenticate("boss1");
		
		Integer totalBefore;
		WorkShop workShop;
		
		workShop = workShopService.create();

		totalBefore = workShopService.findAll().size();
		
		workShop.setCompany("La mejor");
		workShop.setContact("Jaime");
		workShop.setPhoneNumber("661888999");
		workShop.setCity("Sevilla");
		
		workShopService.save(workShop);
		
		Assert.isTrue(totalBefore + 1 == workShopService.findAll().size());
		
		unauthenticate();
	}
}
