package forms;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

public class AdministratorForm {
	// Attributes -----------------------------------------------------
	private String username;
	private String password;
	private String name;
	private String surname;
	private String email;
	private String repeatedPass;

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

}
