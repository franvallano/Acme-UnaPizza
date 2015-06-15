package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class DiscussionMessage extends DomainEntity{

	//Attributes --------------------------------------------------------------------------------
	
	
	//Constructor -------------------------------------------------------------------------------
	public DiscussionMessage(){
		super();
	}
	
	//Getters and setter ------------------------------------------------------------------------

	
	//Relationships -----------------------------------------------------------------------------
	
}
