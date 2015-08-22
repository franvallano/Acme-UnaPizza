package services;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SalesOrderRepository;
import utilities.PatternGenerator;
import domain.Boss;
import domain.Cook;
import domain.Customer;
import domain.DeliveryMan;
import domain.Note;
import domain.Product;
import domain.SalesOrder;
import domain.Staff;
import forms.DrivingTimeForm;
import forms.NoteDrivingTimeForm;
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
	private DeliveryManService deliveryManService;
	
	@Autowired
	private CookService cookService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private BossService bossService;
	
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
	
	public Collection<SalesOrder> findAllForCooking(){
		Collection<SalesOrder> res;
		
		cookService.findByPrincipal();
		
		res = salesOrderRepository.findAllForCooking();
		
		return res;
	}
	
	public Collection<SalesOrder> findAllOnItsWay(){
		Collection<SalesOrder> res;
		DeliveryMan deliveryMan; 
		boolean isOnItsWay = false;
		
		deliveryMan = deliveryManService.findByPrincipal();
		
		// Comprobamos que NO tenga ningun pedido en reparto
		for(SalesOrder salesOrder : deliveryMan.getSalesOrders()) {
			if(salesOrder.getState().equals("ONITSWAY")) {
				isOnItsWay = true;
				break;
			}
		}
		
		if(!isOnItsWay)
			res = salesOrderRepository.findAllOnItsWay();
		else
			res = new ArrayList<SalesOrder>();

		return res;
	}
	
	public Collection<SalesOrder> findOneToFinish() {
		Collection<SalesOrder> res;
		DeliveryMan deliveryMan;
		
		deliveryMan = deliveryManService.findByPrincipal();
		
		res = salesOrderRepository.findOneToFinish(deliveryMan.getId());
		
		// Comprobamos que no venga mas de un pedido en estado ONITSWAY
		Assert.isTrue(res.size() <= 1);
		
		return res;
	}
	
	public Collection<SalesOrder> findAllForPrepared(){
		Collection<SalesOrder> res;
		Cook cook;
		
		cook = cookService.findByPrincipal();
		
		res = salesOrderRepository.findAllForPrepared(cook.getId());
		
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
	
	public SalesOrder findOneCheckBoss(int id) {
		Assert.isTrue(id != 0);
		
		SalesOrder res;
		
		res = this.salesOrderRepository.findOne(id);
		
		bossService.findByPrincipal();
		
		Assert.notNull(res);
		
		return res;
	}
	
	public SalesOrder findOneCheckCook(int id) {
		Assert.isTrue(id != 0);
		
		SalesOrder res;
		
		res = this.salesOrderRepository.findOne(id);
		
		cookService.findByPrincipal();
		
		Assert.notNull(res);
		
		return res;
	}
	
	public SalesOrder findOneCheckDeliveryMan(int id, String state) {
		Assert.isTrue(id != 0);
		
		SalesOrder res;
		
		res = this.salesOrderRepository.findOne(id);
		
		deliveryManService.findByPrincipal();
		
		Assert.notNull(res);
		
		if(res.getDeliveryMan() != null)
			Assert.isTrue(res.getDeliveryMan().getId() == deliveryManService.findByPrincipal().getId());
		Assert.isTrue(res.getId() == id);
		
		if(!state.equals(""))
			Assert.isTrue(res.getState().equals(state));
		
		return res;
	}
	
	public SalesOrder findOneByCustomer(int id) {
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
			Double totalCostOffer = (salesOrder.getTotalCost() * ((100-salesOrderForm.getOffer().getDiscount())/100.0));
			DecimalFormat df = new DecimalFormat("#.##");      
			totalCostOffer = Double.valueOf(df.format(totalCostOffer));
			salesOrder.setTotalCost(totalCostOffer);
			salesOrder.setOffer(salesOrderForm.getOffer()); 
		} else
			Assert.isTrue(salesOrder.getTotalCost() == totalCost);

		return salesOrder;
	}
	
	public void save(SalesOrder saleseOrder, boolean newSalesOrder) {
		Assert.notNull(saleseOrder);
		Assert.isTrue(saleseOrder.getTotalCost() > 0.0);
		Customer customer;
		customer = customerService.findByPrincipal();
		Assert.isTrue(saleseOrder.getCustomer().getId() == customer.getId());
		
		customerService.checkRangeCustomer(customer, newSalesOrder);
		
		Date date;
		
		date = new Date(System.currentTimeMillis()-1);
		
		saleseOrder.setCreationMoment(date);
		
		salesOrderRepository.save(saleseOrder);
	}
	
	public void saveAssignBoss(SalesOrder saleseOrder) {
		Assert.notNull(saleseOrder);
		
		salesOrderRepository.save(saleseOrder);
	}

	public Collection<SalesOrder> findAllSalesOrderUndelivered() {
		Collection<SalesOrder> res;
		
		bossService.findByPrincipal();
		
		res = salesOrderRepository.findAllSalesOrderUndelivered();
		
		return res;
	}
	
	public Collection<SalesOrder> findAllSalesOrderDelivered() {
		Collection<SalesOrder> res;
		
		bossService.findByPrincipal();
		
		res = salesOrderRepository.findAllSalesOrderDelivered();
		
		return res;
	}
	
	public Collection<SalesOrder> findAllSalesOrderOpened() {
		Collection<SalesOrder> res;
		
		bossService.findByPrincipal();
		
		res = salesOrderRepository.findAllSalesOrderOpened();
		
		return res;
	}
	
	public Collection<SalesOrder> findAllSalesOrderInProcess() {
		Collection<SalesOrder> res;
		
		bossService.findByPrincipal();
		
		res = salesOrderRepository.findAllSalesOrderInProcess();
		
		return res;
	}
	
	public void saveByCook(SalesOrder saleseOrder) {
		Assert.notNull(saleseOrder);
		
		salesOrderRepository.save(saleseOrder);
	}
	
	public void saveByDeliveryMan(SalesOrder saleseOrder) {
		Assert.notNull(saleseOrder);
		
		salesOrderRepository.save(saleseOrder);
	}
	
	public Collection<SalesOrder> findMySalesOrders() {
		Collection<SalesOrder> res = null;
		Staff staff;
		
		staff = staffService.findByPrincipal();
		
		Assert.notNull(staff);
		
		if(staffService.isBoss())
			res = salesOrderRepository.findMySalesOrdersBoss(staff.getId());
		else if(staffService.isDeliveryMan())
			res = salesOrderRepository.findMySalesOrdersDeliveryMan(staff.getId());
		else if(staffService.isCook())
			res = salesOrderRepository.findMySalesOrdersCook(staff.getId());
		
		Assert.notNull(res);
		
		return res;
	}
	
	public void cancelSalesOrder(int salesOrderId) {
		Assert.isTrue(salesOrderId != 0);

		SalesOrder salesOrder;
		Boss boss;
		Customer customer;
		
		salesOrder = findOneCheckBoss(salesOrderId);
		
		Assert.notNull(salesOrder);
		Assert.isTrue(salesOrder.getState().equals("OPEN") || salesOrder.getState().equals("COOKING") || salesOrder.getState().equals("PREPARED"));
		Assert.isTrue(salesOrder.getId() == salesOrderId);
		
		// Comprobamos si tiene algun Boss asociado
		boss = bossService.findBossBySalesOrder(salesOrderId);
				
		if(boss != null) {
			boss.getSalesOrders().remove(salesOrder);
			bossService.save(boss);
		}
		
		// Customer asociada tendra siempre
		customer = customerService.findCustomerBySalesOrder(salesOrderId);
		Assert.notNull(customer);
		
		customerService.checkRangeCustomer(customer, false);
		
		customer.getSalesOrders().remove(salesOrder);
		customerService.save(customer);
		
		// Si esta en estado OPEN volveremos a sumar los productos del pedido
		// Si esta en estado COOKING o PREPARED solo añadiremos helados y bebidas
		if(salesOrder.getState().equals("OPEN")) {
			for(Product product : salesOrder.getProducts()) {
				product.setActualStock(product.getActualStock() + 1);
				
				productService.saveCancelProduct(product);
			}
		} else {
			for(Product product : salesOrder.getProducts()) {
				if(product.getType().equals("DESSERT") || product.getType().equals("DRINK")) {
					product.setActualStock(product.getActualStock() + 1);
					
					productService.saveCancelProduct(product);
				}
			}
		}
		
		// Por ultimo, eliminamos todas las relaciones de Sales Order
		salesOrder.setBoss(null);
		salesOrder.setCustomer(null);
				
		salesOrderRepository.delete(salesOrder);
	}
	
	public SalesOrder reconstructDrivingTime(DrivingTimeForm drivingTimeForm) {
		Assert.notNull(drivingTimeForm);
		SalesOrder result;
		
		result = findOneCheckDeliveryMan(drivingTimeForm.getSalesOrderId(), "");
		
		Assert.isTrue(result.getDeliveryMan().getId() == deliveryManService.findByPrincipal().getId());
		Assert.isTrue(result.getId() == drivingTimeForm.getSalesOrderId());
		Assert.isTrue(result.getState().equals("ONITSWAY"));
		
		
		result.setDrivingTime(drivingTimeForm.getDrivingTime());
		
		result.setState("DELIVERED");
		
		return result;
	}
	
	public SalesOrder reconstructNoteDrivingTime(NoteDrivingTimeForm noteDrivingTimeForm) {
		Assert.notNull(noteDrivingTimeForm);
		SalesOrder result;
		Note note;
		
		result = findOneCheckDeliveryMan(noteDrivingTimeForm.getSalesOrderId(), "");
		
		Assert.isTrue(result.getDeliveryMan().getId() == deliveryManService.findByPrincipal().getId());
		Assert.isTrue(result.getId() == noteDrivingTimeForm.getSalesOrderId());
		Assert.isTrue(result.getState().equals("ONITSWAY"));
		
		note = new Note();
		result.setDrivingTime(noteDrivingTimeForm.getDrivingTime());
		note.setCause(noteDrivingTimeForm.getCause());
		note.setDescription(noteDrivingTimeForm.getDescription());
		
		result.setNote(note);
		result.setState("UNDELIVERED");
		
		return result;
	}
	
	public Note note(int salesOrderId) {
		Assert.isTrue(salesOrderId != 0);
		Note result;
		SalesOrder salesOrder;
		
		salesOrder = findOneCheckBoss(salesOrderId);
		
		Assert.notNull(salesOrder);
		Assert.isTrue(salesOrder.getId() == salesOrderId);
		Assert.isTrue(salesOrder.getState().equals("UNDELIVERED"));
		Assert.notNull(salesOrder.getNote());
		
		result = new Note();
		
		result.setCause(salesOrder.getNote().getCause());
		result.setDescription(salesOrder.getNote().getDescription());
		
		return result;
	}
	
	// Cantidad total de productos a pedir por el cliente de cada producto
	public List<Integer> getTotalAmount() {
		List<Integer> res;
			
		res = new ArrayList<Integer>();
				
		for(int i=1;i<=6;i++)
			res.add(i);
				
		return res;
	}
	
	public SalesOrderForm setAllIdProducts(SalesOrderForm salesOrderForm, Collection<Integer> idPizzas,
			Collection<Integer> idComplements, Collection<Integer> idDesserts, Collection<Integer> idDrinks) {
		
		salesOrderForm.setIdPizzas(idPizzas);
		salesOrderForm.setIdComplements(idComplements);
		salesOrderForm.setIdDesserts(idDesserts);
		salesOrderForm.setIdDrinks(idDrinks);
		
		return salesOrderForm;

	}
	
	public List<String> findCauses() {
		List<String> res;
		
		res = new ArrayList<String>();
		
		res.add("CANCELLED");
		res.add("JOKE");
		res.add("OTHER");
		
		return res;
	}
	
	public Integer findTotalOnItsWayByDeliveryMan(int deliveryManId) {
		Assert.isTrue(deliveryManId != 0);
		
		Integer res;
		
		res = salesOrderRepository.findTotalOnItsWayByDeliveryMan(deliveryManId);
		
		if(res == null)
			res = 0;
		
		return res;
	}
	// Ancillary methods ------------------------------------------------------

}