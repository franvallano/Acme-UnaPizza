package forms;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

public class PurchaseOrderForm {
	private String referenceNumber;
	private Double totalCost;
	private Date creationMoment;
	private Collection<Integer> idPizzas;
	// List para poder repetir 
	private List<Double> amountPizzas;
	private Collection<Integer> idComplements;
	private List<Double> amountComplements;
	private Collection<Integer> idDesserts;
	private List<Double> amountDesserts;
	private Collection<Integer> idDrinks;
	private List<Double> amountDrinks;
	
	@NotBlank
	@Pattern(regexp="[A-Z]{3}-[0-9]{8}")
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getReferenceNumber() {
		return referenceNumber;
	}
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	
	@Min(0)
	@Digits(integer=9, fraction=2)
	public Double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}
	
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getCreationMoment() {
		return creationMoment;
	}
	public void setCreationMoment(Date creationMoment) {
		this.creationMoment = creationMoment;
	}
	
	@NotEmpty
	public List<Double> getAmountPizzas() {
		return amountPizzas;
	}
	public void setAmountPizzas(List<Double> amountPizzas) {
		this.amountPizzas = amountPizzas;
	}
	
	@NotEmpty
	public Collection<Integer> getIdPizzas() {
		return idPizzas;
	}
	public void setIdPizzas(Collection<Integer> idPizzas) {
		this.idPizzas = idPizzas;
	}
	
	@NotEmpty
	public Collection<Integer> getIdComplements() {
		return idComplements;
	}
	public void setIdComplements(Collection<Integer> idComplements) {
		this.idComplements = idComplements;
	}
	
	@NotEmpty
	public List<Double> getAmountComplements() {
		return amountComplements;
	}
	public void setAmountComplements(List<Double> amountComplements) {
		this.amountComplements = amountComplements;
	}
	
	@NotEmpty
	public Collection<Integer> getIdDesserts() {
		return idDesserts;
	}
	public void setIdDesserts(Collection<Integer> idDesserts) {
		this.idDesserts = idDesserts;
	}
	
	@NotEmpty
	public List<Double> getAmountDesserts() {
		return amountDesserts;
	}
	public void setAmountDesserts(List<Double> amountDesserts) {
		this.amountDesserts = amountDesserts;
	}
	
	@NotEmpty
	public Collection<Integer> getIdDrinks() {
		return idDrinks;
	}
	public void setIdDrinks(Collection<Integer> idDrinks) {
		this.idDrinks = idDrinks;
	}
	
	@NotEmpty
	public List<Double> getAmountDrinks() {
		return amountDrinks;
	}
	public void setAmountDrinks(List<Double> amountDrinks) {
		this.amountDrinks = amountDrinks;
	}
	
	
}
