package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;


@Entity
@Access(AccessType.PROPERTY)
public class DeliveryMan extends Staff {
	//Attributes --------------------------------------------------------------------------------
	
	private String drivingLicenseNumber;

	//Getters and setter ------------------------------------------------------------------------

	@NotBlank
	@Pattern(regexp = "[0-9]{8}[A-Z]")
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getDrivingLicenseNumber() {
		return drivingLicenseNumber;
	}

	public void setDrivingLicenseNumber(String drivingLicenseNumber) {
		this.drivingLicenseNumber = drivingLicenseNumber;
	}
		
	//Relationships -----------------------------------------------------------------------------
	private Collection<SalesOrder> salesOrders;
	private Motorbike motorbike;
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="deliveryMan")
	public Collection<SalesOrder> getSalesOrders() {
		return salesOrders;
	}

	public void setSalesOrders(Collection<SalesOrder> salesOrders) {
		this.salesOrders = salesOrders;
	}
	
	@Valid
	@OneToOne(optional = true)
	public Motorbike getMotorbike() {
		return motorbike;
	}
	
	public void setMotorbike(Motorbike motorbike) {
		this.motorbike = motorbike;
	}

}
