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
public class DashboardBossServiceTestPositive extends AbstractTest {
	@Autowired
	private SalesOrderService salesOrderService;
	
	@Autowired 
	private MotorbikeService motorbikeService;
	
	// Test para probar el correcto funcionamiento del dashboard para el Boss1
	@Test
	public void testDashboard() {
		authenticate("boss1");
		
		Integer totalSalesOrder;
		Double totalMoreExpensiveSalesOrder;
		Double totalLessExpensiveSalesOrder;
		Double avgSalesOrder;
		Collection<SalesOrder> salesOrderMinDrivingTime;
		Collection<SalesOrder> salesOrderMaxDrivingTime;
		Collection<Motorbike> drivingTimeByMotorbike;
		
		totalSalesOrder = salesOrderService.findTotalSalesOrder();
		totalMoreExpensiveSalesOrder = salesOrderService.findMoreExpensiveSalesOrder();
		totalLessExpensiveSalesOrder = salesOrderService.findLessExpensiveSalesOrder();
		avgSalesOrder = salesOrderService.findAvgOrders();
		salesOrderMinDrivingTime = salesOrderService.findSalesOrderWithMinDrinvingTime();
		salesOrderMaxDrivingTime = salesOrderService.findSalesOrderWithMaxDrinvingTime();
		drivingTimeByMotorbike = motorbikeService.findAllMotorbikesOrderedByDrivingTime();
		
		Assert.isTrue(totalSalesOrder == 8);
		Assert.isTrue(totalMoreExpensiveSalesOrder == 60.0);
		Assert.isTrue(totalLessExpensiveSalesOrder == 12.75);
		Assert.isTrue(avgSalesOrder == 34.80625);
		Assert.isTrue(salesOrderMinDrivingTime.size() == 1);
		Assert.isTrue(salesOrderMaxDrivingTime.size() == 1);
		Assert.isTrue(drivingTimeByMotorbike.size() == 4);
		
		unauthenticate();
	}
}
