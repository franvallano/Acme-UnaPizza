package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Repair extends DomainEntity{

	//Attributes --------------------------------------------------------------------------------
	private Date moment;
	private double cost;
	
	//Constructor -------------------------------------------------------------------------------
	public Repair(){
		super();
	}

	//Getters and setter ------------------------------------------------------------------------
	@Past
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	@Min(0)
	@Digits(integer=3, fraction=2)
	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "Repair [moment=" + moment + ", cost=" + cost + "]";
	}
	
	//Relationships -----------------------------------------------------------------------------
	private Staff staff;
	private Workshop workshop;

	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Workshop getWorkshop() {
		return workshop;
	}

	public void setWorkshop(Workshop workshop) {
		this.workshop = workshop;
	}
	
	
}