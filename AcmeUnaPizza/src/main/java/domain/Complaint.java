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
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
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
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
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
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	@NotBlank
	@Pattern(regexp = "^OPEN$|^CANCELLED$|^CLOSED$")
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	// Relationships ----------------------------------------------------------
	
	private Administrator administrator;
	private Collection<DiscussionMessage> discussionMessages;
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
	public Collection<DiscussionMessage> getDiscussionMessages() {
		return discussionMessages;
	}
	public void setDiscussionMessages(Collection<DiscussionMessage> discussionMessages) {
		this.discussionMessages = discussionMessages;
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
}
