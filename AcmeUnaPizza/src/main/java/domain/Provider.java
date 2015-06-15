package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Provider extends DomainEntity{

	//Attributes --------------------------------------------------------------------------------
	private String name, phone, cif;
	
	//Constructor -------------------------------------------------------------------------------
	public Provider(){
		super();
	}
	
	//Getters and setter ------------------------------------------------------------------------
	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	@Pattern(regexp="[0-9]{9}")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@NotBlank
	@Pattern(regexp="[A-Z][0-9]{8}")
	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}
	
	//Relationships -----------------------------------------------------------------------------
	
}
