package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Product;

import repositories.ProductRepository;

@Service
@Transactional
public class ProductService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ProductRepository productRepository;

	// Ancillary services -----------------------------------------------------
	
	// Constructor ------------------------------------------------------------
	public ProductService(){
		super();
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
	
	// Ancillary methods ------------------------------------------------------

}