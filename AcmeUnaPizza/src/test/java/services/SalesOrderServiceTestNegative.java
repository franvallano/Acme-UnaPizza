package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Customer;
import domain.Note;
import domain.Offer;
import domain.SalesOrder;
import forms.DrivingTimeForm;
import forms.NoteDrivingTimeForm;
import forms.SalesOrderForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
//@Transactional
//@TransactionConfiguration(defaultRollback = false)
public class SalesOrderServiceTestNegative extends AbstractTest {
	@Autowired
	private SalesOrderService salesOrderService;
	@Autowired
	private OfferService offerService;
	@Autowired
	private ProductService productService;
	@Autowired
	private CookService cookService;
	@Autowired
	private BossService bossService;
	@Autowired
	private DeliveryManService deliveryManService;
	
	// Test crear pedido sin oferta
	// Error: No autenticado
	@Test(expected = IllegalArgumentException.class)
	public void testCreateSalesOrderByCustomerWithoutOfferNotAuth() {
		authenticate(null);
		
		SalesOrderForm salesOrderForm;
		SalesOrder salesOrder;
		
		Collection<Integer> idPizzas;
		Collection<Integer> idComplements;
		Collection<Integer> idDesserts;
		Collection<Integer> idDrinks;
		List<Double> amountPizzas;
		List<Double> amountComplements;
		List<Double> amountDesserts;
		List<Double> amountDrinks;
		Integer totalBefore;
		
		totalBefore = salesOrderService.findAll().size();
		
		amountPizzas = new ArrayList<Double>();
		amountComplements = new ArrayList<Double>();
		amountDesserts = new ArrayList<Double>();
		amountDrinks = new ArrayList<Double>();

		idPizzas = productService.findAllIdsPizzasMin(5);
		idComplements = productService.findAllIdsComplementsMin(5);
		idDesserts = productService.findAllIdsDessertsMin(5);
		idDrinks = productService.findAllIdsDrinksMin(5);
		
		// Iniciamos la cantida de cada producto pedido a 0
		for(int i=0;i<idPizzas.size();i++)
			amountPizzas.add(0.0);
		for(int i=0;i<idComplements.size();i++)
			amountComplements.add(0.0);
		for(int i=0;i<idDrinks.size();i++)
			amountDrinks.add(0.0);
		for(int i=0;i<idDesserts.size();i++)
			amountDesserts.add(0.0);
		
		salesOrderForm = salesOrderService.createForm();
		
		salesOrderForm = salesOrderService.setAllIdProducts(
				salesOrderForm, idPizzas, idComplements, idDesserts, idDrinks);
		
		// Estos valores vendrian rellenos de la vista
		salesOrderForm.setAmountPizzas(amountPizzas);
		salesOrderForm.setAmountComplements(amountComplements);
		salesOrderForm.setAmountDrinks(amountDrinks);
		salesOrderForm.setAmountDesserts(amountDesserts);
		
		// Oferta 1
		salesOrderForm.setOffer(null);
		
		// Elegimos 2 pizzas barbacoas (2 x 12 = 24)
		amountPizzas.set(0, 24.0);
		
		// Elegimos 3 delicias de pollo (3 x 4 = 12)
		amountComplements.set(1, 12.0);
		
		// Elegimos 2 cocacolas y 2 fantas de naranja (2 x 1.5 = 3 cada una)
		amountDrinks.set(0, 3.0);
		amountDrinks.set(1, 3.0);
		
		// Elegimos 5 helados de fresa (5 x 5 = 25)
		amountDesserts.set(0, 25.0);
		
		// Coste total (aunque se recalcula en el servicio)
		salesOrderForm.setTotalCost(67.0);
		
		salesOrder = salesOrderService.reconstruct(salesOrderForm);
		salesOrderService.save(salesOrder, true);
		
		Assert.isTrue(totalBefore + 1 == salesOrderService.findAll().size());
		
		unauthenticate();
	}
	
