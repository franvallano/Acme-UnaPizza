package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Staff extends Actor{

	//Attributes --------------------------------------------------------------------------------
	private String dni;
	private String ssNumber;
	private Date contractStartDate;
	private Date contractEndDate;
	private String accountNumber;
	private String phone;
	private Date birthDate;
	private String address;
	
	//Getters and setter ------------------------------------------------------------------------
	
	@NotBlank
	@Pattern(regexp = "[0-9]{8}[A-Z]")
	@Column(unique = true)
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}
	
	@NotBlank
	@Pattern(regexp = "[A-Z]{2}-[0-9]{10}")
	@Column(unique = true)
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getSsNumber() {
		return ssNumber;
	}

	public void setSsNumber(String ssNumber) {
		this.ssNumber = ssNumber;
	}

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getContractStartDate() {
		return contractStartDate;
	}

	public void setContractStartDate(Date contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getContractEndDate() {
		return contractEndDate;
	}

	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	
	@NotBlank
	@Pattern(regexp = "[0-9]{20}")
	@Column(unique = true)
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@NotBlank
	@Pattern(regexp = "(((\\+34)? ?(\\(0\\))? ?)|(0))( ?[0-9]{3,4}){3}")
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}

