package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Offer extends DomainEntity{

	//Attributes --------------------------------------------------------------------------------
	private String name;
	private Date startDate;
	private Date endDate;
	private Integer discount;
	private Boolean combinable;
	private String range;
	private String loop;
	
	
	//Constructor -------------------------------------------------------------------------------
	public Offer(){
		super();
	}
	
	//Getters and setter ------------------------------------------------------------------------
	@NotBlank
	@Size(min = 5, max = 32)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	@Past
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	@Range(min = 0,max = 100)
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}


	@NotNull
	public Boolean getCombinable() {
		return combinable;
	}
	public void setCombinable(Boolean combinable) {
		this.combinable = combinable;
	}


	@NotBlank
	@Pattern(regexp = "^STANDARD$|^SILVER$|^GOLD$|^VIP$")
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}


	//REVISAR
	@Pattern(regexp = "[LMXJVSD]{1,7}")
	public String getLoop() {
		return loop;
	}
	public void setLoop(String loop) {
		this.loop = loop;
	}

	@Override
	public String toString() {
		return "Offer [name=" + name + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", discount=" + discount
				+ ", combinable=" + combinable + ", range=" + range + ", loop="
				+ loop + "]";}
	
	//Relationships -----------------------------------------------------------------------------
	private Collection<PurchaseOrder> purchasesOrders;
	private Collection<Product> products;


	@Valid
	@ManyToMany
	public Collection<PurchaseOrder> getPurchasesOrders() {
		return purchasesOrders;
	}
	public void setPurchasesOrders(Collection<PurchaseOrder> purchasesOrders) {
		this.purchasesOrders = purchasesOrders;
	}

	@Valid
	@ManyToMany
	public Collection<Product> getProducts() {
		return products;
	}
	public void setProducts(Collection<Product> products) {
		this.products = products;
	}
	
	
	
	
	
}