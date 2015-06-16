package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;


@Embeddable
@Access(AccessType.PROPERTY)
public class Motorbike {
	
		//Attributes --------------------------------------------------------------------------------
		private Integer number;
		private Long drivingTime;
		private String licensePlate;
		
		//Constructor -------------------------------------------------------------------------------
		public Motorbike(){
		}

		//Getters and setter ------------------------------------------------------------------------
		
		@Min(1)
		public Integer getNumber() {
			return number;
		}

		public void setNumber(Integer number) {
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
		public String getLicensePlate() {
			return licensePlate;
		}

		public void setLicensePlate(String licensePlate) {
			this.licensePlate = licensePlate;
		}

		
		
		//Relationships -----------------------------------------------------------------------------
		
	}
