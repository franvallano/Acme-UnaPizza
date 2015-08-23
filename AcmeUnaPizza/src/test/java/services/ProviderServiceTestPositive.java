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
import domain.Provider;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class ProviderServiceTestPositive extends AbstractTest {
	@Autowired
	private ProviderService providerService;
	
	// Test listar proveedores
	// Autenticado como Administrator
	@Test
	public void testListAllProdivers() {
		authenticate("admin1");
		
		Collection<Provider> providers;

		providers = providerService.findAll();

		Assert.isTrue(providers.size() == 4);
		
		unauthenticate();
	}
	
	// Test editar proveedor
	// Autenticado como Administrator
	@Test
	public void testEditProvider() {
		authenticate("admin1");
			
		Provider provider;
		
		provider = providerService.findOne(46);
		
		provider.setName("Buenos bonitos y baratos");
		
		providerService.save(provider);
		
		provider = providerService.findOne(46);
		
		Assert.isTrue(provider.getName().equals("Buenos bonitos y baratos"));
		
		unauthenticate();
	}
}
