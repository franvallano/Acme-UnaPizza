package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.PurchaseOrder;
import forms.PurchaseOrderForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
//@Transactional
//@TransactionConfiguration(defaultRollback = false)
public class PurcharseOrderServiceTestNegative extends AbstractTest {
	@Autowired
	private ProductService productService;
	@Autowired
	private PurchaseOrderService purchaseOrderService;
	
	// Test listar todos los pedidos a proveedor
	// Error: No autenticado
	@Test(expected = IllegalArgumentException.class)
	public void testListPurchaseOrders() {
		authenticate(null);
		
		Collection<PurchaseOrder> orders;
		
		orders = purchaseOrderService.findAll();
				
		unauthenticate();
	}
	
	// Test crear pedido proveedor
	// Error: Pedido vacio
	@Test(expected = IllegalArgumentException.class)
	public void testCreatePurchaseOrderEmpty() {
		authenticate("admin1");
		
		PurchaseOrderForm purchaseOrderForm;
		PurchaseOrder purchaseOrder;
		
		Collection<Integer> idPizzas;
		Collection<Integer> idComplements;
		Collection<Integer> idDesserts;
		Collection<Integer> idDrinks;
		List<Double> amountPizzas;
		List<Double> amountComplements;
		List<Double> amountDesserts;
		List<Double> amountDrinks;
		Integer totalBefore;
		
		totalBefore = purchaseOrderService.findAll().size();
		
		amountPizzas = new ArrayList<Double>();
		amountComplements = new ArrayList<Double>();
		amountDesserts = new ArrayList<Double>();
		amountDrinks = new ArrayList<Double>();

		idPizzas = productService.findAllIdsPizzas();
		idComplements = productService.findAllIdsComplements();
		idDesserts = productService.findAllIdsDesserts();
		idDrinks = productService.findAllIdsDrinks();
		
		// Iniciamos la cantida de cada producto pedido a 0
		for(int i=0;i<idPizzas.size();i++)
			amountPizzas.add(0.0);
		for(int i=0;i<idComplements.size();i++)
			amountComplements.add(0.0);
		for(int i=0;i<idDrinks.size();i++)
			amountDrinks.add(0.0);
		for(int i=0;i<idDesserts.size();i++)
			amountDesserts.add(0.0);

		purchaseOrderForm = purchaseOrderService.createForm();
		
		purchaseOrderForm = purchaseOrderService.setAllIdProducts(
				purchaseOrderForm, idPizzas, idComplements, idDesserts, idDrinks);
		
		// Estos valores vendrian rellenos de la vista
		purchaseOrderForm.setAmountPizzas(amountPizzas);
		purchaseOrderForm.setAmountComplements(amountComplements);
		purchaseOrderForm.setAmountDrinks(amountDrinks);
		purchaseOrderForm.setAmountDesserts(amountDesserts);
				
		// Coste total (aunque se recalcula en el servicio)
		purchaseOrderForm.setTotalCost(99.0);
				
		purchaseOrder = purchaseOrderService.reconstruct(purchaseOrderForm);
		purchaseOrderService.save(purchaseOrder);
		
		unauthenticate();
	}
	
	// Test crear pedido proveedor
	// Error: Intento de hackeo poniendo el precio a 0.0
	@Test(expected = IllegalArgumentException.class)
	public void testCreatePurchaseOrderTryHacking() {
		authenticate("admin1");
			
		PurchaseOrderForm purchaseOrderForm;
		PurchaseOrder purchaseOrder;
			
		Collection<Integer> idPizzas;
		Collection<Integer> idComplements;
		Collection<Integer> idDesserts;
		Collection<Integer> idDrinks;
		List<Double> amountPizzas;
		List<Double> amountComplements;
		List<Double> amountDesserts;
		List<Double> amountDrinks;
		Integer totalBefore;
		
		totalBefore = purchaseOrderService.findAll().size();
		
		amountPizzas = new ArrayList<Double>();
		amountComplements = new ArrayList<Double>();
		amountDesserts = new ArrayList<Double>();
		amountDrinks = new ArrayList<Double>();
		idPizzas = productService.findAllIdsPizzas();
		idComplements = productService.findAllIdsComplements();
		idDesserts = productService.findAllIdsDesserts();
		idDrinks = productService.findAllIdsDrinks();
			
		// Iniciamos la cantida de cada producto pedido a 0
		for(int i=0;i<idPizzas.size();i++)
			amountPizzas.add(0.0);
		for(int i=0;i<idComplements.size();i++)
			amountComplements.add(0.0);
		for(int i=0;i<idDrinks.size();i++)
			amountDrinks.add(0.0);
		for(int i=0;i<idDesserts.size();i++)
			amountDesserts.add(0.0);

		purchaseOrderForm = purchaseOrderService.createForm();
		
		purchaseOrderForm = purchaseOrderService.setAllIdProducts(
			purchaseOrderForm, idPizzas, idComplements, idDesserts, idDrinks);
		
		// Estos valores vendrian rellenos de la vista
		purchaseOrderForm.setAmountPizzas(amountPizzas);
		purchaseOrderForm.setAmountComplements(amountComplements);
		purchaseOrderForm.setAmountDrinks(amountDrinks);
		purchaseOrderForm.setAmountDesserts(amountDesserts);
		
		// Elegimos 10 pizzas barbacoas (10 x 7.5 = 75)
		amountPizzas.set(0, 75.0);
				
		// Elegimos 3 delicias de pollo (10 x 1.5 = 15)
		amountComplements.set(1, 15.0);
				
		// Elegimos 10 cocacolas y 10 fantas de naranja (10 x 0.5 = 5 cada una)
		amountDrinks.set(0, 5.0);
		amountDrinks.set(1, 5.0);
				
		// Elegimos 10 helados de fresa (10 x 2.5 = 25)
		amountDesserts.set(0, 25.0);
				
		// Coste total (aunque se recalcula en el servicio)
		purchaseOrderForm.setTotalCost(1.0);
				
		purchaseOrder = purchaseOrderService.reconstruct(purchaseOrderForm);
		purchaseOrderService.save(purchaseOrder);
		
		unauthenticate();
	}
}
