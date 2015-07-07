package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

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
	@Autowired
	private AdministratorService administratorService;
	
	// Constructor ------------------------------------------------------------
	public OfferService(){
		super();
	}
	
	public Offer create(){
		Offer offer;
		
		administratorService.findByPrincipal();
		
		offer = new Offer();
		
		return offer;
	}
	
	public void save(Offer offer) {
		Assert.notNull(offer);
		Date startDate;
		Date endDate;
		
		// Colocamos la fecha de inicio a las 00:00
		Calendar calendar = Calendar.getInstance();
		Calendar actualDate = Calendar.getInstance();
		actualDate.setTimeInMillis(System.currentTimeMillis());
		actualDate.setTimeInMillis(offer.getStartDate().getTime());
		
		actualDate.set(Calendar.HOUR_OF_DAY, 0);
		actualDate.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		
		startDate = new Date(calendar.getTimeInMillis());
		
		offer.setStartDate(startDate);
		
		// Comparamos si la fecha es igual o mayor a la actual
		Assert.isTrue(calendar.getTimeInMillis() >= actualDate.getTimeInMillis());
		
		if(offer.getEndDate() != null) {
			// Colocamos la fecha de fin a las 23:59
			calendar.setTimeInMillis(offer.getEndDate().getTime());
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			endDate = new Date(calendar.getTimeInMillis());
			
			offer.setEndDate(endDate);
			
			Assert.isTrue(offer.getStartDate().before(offer.getEndDate()));
		}
		
		offerRepository.save(offer);
	}

	public Collection<Offer> findAll() {
		Collection<Offer> result;
		
		result = offerRepository.findAll();
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Offer> findCurrentOffers() {
		Collection<Offer> result;
		
		result = offerRepository.findCurrentOffers();
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Offer findOne(int id){
		Assert.isTrue(id != 0);
		
		Offer res;
		
		res = this.offerRepository.findOne(id);
		
		Assert.notNull(res);
		
		return res;
	}
	// Ancillary methods ------------------------------------------------------

}