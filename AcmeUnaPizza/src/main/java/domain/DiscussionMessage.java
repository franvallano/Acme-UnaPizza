package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class DiscussionMessage extends DomainEntity{

	//Attributes --------------------------------------------------------------------------------
	private String message;
	private Date moment;
	
	//Constructor -------------------------------------------------------------------------------
	public DiscussionMessage(){
		super();
	}
	
	//Getters and setter ------------------------------------------------------------------------
	@NotBlank
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return moment;
	}
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	
	
	@Override
	public String toString() {
		return "DiscussionMessage [message=" + message + ", moment=" + moment
				+ "]";
	}


	//Relationships -----------------------------------------------------------------------------
	private Administrator administrator;
	private Complaint complaint;
	private Customer customer;
	
	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Administrator getAdministrator() {
		return administrator;
	}
	public void setAdministrator(Administrator administrator) {
		this.administrator = administrator;
	}
	
	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Complaint getComplaint() {
		return complaint;
	}
	public void setComplaint(Complaint complaint) {
		this.complaint = complaint;
	}
	
}
