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
import domain.WorkShop;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
//@Transactional
//@TransactionConfiguration(defaultRollback = false)
public class WorkShopServiceTestNegative extends AbstractTest {
	@Autowired
	private WorkShopService workShopService;
	
	// Test editar taller
	// Error: Autenticado como Administrator
	@Test(expected = IllegalArgumentException.class)
	public void testEditWorkShopBadAuth() {
		authenticate("admin1");
			
		WorkShop workShop;
		
		workShop = workShopService.findOne(20);
		
		workShop.setCompany("La mejor");
		
		workShopService.save(workShop);
		
		workShop = workShopService.findOne(20);
		
		Assert.isTrue(workShop.getCompany().equals("La mejor"));
		
		unauthenticate();
	}
	
	// Test editar taller
	// Error: Compañia vacia
	@Test(expected = TransactionSystemException.class)
	public void testEditWorkShopEmptyCompany() {
		authenticate("boss1");
				
		WorkShop workShop;
			
		workShop = workShopService.findOne(20);
			
		workShop.setCompany("");
			
		workShopService.save(workShop);
			
		workShop = workShopService.findOne(20);
			
		Assert.isTrue(workShop.getCompany().equals("La mejor"));
			
		unauthenticate();
	}
	
	// Test crear taller
	// Error: Tlf y ciudad vacios
	@Test(expected = TransactionSystemException.class)
	public void testCreateWorkShopEmptyField() {
		authenticate("boss1");
		
		Integer totalBefore;
		WorkShop workShop;
		
		workShop = workShopService.create();

		totalBefore = workShopService.findAll().size();
		
		workShop.setCompany("La mejor");
		workShop.setContact("Jaime");
		
		workShopService.save(workShop);
		
		Assert.isTrue(totalBefore + 1 == workShopService.findAll().size());
		
		unauthenticate();
	}
}
