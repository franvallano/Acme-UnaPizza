package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;


@Embeddable
@Access(AccessType.PROPERTY)
public class Note {
	
		//Attributes --------------------------------------------------------------------------------
		private String cause;
		private String description;
		
		//Constructor -------------------------------------------------------------------------------
		public Note(){
		}

		//Getters and setter ------------------------------------------------------------------------
		@NotBlank
		@Pattern(regexp = "^joke$|^cancelled$|^undelivered$|^other$")
		public String getCause() {
			return cause;
		}

		public void setCause(String cause) {
			this.cause = cause;
		}

		@NotBlank
		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		
		public String toString() {
			return "Note [cause=" + cause + ", description=" + description
					+ "]";
		}
		
		//Relationships -----------------------------------------------------------------------------
		
	}
