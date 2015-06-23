package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {
	
	// Attributes -------------------------------------------------------------
	
	private CreditCard creditCard;
	private String phone;
	private Date birthDate;
	private String address;
	private String rangee;
	
	@NotBlank
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	@NotBlank
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@NotBlank
	@Pattern(regexp = "^STANDARD$|^SILVER$|^GOLD$|^VIP$")
	public String getRangee() {
		return rangee;
	}
	public void setRangee(String rangee) {
		this.rangee = rangee;
	}
	
	@Valid
	@NotNull
	public CreditCard getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	
	// Relationships ----------------------------------------------------------
	
	private Collection<Complaint> complaints;
	private Collection<DiscussionMessage> discussionMessages;
	private Collection<SalesOrder> salesOrder;
	
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="customer")
	public Collection<DiscussionMessage> getDiscussionMessages() {
		return discussionMessages;
	}
	public void setDiscussionMessages(Collection<DiscussionMessage> discussionMessages) {
		this.discussionMessages = discussionMessages;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="customer")
	public Collection<SalesOrder> getSalesOrder() {
		return salesOrder;
	}
	public void setSalesOrder(Collection<SalesOrder> salesOrder) {
		this.salesOrder = salesOrder;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="customer")
	public Collection<Complaint> getComplaints() {
		return complaints;
	}
	public void setComplaints(Collection<Complaint> complaints) {
		this.complaints = complaints;
	}
	
}