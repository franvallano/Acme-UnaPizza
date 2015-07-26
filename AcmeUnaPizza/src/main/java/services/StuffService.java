package services;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.StuffRepository;
import utilities.EntityHackingException;
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
	public Stuff create(){
		Stuff newbye;
		
		newbye = new Stuff();
		
		return newbye;
	}
	
	public void save(Stuff stuff){
		if(isHacked(stuff))
			throw new EntityHackingException("Stuff: tried to save a hacked entity");
		
		stuffRepository.save(stuff);
	}
	
	public Stuff findOne(int id){
		Stuff res;
		
		Assert.isTrue(id!=0, "tried to find a stuff with id zero");
		
		res = stuffRepository.findOne(id);
		Assert.notNull(res, "found stuff is null (id "+id+")");
		
		return res;
	}

	public Collection<Stuff> findAll(){
		Collection<Stuff> allStuff;
		
		allStuff = stuffRepository.findAll();
		
		return allStuff;
	}
	
	public Collection<Stuff> findStuffMoreRepaired() {
		Collection<Stuff> result;
		
		result = stuffRepository.findStuffMoreRepaired();
		
		Assert.notNull(result, "collection of more repaired stuff is null");
		
		return result;
	}
	
	/**
	 * Finds all stuff with status MALFUNCTION that can be repaired at the specified workshop.
	 * 
	 * @param workshop - workshop where the found stuff can be repaired.
	 * @return list of malfunctioning stuff that can be repaired at the new repair workshop.
	 * */
	public Collection<Stuff> findMalfunctioningStuffByWorkshop(WorkShop repairWorkshop){
		Collection<Stuff> res;
		
		res = stuffRepository.findAllMalfunctionStuffByWorkshopId(repairWorkshop.getId());
		
		return res;
	}
	
	/**
	 * Finds all stuff with MALFUNCTION status.
	 * 
	 * @return all stuff with MALFUNCTION status.
	 * */
	public Collection<Stuff> findMalfunctioningStuff(){
		Collection<Stuff> res;
		
		res = stuffRepository.findAllMalfunctionStuff();
		
		return res;
	}
	
	/**
	 * Finds all stuff with REPAIRING status.
	 * 
	 * @return all stuff with REPAIRING status.
	 * */
	public Collection<Stuff> findRepairingStuff(){
		Collection<Stuff> res;
		
		res = stuffRepository.findAllRepairingStuff();
		
		return res;
	}
	
	// Busines logic methods --------------------------------------------------
	/**
	 * Checks if stuff has been hacked.
	 * Stuff is considered to be hacked if id, version or repairs collection has 
	 * been modified.
	 * 
	 * @param stuff - stuff to be checked.
	 * @return True if the stuff has been hacked, False otherwise.
	 * */
	private Boolean isHacked(Stuff stuff){
		Boolean res;
		Stuff oldVersion;
		
		oldVersion = stuffRepository.findOne(stuff.getId());
		if(oldVersion != null){
			res = stuff.getId() != oldVersion.getId();
			res = res || stuff.getVersion() != oldVersion.getVersion();
			res = res || !stuff.getRepairs().equals(oldVersion.getRepairs());
		}
		else
			res = true;
		
		return res;
	}
}
