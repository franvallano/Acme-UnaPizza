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
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class SalesOrderServiceTestPositive extends AbstractTest {
	@Autowired
	private SalesOrderService salesOrderService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private OfferService offerService;
	@Autowired
	private ProductService productService;
	@Autowired
	private BossService bossService;
	@Autowired
	private CookService cookService;
	@Autowired
	private DeliveryManService deliveryManService;
	
	// Test crear pedido sin oferta
	// Autenticado como Customer
	@Test
	public void testCreateSalesOrderByCustomerWithoutOffer() {
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
		salesOrderForm.setTotalCost(67.0);
		
		salesOrder = salesOrderService.reconstruct(salesOrderForm);
		salesOrderService.save(salesOrder, true);
		
		Assert.isTrue(totalBefore + 1 == salesOrderService.findAll().size());
		
		unauthenticate();
	}
	
	// Test crear pedido con oferta
	// Autenticado como Customer
	@Test
	public void testCreateSalesOrderByCustomerWithOffer() {
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
		List<Offer> availableOffers;
		String range;
		Integer totalBefore;
		
		range = customerService.findByPrincipal().getRangee();
		
		totalBefore = salesOrderService.findAll().size();
		
		availableOffers = new ArrayList<Offer>(offerService.findOffersByRange(range));
		
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
		salesOrderForm.setOffer(availableOffers.get(0));
		
		// Elegimos 2 pizzas barbacoas (2 x 12 = 24)
		amountPizzas.set(0, 24.0);
		
		// Elegimos 3 delicias de pollo (3 x 4 = 12)
		amountComplements.set(1, 12.0);
		
		// Elegimos 2 cocacolas y 2 fantas de naranja (2 x 1.5 = 3 cada una)
		amountDrinks.set(0, 3.0);
		amountDrinks.set(1, 3.0);
		
		// Elegimos 5 helados de fresa (5 x 5 = 25)
		amountDesserts.set(0, 25.0);
		
		// Coste total con oferta aplicada (aunque se recalcula en el servicio)
		salesOrderForm.setTotalCost(53.6);
		
		salesOrder = salesOrderService.reconstruct(salesOrderForm);
		salesOrderService.save(salesOrder, true);
		
		Assert.isTrue(totalBefore + 1 == salesOrderService.findAll().size());
		
		unauthenticate();
	}
	
	// Test listar los pedidos de un Customer
	// Autenticado como Customer
	@Test
	public void testListSalesOrdersByCustomer() {
		authenticate("customer1");
		
		Collection<SalesOrder> mySalesOrders;
		
		mySalesOrders = salesOrderService.findAllByCustomerId();
		
		Assert.isTrue(mySalesOrders.size() == 3);
		
		unauthenticate();
	}
	
	// Test asignar un pedido a un Boss
	// Autenticado como Boss
	@Test
	public void testAssignBoss() {
		authenticate("boss1");
			
		SalesOrder salesOrder;
		
		salesOrder = salesOrderService.findOne(60);
		
		Assert.isTrue(salesOrder.getBoss() == null);
		
		bossService.assignBoss(salesOrder.getId());
		
		salesOrder = salesOrderService.findOne(60);
		
		Assert.isTrue(salesOrder.getBoss().getUserAccount().getUsername().equals("boss1"));
			
		unauthenticate();
	}
	
	// Test cancelar un pedido en estado OPEN
	// Autenticado como Boss
	@Test
	public void testCancelSalesOrderOpen() {
		authenticate("boss1");
				
		SalesOrder salesOrder;
			
		salesOrder = salesOrderService.findOne(60);
			
		Assert.isTrue(salesOrder.getBoss() == null);
			
		salesOrderService.cancelSalesOrder(salesOrder.getId());
			
		Assert.isNull(salesOrderService.findOne(60));
				
		unauthenticate();
	}
	
	// Test cancelar un pedido en estado COOKING
	// Autenticado como Boss
	@Test
	public void testCancelSalesOrderCooking() {
		authenticate("boss1");
					
		SalesOrder salesOrder;
				
		salesOrder = salesOrderService.findOne(61);
				
		Assert.isTrue(salesOrder.getState().equals("COOKING"));
				
		salesOrderService.cancelSalesOrder(salesOrder.getId());
				
		Assert.isNull(salesOrderService.findOne(61));
					
		unauthenticate();
	}
	
	// Test cancelar un pedido en estado PREPARED
	// Autenticado como Boss
	@Test
	public void testCancelSalesOrderToPrepared() {
		authenticate("boss1");
					
		SalesOrder salesOrder;
					
		salesOrder = salesOrderService.findOne(62);
					
		Assert.isTrue(salesOrder.getState().equals("PREPARED"));
					
		salesOrderService.cancelSalesOrder(salesOrder.getId());
					
		Assert.isNull(salesOrderService.findOne(62));
						
		unauthenticate();
	}
	
	// Test poner pedido en estado COOKING asignando un Cook
	// Autenticado como Cook
	@Test
	public void testAssignCookAndChangeToCooking() {
		authenticate("cook1");
				
		SalesOrder salesOrder;
			
		salesOrder = salesOrderService.findOne(60);
			
		cookService.assignAndCooking(salesOrder.getId());
			
		salesOrder = salesOrderService.findOne(60);
			
		Assert.isTrue(salesOrder.getCook().getUserAccount().getUsername().equals("cook1"));
		Assert.isTrue(salesOrder.getState().equals("COOKING"));
				
		unauthenticate();
	}
	
	// Test poner pedido en estado PREPARED
	// Autenticado como Cook
	@Test
	public void testChangeSalesOrderToCooking() {
		authenticate("cook1");
			
		SalesOrder salesOrder;
		
		salesOrder = salesOrderService.findOne(61);
		
		cookService.prepared(salesOrder.getId());
		
		salesOrder = salesOrderService.findOne(61);
		
		Assert.isTrue(salesOrder.getState().equals("PREPARED"));
			
		unauthenticate();
	}
	
	// Test poner pedido en estado ONITSWAY asignando un DeliveryMan
	// NOTA: Como el populate tiene los dos deliveryman en reparto, simularemos
	// entregar uno antes
	// Autenticado como DeliveryMan
	@Test
	public void testAssignDeliveryManAndChangeSalesOrderToOnItsWay() {
		authenticate("deliveryman1");
		
		SalesOrder salesOrder;
		
		salesOrder = salesOrderService.findOne(63);
		
		DrivingTimeForm drivingTimeForm;
		
		drivingTimeForm = new DrivingTimeForm();
		salesOrder = salesOrderService.findOneCheckDeliveryMan(salesOrder.getId(), "ONITSWAY");
		
		drivingTimeForm.setSalesOrderId(salesOrder.getId());
		drivingTimeForm.setDrivingTime(30);
		
		salesOrder = salesOrderService.reconstructDrivingTime(drivingTimeForm);
		// Finalizamos un pedido
		salesOrderService.saveByDeliveryMan(salesOrder, true);
		
		// Nos poemos en reparto con otro
		salesOrder = salesOrderService.findOne(62);
			
		deliveryManService.onItsWay(salesOrder.getId());
			
		salesOrder = salesOrderService.findOne(62);
			
		Assert.isTrue(salesOrder.getState().equals("ONITSWAY"));
				
		unauthenticate();
	}
	
	// Test para finalizar un pedido con estado DELIVERED y rellenar tiempo conduccion
	// Autenticado como DeliveryMan
	@Test
	public void testFinishDeliveredSalesOrder() {
		authenticate("deliveryman1");
		
		SalesOrder salesOrder;
		
		salesOrder = salesOrderService.findOne(63);
		
		DrivingTimeForm drivingTimeForm;
		
		drivingTimeForm = new DrivingTimeForm();
		salesOrder = salesOrderService.findOneCheckDeliveryMan(salesOrder.getId(), "ONITSWAY");
		
		drivingTimeForm.setSalesOrderId(salesOrder.getId());
		drivingTimeForm.setDrivingTime(30);
		
		salesOrder = salesOrderService.reconstructDrivingTime(drivingTimeForm);
		salesOrderService.saveByDeliveryMan(salesOrder, true);
		
		salesOrder = salesOrderService.findOne(63);
		
		Assert.isTrue(salesOrder.getState().equals("DELIVERED") && salesOrder.getDrivingTime()== 30);
		
		unauthenticate();
	}
	
	// Test para finalizar un pedido con estado UNDELIVERED, rellenar tiempo conduccion y 
	// la causa de la no entrega
	// Autenticado como DeliveryMan
	@Test
	public void testFinishUndeliveredSalesOrder() {
		authenticate("deliveryman1");
			
		SalesOrder salesOrder;
			
		salesOrder = salesOrderService.findOne(63);
			
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
	// Autenticado como Boss
	@Test
	public void testShowNote() {
		authenticate("boss1");
		
		Note note;
		SalesOrder salesOrder;
				
		salesOrder = salesOrderService.findOne(66);
		note = salesOrderService.note(salesOrder.getId());
		
		Assert.isTrue(note.getCause().equals("JOKE"));
		Assert.isTrue(note.getDescription().equals("Se escuchaba gente riendose y no han querido abrir la puerta"));
		
		unauthenticate();
	}
}
