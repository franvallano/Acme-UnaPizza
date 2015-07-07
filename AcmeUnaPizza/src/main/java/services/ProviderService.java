package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProviderRepository;
import domain.Garage;
import domain.Motorbike;
import domain.Provider;

@Service
@Transactional
public class ProviderService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ProviderRepository providerRepository;

	// Ancillary services -----------------------------------------------------
	@Autowired
	private AdministratorService administratorService;
	
	// Constructor ------------------------------------------------------------
	public ProviderService(){
		super();
	}
	
	public Provider create(){
		Provider provider;
		
		administratorService.findByPrincipal();
		
		provider = new Provider();
		
		return provider;
	}

	public void save(Provider provider){
		Assert.notNull(provider);
		administratorService.findByPrincipal();
		
		this.providerRepository.save(provider);
	}

	public Provider findOne( int id ){
		Assert.isTrue( id != 0);
		
		Provider res;
		
		res = this.providerRepository.findOne( id );
		
		Assert.notNull(res);
		
		return res;
	}

	public Collection<Provider> findAll() {
		Collection<Provider> result;
		
		result = providerRepository.findAll();
		
		Assert.notNull(result);
		
		return result;
	}
	

	// Ancillary methods ------------------------------------------------------

}