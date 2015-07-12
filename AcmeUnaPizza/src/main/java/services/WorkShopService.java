/* 
* Autogenerated service code 
* Variables (text between %) must have been replaced when code is autogenerated
*/

package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.WorkShopRepository;
import domain.Repair;
import domain.WorkShop;

@Service
@Transactional
public class WorkShopService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private WorkShopRepository workshopRepository;

	// Ancillary services -----------------------------------------------------
	@Autowired
	private BossService bossService;

	// Constructor ------------------------------------------------------------
	public WorkShopService(){
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public WorkShop create(){
		WorkShop newbye;
		Collection<Repair> repairs;
		
		bossService.findByPrincipal();
		
		newbye = new WorkShop();
		repairs = new ArrayList<Repair>();
		
		newbye.setRepairs(repairs);
		
		return newbye;
	}

	public void save( WorkShop entity ){
		Assert.notNull( entity );
		
		bossService.findByPrincipal();
		
		this.workshopRepository.save( entity );
	}

//	public void delete( WorkShop entity ){
//		Assert.isTrue( entity.getId()!=0 );
//		Assert.isTrue( this.workshopRepository.exists(entity.getId() ));
//		
//		this.workshopRepository.delete( entity );
//		
//		Assert.isTrue( !this.workshopRepository.exists(entity.getId() ));
//	}

	public WorkShop findOne( int id ){
		Assert.isTrue( id != 0);
		
		WorkShop res;
		
		res = this.workshopRepository.findOne( id );
		
		Assert.notNull(res);
		
		return res;
	}

	public Collection<WorkShop> findAll(){
		Collection<WorkShop> res;
		
		res = workshopRepository.findAll();
		
		return res;
	}

	// Other business methods -------------------------------------------------

	// Ancillary methods ------------------------------------------------------

}
