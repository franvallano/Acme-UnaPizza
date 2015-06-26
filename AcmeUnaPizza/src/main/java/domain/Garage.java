package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Garage extends DomainEntity{

	//Attributes --------------------------------------------------------------------------------
	private String location;
	private Integer size;
	
	//Getters and setter ------------------------------------------------------------------------

	@NotBlank
	@Size(min = 10, max = 250)
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	@Min(0)
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	
	//Relationships -----------------------------------------------------------------------------
	private Collection<Motorbike> motorbikes;

	@Valid
	@NotNull
	@OneToMany(mappedBy = "garage")
	public Collection<Motorbike> getMotorbikes() {
		return motorbikes;
	}
	public void setMotorbikes(Collection<Motorbike> motorbikes) {
		this.motorbikes = motorbikes;
	}

	
	
}

