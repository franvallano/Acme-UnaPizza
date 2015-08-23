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

import domain.Cook;
import domain.Customer;
import domain.DeliveryMan;
import domain.Product;
import domain.PurchaseOrder;
import domain.Stuff;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class DashboardAdministratorServiceTestPositive extends AbstractTest {
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private DeliveryManService deliveryManService;
	
	@Autowired
	private RepairService repairService;
	
	@Autowired
	private CookService cookService;
	
	@Autowired
	private PurchaseOrderService purchaseOrderService;
	
	@Autowired
	private SalesOrderService salesOrderService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private StuffService stuffService;
	
	// Test para probar el correcto funcionamiento del dashboard para el Administrator
	@Test
	public void testDashboard() {
		authenticate("admin1");
		
		Double investedMoney;
		Collection<Customer> customerMoreComplaints;
		Double salesMoney;
		Double netSalesMoney;
		Double totalCostRepairs;
		Collection<Customer> customerMoreOrders;
		Double avgOrders;
		Collection<DeliveryMan> deliveryManMoreOrders;
		Collection<Customer> customerMoreMoneySpent;
		Collection<Cook> cookMoreOrders;
		Double totalMoneyUndeliveredOrders;
		Collection<PurchaseOrder> purchaseOrdersMoreExpensive;
		Collection<Product> moreSoldPizza;
		Collection<Product> lessSoldPizza;
		Collection<Product> moreSoldComplement;
		Collection<Product> lessSoldComplement;
		Collection<Product> moreSoldDrink;
		Collection<Product> lessSoldDrink;
		Collection<Product> moreSoldDessert;
		Collection<Product> lessSoldDessert;
		Collection<Stuff> stuffMoreRepaired;
		
		investedMoney = purchaseOrderService.findInvestedMoney();
		customerMoreComplaints = customerService.findCustomerMoreComplaints();
		salesMoney = salesOrderService.findSalesMoney();
		totalCostRepairs = repairService.findTotalCostRepairs();
		netSalesMoney = salesMoney - investedMoney - totalCostRepairs;
		customerMoreOrders = customerService.findCustomerMoreOrders();
		avgOrders = salesOrderService.findAvgOrders();
		deliveryManMoreOrders = deliveryManService.findDeliveryManMoreOrders();
		customerMoreMoneySpent = customerService.findCustomerMoreMoneySpent();
		cookMoreOrders = cookService.findCookMoreOrders();
		totalMoneyUndeliveredOrders = salesOrderService.findTotalMoneyUndeliveredOrders();
		purchaseOrdersMoreExpensive = purchaseOrderService.findPurchaseOrdersMoreExpensive();
		
		moreSoldPizza = productService.findMoreSoldPizza();
		lessSoldPizza = productService.findLessSoldPizza();
		moreSoldComplement = productService.findMoreSoldComplement();
		lessSoldComplement = productService.findLessSoldComplement();
		moreSoldDrink = productService.findMoreSoldDrink();
		lessSoldDrink = productService.findLessSoldDrink();
		moreSoldDessert = productService.findMoreSoldDessert();
		lessSoldDessert = productService.findLessSoldDessert();
		
		stuffMoreRepaired = stuffService.findStuffMoreRepaired();
		
		Assert.isTrue(investedMoney == 32.0);
		Assert.isTrue(salesMoney == 188.75);
		Assert.isTrue(netSalesMoney == 55.25);
		Assert.isTrue(customerMoreComplaints.size() == 2);
		Assert.isTrue(customerMoreOrders.size() == 1);
		Assert.isTrue(customerMoreMoneySpent.size() == 1);
		Assert.isTrue(avgOrders == 34.80625);
		Assert.isTrue(deliveryManMoreOrders.size() == 1);
		Assert.isTrue(cookMoreOrders.size() == 1);
		
		Assert.isTrue(moreSoldPizza.size() == 1);
		Assert.isTrue(lessSoldPizza.size() == 1);
		Assert.isTrue(moreSoldComplement.size() == 1);
		Assert.isTrue(lessSoldComplement.size() == 1);
		Assert.isTrue(moreSoldDrink.size() == 1);
		Assert.isTrue(lessSoldDrink.size() == 1);
		Assert.isTrue(moreSoldDessert.size() == 1);
		Assert.isTrue(lessSoldDessert.size() == 1);
		Assert.isTrue(purchaseOrdersMoreExpensive.size() == 1);
		Assert.isTrue(totalMoneyUndeliveredOrders == 89.7);
		Assert.isTrue(stuffMoreRepaired.size() == 1);
		
		unauthenticate();
	}
	
	// Test para comprobar el correcto funcionamiento de las sugerencias de pedidos
	@Test
	public void testOrdersSuggestion() {
		Collection<Product> stockMinPizzas;
		Collection<Product> stockMinComplements;
		Collection<Product> stockMinDesserts;
		Collection<Product> stockMinDrinks;
		
		stockMinPizzas = productService.findStockMinPizzas();
		stockMinComplements = productService.findStockMinComplements();
		stockMinDesserts = productService.findStockMinDesserts();
		stockMinDrinks = productService.findStockMinDrinks();
		
		Assert.isTrue(stockMinPizzas.size() == 1);
		Assert.isTrue(stockMinComplements.size() == 2);
		Assert.isTrue(stockMinDesserts.size() == 2);
		Assert.isTrue(stockMinDrinks.size() == 1);
	}
}
