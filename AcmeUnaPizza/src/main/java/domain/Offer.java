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
	private boolean combinable;
	private String rangee;
	private String loopp;
	
	
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
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
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
	public boolean getCombinable() {
		return combinable;
	}
	public void setCombinable(boolean combinable) {
		this.combinable = combinable;
	}


	@NotBlank
	@Pattern(regexp = "^STANDARD$|^SILVER$|^GOLD$|^VIP$")
	public String getRangee() {
		return rangee;
	}
	public void setRangee(String rangee) {
		this.rangee = rangee;
	}


	//REVISAR
	@Pattern(regexp = "[LMXJVSD]{1,7}")
	public String getLoopp() {
		return loopp;
	}
	public void setLoopp(String loopp) {
		this.loopp = loopp;
	}

	
}