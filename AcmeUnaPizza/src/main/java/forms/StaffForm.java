package forms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

import domain.Motorbike;

public class StaffForm {
	// Attributes -----------------------------------------------------
	private String username;
	private String password;
	private String name;
	private String surname;
	private String email;
	private String repeatedPass;
	private String dni;
	private String ssNumber;
	private String accountNumber;
	private String phone;
	private Date birthDate;
	private String address;
	private String drivingLicenseNumber;
	private Motorbike motorbike;

	// Methods ---------------------------------------------------------

	@Size(min=5, max=32)
	@SafeHtml(whitelistType=WhiteListType.SIMPLE_TEXT)
	@NotBlank
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Size(min=5, max=32)
	@SafeHtml(whitelistType=WhiteListType.SIMPLE_TEXT)
	@NotBlank
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@SafeHtml(whitelistType=WhiteListType.SIMPLE_TEXT)
	@NotBlank
	@Size(min=5, max=32)
	public String getRepeatedPass() {
		return repeatedPass;
	}
	public void setRepeatedPass(String repeatedPass) {
		this.repeatedPass = repeatedPass;
	}

	@SafeHtml(whitelistType=WhiteListType.SIMPLE_TEXT)
	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@SafeHtml(whitelistType=WhiteListType.SIMPLE_TEXT)
	@NotBlank
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@SafeHtml(whitelistType=WhiteListType.SIMPLE_TEXT)
	@NotBlank
	@Email
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotBlank
	@Pattern(regexp = "[0-9]{8}[A-Z]")
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	@NotBlank
	@Pattern(regexp = "[A-Z]{2}-[0-9]{10}")
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getSsNumber() {
		return ssNumber;
	}

	public void setSsNumber(String ssNumber) {
		this.ssNumber = ssNumber;
	}

	@NotBlank
	@Pattern(regexp = "[0-9]{20}")
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
	@DateTimeFormat(pattern = "dd/MM/yyyy")
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

	// Aqui no lleva @NotBlank para que el Form no salte cuando se registre algun Staff que NO sea DeliveryMan
	// En la clase de dominio si lo lleva
	@Pattern(regexp = "[0-9]{8}[A-Z]")
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getDrivingLicenseNumber() {
		return drivingLicenseNumber;
	}

	public void setDrivingLicenseNumber(String drivingLicenseNumber) {
		this.drivingLicenseNumber = drivingLicenseNumber;
	}

	@Valid
	public Motorbike getMotorbike() {
		return motorbike;
	}

	public void setMotorbike(Motorbike motorbike) {
		this.motorbike = motorbike;
	}
	
}
