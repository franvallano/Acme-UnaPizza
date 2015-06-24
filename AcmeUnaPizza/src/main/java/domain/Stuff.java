package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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

	
	//Relationships -----------------------------------------------------------------------------
	private WorkShop workShop;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public WorkShop getWorkShop() {
		return workShop;
	}

	public void setWorkShop(WorkShop workShop) {
		this.workShop = workShop;
	}
	
	
	
}
