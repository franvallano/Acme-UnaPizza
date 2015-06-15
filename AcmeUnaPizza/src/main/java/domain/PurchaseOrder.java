package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

@Entity
@Access(AccessType.PROPERTY)
public class PurchaseOrder extends DomainEntity{

	//Attributes --------------------------------------------------------------------------------
	private String referenceNumber;
	private Double totalCost;
	private Date creationMoment;
	
	//Constructor -------------------------------------------------------------------------------
	public PurchaseOrder(){
		super();
	}
	
	//Getters and setter ------------------------------------------------------------------------
	@Pattern(regexp="[A-Z]{3}-[0-9]{8}")
	@Column(unique=true)
	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	@Min(0)
	@Digits(integer=9, fraction=2)
	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	@Past
	public Date getCreationMoment() {
		return creationMoment;
	}

	public void setCreationMoment(Date creationMoment) {
		this.creationMoment = creationMoment;
	}
	
	
	//Relationships -----------------------------------------------------------------------------
	
}
