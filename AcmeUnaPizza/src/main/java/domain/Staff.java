package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Staff extends DomainEntity{

	//Attributes --------------------------------------------------------------------------------
	private String dni;
	private String ssNumber;
	private Date contractStartDate;
	private Date contractEndDate;
	private String accountNumber;
	private String phone;
	private Date birthDate;
	private String address;
	
	//Constructor -------------------------------------------------------------------------------
	public Staff(){
		super();
	}
	
	//Getters and setter ------------------------------------------------------------------------
	
	//HAY QUE PONER BIEN EL PATRON
	@NotBlank
	//@Pattern(regexp = )
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}
	
	//AÑADIR PATRON
	@NotBlank
	public String getSsNumber() {
		return ssNumber;
	}

	public void setSsNumber(String ssNumber) {
		this.ssNumber = ssNumber;
	}

	@Past
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getContractStartDate() {
		return contractStartDate;
	}

	public void setContractStartDate(Date contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	@Past
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getContractEndDate() {
		return contractEndDate;
	}

	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	//AÑADIR PATRON
	@NotBlank
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@NotBlank
	//AÑADIR PATRON
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Past
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@NotBlank
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	@Override
	public String toString() {
		return "Staff [dni=" + dni + ", ssNumber=" + ssNumber + ", phone="
				+ phone + ", address=" + address + "]";
	}


	//Relationships -----------------------------------------------------------------------------
	private Collection<Repair> repairs;
	private Collection<Stuff> stuffs;

	@Valid
	@OneToMany(mappedBy="staff")
	public Collection<Repair> getRepairs() {
		return repairs;
	}

	public void setRepairs(Collection<Repair> repairs) {
		this.repairs = repairs;
	}
	
	//ESTA BIEN?
	@Valid
	@ManyToMany(cascade = CascadeType.ALL)
	public Collection<Stuff> getStuffs() {
		return stuffs;
	}

	public void setStuffs(Collection<Stuff> stuffs) {
		this.stuffs = stuffs;
	}
	
	
}
