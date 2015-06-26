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

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;


@Entity
@Access(AccessType.PROPERTY)
public class Motorbike extends DomainEntity{

	// Attributes
	// --------------------------------------------------------------------------------
	private int number;
	private Long drivingTime;
	private String licensePlate;

	// Getters and setter
	// ------------------------------------------------------------------------

	@Min(1)
	@Column(unique = true)
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Min(0)
	public Long getDrivingTime() {
		return drivingTime;
	}

	public void setDrivingTime(Long drivingTime) {
		this.drivingTime = drivingTime;
	}

	@NotBlank
	@Pattern(regexp = "[0-9]{4}[A-Z]{3}")
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	// Relationships
	// -----------------------------------------------------------------------------
	private Garage garage;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Garage getGarage() {
		return garage;
	}

	public void setGarage(Garage garage) {
		this.garage = garage;
	}
	
	
}