	// Test crear pedido sin oferta
	// Error: Intento de hackeo poniendo el precio a 0.0
	@Test(expected = IllegalArgumentException.class)
	public void testCreateSalesOrderByCustomerWithoutOfferTryHacking() {
		authenticate("customer1");
			
		SalesOrderForm salesOrderForm;
		SalesOrder salesOrder;
			
		Collection<Integer> idPizzas;
		Collection<Integer> idComplements;
		Collection<Integer> idDesserts;
		Collection<Integer> idDrinks;
		List<Double> amountPizzas;
		List<Double> amountComplements;
		List<Double> amountDesserts;
		List<Double> amountDrinks;
		Integer totalBefore;
		
		totalBefore = salesOrderService.findAll().size();
		
		amountPizzas = new ArrayList<Double>();
		amountComplements = new ArrayList<Double>();
		amountDesserts = new ArrayList<Double>();
		amountDrinks = new ArrayList<Double>();
		idPizzas = productService.findAllIdsPizzasMin(5);
		idComplements = productService.findAllIdsComplementsMin(5);
		idDesserts = productService.findAllIdsDessertsMin(5);
		idDrinks = productService.findAllIdsDrinksMin(5);
		
		// Iniciamos la cantida de cada producto pedido a 0
		for(int i=0;i<idPizzas.size();i++)
			amountPizzas.add(0.0);
		for(int i=0;i<idComplements.size();i++)
			amountComplements.add(0.0);
		for(int i=0;i<idDrinks.size();i++)
			amountDrinks.add(0.0);
		for(int i=0;i<idDesserts.size();i++)
			amountDesserts.add(0.0);
		
		salesOrderForm = salesOrderService.createForm();
		
		salesOrderForm = salesOrderService.setAllIdProducts(
				salesOrderForm, idPizzas, idComplements, idDesserts, idDrinks);
		
		// Estos valores vendrian rellenos de la vista
		salesOrderForm.setAmountPizzas(amountPizzas);
		salesOrderForm.setAmountComplements(amountComplements);
		salesOrderForm.setAmountDrinks(amountDrinks);
		salesOrderForm.setAmountDesserts(amountDesserts);
		
		// Oferta 1
		salesOrderForm.setOffer(null);
		
		// Elegimos 2 pizzas barbacoas (2 x 12 = 24)
		amountPizzas.set(0, 24.0);
		
		// Elegimos 3 delicias de pollo (3 x 4 = 12)
		amountComplements.set(1, 12.0);
		
		// Elegimos 2 cocacolas y 2 fantas de naranja (2 x 1.5 = 3 cada una)
		amountDrinks.set(0, 3.0);
		amountDrinks.set(1, 3.0);
		
		// Elegimos 5 helados de fresa (5 x 5 = 25)
		amountDesserts.set(0, 25.0);
		
		// Coste total (aunque se recalcula en el servicio)
		salesOrderForm.setTotalCost(0.0);
		
		salesOrder = salesOrderService.reconstruct(salesOrderForm);
		salesOrderService.save(salesOrder, true);
		
		Assert.isTrue(totalBefore + 1 == salesOrderService.findAll().size());
		
		unauthenticate();
	}
	
