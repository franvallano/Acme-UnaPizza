package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
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
@Table(uniqueConstraints = 
	@UniqueConstraint(columnNames={"number"}))
public class Customer extends Actor {
	
	// Attributes -------------------------------------------------------------
	
	private CreditCard creditCard;
	private String phone;
	private Date birthDate;
	private String address;
	private String rangee;
	
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	@Pattern(regexp = "(((\\+34)? ?(\\(0\\))? ?)|(0))( ?[0-9]{3,4}){3}")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@NotBlank
	@Pattern(regexp = "^STANDARD$|^SILVER$|^GOLD$|^VIP$")
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getRangee() {
		return rangee;
	}
	public void setRangee(String rangee) {
		this.rangee = rangee;
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
	private Collection<SalesOrder> salesOrders;
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="customer")
	public Collection<SalesOrder> getSalesOrders() {
		return salesOrders;
	}
	public void setSalesOrders(Collection<SalesOrder> salesOrders) {
		this.salesOrders = salesOrders;
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