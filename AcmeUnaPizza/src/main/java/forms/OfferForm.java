package forms;

import java.util.Date;

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

public class OfferForm {
	// Attributes -----------------------------------------------------
	private Integer id;
	private String name;
	private Date startDate;
	private Date endDate;
	private int discount;
	private String rangee;
	private boolean monday;
	private boolean tuesday;
	private boolean wednesday;
	private boolean thursday;
	private boolean friday;
	private boolean saturday;
	private boolean sunday;
	private boolean allDays;
	
	public OfferForm() {
		// Todos los dias activados por defecto
		setMonday(true);
		setTuesday(true);
		setWednesday(true);
		setThursday(true);
		setFriday(true);
		setSaturday(true);
		setSunday(true);
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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
	
	@Range(min = 1,max = 100)
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
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
	
	public boolean isMonday() {
		return monday;
	}
	
	public void setMonday(boolean monday) {
		this.monday = monday;
	}
	
	public boolean isTuesday() {
		return tuesday;
	}
	
	public void setTuesday(boolean tuesday) {
		this.tuesday = tuesday;
	}
	
	public boolean isWednesday() {
		return wednesday;
	}
	
	public void setWednesday(boolean wednesday) {
		this.wednesday = wednesday;
	}
	
	public boolean isThursday() {
		return thursday;
	}
	
	public void setThursday(boolean thursday) {
		this.thursday = thursday;
	}
	
	public boolean isFriday() {
		return friday;
	}
	
	public void setFriday(boolean friday) {
		this.friday = friday;
	}
	
	public boolean isSaturday() {
		return saturday;
	}
	
	public void setSaturday(boolean saturday) {
		this.saturday = saturday;
	}
	
	public boolean isSunday() {
		return sunday;
	}
	
	public void setSunday(boolean sunday) {
		this.sunday = sunday;
	}
	
	public boolean isAllDays() {
		return allDays;
	}
	
	public void setAllDays(boolean allDays) {
		this.allDays = allDays;
	}

	// Methods ---------------------------------------------------------
	
	
}
