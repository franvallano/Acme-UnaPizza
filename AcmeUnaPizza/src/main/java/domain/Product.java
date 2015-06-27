package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Product extends DomainEntity{

	//Attributes --------------------------------------------------------------------------------
	private String type;
	private String name;
	private String description;
	private String code;
	private double stockPrice;
	private double salePrice;
	private int actualStock;
	private int minStock;
	
	//Getters and setter ------------------------------------------------------------------------
	@NotBlank
	@Pattern(regexp="^PIZZA$|^DRINK$|^COMPLEMENT$|^DESSERT$")
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@NotBlank
	@Column(unique=true)
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Size(min=10, max=250)
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotBlank
	@Pattern(regexp="[a-zA-Z]{2,5}")
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	@Column(unique = true)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Min(0)
	@Digits(integer=9, fraction=2)
	public double getStockPrice() {
		return stockPrice;
	}

	public void setStockPrice(double stockPrice) {
		this.stockPrice = stockPrice;
	}

	@Min(0)
	@Digits(integer=9, fraction=2)
	public double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	@Min(0)
	public int getActualStock() {
		return actualStock;
	}

	public void setActualStock(int actualStock) {
		this.actualStock = actualStock;
	}

	@Min(0)
	public int getMinStock() {
		return minStock;
	}

	public void setMinStock(int minStock) {
		this.minStock = minStock;
	}
	
	//Relationships -----------------------------------------------------------------------------
	private Provider provider;

	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}
	
	
}

