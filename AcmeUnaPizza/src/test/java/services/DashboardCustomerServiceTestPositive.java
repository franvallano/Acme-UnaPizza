package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import org.springframework.util.Assert;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class DashboardCustomerServiceTestPositive extends AbstractTest {
	@Autowired
	private CustomerService customerService;
	
	// Test para probar el correcto funcionamiento del dashboard para el Customer1
	@Test
	public void testDashboard() {
		authenticate("customer1");
		
		Integer totalNumberOrders;
		Collection<Date> dateLastOrder;
		
		totalNumberOrders = customerService.findTotalNumberOrders();
		dateLastOrder = customerService.findDateLastOrder();
		
		Assert.isTrue(totalNumberOrders == 3);
		Assert.isTrue(dateLastOrder != null && !dateLastOrder.isEmpty());
		
		unauthenticate();
	}
}
