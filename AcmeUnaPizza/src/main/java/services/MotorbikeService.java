package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MotorbikeRepository;
import domain.DeliveryMan;
import domain.Garage;
import domain.Motorbike;

@Service
@Transactional
public class MotorbikeService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private MotorbikeRepository motorbikeRepository;

	// Ancillary services -----------------------------------------------------
	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private DeliveryManService deliveryManService;
	@Autowired
	private GarageService garageService;
	
	// Constructor ------------------------------------------------------------
	public MotorbikeService(){
		super();
	}
	public Motorbike create(){
		Motorbike newbye;
		Garage garage;
		
		administratorService.findByPrincipal();
		
		newbye = new Motorbike();
		garage = new Garage();
		
		newbye.setGarage(garage);
		newbye.setDrivingTime(0L);
		
		return newbye;
	}

	public void save(Motorbike motorbike){
		Assert.notNull(motorbike);
		administratorService.findByPrincipal();
		
		Assert.notNull(garageService.findFreeGarage(motorbike.getGarage().getId()));

		this.motorbikeRepository.save(motorbike);
	}

	public void saveByDeliveryMan(Motorbike motorbike){
		Assert.notNull(motorbike);
		Assert.isTrue(motorbike.getId() == deliveryManService.findByPrincipal().getMotorbike().getId());

		this.motorbikeRepository.save(motorbike);
	}

	public void delete(Motorbike motorbike){
		Assert.notNull(motorbike);
		Assert.isTrue(motorbike.getId() != 0);
		administratorService.findByPrincipal();
		
		boolean canDelete;
		
		canDelete = canDeleteMotorbike(motorbike.getId());
		
		Assert.isTrue(canDelete);
		
		motorbikeRepository.delete(motorbike);
	}

	
	public Motorbike findOne( int id ){
		Assert.isTrue( id != 0);
		
		Motorbike res;
		
		res = this.motorbikeRepository.findOne( id );
		
		Assert.notNull(res);
		
		return res;
	}

	public Collection<Motorbike> findAll() {
		Collection<Motorbike> result;
		
		result = motorbikeRepository.findAll();
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Motorbike> findAllMotorbikesOrderedByDrivingTime() {
		Collection<Motorbike> result;
		
		result = motorbikeRepository.findAllMotorbikesOrderedByDrivingTime();
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Integer findTotalMotorbikesByGarage(int garageId) {
		Integer result;
		
		result = motorbikeRepository.findTotalMotorbikesByGarage(garageId);
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Motorbike> findFreeMotorbikes() {
		Collection<Motorbike> result;
		
		result = motorbikeRepository.findFreeMotorbikes();
		
		Assert.notNull(result);
		
		return result;
	}
	
	public boolean canDeleteMotorbike(int motorbikeId) {
		Motorbike motorbike;
		boolean res = true;
		
		motorbike = motorbikeRepository.findMotorbikeInDeliveryMan(motorbikeId);
		
		if(motorbike != null)
			res = false;
		
		return res;
	}
	// Ancillary methods ------------------------------------------------------

}