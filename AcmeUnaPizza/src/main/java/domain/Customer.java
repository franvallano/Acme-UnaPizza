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
	
	// Constructors -----------------------------------------------------------
	public Customer(){
		super();
	}
	
	// Attributes -------------------------------------------------------------
	
	private CreditCard creditCard;
	private String phone;
	private Date birthDate;
	private String address;
	private String range;
	
	@NotBlank
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Past
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
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
	@Pattern(regexp = "^standard$|^silver$|^gold$|^vip$")
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	
	@Valid
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
	@OneToMany(mappedBy="customer")
	public Collection<DiscussionMessage> getDiscussionMessage() {
		return discussionMessages;
	}
	public void setDiscussionMessage(Collection<DiscussionMessage> discussionMessages) {
		this.discussionMessages = discussionMessages;
	}
	
	@Valid
	@OneToMany(mappedBy="customer")
	public Collection<SalesOrder> getSalesOrder() {
		return salesOrder;
	}
	public void setSalesOrder(Collection<SalesOrder> salesOrder) {
		this.salesOrder = salesOrder;
	}
	
	@Valid
	@OneToMany(mappedBy="customer")
	public Collection<Complaint> getComplaints() {
		return complaints;
	}
	public void setLandmarks(Collection<Complaint> landmarks) {
		this.complaints = complaints;
	}
	
}
