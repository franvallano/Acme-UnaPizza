package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
		@Index(columnList = "startDate"),
		@Index(columnList = "endDate"),
		@Index(columnList = "rangee")})
public class Offer extends DomainEntity{

	//Attributes --------------------------------------------------------------------------------
	private String name;
	private Date startDate;
	private Date endDate;
	private int discount;
	private String rangee;
	private String loopp;
	
	
	//Getters and setter ------------------------------------------------------------------------
	@NotBlank
	@Size(min = 5, max = 32)
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	@Range(min = 1, max = 100)
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	@NotBlank
	@Pattern(regexp = "^STANDARD$|^SILVER$|^GOLD$|^VIP$")
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getRangee() {
		return rangee;
	}
	public void setRangee(String rangee) {
		this.rangee = rangee;
	}

	@NotBlank
	@Pattern(regexp = "[LMXJVSD]{1,7}")
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getLoopp() {
		return loopp;
	}
	public void setLoopp(String loopp) {
		this.loopp = loopp;
	}

	
}