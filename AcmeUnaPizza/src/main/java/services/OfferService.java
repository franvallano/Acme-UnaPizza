package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.OfferRepository;
import domain.Offer;

@Service
@Transactional
public class OfferService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private OfferRepository offerRepository;

	// Ancillary services -----------------------------------------------------
	
	// Constructor ------------------------------------------------------------
	public OfferService(){
		super();
	}

	public Collection<Offer> findAll() {
		Collection<Offer> result;
		
		result = offerRepository.findAll();
		
		Assert.notNull(result);
		
		return result;
	}
	// Ancillary methods ------------------------------------------------------

}