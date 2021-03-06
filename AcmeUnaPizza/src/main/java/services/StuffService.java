package services;


import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.StuffRepository;
import security.Authority;
import security.UserAccount;
import utilities.EntityHackingException;
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
	
	@Autowired
	private BossService bossService;
	
	// Constructors -----------------------------------------------------------
	
	public StuffService() {
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	public Stuff create(){
		Stuff newbye;
		
		Assert.isTrue(ppalIsABoss(), "user that isn't a boss tried to create a new stuff");
		
		newbye = new Stuff();
		
		newbye.setRepairs(new ArrayList<Repair>());
		
		return newbye;
	}
	
	public void save(Stuff stuff){
		if(isHacked(stuff))
			throw new EntityHackingException("Stuff: tried to save a hacked entity");
		
		Assert.isTrue(ppalIsABoss(), "user that isn't a boss tried to modify a stuff");
		
		bossService.findByPrincipal();
		
		stuffRepository.save(stuff);
	}
	
	public void delete(Stuff entity) {
		Assert.isTrue(ppalIsABoss(), "user that isn't a boss tried to delete a stuff");
		Assert.notNull(entity, "tried to delete a null stuff");
		Assert.isTrue(entity.getId()!=0, "tried to delete a stuff with id zero");
		Assert.isTrue(stuffRepository.exists(entity.getId()), "tried to delete a stuff that doesn't exist");
	
		stuffRepository.delete(entity.getId());
		
		Assert.isNull(stuffRepository.findOne(entity.getId()), "entity hasn't been removed after delete() call");
	}
	
	public Stuff findOne(int id){
		Stuff res;
		
		Assert.isTrue(id!=0, "tried to find a stuff with id zero");
		
		bossService.findByPrincipal();
		
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
	public Collection<Stuff> findAllMalfunctioningStuff(){
		Collection<Stuff> res;
		
		res = stuffRepository.findAllMalfunctionStuff();
		
		return res;
	}
	
	/**
	 * Finds all stuff with REPAIRING status.
	 * 
	 * @return all stuff with REPAIRING status.
	 * */
	public Collection<Stuff> findAllRepairingStuff(){
		Collection<Stuff> res;
		
		res = stuffRepository.findAllRepairingStuff();
		
		return res;
	}
	
	/**
	 * Finds all stuff with OK status.
	 * 
	 * @return all stuff with OK status.
	 * */
	public Collection<Stuff> findAllOkStuff(){
		Collection<Stuff> res;
		
		res = stuffRepository.findAllOkStuff();
		
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
			res = res || (stuff.getRepairs().size() != oldVersion.getRepairs().size());
			res = res || !oldVersion.getRepairs().containsAll(stuff.getRepairs());
		}
		else
			res = true;
		
		if (stuff.getId() == 0)
			res = false;
		
		return res;
	}
	
	/**
	 * Checks if principal has a Boss authority.
	 * 
	 * @return true if principal is a boss, false otherwise.
	 * */
	private Boolean ppalIsABoss(){
		UserAccount ppal;
		Authority bossAuthority;
		
		bossAuthority = new Authority();
		bossAuthority.setAuthority("BOSS");
		
		ppal = bossService.findByPrincipal().getUserAccount();
		
		return ppal.getAuthorities().contains(bossAuthority);
	}
	
	public Stuff findCheckMalfunction(int stuffId) {
		Assert.isTrue(stuffId != 0);
		
		Stuff res;
		
		res = stuffRepository.findCheckMalfunction(stuffId);
		
		Assert.notNull(res);
		
		return res;
	}
}
