package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class WorkShop extends DomainEntity{

	//Attributes --------------------------------------------------------------------------------
	private String company;
	private String city;
	private Double taxes;
	private String phoneNumber;
	private String contact;
	
	//Getters and setter ------------------------------------------------------------------------

	@NotBlank
	@Size(min = 5, max = 32)
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}


	@NotBlank
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}


	@Digits(fraction = 3, integer = 2)
	@Min(0)
	public Double getTaxes() {
		return taxes;
	}
	public void setTaxes(Double taxes) {
		this.taxes = taxes;
	}

	
	//REVISAR
	@Pattern(regexp = "+[0-9]{2}-[0-9]{9}")
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	@Size(min = 5, max = 32)
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}

	
	@Override
	public String toString() {
		return "WorkShop [company=" + company + ", city=" + city + ", taxes="
				+ taxes + ", phoneNumber=" + phoneNumber + ", contact="
				+ contact + "]";
	}

	//Relationships -----------------------------------------------------------------------------
	private Collection<Repair> repairs;
	private Collection<Stuff> stuffs;

	@Valid
	@OneToMany(mappedBy="workShop")
	public Collection<Repair> getRepairs() {
		return repairs;
	}
	public void setRepairs(Collection<Repair> repairs) {
		this.repairs = repairs;
	}

	
	//REVISAR
	@Valid
	@NotNull
	@ManyToMany
	public Collection<Stuff> getStuffs() {
		return stuffs;
	}
	public void setStuffs(Collection<Stuff> stuffs) {
		this.stuffs = stuffs;
	}
	
	
	
	
}
