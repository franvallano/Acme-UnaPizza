package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;


@Embeddable
@Access(AccessType.PROPERTY)
public class Motorbike {

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

}
