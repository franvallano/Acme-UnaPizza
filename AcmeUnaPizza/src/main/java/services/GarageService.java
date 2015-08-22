/* 
* Autogenerated service code 
* Variables (text between %) must have been replaced when code is autogenerated
*/

package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.GarageRepository;
import domain.Garage;
import domain.Motorbike;

@Service
@Transactional
public class GarageService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private GarageRepository garageRepository;

	// Ancillary services -----------------------------------------------------
	@Autowired
	private AdministratorService administratorService;

	// Constructor ------------------------------------------------------------
	public GarageService(){
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Garage create(){
		Garage newbye;
		Collection<Motorbike> motos;
		
		administratorService.findByPrincipal();
		
		newbye = new Garage();
		
		motos = new ArrayList<Motorbike>();
		
		newbye.setMotorbikes(motos);
		
		return newbye;
	}

	public void save(Garage garage){
		Assert.notNull(garage);
		administratorService.findByPrincipal();
		
		Integer totalMotos = garage.getMotorbikes().size();
		
		Assert.isTrue(garage.getSize() >= totalMotos);
		
		this.garageRepository.save(garage);
	}

	public Garage findOne( int id ){
		Assert.isTrue( id != 0);
		
		Garage res;
		
		res = this.garageRepository.findOne( id );
		
		Assert.notNull(res);
		
		return res;
	}

	public Collection<Garage> findAll(){
		Collection<Garage> res;
		
		res = garageRepository.findAll();
		
		return res;
	}
	
	public Collection<Garage> findFreeGarages() {
		Collection<Garage> result;
		
		result = garageRepository.findFreeGarages();
		
		return result;
	}
	
	public List<Integer> findFreeParkings(Collection<Garage> garages) {
		List<Integer> res;
		
		res = new ArrayList<Integer>();
		
		for(Garage garage : garages)
			res.add(garage.getSize() - garage.getMotorbikes().size());
		
		return res;
	}

	// Other business methods -------------------------------------------------

	// Ancillary methods ------------------------------------------------------

}
