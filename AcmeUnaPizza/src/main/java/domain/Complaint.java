package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
public class Complaint extends DomainEntity {
	
	// Attributes -------------------------------------------------------------
	
	private String title;
	private Date creationMoment;
	private String description;
	private String result;
	private String state;
	
	@NotBlank
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getCreationMoment() {
		return creationMoment;
	}
	public void setCreationMoment(Date creationMoment) {
		this.creationMoment = creationMoment;
	}
	
	@NotBlank
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	@NotBlank
	@Pattern(regexp = "^open$|^cancelled$|^closed$")
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	// Relationships ----------------------------------------------------------
	
	private Administrator administrator;
	private Collection<DiscussionMessage> complaintMessages;
	private Customer customer;
	
	@Valid
	@ManyToOne(optional = true)
	public Administrator getAdministrator(){
		return administrator;
	}
	public void setAdministrator(Administrator administrator) {
		this.administrator = administrator;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="complaint")
	public Collection<DiscussionMessage> getComplaintMessages() {
		return complaintMessages;
	}
	public void setComplaintMessages(Collection<DiscussionMessage> complaintMessages) {
		this.complaintMessages = complaintMessages;
	}
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Customer getCustomer(){
		return customer;
	}
	public void setCustomer(Customer customer){
		this.customer = customer;
	}
	
	// toString ---------------------------------------------------------------
	@Override
	public String toString() {
		return "Complaint [title=" + title + ", creationMoment="
				+ creationMoment + ", state=" + state + "]";
	}
}
