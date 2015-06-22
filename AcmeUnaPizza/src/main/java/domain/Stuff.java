package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Stuff extends DomainEntity{

	//Attributes --------------------------------------------------------------------------------
	private String name;
	private String status;
	private String referenceCode;
	private Integer powerConsumption;
	

	//Constructor -------------------------------------------------------------------------------
	public Stuff(){
		super();
	}
	
	//Getters and setter ------------------------------------------------------------------------

	@NotBlank
	@Size(min = 5, max = 15)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@NotBlank
	@Pattern(regexp= "^OK$|^MALFUNCTION$|^REPAIRING$")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	@Column(unique=true)
	@NotBlank
	@Pattern(regexp="[a-zA-Z]{3}[0-9]{5}")
	public String getReferenceCode() {
		return referenceCode;
	}

	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}

	
	@Min(0)
	public Integer getPowerConsumption() {
		return powerConsumption;
	}

	public void setPowerConsumption(Integer powerConsumption) {
		this.powerConsumption = powerConsumption;
	}

	


	public String toString() {
		return "Stuff [name=" + name + ", status=" + status
				+ ", referenceCode=" + referenceCode + ", powerConsumption="
				+ powerConsumption + "]";
	}

	
	//Relationships -----------------------------------------------------------------------------
	private Collection<Staff> staffs;
	private Collection<WorkShop> workShops;


	@Valid
	@ManyToMany
	public Collection<Staff> getStaffs() {
		return staffs;
	}
	public void setStaffs(Collection<Staff> staffs) {
		this.staffs = staffs;
	}

	@Valid
	@ManyToMany(cascade = CascadeType.ALL)
	public Collection<WorkShop> getWorkShops() {
		return workShops;
	}
	public void setWorkShops(Collection<WorkShop> workShops) {
		this.workShops = workShops;
	}
	
	
	
	
}
