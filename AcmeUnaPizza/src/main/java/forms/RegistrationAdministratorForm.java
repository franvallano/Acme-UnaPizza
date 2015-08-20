package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Access(AccessType.PROPERTY)
public class RegistrationAdministratorForm {

	
		// Constructors ---------------------------------------------------

		public RegistrationAdministratorForm() {
			super();
		}

		// Attributes -----------------------------------------------------

		private String username;
		private String password;
		private String name;
		private String surname;
		private String email;
		
		// Methods ---------------------------------------------------------
		
		@Size(min = 5, max = 32)
		public String getUsername() {
			return username;
		}
		
		public void setUsername(String username) {
			this.username = username;
		}
		
		@Size(min = 5, max = 32)
		public String getPassword() {
			return password;
		}
		
		public void setPassword(String password) {
			this.password = password;
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
				
	
}
