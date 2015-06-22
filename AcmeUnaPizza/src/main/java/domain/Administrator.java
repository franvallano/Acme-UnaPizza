package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Administrator extends DomainEntity{

	//Attributes --------------------------------------------------------------------------------
	
	
	//Constructor -------------------------------------------------------------------------------
	public Administrator(){
		super();
	}

	
	//Getters and setter ------------------------------------------------------------------------

	@Override
	public String toString() {
		return "Administrator [" + super.toString() + "]";
	}
	
	
	//Relationships -----------------------------------------------------------------------------
	private Collection<Complaint> complaints;

	@Valid
	@OneToMany(mappedBy="administrator")
	public Collection<Complaint> getComplaints() {
		return complaints;
	}
	public void setComplaints(Collection<Complaint> complaints) {
		this.complaints = complaints;
	}
}
