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

import utilities.AbstractTest;
import domain.Product;
import domain.Provider;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class ProductServiceTestPositive extends AbstractTest {
	@Autowired
	private ProductService productService;
	@Autowired
	private ProviderService providerService;
	
	// Test listar productos juntos y separados
	// Autenticado como Administrator
	@Test
	public void testListAllProducts() {
		authenticate("admin1");
		
		Collection<Product> allProducts;
		Collection<Product> productPizzas;
		Collection<Product> productDesserts;
		Collection<Product> productComplements;
		Collection<Product> productDrinks;
		
		allProducts = productService.findAll();
		productPizzas = productService.findAllPizzas();
		productDesserts = productService.findAllDesserts();
		productComplements = productService.findAllComplements();
		productDrinks = productService.findAllDrinks();
		
		Assert.isTrue(allProducts.size() == 8);
		Assert.isTrue(productPizzas.size() == 2);
		Assert.isTrue(productDesserts.size() == 2);
		Assert.isTrue(productComplements.size() == 2);
		Assert.isTrue(productDrinks.size() == 2);
		
		unauthenticate();
	}
	
	// Test crear nuevo producto
	// Autenticado como Administrator
	@Test
	public void testCreateProduct() {
		authenticate("admin1");
		
		Integer totalBefore;
		Product product;
		Provider provider;
		
		totalBefore = productService.findAll().size();
		product = productService.create();
		provider = providerService.findOne(48);
		
		product.setActualStock(30);
		product.setCode("DLCA");
		product.setDescription("Bacon, champiñones, tomate y verduras");
		product.setMinStock(15);
		product.setName("Especial de la casa");
		product.setProvider(provider);
		product.setSalePrice(8.0);
		product.setStockPrice(4.5);
		product.setType("PIZZA");
		
		productService.save(product);
		
		Assert.isTrue(totalBefore + 1 == productService.findAll().size());
		
		unauthenticate();
	}
	
	// Test editar producto
	// Autenticado como Administrator
	@Test
	public void testEditProduct() {
		authenticate("admin1");
			
		Product product;
		
		product = productService.findOne(50);
		
		product.setName("Hawaiana");
		product.setSalePrice(99.9);
		
		productService.save(product);
		
		product = productService.findOne(50);
		
		Assert.isTrue(product.getName().equals("Hawaiana") && product.getSalePrice() == 99.9);
		
		unauthenticate();
	}
}
