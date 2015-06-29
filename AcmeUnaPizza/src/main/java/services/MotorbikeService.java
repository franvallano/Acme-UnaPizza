package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MotorbikeRepository;
import domain.Motorbike;

@Service
@Transactional
public class MotorbikeService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private MotorbikeRepository motorbikeRepository;

	// Ancillary services -----------------------------------------------------
	
	// Constructor ------------------------------------------------------------
	public MotorbikeService(){
		super();
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
	// Ancillary methods ------------------------------------------------------

}