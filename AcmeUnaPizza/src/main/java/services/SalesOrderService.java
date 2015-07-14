package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SalesOrderRepository;
import utilities.PatternGenerator;
import domain.Administrator;
import domain.Customer;
import domain.Product;
import domain.PurchaseOrder;
import domain.SalesOrder;
import domain.Staff;
import forms.PurchaseOrderForm;
import forms.SalesOrderForm;

@Service
@Transactional
public class SalesOrderService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private SalesOrderRepository salesOrderRepository;

	// Ancillary services -----------------------------------------------------
	@Autowired
	private StaffService staffService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ProductService productService;
	
	// Constructor ------------------------------------------------------------
	public SalesOrderService(){
		super();
	}
	
	public SalesOrderForm createForm() {
		SalesOrderForm salesOrderForm;
		Date date;
		
		customerService.findByPrincipal();
		
		date = new Date(System.currentTimeMillis()-1);
		salesOrderForm = new SalesOrderForm();
		salesOrderForm.setCreationMoment(date);
		salesOrderForm.setReferenceNumber(PatternGenerator.purchaseOrderReferenceNumber());
		salesOrderForm.setTotalCost(0.0);
		
		return salesOrderForm;
	}
	
	public SalesOrder create() {
		SalesOrder salesOrder;
		Collection<Product> products;
		Date date;
		Customer customer;

		date = new Date(System.currentTimeMillis()-1);
		salesOrder = new SalesOrder();
		products = new ArrayList<Product>();
		customer = customerService.findByPrincipal();
		
		salesOrder.setProducts(products);
		salesOrder.setCreationMoment(date);
		
		salesOrder.setState("OPEN");
		
		salesOrder.setCustomer(customer);
		
		return salesOrder;
	}

	public Collection<SalesOrder> findAll(){
		Collection<SalesOrder> res;
		
		res = salesOrderRepository.findAll();
		
		return res;
	}
	
	public Collection<SalesOrder> findAllByCustomerId(){
		Collection<SalesOrder> res;
		Customer customer;
		
		customer = customerService.findByPrincipal();
		
		res = salesOrderRepository.findAllByCustomerId(customer.getId());
		
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
	
	public Collection<SalesOrder> findSalesOrderWithMinDrinvingTime(){
		Collection<SalesOrder> s;
		
		s = salesOrderRepository.findSalesOrderMinDrinvingTime();
		
		Assert.notNull(s);
		
		return s;
	}
	
	public Collection<SalesOrder> findSalesOrderWithMaxDrinvingTime(){
		Collection<SalesOrder> s;
		
		s = salesOrderRepository.findSalesOrderMaxDrivingTime();
		
		Assert.notNull(s);
		
		return s;
	}
	
	public SalesOrder findOne(int id) {
		Assert.isTrue(id != 0);
		
		SalesOrder res;
		
		res = this.salesOrderRepository.findOne(id);
		
		Assert.notNull(res);
		Assert.isTrue(res.getCustomer().getId() == customerService.findByPrincipal().getId());
		
		return res;
	}
	
	// Metodo que recibe un objeto formulario y reconstruye un objeto de dominio
	public SalesOrder reconstruct(SalesOrderForm salesOrderForm) {
		Assert.notNull(salesOrderForm);
		Assert.isTrue(salesOrderForm.getTotalCost() > 0.0);
		SalesOrder salesOrder;
		Double totalCost = 0.0;

		salesOrder = create();

		Assert.notNull(salesOrder);

		salesOrder.setReferenceNumber(salesOrderForm.getReferenceNumber());
		salesOrder.setTotalCost(salesOrderForm.getTotalCost());

		if(salesOrderForm.getIdPizzas() != null && salesOrderForm.getAmountPizzas() != null)
			Assert.isTrue(salesOrderForm.getIdPizzas().size() == salesOrderForm.getAmountPizzas().size());
		
		if(salesOrderForm.getIdDrinks() != null && salesOrderForm.getAmountDrinks() != null)
			Assert.isTrue(salesOrderForm.getIdDrinks().size() == salesOrderForm.getAmountDrinks().size());
		
		if(salesOrderForm.getIdComplements() != null && salesOrderForm.getAmountComplements() != null)
			Assert.isTrue(salesOrderForm.getIdComplements().size() == salesOrderForm.getAmountComplements().size());
		
		if(salesOrderForm.getIdDesserts() != null && salesOrderForm.getAmountDesserts() != null)
			Assert.isTrue(salesOrderForm.getIdDesserts().size() == salesOrderForm.getAmountDesserts().size());

		int index = 0;

		for (Integer id : salesOrderForm.getIdPizzas()) {
			Double costProduct = salesOrderForm.getAmountPizzas().get(index);

			if (costProduct != 0.0) {
				Product product = productService.findOne(id);

				int amountProduct = (int) (costProduct / product
						.getStockPrice());
				totalCost += costProduct;

				for (int i = 0; i < amountProduct; i++)
					salesOrder.getProducts().add(product);

				Assert.isTrue((product.getActualStock() - amountProduct) >= 0);
				
				product.setActualStock(product.getActualStock() - amountProduct);

				productService.saveProductByCustomer(product);
			}

			index++;
		}

		index = 0;

		for (Integer id : salesOrderForm.getIdComplements()) {
			Double costProduct = salesOrderForm.getAmountComplements().get(
					index);

			if (costProduct != 0.0) {
				Product product = productService.findOne(id);

				int amountProduct = (int) (costProduct / product
						.getStockPrice());
				totalCost += costProduct;

				for (int i = 0; i < amountProduct; i++)
					salesOrder.getProducts().add(product);

				Assert.isTrue((product.getActualStock() - amountProduct) >= 0);
				
				product.setActualStock(product.getActualStock() - amountProduct);

				productService.saveProductByCustomer(product);
			}

			index++;
		}

		index = 0;

		for (Integer id : salesOrderForm.getIdDesserts()) {
			Double costProduct = salesOrderForm.getAmountDesserts().get(index);

			if (costProduct != 0.0) {
				Product product = productService.findOne(id);

				int amountProduct = (int) (costProduct / product
						.getStockPrice());
				totalCost += costProduct;

				for (int i = 0; i < amountProduct; i++)
					salesOrder.getProducts().add(product);

				Assert.isTrue((product.getActualStock() - amountProduct) >= 0);
				
				product.setActualStock(product.getActualStock() - amountProduct);

				productService.saveProductByCustomer(product);
			}

			index++;
		}

		index = 0;

		for (Integer id : salesOrderForm.getIdDrinks()) {
			Double costProduct = salesOrderForm.getAmountDrinks().get(index);

			if (costProduct != 0.0) {
				Product product = productService.findOne(id);

				int amountProduct = (int) (costProduct / product
						.getStockPrice());
				totalCost += costProduct;

				for (int i = 0; i < amountProduct; i++)
					salesOrder.getProducts().add(product);

				Assert.isTrue((product.getActualStock() - amountProduct) >= 0);
				
				product.setActualStock(product.getActualStock() - amountProduct);

				productService.saveProductByCustomer(product);
			}

			index++;
		}

		if(salesOrderForm.getOffer() != null) {
			salesOrder.setTotalCost(salesOrder.getTotalCost() * ((100-salesOrderForm.getOffer().getDiscount())/100));
			salesOrder.setOffer(salesOrderForm.getOffer());
		} else
			Assert.isTrue(salesOrder.getTotalCost() == totalCost);

		return salesOrder;
	}
	
	public void save(SalesOrder saleseOrder) {
		Assert.notNull(saleseOrder);
		Assert.isTrue(saleseOrder.getCustomer().getId() == customerService.findByPrincipal().getId());
		
		Date date;
		
		date = new Date(System.currentTimeMillis()-1);
		
		saleseOrder.setCreationMoment(date);
		
		salesOrderRepository.save(saleseOrder);
	}
	
	// Ancillary methods ------------------------------------------------------

}