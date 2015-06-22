package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Garage extends DomainEntity{

	//Attributes --------------------------------------------------------------------------------
	private String location;
	private Integer size;

	//Constructor -------------------------------------------------------------------------------
	public Garage(){
		super();
	}
	
	//Getters and setter ------------------------------------------------------------------------

	@NotBlank
	@Size(min = 10, max = 250)
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	@Min(0)
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	
	//Relationships -----------------------------------------------------------------------------
	private Collection<DeliveryMan> deliveryMans;

	@Valid
	@NotNull
	@OneToMany(mappedBy="garage")
	public Collection<DeliveryMan> getDeliveryMans() {
		return deliveryMans;
	}

	public void setDeliveryMans(Collection<DeliveryMan> deliveryMans) {
		this.deliveryMans = deliveryMans;
	}
	
	
	
}