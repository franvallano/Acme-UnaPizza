package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class SalesOrder extends DomainEntity{

	//Attributes --------------------------------------------------------------------------------
	
	
	//Constructor -------------------------------------------------------------------------------
	public SalesOrder(){
		super();
	}
	
	//Getters and setter ------------------------------------------------------------------------

	
	//Relationships -----------------------------------------------------------------------------
	
}
