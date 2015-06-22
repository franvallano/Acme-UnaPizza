package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Entity
@Access(AccessType.PROPERTY)
public class Boss extends Staff{

	// Constructors -----------------------------------------------------------

	public Boss() {
		super();
	}

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	private Collection<SalesOrder> salesOrders;

	@Valid
	@NotNull
	@OneToMany(mappedBy = "boss")
	public Collection<SalesOrder> getSalesOrders() {
		return salesOrders;
	}

	public void setSalesOrders(Collection<SalesOrder> salesOrders) {
		this.salesOrders = salesOrders;
	}
}