	// Test crear pedido con oferta
	// Error: Pedido una oferta de rango superior a la del Customer logueado
	@Test(expected = IllegalArgumentException.class)
	public void testCreateSalesOrderByCustomerWithOfferIllegal() {
		authenticate("customer1");
			
		SalesOrderForm salesOrderForm;
		SalesOrder salesOrder;
			
		Collection<Integer> idPizzas;
		Collection<Integer> idComplements;
		Collection<Integer> idDesserts;
		Collection<Integer> idDrinks;
		List<Double> amountPizzas;
		List<Double> amountComplements;
		List<Double> amountDesserts;
		List<Double> amountDrinks;
		Offer offer;
		Integer totalBefore;
		
		totalBefore = salesOrderService.findAll().size();
		
		offer = offerService.findOne(43);
		
		amountPizzas = new ArrayList<Double>();
		amountComplements = new ArrayList<Double>();
		amountDesserts = new ArrayList<Double>();
		amountDrinks = new ArrayList<Double>();
		idPizzas = productService.findAllIdsPizzasMin(5);
		idComplements = productService.findAllIdsComplementsMin(5);
		idDesserts = productService.findAllIdsDessertsMin(5);
		idDrinks = productService.findAllIdsDrinksMin(5);
			
		// Iniciamos la cantida de cada producto pedido a 0
		for(int i=0;i<idPizzas.size();i++)
			amountPizzas.add(0.0);
		for(int i=0;i<idComplements.size();i++)
			amountComplements.add(0.0);
		for(int i=0;i<idDrinks.size();i++)
			amountDrinks.add(0.0);
		for(int i=0;i<idDesserts.size();i++)
			amountDesserts.add(0.0);
		
		salesOrderForm = salesOrderService.createForm();
		
		salesOrderForm = salesOrderService.setAllIdProducts(
				salesOrderForm, idPizzas, idComplements, idDesserts, idDrinks);
		
		// Estos valores vendrian rellenos de la vista
		salesOrderForm.setAmountPizzas(amountPizzas);
		salesOrderForm.setAmountComplements(amountComplements);
		salesOrderForm.setAmountDrinks(amountDrinks);
		salesOrderForm.setAmountDesserts(amountDesserts);
		
		// Oferta 1
		salesOrderForm.setOffer(offer);
		
		// Elegimos 5 pizzas barbacoas (5 x 12 = 60)
		amountPizzas.set(0, 62.0);
		
		// Coste total con oferta aplicada (aunque se recalcula en el servicio)
		salesOrderForm.setTotalCost(60.0);
		
		salesOrder = salesOrderService.reconstruct(salesOrderForm);
		salesOrderService.save(salesOrder, true);
		
		Assert.isTrue(totalBefore + 1 == salesOrderService.findAll().size());
		
		unauthenticate();
	}
	
	// Test crear pedido con oferta
	// Error: Pedido vacio
	@Test(expected = IllegalArgumentException.class)
	public void testCreateSalesOrderByCustomerWithOfferEmpty() {
		authenticate("customer1");
				
		SalesOrderForm salesOrderForm;
		SalesOrder salesOrder;
				
		Collection<Integer> idPizzas;
		Collection<Integer> idComplements;
		Collection<Integer> idDesserts;
		Collection<Integer> idDrinks;
		List<Double> amountPizzas;
		List<Double> amountComplements;
		List<Double> amountDesserts;
		List<Double> amountDrinks;
		Offer offer;
		Integer totalBefore;
		
		totalBefore = salesOrderService.findAll().size();
		
		offer = offerService.findOne(43);
		
		amountPizzas = new ArrayList<Double>();
		amountComplements = new ArrayList<Double>();
		amountDesserts = new ArrayList<Double>();
		amountDrinks = new ArrayList<Double>();
		idPizzas = productService.findAllIdsPizzasMin(5);
		idComplements = productService.findAllIdsComplementsMin(5);
		idDesserts = productService.findAllIdsDessertsMin(5);
		idDrinks = productService.findAllIdsDrinksMin(5);
			
		// Iniciamos la cantida de cada producto pedido a 0
		for(int i=0;i<idPizzas.size();i++)
			amountPizzas.add(0.0);
		for(int i=0;i<idComplements.size();i++)
			amountComplements.add(0.0);
		for(int i=0;i<idDrinks.size();i++)
			amountDrinks.add(0.0);
		for(int i=0;i<idDesserts.size();i++)
			amountDesserts.add(0.0);
		
		salesOrderForm = salesOrderService.createForm();
		
		salesOrderForm = salesOrderService.setAllIdProducts(
				salesOrderForm, idPizzas, idComplements, idDesserts, idDrinks);
		
		// Estos valores vendrian rellenos de la vista
		salesOrderForm.setAmountPizzas(amountPizzas);
		salesOrderForm.setAmountComplements(amountComplements);
		salesOrderForm.setAmountDrinks(amountDrinks);
		salesOrderForm.setAmountDesserts(amountDesserts);
		
		// Oferta 1
		salesOrderForm.setOffer(offer);
		
		// Coste total con oferta aplicada (aunque se recalcula en el servicio)
		salesOrderForm.setTotalCost(10.0);
		
		salesOrder = salesOrderService.reconstruct(salesOrderForm);
		salesOrderService.save(salesOrder, true);
		
		Assert.isTrue(totalBefore + 1 == salesOrderService.findAll().size());
		
		unauthenticate();
	}
	
