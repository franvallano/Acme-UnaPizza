package services;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.StuffRepository;
import domain.Stuff;
import domain.WorkShop;

@Service
@Transactional
public class StuffService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private StuffRepository stuffRepository;
	
	// Supporting services ----------------------------------------------------
	
	// Constructors -----------------------------------------------------------
	
	public StuffService() {
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public void save(Stuff stuff){
		stuffRepository.save(stuff);
	}

	public Collection<Stuff> findStuffMoreRepaired() {
		Collection<Stuff> result;
		
		result = stuffRepository.findStuffMoreRepaired();
		
		Assert.notNull(result);
		
		return result;
	}
	
	// Busines logic methods --------------------------------------------------
	/**
	 * Finds all stuff with status MALFUNCTION that can be repaired at the specified workshop.
	 * 
	 * @param workshop - workshop where the found stuff can be repaired.
	 * @return list of malfunctioning stuff that can be repaired at the new repair workshop.
	 * */
	public Collection<Stuff> findMalfunctioningStuff(WorkShop repairWorkshop){
		Collection<Stuff> res;
		
		res = stuffRepository.findAllMalfunctionStuffByWorkshopId(repairWorkshop.getId());
		
		return res;
	}
}
