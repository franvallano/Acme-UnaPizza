package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames={"referenceCode"})},
	indexes = {
		@Index(columnList = "status")})

public class Stuff extends DomainEntity{

	//Attributes --------------------------------------------------------------------------------
	private String name;
	private String status;
	private String referenceCode;
	private Integer powerConsumption;
	
	//Getters and setter ------------------------------------------------------------------------

	@NotBlank
	@Size(min = 5, max = 32)
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@NotBlank
	@Pattern(regexp= "^OK$|^MALFUNCTION$|^REPAIRING$")
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	@NotBlank
	@Pattern(regexp="[a-zA-Z]{3}[0-9]{5}")
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
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
	private Collection<Repair> repairs;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public WorkShop getWorkShop() {
		return workShop;
	}

	public void setWorkShop(WorkShop workShop) {
		this.workShop = workShop;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy="stuff")
	public Collection<Repair> getRepairs() {
		return repairs;
	}

	public void setRepairs(Collection<Repair> repairs) {
		this.repairs = repairs;
	}
}
