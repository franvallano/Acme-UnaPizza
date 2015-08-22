package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.OfferRepository;
import domain.Offer;
import forms.OfferForm;

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
		
		administratorService.findByPrincipal();
		
		Date startDate;
		Date endDate;
		
		// Colocamos la fecha de inicio a las 00:00
		Calendar calendar = Calendar.getInstance();
		Calendar actualDate = Calendar.getInstance();
		actualDate.setTimeInMillis(System.currentTimeMillis());
		calendar.setTimeInMillis(offer.getStartDate().getTime());
		
		actualDate.set(Calendar.HOUR_OF_DAY, 0);
		actualDate.set(Calendar.MINUTE, 0);
		actualDate.set(Calendar.SECOND, 0);
		
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		
		
		startDate = new Date(calendar.getTimeInMillis());
		
		offer.setStartDate(startDate);
		
		// Si la fecha es del mismo dia no hacemos nada porque sera valida
		if((calendar.get(Calendar.YEAR) == actualDate.get(Calendar.YEAR)) && (calendar.get(Calendar.MONTH) == actualDate.get(Calendar.MONTH)) && 
				(calendar.get(Calendar.DAY_OF_MONTH) == actualDate.get(Calendar.DAY_OF_MONTH))) {
		// Si no coincide la fecha, la fecha de inicio debera ser posterior a la actual
		} else {
			Assert.isTrue(calendar.getTimeInMillis() > actualDate.getTimeInMillis());
		}
		
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
	
	//Metodo que recibe un objeto formulario y reconstruye un objeto de dominio
	public Offer reconstruct(OfferForm offerForm) {
		Assert.notNull(offerForm);
		Offer offer;
		String loop = "";
		
		if(offerForm.getId() != null)
			offer = offerRepository.findOne(offerForm.getId());
		else
			offer = create();
		
		Assert.notNull(offer);
			
		offer.setName(offerForm.getName());
		offer.setDiscount(offerForm.getDiscount());
		offer.setStartDate(offerForm.getStartDate());
		offer.setEndDate(offerForm.getEndDate());
		offer.setRangee(offerForm.getRangee());
		
		// Al menos un dia debe ser seleccionado
		Assert.isTrue(offerForm.isMonday() || offerForm.isTuesday() || offerForm.isWednesday() || offerForm.isThursday() || 
				offerForm.isFriday() || offerForm.isFriday() || offerForm.isSaturday() || offerForm.isSunday());
		
		if(offerForm.isMonday())
			loop += "L";
		if(offerForm.isTuesday())
			loop += "M";
		if(offerForm.isWednesday())
			loop += "X";
		if(offerForm.isThursday())
			loop += "J";
		if(offerForm.isFriday())
			loop += "V";
		if(offerForm.isSaturday())
			loop += "S";
		if(offerForm.isSunday())
			loop += "D";
		
		offer.setLoopp(loop);

		return offer;
	}
	
	public OfferForm desreconstruct(Offer offer) {
		Assert.notNull(offer);
		
		OfferForm offerForm = new OfferForm();
		
		offerForm.setId(offer.getId());
		offerForm.setName(offer.getName());
		offerForm.setDiscount(offer.getDiscount());
		offerForm.setStartDate(offer.getStartDate());
		offerForm.setEndDate(offer.getEndDate());
		offerForm.setRangee(offer.getRangee());
		
		if(offer.getLoopp().contains("L"))
			offerForm.setMonday(true);
		else
			offerForm.setMonday(false);
		
		if(offer.getLoopp().contains("M"))
			offerForm.setTuesday(true);
		else
			offerForm.setTuesday(false);
		
		if(offer.getLoopp().contains("X"))
			offerForm.setWednesday(true);
		else
			offerForm.setWednesday(false);
		
		if(offer.getLoopp().contains("J"))
			offerForm.setThursday(true);
		else
			offerForm.setThursday(false);
		
		if(offer.getLoopp().contains("V"))
			offerForm.setFriday(true);
		else
			offerForm.setFriday(false);
		
		if(offer.getLoopp().contains("S"))
			offerForm.setSaturday(true);
		else
			offerForm.setSaturday(false);
		
		if(offer.getLoopp().contains("D"))
			offerForm.setSunday(true);
		else
			offerForm.setSunday(false);
		
		Assert.notNull(offerForm);
		
		return offerForm;
	}
	
	public Collection<Offer> findOffersVIP() {
		Collection<Offer> result;
		
		result = offerRepository.findOffersVIP();
		
		Assert.notNull(result);
		
		result = filterOfferDays(result);
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Offer> findOffersGOLD() {
		Collection<Offer> result;
		
		result = offerRepository.findOffersGOLD();
		
		Assert.notNull(result);
		
		result = filterOfferDays(result);
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Offer> findOffersSILVER() {
		Collection<Offer> result;
		
		result = offerRepository.findOffersSILVER();
		
		Assert.notNull(result);
		
		result = filterOfferDays(result);
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Offer> findOffersSTANDARD() {
		Collection<Offer> result;
		
		result = offerRepository.findOffersSTANDARD();
		
		Assert.notNull(result);
		
		result = filterOfferDays(result);
		
		Assert.notNull(result);
		
		return result;
	}
	
	// Filtramos la coleccion segun los dias
	private Collection<Offer> filterOfferDays(Collection<Offer> offersUnfilter) {
		Collection<Offer> result;
		Calendar actualDate = Calendar.getInstance();
		actualDate.setTimeInMillis(System.currentTimeMillis());
		int dayOfWeek;
		
		result = new HashSet<Offer>();
		
		for(Offer offer : offersUnfilter) {
			dayOfWeek = actualDate.get(Calendar.DAY_OF_WEEK);
			
			// Domingo 0 Lunes 1 Martes 2
			
			// NOTA: El numero de dia va del Domingo al Sabado, por lo que va adelantado uno
			switch(dayOfWeek) {
				// Domingo
				case 1:
					if(offer.getLoopp().contains("D"))
						result.add(offer);
					break;
				// Lunes
				case 2:
					if(offer.getLoopp().contains("L"))
						result.add(offer);
					break;
				// Martes
				case 3:
					if(offer.getLoopp().contains("M"))
						result.add(offer);
					break;
				// Miercoles
				case 4:
					if(offer.getLoopp().contains("X"))
						result.add(offer);
					break;
				// Jueves
				case 5:
					if(offer.getLoopp().contains("J"))
						result.add(offer);
					break;
				// Viernes
				case 6:
					if(offer.getLoopp().contains("V"))
						result.add(offer);
					break;
				// Sabado
				case 7:
					if(offer.getLoopp().contains("S"))
						result.add(offer);
					break;
			}
		}
		
		return result;
	}
	
	public List<String> getAllRanges() {
		List<String> res;
		
		res = new ArrayList<String>();
		
		res.add("STANDARD");
		res.add("SILVER");
		res.add("GOLD");
		res.add("VIP");
		
		return res;
	}
	
	public Collection<Offer> findOffersByRange(String range) {
		Collection<Offer> res;
		
		if(range.equals("STANDARD"))
			res = findOffersSTANDARD();
		else if(range.equals("SILVER"))
			res = findOffersSILVER();
		else if(range.equals("GOLD"))
			res = findOffersGOLD();
		else if(range.equals("VIP"))
			res = findOffersVIP();
		else
			res = new ArrayList<Offer>();
		
		return res;
	}
	
	// Ancillary methods ------------------------------------------------------

}