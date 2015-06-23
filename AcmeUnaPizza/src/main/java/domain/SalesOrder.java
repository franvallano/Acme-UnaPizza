package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class SalesOrder extends DomainEntity{

	//Attributes --------------------------------------------------------------------------------
	private String referenceNumber;
	private String state;
	private Note note;
	private Date creationMoment;
	private Date deliveredMoment;
	private double totalCost;
	
	//Getters and setter ------------------------------------------------------------------------
	@Column(unique=true)
	@Pattern(regexp="[A-Z]{3}-[0-9]{8}")
	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	@Pattern(regexp="^open$|^cooking$|^prepared$|^onitsway$|^delivered$|^undelivered$")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Valid
	public Note getNote() {
		return note;
	}

	public void setNote(Note note) {
		this.note = note;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getCreationMoment() {
		return creationMoment;
	}

	public void setCreationMoment(Date creationMoment) {
		this.creationMoment = creationMoment;
	}

	@Past
	public Date getDeliveredMoment() {
		return deliveredMoment;
	}

	public void setDeliveredMoment(Date deliveredMoment) {
		this.deliveredMoment = deliveredMoment;
	}

	@Min(0)
	@Digits(integer=9, fraction=2)
	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}
	
	//Relationships -----------------------------------------------------------------------------
	private Collection<Product> products;
	private Boss boss;
	private Cook cook;
	private DeliveryMan deliveryMan;
	private Customer customer;

	@Valid
	@NotNull
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Product> getProducts() {
		return products;
	}

	public void setProducts(Collection<Product> products) {
		this.products = products;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Boss getBoss() {
		return boss;
	}

	public void setBoss(Boss boss) {
		this.boss = boss;
	}

	@Valid
	@ManyToOne(optional=true)
	public Cook getCook() {
		return cook;
	}

	public void setCook(Cook cook) {
		this.cook = cook;
	}

	@Valid
	@ManyToOne(optional=true)
	public DeliveryMan getDeliveryMan() {
		return deliveryMan;
	}

	public void setDeliveryMan(DeliveryMan deliveryMan) {
		this.deliveryMan = deliveryMan;
	}

	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
}

