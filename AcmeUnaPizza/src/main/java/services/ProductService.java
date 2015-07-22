package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProductRepository;
import domain.Product;
import domain.Provider;

@Service
@Transactional
public class ProductService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ProductRepository productRepository;

	// Ancillary services -----------------------------------------------------
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private CustomerService customerService;
	
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
		administratorService.findByPrincipal();
		
		Assert.isTrue(product.getSalePrice() > 0.0 && product.getStockPrice() > 0.0);
		Assert.isTrue(product.getSalePrice() >= product.getStockPrice());
		
		this.productRepository.save(product);
	}
	
	public void saveCancelProduct(Product product){
		Assert.notNull(product);
		
		this.productRepository.save(product);
	}
	
	public void saveProductByCustomer(Product product){
		Assert.notNull(product);
		customerService.findByPrincipal();
		
		Assert.isTrue(product.getSalePrice() > 0.0 && product.getStockPrice() > 0.0);
		Assert.isTrue(product.getSalePrice() >= product.getStockPrice());
		
		this.productRepository.save(product);
	}
	
	public Collection<Product> findAll() {
		Collection<Product> result;
		
		result = productRepository.findAll();
		
		Assert.notNull(result);
		
		return result;
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
	
	public Collection<Integer> findAllIdsPizzas() {
		Collection<Integer> result;
		
		result = productRepository.findAllIdsPizzas();
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Integer> findAllIdsComplements() {
		Collection<Integer> result;
		
		result = productRepository.findAllIdsComplements();
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Integer> findAllIdsDesserts() {
		Collection<Integer> result;
		
		result = productRepository.findAllIdsDesserts();
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Integer> findAllIdsDrinks() {
		Collection<Integer> result;
		
		result = productRepository.findAllIdsDrinks();
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Product> findAllPizzasMin(int amountMin) {
		Collection<Product> result;
		
		result = productRepository.findAllPizzasMin(amountMin);
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Product> findAllComplementsMin(int amountMin) {
		Collection<Product> result;
		
		result = productRepository.findAllComplementsMin(amountMin);
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Product> findAllDessertsMin(int amountMin) {
		Collection<Product> result;
		
		result = productRepository.findAllDessertsMin(amountMin);
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Product> findAllDrinksMin(int amountMin) {
		Collection<Product> result;
		
		result = productRepository.findAllDrinksMin(amountMin);
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Integer> findAllIdsPizzasMin(int amountMin) {
		Collection<Integer> result;
		
		result = productRepository.findAllIdsPizzasMin(amountMin);
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Integer> findAllIdsComplementsMin(int amountMin) {
		Collection<Integer> result;
		
		result = productRepository.findAllIdsComplementsMin(amountMin);
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Integer> findAllIdsDessertsMin(int amountMin) {
		Collection<Integer> result;
		
		result = productRepository.findAllIdsDessertsMin(amountMin);
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Integer> findAllIdsDrinksMin(int amountMin) {
		Collection<Integer> result;
		
		result = productRepository.findAllIdsDrinksMin(amountMin);
		
		Assert.notNull(result);
		
		return result;
	}
	// Ancillary methods ------------------------------------------------------

}