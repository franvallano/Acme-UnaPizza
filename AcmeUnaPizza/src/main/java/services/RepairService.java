package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RepairRepository;
import security.Authority;
import security.UserAccount;
import domain.Actor;
import domain.Boss;
import domain.Repair;

@Service
@Transactional
public class RepairService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private RepairRepository repairRepository;

	// Ancillary services -----------------------------------------------------
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private StaffService staffService;
	
	@Autowired
	private BossService bossService;
	
	// Constructor ------------------------------------------------------------
	public RepairService(){
		super();
	}

	public Double findTotalCostRepairs() {
		Double result;
		
		result = repairRepository.findTotalCostRepairs();
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Repair create(){
		Repair newbye;
		checkPpalAuthorities();
		Boss ppal;
		
		ppal = bossService.findByPrincipal();
		
		Assert.notNull(ppal, "Repairs cannot be created without being loged in");
		
		newbye = new Repair();
		newbye.setBoss(ppal);
		
		return newbye;
	}
	
	public void save(Repair entity){
		checkModificationPreconditions(entity);
		
		this.repairRepository.save(entity);
	}
	
	public Repair findOne( int id ){
		Assert.isTrue( id != 0);
		
		Repair res;
		
		res = this.repairRepository.findOne( id );
		
		Assert.notNull(res);
		
		return res;
	}

	public Collection<Repair> findAll() {
		Collection<Repair> result;
		
		result = repairRepository.findAll();
		
		bossService.findByPrincipal();
		
		Assert.notNull(result);
		
		return result;
	}
	
	// Ancillary methods ------------------------------------------------------
	
	private Boolean checkPpalAuthorities(){
		Boolean res;
		Actor ppal;
		UserAccount ppalUserAccount;
		Collection<Authority> ppalAuthorities;
		
		ppal = actorService.findByPrincipal();
		ppalUserAccount = ppal.getUserAccount();
		ppalAuthorities = ppalUserAccount.getAuthorities();
		// User must have staff authority (bossess, delivery men and cooks are staffs)
		res = ppalAuthorities.contains("BOSS");
		res = res || ppalAuthorities.contains("DELIVERY_MAN");
		res = res || ppalAuthorities.contains("COOK");
		
		return res;
	}
	
	/***
	 * Check if all business rules and relationships are met.
	 * 
	 * @param entity
	 * @return true if all conditions are satisfied, false otherwise.
	 */
	private Boolean checkModificationPreconditions(Repair entity){
		Boolean res = false;
		Date currMoment;
		String stuffStatus;
		
		currMoment = new Date(System.currentTimeMillis()-1);
		
		// Moment must be null or past
		if(entity.getMoment() != null)
			if(entity.getMoment().before(currMoment))
				res = true;
			
		res = res && entity.getCost() > 0.0;
		
		// Principal must be a member of staff
		if(entity.getBoss() != null)
			res = checkPpalAuthorities();
		
		// modification must be donde at an workshop associated with the stuff
		res = res && (entity.getWorkShop().equals(entity.getStuff().getWorkShop()));
		
		//stuff must be in MALFUNCTION status
		stuffStatus = entity.getStuff().getStatus();
		res = stuffStatus.equals("MALFUNCTION");
		
		return res;
	}
	
}