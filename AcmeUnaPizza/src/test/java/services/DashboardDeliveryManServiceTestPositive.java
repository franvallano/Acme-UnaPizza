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

import domain.Motorbike;
import domain.SalesOrder;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class DashboardDeliveryManServiceTestPositive extends AbstractTest {
	@Autowired
	private SalesOrderService salesOrderService;
	
	// Test para probar el correcto funcionamiento del dashboard para el DeliveryMan1
	@Test
	public void testDashboard() {
		authenticate("deliveryman1");
		
		Integer totalSalesOrdersByStaff;
		Double moreExpensiveSalesOrderByStaff;
		Double lessExpensiveSalesOrderByStaff;
		Double avgSalesOrderByStaff;
		Collection<SalesOrder> salesOrderMinDrivingTime;
		Collection<SalesOrder> salesOrderMaxDrivingTime;
		
		totalSalesOrdersByStaff = salesOrderService.findTotalSalesOrderByStaffOrAll();
		moreExpensiveSalesOrderByStaff = salesOrderService.findMoreExpensiveSalesOrderByStaffOrAll();
		lessExpensiveSalesOrderByStaff = salesOrderService.findLessExpensiveSalesOrderByStaffOrAll();
		avgSalesOrderByStaff = salesOrderService.findAvgSalesOrderByStaffOrAll();
		salesOrderMinDrivingTime = salesOrderService.findSalesOrderWithMinDrinvingTime();
		salesOrderMaxDrivingTime = salesOrderService.findSalesOrderWithMaxDrinvingTime();
		
		Assert.isTrue(totalSalesOrdersByStaff == 2);
		Assert.isTrue(moreExpensiveSalesOrderByStaff == 35.5);
		Assert.isTrue(lessExpensiveSalesOrderByStaff == 32.5);
		Assert.isTrue(avgSalesOrderByStaff == 34.0);
		Assert.isTrue(salesOrderMinDrivingTime.size() == 1);
		Assert.isTrue(salesOrderMaxDrivingTime.size() == 1);
		
		unauthenticate();
	}
}