	// Test listar los pedidos de un Customer
	// Error: Autenticado como Boss
	@Test(expected = IllegalArgumentException.class)
	public void testListSalesOrdersByCustomer() {
		authenticate("boss1");
			
		Collection<SalesOrder> mySalesOrders;
		
		mySalesOrders = salesOrderService.findAllByCustomerId();

		unauthenticate();
	}
	
	
	// Test asignar un pedido a un Boss
	// Error: Boss ya asignado
	@Test(expected = IllegalArgumentException.class)
	public void testAssignBossAlreadyAssigned() {
		authenticate("boss1");
				
		SalesOrder salesOrder;
			
		salesOrder = salesOrderService.findOne(61);
			
		bossService.assignBoss(salesOrder.getId());
			
		salesOrder = salesOrderService.findOne(61);
			
		Assert.isTrue(salesOrder.getBoss().getUserAccount().getUsername().equals("boss1"));
				
		unauthenticate();
	}
	
	// Test asignar un pedido a un Boss
	// Error: No autenticado como Boss
	@Test(expected = IllegalArgumentException.class)
	public void testAssignBossBadAuth() {
		authenticate("cook1");
					
		SalesOrder salesOrder;
				
		salesOrder = salesOrderService.findOne(62);
				
		Assert.isTrue(salesOrder.getBoss() == null);
				
		bossService.assignBoss(salesOrder.getId());
				
		salesOrder = salesOrderService.findOne(62);
				
		Assert.isTrue(salesOrder.getBoss().getUserAccount().getUsername().equals("boss1"));
					
		unauthenticate();
	}
		
	// Test cancelar un pedido
	// Error: El pedido esta en estado ONITSWAY
	@Test(expected = IllegalArgumentException.class)
	public void testCancelSalesOrderOnItsWay() {
		authenticate("boss1");
					
		SalesOrder salesOrder;
				
		salesOrder = salesOrderService.findOne(63);
				
		Assert.isTrue(salesOrder.getBoss() == null);
				
		salesOrderService.cancelSalesOrder(salesOrder.getId());
				
		Assert.isNull(salesOrderService.findOne(63));
					
		unauthenticate();
	}
		
	// Test cancelar un pedido 
	// Error: El pedido esta en estado DELIVERED
	@Test(expected = IllegalArgumentException.class)
	public void testCancelSalesOrderDelivered() {
		authenticate("boss1");
						
		SalesOrder salesOrder;
					
		salesOrder = salesOrderService.findOne(65);
					
		salesOrderService.cancelSalesOrder(salesOrder.getId());
					
		Assert.isNull(salesOrderService.findOne(65));
						
		unauthenticate();
	}
	
	// Test poner pedido en estado COOKING asignando un Cook
	// Error: Cook ya asignado
	@Test(expected = IllegalArgumentException.class)
	public void testAssignCookAndChangeToCookingAlreadyCook() {
		authenticate("cook1");
				
		SalesOrder salesOrder;
			
		salesOrder = salesOrderService.findOne(61);
			
		cookService.assignAndCooking(salesOrder.getId());
			
		salesOrder = salesOrderService.findOne(61);
			
		Assert.isTrue(salesOrder.getCook().getUserAccount().getUsername().equals("cook1"));
		Assert.isTrue(salesOrder.getState().equals("COOKING"));
				
		unauthenticate();
	}
	
	// Test poner pedido en estado COOKING asignando un Cook
	// Error: No autenticado
	@Test(expected = IllegalArgumentException.class)
	public void testAssignCookAndChangeToCookingNotAuth() {
		authenticate(null);
					
		SalesOrder salesOrder;
				
		salesOrder = salesOrderService.findOne(61);
				
		cookService.assignAndCooking(salesOrder.getId());
				
		salesOrder = salesOrderService.findOne(61);
				
		Assert.isTrue(salesOrder.getCook().getUserAccount().getUsername().equals("cook1"));
		Assert.isTrue(salesOrder.getState().equals("COOKING"));
					
		unauthenticate();
	}
	
