package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

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

	
	//Relationships -----------------------------------------------------------------------------
	
}
