package services;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.StuffRepository;
import domain.Repair;
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

	public Collection<Stuff> findStuffMoreRepaired() {
		Collection<Stuff> result;
		
		result = stuffRepository.findStuffMoreRepaired();
		
		Assert.notNull(result);
		
		return result;
	}
	
	// Busines logic methods --------------------------------------------------
	/**
	 * Finds all stuff with status MALFUNCTION that can be repaired at the repair workshop.
	 * This method get the repair workshop and find all stuff that can be repaired at the found workshop.
	 * 
	 * @param repair - new repair to be done at a specified workshop.
	 * @return list of malfunctioning stuff that can be repaired at the new repair workshop.
	 * */
	public Collection<Stuff> getMalfunctioningStuff(Repair repair){
		Collection<Stuff> res;
		WorkShop repairWorkshop;
		
		repairWorkshop = repair.getWorkShop();
		res = stuffRepository.findAllMalfunctionStuffByWorkshopId(repairWorkshop.getId());
		
		return res;
	}
}