	// Test poner pedido en estado PREPARED
	// Error: No autenticado como Cook
	@Test(expected = IllegalArgumentException.class)
	public void testChangeSalesOrderToCookingBadAuth() {
		authenticate("deliveryman1");
			
		SalesOrder salesOrder;
		
		salesOrder = salesOrderService.findOne(60);
		
		cookService.prepared(salesOrder.getId());
		
		salesOrder = salesOrderService.findOne(60);
		
		Assert.isTrue(salesOrder.getState().equals("PREPARED"));
			
		unauthenticate();
	}
	
	// Test poner pedido en estado ONITSWAY asignando un DeliveryMan
	// Error: DeliveryMan tiene pedidos en estado ONITSWAY
	@Test(expected = IllegalArgumentException.class)
	public void testAssignDeliveryManAndChangeSalesOrderToOnItsWay() {
		authenticate("deliveryman1");
		
		SalesOrder salesOrder;
		
		salesOrder = salesOrderService.findOne(62);
			
		deliveryManService.onItsWay(salesOrder.getId());
			
		salesOrder = salesOrderService.findOne(62);
			
		Assert.isTrue(salesOrder.getState().equals("ONITSWAY"));
				
		unauthenticate();
	}
	
	// Test para finalizar un pedido con estado DELIVERED y rellenar tiempo conduccion
	// Error: No rellenado el tiempo de conduccion
	@Test(expected = TransactionSystemException.class)
	public void testFinishDeliveredSalesOrderDrivingTimeEmpty() {
		authenticate("deliveryman1");
		
		SalesOrder salesOrder;
		
		salesOrder = salesOrderService.findOne(63);
		
		DrivingTimeForm drivingTimeForm;
		
		drivingTimeForm = new DrivingTimeForm();
		salesOrder = salesOrderService.findOneCheckDeliveryMan(salesOrder.getId(), "ONITSWAY");
		
		drivingTimeForm.setSalesOrderId(salesOrder.getId());
		
		salesOrder = salesOrderService.reconstructDrivingTime(drivingTimeForm);
		salesOrderService.saveByDeliveryMan(salesOrder, true);
		
		unauthenticate();
	}
	
	// Test para finalizar un pedido con estado UNDELIVERED, rellenar tiempo conduccion y 
	// Error: Pedido ya finalizado
	@Test(expected = IllegalArgumentException.class)
	public void testFinishUndeliveredSalesOrderAlreadyFinish() {
		authenticate("deliveryman1");
			
		SalesOrder salesOrder;
			
		salesOrder = salesOrderService.findOne(65);
			
		NoteDrivingTimeForm noteDrivingTimeForm;
			
		noteDrivingTimeForm = new NoteDrivingTimeForm();
		salesOrder = salesOrderService.findOneCheckDeliveryMan(salesOrder.getId(), "ONITSWAY");
			
		noteDrivingTimeForm.setSalesOrderId(salesOrder.getId());
		noteDrivingTimeForm.setDrivingTime(30);
		noteDrivingTimeForm.setCause("JOKE");
		noteDrivingTimeForm.setDescription("He escuchado a gente riéndose de mi y no me han abierto");
			
		salesOrder = salesOrderService.reconstructNoteDrivingTime(noteDrivingTimeForm);
		salesOrderService.saveByDeliveryMan(salesOrder, true);
			
		salesOrder = salesOrderService.findOne(63);
			
		Assert.isTrue(salesOrder.getState().equals("UNDELIVERED") && salesOrder.getDrivingTime()== 30);
		Assert.isTrue(salesOrder.getNote().getCause().equals("JOKE") && 
				salesOrder.getNote().getDescription().equals("He escuchado a gente riéndose de mi y no me han abierto"));
		
		unauthenticate();
	}
	
	// Test para ver las notas de un pedido
	// Error: Autenticado como Cook
	@Test(expected = IllegalArgumentException.class)
	public void testShowNote() {
		authenticate("cook1");
			
		Note note;
		SalesOrder salesOrder;
					
		salesOrder = salesOrderService.findOne(66);
		note = salesOrderService.note(salesOrder.getId());
			
		Assert.isTrue(note.getCause().equals("JOKE"));
		Assert.isTrue(note.getDescription().equals("Se escuchaba gente riendose y no han querido abrir la puerta"));
		
		unauthenticate();
	}
}
