package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PurchaseOrderRepository;
import utilities.PatternGenerator;
import domain.Administrator;
import domain.Product;
import domain.PurchaseOrder;
import forms.PurchaseOrderForm;

@Service
@Transactional
public class PurchaseOrderService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;

	// Ancillary services -----------------------------------------------------
	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private ProductService productService;
	
	// Constructor ------------------------------------------------------------
	public PurchaseOrderService(){
		super();
	}
	
	public PurchaseOrderForm createForm() {
		PurchaseOrderForm purchaseOrderForm;
		Date date;

		administratorService.findByPrincipal();
		
		date = new Date(System.currentTimeMillis()-1);
		purchaseOrderForm = new PurchaseOrderForm();
		purchaseOrderForm.setCreationMoment(date);
		purchaseOrderForm.setReferenceNumber(PatternGenerator.purchaseOrderReferenceNumber());
		purchaseOrderForm.setTotalCost(0.0);
		
		return purchaseOrderForm;
	}
	
	public PurchaseOrder create() {
		PurchaseOrder purchaseOrder;
		Collection<Product> products;
		Administrator administrator;
		Date date;
		
		administrator = administratorService.findByPrincipal();
		
		date = new Date(System.currentTimeMillis()-1);
		purchaseOrder = new PurchaseOrder();
		products = new ArrayList<Product>();
		
		purchaseOrder.setProducts(products);
		purchaseOrder.setCreationMoment(date);
		purchaseOrder.setAdministrator(administrator);
		
		return purchaseOrder;
	}

	public Collection<PurchaseOrder> findAll() {
		Collection<PurchaseOrder> result;
		
		result = purchaseOrderRepository.findAll();
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Double findInvestedMoney() {
		Double result;
		
		result = purchaseOrderRepository.findInvestedMoney();
		
		Assert.notNull(result);
		
		return result;
	}
	
	public PurchaseOrder findOne(int id) {
		Assert.isTrue(id != 0);
		
		PurchaseOrder res;
		
		res = this.purchaseOrderRepository.findOne(id);
		
		Assert.notNull(res);
		
		return res;
	}
	
	// Metodo que recibe un objeto formulario y reconstruye un objeto de dominio
	public PurchaseOrder reconstruct(PurchaseOrderForm purchaseOrderForm) {
		Assert.notNull(purchaseOrderForm);
		Assert.isTrue(purchaseOrderForm.getTotalCost() > 0.0);
		PurchaseOrder purchaseOrder;
		Double totalCost = 0.0;

		purchaseOrder = create();

		Assert.notNull(purchaseOrder);
		
		purchaseOrder.setReferenceNumber(purchaseOrderForm.getReferenceNumber());
		purchaseOrder.setTotalCost(purchaseOrderForm.getTotalCost());
		
		Assert.isTrue(purchaseOrderForm.getIdPizzas().size() == purchaseOrderForm.getAmountPizzas().size());
		Assert.isTrue(purchaseOrderForm.getIdDrinks().size() == purchaseOrderForm.getAmountDrinks().size());
		Assert.isTrue(purchaseOrderForm.getIdComplements().size() == purchaseOrderForm.getAmountComplements().size());
		Assert.isTrue(purchaseOrderForm.getIdDesserts().size() == purchaseOrderForm.getAmountDesserts().size());
		
		int index = 0;
		
		for(Integer id : purchaseOrderForm.getIdPizzas()) {
			Double costProduct = purchaseOrderForm.getAmountPizzas().get(index);
			
			if(costProduct != 0.0) {
				Product product = productService.findOne(id);

				int amountProduct = (int)(costProduct/product.getStockPrice());
				totalCost += costProduct;
				
				for(int i=0;i<amountProduct;i++)
					purchaseOrder.getProducts().add(product);
				
				product.setActualStock(product.getActualStock() + amountProduct);
				
				productService.save(product);
			}
			
			index++;
		}
		
		index = 0;
		
		for(Integer id : purchaseOrderForm.getIdComplements()) {
			Double costProduct = purchaseOrderForm.getAmountComplements().get(index);
			
			if(costProduct != 0.0) {
				Product product = productService.findOne(id);

				int amountProduct = (int)(costProduct/product.getStockPrice());
				totalCost += costProduct;
				
				for(int i=0;i<amountProduct;i++)
					purchaseOrder.getProducts().add(product);
				
				product.setActualStock(product.getActualStock() + amountProduct);
				
				productService.save(product);
			}
			
			index++;
		}
		
		index = 0;
		
		for(Integer id : purchaseOrderForm.getIdDesserts()) {
			Double costProduct = purchaseOrderForm.getAmountDesserts().get(index);
			
			if(costProduct != 0.0) {
				Product product = productService.findOne(id);

				int amountProduct = (int)(costProduct/product.getStockPrice());
				totalCost += costProduct;
				
				for(int i=0;i<amountProduct;i++)
					purchaseOrder.getProducts().add(product);
				
				product.setActualStock(product.getActualStock() + amountProduct);
				
				productService.save(product);
			}
			
			index++;
		}
		
		index = 0;
		
		for(Integer id : purchaseOrderForm.getIdDrinks()) {
			Double costProduct = purchaseOrderForm.getAmountDrinks().get(index);
			
			if(costProduct != 0.0) {
				Product product = productService.findOne(id);

				int amountProduct = (int)(costProduct/product.getStockPrice());
				totalCost += costProduct;
				
				for(int i=0;i<amountProduct;i++)
					purchaseOrder.getProducts().add(product);
				
				product.setActualStock(product.getActualStock() + amountProduct);
				
				productService.save(product);
			}
			
			index++;
		}
		
		Assert.isTrue(purchaseOrder.getTotalCost() == totalCost);
		
		return purchaseOrder;
	}
	
	public void save(PurchaseOrder purchaseOrder) {
		Assert.notNull(purchaseOrder);
		Assert.isTrue(purchaseOrder.getTotalCost() > 0.0);
		Assert.isTrue(purchaseOrder.getAdministrator().getId() == administratorService.findByPrincipal().getId());
		
		Date date;
		
		date = new Date(System.currentTimeMillis()-1);
		
		purchaseOrder.setCreationMoment(date);
		
		purchaseOrderRepository.save(purchaseOrder);
	}
	
	public Collection<PurchaseOrder> findPurchaseOrdersMoreExpensive() {
		Collection<PurchaseOrder> result;
		
		result = purchaseOrderRepository.findPurchaseOrdersMoreExpensive();
		
		Assert.notNull(result);
		
		return result;
	}
	
	// Cantidad total de productos a pedir para rellenar el almacen
	public List<Integer> getTotalAmount() {
		List<Integer> res;
		
		res = new ArrayList<Integer>();
		
		for(int i=1;i<=51;i++)
			res.add(i);
		
		return res;
	}
	
	public PurchaseOrderForm setAllIdProducts(PurchaseOrderForm purchaseOrderForm, Collection<Integer> idPizzas,
			Collection<Integer> idComplements, Collection<Integer> idDesserts, Collection<Integer> idDrinks) {
		
		purchaseOrderForm.setIdPizzas(idPizzas);
		purchaseOrderForm.setIdComplements(idComplements);
		purchaseOrderForm.setIdDesserts(idDesserts);
		purchaseOrderForm.setIdDrinks(idDrinks);
		
		return purchaseOrderForm;

	}
	// Ancillary methods ------------------------------------------------------

}