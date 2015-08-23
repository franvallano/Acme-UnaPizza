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
import domain.Provider;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
//@Transactional
//@TransactionConfiguration(defaultRollback = false)
public class ProviderServiceTestNegative extends AbstractTest {
	@Autowired
	private ProviderService providerService;
	
	// Test editar proveedor
	// Error: No autenticado como Administrator
	@Test(expected = IllegalArgumentException.class)
	public void testEditProviderNoAdmin() {
		authenticate("boss1");
				
		Provider provider;
			
		provider = providerService.findOne(46);
			
		provider.setName("Buenos bonitos y baratos");
			
		providerService.save(provider);
			
		provider = providerService.findOne(46);
			
		Assert.isTrue(provider.getName().equals("Buenos bonitos y baratos"));
			
		unauthenticate();
	}
	
	// Test editar proveedor
	// Error: CIF repetido
	@Test(expected = DataIntegrityViolationException.class)
	public void testEditProviderCifUnique() {
		authenticate("admin1");
				
		Provider provider;
				
		provider = providerService.findOne(46);
				
		provider.setCif("C66894568");
				
		providerService.save(provider);
				
		unauthenticate();
	}
	
	// Test editar proveedor
	// Error: Nombre vacio
	@Test(expected = TransactionSystemException.class)
	public void testEditProviderNameEmpty() {
		authenticate("admin1");
					
		Provider provider;
					
		provider = providerService.findOne(46);
					
		provider.setName("");
					
		providerService.save(provider);
					
		provider = providerService.findOne(46);
					
		Assert.isTrue(provider.getName().equals("Buenos bonitos y baratos"));
					
		unauthenticate();
	}
}
