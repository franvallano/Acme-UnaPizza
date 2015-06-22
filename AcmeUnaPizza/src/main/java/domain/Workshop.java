package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
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
public class Workshop extends DomainEntity {
	//Attributes --------------------------------------------------------------------------------
	private String company;
	private String city;
	private Double taxes;
	private String phoneNumber;
	private String contact;
		
	//Constructor -------------------------------------------------------------------------------
	public Workshop(){
		super();
	}

	//Getters and setter ------------------------------------------------------------------------
	@NotBlank
	@Size(min=10, max=250)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Min(0)
	@Digits(integer=3, fraction=2)
	public Double getTaxes() {
		return taxes;
	}

	public void setTaxes(Double taxes) {
		this.taxes = taxes;
	}
	
	@Pattern(regexp = "[0-9]{9}")
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Size(min=10, max=250)
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	//Relationships -----------------------------------------------------------------------------
	private Collection<Repair> repairs;

	@Valid
	@NotNull
	@OneToMany(mappedBy="workshop")
	public Collection<Repair> getRepairs() {
		return repairs;
	}

	public void setRepairs(Collection<Repair> repairs) {
		this.repairs = repairs;
	}
	
	
}
