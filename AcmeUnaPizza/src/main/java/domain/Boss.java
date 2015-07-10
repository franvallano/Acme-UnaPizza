package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Entity
@Access(AccessType.PROPERTY)
public class Boss extends Staff{

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	private Collection<SalesOrder> salesOrders;
	private Collection<Stuff> stuffs;
	private Collection<Repair> repairs;

	@Valid
	@NotNull
	@OneToMany(mappedBy = "boss")
	public Collection<SalesOrder> getSalesOrders() {
		return salesOrders;
	}

	public void setSalesOrders(Collection<SalesOrder> salesOrders) {
		this.salesOrders = salesOrders;
	}

	@Valid
	@NotNull
	@ManyToMany
	public Collection<Stuff> getStuffs() {
		return stuffs;
	}

	public void setStuffs(Collection<Stuff> stuffs) {
		this.stuffs = stuffs;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "staff")
	public Collection<Repair> getRepairs() {
		return repairs;
	}

	public void setRepairs(Collection<Repair> repairs) {
		this.repairs = repairs;
	}
	
	
}