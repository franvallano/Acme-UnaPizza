package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SalesOrderRepository;
import domain.SalesOrder;
import domain.Staff;

@Service
@Transactional
public class SalesOrderService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private SalesOrderRepository salesOrderRepository;

	// Ancillary services -----------------------------------------------------
	@Autowired
	private StaffService staffService;
	
	// Constructor ------------------------------------------------------------
	public SalesOrderService(){
		super();
	}

	public Collection<SalesOrder> findAll(){
		Collection<SalesOrder> res;
		
		res = salesOrderRepository.findAll();
		
		return res;
	}
	
	public Integer findTotalSalesOrderByStaffOrAll(){
		Integer res;
		
		Staff staff = staffService.findByPrincipal();
		
		Assert.notNull(staff);
		
		if(staffService.isCook()) {
			res = salesOrderRepository.findTotalSalesOrderByCook(staff.getId());
		} else if(staffService.isDeliveryMan()) {
			res = salesOrderRepository.findTotalSalesOrderByDeliveryMan(staff.getId());
		} else if(staffService.isBoss()) {
			res = findTotalSalesOrder();
		} else {
			res = null;
		}
		
		Assert.notNull(res);
		
		return res;
	}
	
	public Integer findTotalSalesOrder() {
		Integer res;
		
		res = salesOrderRepository.findTotalSalesOrder();
		
		return res;
	}

	public Double findSalesMoney() {
		Double result;
		
		result = salesOrderRepository.findSalesMoney();
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Double findAvgOrders() {
		Double result;
		
		result = salesOrderRepository.findAvgOrders();
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Double findTotalMoneyUndeliveredOrders() {
		Double result;
		
		result = salesOrderRepository.findTotalMoneyUndeliveredOrders();
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Double findMoreExpensiveSalesOrder() {
		Double res;
		
		res = salesOrderRepository.findMoreExpensiveSalesOrder();
		
		return res;
	}
	
	public Double findLessExpensiveSalesOrder() {
		Double res;
		
		res = salesOrderRepository.findLessExpensiveSalesOrder();
		
		return res;
	}
	
	public Double findMoreExpensiveSalesOrderByStaffOrAll() {
		Double res;
		Staff staff = staffService.findByPrincipal();
		
		Assert.notNull(staff);
		
		if(staffService.isCook()) {
			res = salesOrderRepository.findMoreExpensiveSalesOrderByCook(staff.getId());
		} else if(staffService.isDeliveryMan()) {
			res = salesOrderRepository.findMoreExpensiveSalesOrderByDeliveryMan(staff.getId());
		} else if(staffService.isBoss()) {
			res = findMoreExpensiveSalesOrder();
		} else {
			res = null;
		}
		
		Assert.notNull(res);
		
		return res;
	}
	
	public Double findLessExpensiveSalesOrderByStaffOrAll() {
		Double res;
		Staff staff = staffService.findByPrincipal();
		
		Assert.notNull(staff);
		
		if(staffService.isCook()) {
			res = salesOrderRepository.findLessExpensiveSalesOrderByCook(staff.getId());
		} else if(staffService.isDeliveryMan()) {
			res = salesOrderRepository.findLessExpensiveSalesOrderByDeliveryMan(staff.getId());
		} else if(staffService.isBoss()) {
			res = findLessExpensiveSalesOrder();
		} else {
			res = null;
		}
		
		Assert.notNull(res);
		
		return res;
	}
	
	public Double findAvgSalesOrderByStaffOrAll() {
		Double res;
		Staff staff = staffService.findByPrincipal();
		
		Assert.notNull(staff);
		
		if(staffService.isCook()) {
			res = salesOrderRepository.findAvgSalesOrderByCook(staff.getId());
		} else if(staffService.isDeliveryMan()) {
			res = salesOrderRepository.findAvgSalesOrderByDeliveryMan(staff.getId());
		} else if(staffService.isBoss()) {
			res = findAvgSalesOrder();
		} else {
			res = null;
		}
		
		Assert.notNull(res);
		
		return res;
	}
	
	public Double findAvgSalesOrder() {
		Double res;
		
		res = salesOrderRepository.findAvgSalesOrder();
		
		return res;
	}
	
	
	// Ancillary methods ------------------------------------------------------

}