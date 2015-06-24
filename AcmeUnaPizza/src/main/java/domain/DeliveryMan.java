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
	private Motorbike motorbike;
	private String drivingLicenseNumber;

	//Getters and setter ------------------------------------------------------------------------
	@Valid
	@NotNull
	public Motorbike getMotorbike() {
		return motorbike;
	}
	
	public void setMotorbike(Motorbike motorbike) {
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
	private Collection<SalesOrder> salesOrders;

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
	@NotNull
	@OneToMany(mappedBy="deliveryMan")
	public Collection<SalesOrder> getSalesOrders() {
		return salesOrders;
	}

	public void setSalesOrders(Collection<SalesOrder> salesOrders) {
		this.salesOrders = salesOrders;
	}
	
	

}
