package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = 
	@UniqueConstraint(columnNames={"cif"}))
public class Provider extends DomainEntity{

	//Attributes --------------------------------------------------------------------------------
	private String name;
	private String phone;
	private String cif;

	//Getters and setter ------------------------------------------------------------------------
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@NotBlank
	@Pattern(regexp="[A-Z][0-9]{8}")
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}
	
	//Relationships -----------------------------------------------------------------------------
	private Collection<Product> products;

	@Valid
	@NotNull
	@OneToMany(mappedBy="provider")
	public Collection<Product> getProducts() {
		return products;
	}

	public void setProducts(Collection<Product> products) {
		this.products = products;
	}
	
	
	
}