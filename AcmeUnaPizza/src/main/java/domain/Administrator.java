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
public class Administrator extends Actor {

	//Attributes --------------------------------------------------------------------------------

	//Getters and setter ------------------------------------------------------------------------

	//Relationships -----------------------------------------------------------------------------
	private Collection<Complaint> complaints;
	private Collection<PurchaseOrder> purchaseOrders;
	private Collection<DiscussionMessage> discussionMessages;

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
	public Collection<PurchaseOrder> getPurchaseOrders() {
		return purchaseOrders;
	}
	public void setPurchaseOrders(Collection<PurchaseOrder> purchaseOrders) {
		this.purchaseOrders = purchaseOrders;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="administrator")
	public Collection<DiscussionMessage> getDiscussionMessages() {
		return discussionMessages;
	}
	public void setDiscussionMessages(
			Collection<DiscussionMessage> discussionMessages) {
		this.discussionMessages = discussionMessages;
	}
	
	
}
