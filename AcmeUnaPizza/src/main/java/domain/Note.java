package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;


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
		
		public String getCause() {
			return cause;
		}

		public void setCause(String cause) {
			this.cause = cause;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
		
		
		//Relationships -----------------------------------------------------------------------------
		
	}
