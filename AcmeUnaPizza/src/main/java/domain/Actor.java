package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends DomainEntity{

	//Attributes --------------------------------------------------------------------------------
	private String name;
	private String surname;
	private String email;
	

	public Actor(){
		super();
	}
	
	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotBlank
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Email
	@NotBlank
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String toString() {
		return "Actor [name=" + name + ", surname=" + surname + ", email="
				+ email + "]";
	}
	
	
	//Relationships -----------------------------------------------------------------------------
	private UserAccount userAccount;
	
	@Valid
	@NotNull
	@OneToOne(cascade=CascadeType.ALL, optional=false)
	public UserAccount getUserAccount() {
		return userAccount;
	}
	
	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

}
