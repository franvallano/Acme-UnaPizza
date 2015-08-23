package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Product;
import domain.Provider;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
//@Transactional
//@TransactionConfiguration(defaultRollback = false)
public class ProductServiceTestNegative extends AbstractTest {
	@Autowired
	private ProductService productService;
	@Autowired
	private ProviderService providerService;
	
	// Test crear nuevo producto
	// Error: No autenticado como Administrator
	@Test(expected = IllegalArgumentException.class)
	public void testCreateProductNotAuth() {
		authenticate("boss1");
		
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
	
	// Test crear nuevo producto
	// Error: El código está repetido
	@Test(expected = DataIntegrityViolationException.class)
	public void testCreateProductCodeNotUnique() {
		authenticate("admin1");
			
		Integer totalBefore;
		Product product;
		Provider provider;
			
		totalBefore = productService.findAll().size();
		product = productService.create();
		provider = providerService.findOne(48);
			
		product.setActualStock(30);
		product.setCode("PZBBQ");
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
	// Error: Stock minimo por debajo de 10
	@Test(expected = TransactionSystemException.class)
	public void testEditProductMinStock() {
		authenticate("admin1");
			
		Product product;
			
		product = productService.findOne(50);
			
		product.setName("Hawaiana");
		product.setSalePrice(99.9);
		product.setMinStock(2);
		
		productService.save(product);
			
		product = productService.findOne(50);
			
		Assert.isTrue(product.getName().equals("Hawaiana") && product.getSalePrice() == 99.9);
			
		unauthenticate();
	}
	
	// Test editar producto
	// Error: Precio de venta menor que el precio de stock
	@Test(expected = IllegalArgumentException.class)
	public void testEditProductPrice() {
		authenticate("admin1");
				
		Product product;
				
		product = productService.findOne(50);
				
		product.setName("Hawaiana");
		product.setSalePrice(5.9);
		product.setStockPrice(15.0);
		product.setMinStock(2);
			
		productService.save(product);
				
		product = productService.findOne(50);
				
		Assert.isTrue(product.getName().equals("Hawaiana") && product.getSalePrice() == 99.9);
				
		unauthenticate();
	}
}