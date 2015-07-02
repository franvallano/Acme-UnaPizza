package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MotorbikeRepository;
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
	private GarageService garageService;
	
	// Constructor ------------------------------------------------------------
	public MotorbikeService(){
		super();
	}
	public Motorbike create(){
		Motorbike newbye;
		
		newbye = new Motorbike();
		
		return newbye;
	}

	public void save( Motorbike entity ){
		Assert.notNull(entity);
		//COGER GARAGE Y RESTARLE UNO
		this.motorbikeRepository.save( entity );
	}

//HAY QUE ARREGLAR METODO ELIMINAR SI TIENE RELACION CON ALGUN DELIVERY MAN
	public void delete( Motorbike entity ){
		Assert.isTrue( entity.getId()!=0 );
		Assert.isTrue( this.motorbikeRepository.exists(entity.getId() ));
		
		this.motorbikeRepository.delete( entity );
	
		Assert.isTrue( !this.motorbikeRepository.exists(entity.getId() ));
	}

	
	public Motorbike findOne( int id ){
		Assert.isTrue( id != 0);
		
		Motorbike res;
		
		res = this.motorbikeRepository.findOne( id );
		
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
	
	public Collection<Motorbike> findFreeMotorbikes() {
		Collection<Motorbike> result;
		
		
		
		return null;
	}
	// Ancillary methods ------------------------------------------------------

}