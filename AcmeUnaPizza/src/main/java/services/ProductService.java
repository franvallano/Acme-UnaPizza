package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Garage;
import domain.Motorbike;
import domain.Product;
import domain.Provider;

import repositories.ProductRepository;

@Service
@Transactional
public class ProductService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ProductRepository productRepository;

	// Ancillary services -----------------------------------------------------
	@Autowired
	private AdministratorService administratorService;
	
	// Constructor ------------------------------------------------------------
	public ProductService(){
		super();
	}
	
	public Product create(){
		Product product;
		Provider provider; 
		
		administratorService.findByPrincipal();
		
		product = new Product();
		provider = new Provider();
		
		product.setProvider(provider);
		
		return product;
	}
	
	public void save(Product product){
		Assert.notNull(product);
		
		Assert.isTrue(product.getSalePrice() >= product.getStockPrice());
		
		this.productRepository.save(product);
	}

	public Collection<Product> findMoreSoldPizza() {
		Collection<Product> result;
		
		result = productRepository.findMoreSoldPizza();
		
		Assert.notNull(result);
		
		return result;
	}
	public Collection<Product> findLessSoldPizza() {
		Collection<Product> result;
		
		result = productRepository.findLessSoldPizza();
		
		Assert.notNull(result);
		
		return result;
	}
	public Collection<Product> findMoreSoldComplement() {
		Collection<Product> result;
		
		result = productRepository.findMoreSoldComplement();
		
		Assert.notNull(result);
		
		return result;
	}
	public Collection<Product> findLessSoldComplement() {
		Collection<Product> result;
		
		result = productRepository.findLessSoldComplement();
		
		Assert.notNull(result);
		
		return result;
	}
	public Collection<Product> findMoreSoldDrink() {
		Collection<Product> result;
		
		result = productRepository.findMoreSoldDrink();
		
		Assert.notNull(result);
		
		return result;
	}
	public Collection<Product> findLessSoldDrink() {
		Collection<Product> result;
		
		result = productRepository.findLessSoldDrink();
		
		Assert.notNull(result);
		
		return result;
	}
	public Collection<Product> findMoreSoldDessert() {
		Collection<Product> result;
		
		result = productRepository.findMoreSoldDessert();
		
		Assert.notNull(result);
		
		return result;
	}
	public Collection<Product> findLessSoldDessert() {
		Collection<Product> result;
		
		result = productRepository.findLessSoldDessert();
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Product> findStockMinPizzas() {
		Collection<Product> result;
		
		result = productRepository.findStockMinPizzas();
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Product> findStockMinComplements() {
		Collection<Product> result;
		
		result = productRepository.findStockMinComplements();
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Product> findStockMinDesserts() {
		Collection<Product> result;
		
		result = productRepository.findStockMinDesserts();
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Product> findStockMinDrinks() {
		Collection<Product> result;
		
		result = productRepository.findStockMinDrinks();
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Product> findAllPizzas() {
		Collection<Product> result;
		
		result = productRepository.findAllPizzas();
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Product> findAllComplements() {
		Collection<Product> result;
		
		result = productRepository.findAllComplements();
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Product> findAllDesserts() {
		Collection<Product> result;
		
		result = productRepository.findAllDesserts();
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Product> findAllDrinks() {
		Collection<Product> result;
		
		result = productRepository.findAllDrinks();
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Product findOne(int id){
		Assert.isTrue(id != 0);
		
		Product res;
		
		res = this.productRepository.findOne(id);
		
		Assert.notNull(res);
		
		return res;
	}
	
	// Ancillary methods ------------------------------------------------------

}