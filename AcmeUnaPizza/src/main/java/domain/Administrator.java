package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Administrator extends DomainEntity{

	//Attributes --------------------------------------------------------------------------------
	
	
	//Constructor -------------------------------------------------------------------------------
	public Administrator(){
		super();
	}

	
	//Getters and setter ------------------------------------------------------------------------
	
	
	//Relationships -----------------------------------------------------------------------------
	private Collection<Complaint> complaints;
	private Collection<PurchaseOrder> purchaseOrder;

	@Valid
	@NotNull
	@OneToMany(mappedBy="administrator")
	public Collection<Complaint> getComplaints() {
		return complaints;
	}
	public void setComplaints(Collection<Complaint> complaints) {
		this.complaints = complaints;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="administrator")
	public Collection<PurchaseOrder> getPurchaseOrder() {
		return purchaseOrder;
	}
	public void setPurchaseOrder(Collection<PurchaseOrder> purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}
	
	
}
