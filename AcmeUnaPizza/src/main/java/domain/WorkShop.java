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
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class WorkShop extends DomainEntity{

	//Attributes --------------------------------------------------------------------------------
	private String company;
	private String city;
	private double taxes;
	private String phoneNumber;
	private String contact;
	
	//Getters and setter ------------------------------------------------------------------------

	@NotBlank
	@Size(min = 5, max = 32)
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}


	@Digits(fraction = 3, integer = 2)
	@Min(0)
	public double getTaxes() {
		return taxes;
	}
	public void setTaxes(double taxes) {
		this.taxes = taxes;
	}
	
	@Pattern(regexp = "(((\\+34)? ?(\\(0\\))? ?)|(0))( ?[0-9]{3,4}){3}")
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	@Size(min = 5, max = 32)
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}

	//Relationships -----------------------------------------------------------------------------
	private Collection<Repair> repairs;

	@Valid
	@OneToMany(mappedBy="workShop")
	public Collection<Repair> getRepairs() {
		return repairs;
	}
	public void setRepairs(Collection<Repair> repairs) {
		this.repairs = repairs;
	}
	
}
