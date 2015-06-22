package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;


@Entity
@Access(AccessType.PROPERTY)
public class DeliveryMan extends Staff {
	//Attributes --------------------------------------------------------------------------------
	private MotorBike motorbike;
	private String drivingLicenseNumber;


	//Constructor -------------------------------------------------------------------------------
	public DeliveryMan(){
		super();
	}
		
	//Getters and setter ------------------------------------------------------------------------
	
	public MotorBike getMotorbike() {
		return motorbike;
	}
	
	public void setMotorbike(MotorBike motorbike) {
		this.motorbike = motorbike;
	}

	
	@NotBlank
	@Pattern(regexp = "[0-9]{8}[A-Z]")
	public String getDrivingLicenseNumber() {
		return drivingLicenseNumber;
	}

	public void setDrivingLicenseNumber(String drivingLicenseNumber) {
		this.drivingLicenseNumber = drivingLicenseNumber;
	}
		
	//Relationships -----------------------------------------------------------------------------
	private Garage garage;
	private Collection<SalesOrder> salesOrder;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Garage getGarage() {
		return garage;
	}

	public void setGarage(Garage garage) {
		this.garage = garage;
	}
	
	@Valid
	@OneToMany(mappedBy="deliveryMan")
	public Collection<SalesOrder> getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(Collection<SalesOrder> salesOrder) {
		this.salesOrder = salesOrder;
	}
	
	

}
