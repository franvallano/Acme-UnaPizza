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
	private Double stockPrice;
	private Double salePrice;
	private Integer actualStock;
	private Integer minStock;
	
	//Getters and setter ------------------------------------------------------------------------
	@NotBlank
	@Pattern(regexp="^pizza$|^drink$|^complement$|^dessert$")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@NotBlank
	@Column(unique=true)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Size(min=10, max=250)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotBlank
	@Pattern(regexp="[a-zA-Z]{2,5}")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Min(0)
	@Digits(integer=9, fraction=2)
	public Double getStockPrice() {
		return stockPrice;
	}

	public void setStockPrice(Double stockPrice) {
		this.stockPrice = stockPrice;
	}

	@Min(0)
	@Digits(integer=9, fraction=2)
	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	@Min(0)
	public Integer getActualStock() {
		return actualStock;
	}

	public void setActualStock(Integer actualStock) {
		this.actualStock = actualStock;
	}

	@Min(0)
	public Integer getMinStock() {
		return minStock;
	}

	public void setMinStock(Integer minStock) {
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

