package utilities.custom.toolboxes;

/**
 * Datatype that contains all data necessary to define an entities association
 * */
public class Association {

	// Fields ----------------------------
	private String aMultiplicity, bMultiplicity;
	private String aRole, bRole, association;
	/**
	 * options like optionality, cascade association, etc.
	 * */
	private String options;
	/**
	 * association type (OneToOne, OneToMany, etc.)
	 * */
	private String type = "";
	
	// Constructor ----------------------
	public Association(){
		throw new UnsupportedOperationException("Must specify at least entities mutiplicity and association type");
	}
	
	public Association(String aMultiplicity, String bMultiplicity, String association,
			String aRole, String bRole){
		checkAssociationMultiplicity(aMultiplicity);
		checkAssociationMultiplicity(aMultiplicity);
		checkAssociatonType(association);
		
		this.aMultiplicity = aMultiplicity;
		this.bMultiplicity = bMultiplicity;
		this.aRole = aRole;
		this.bRole = bRole;
		this.association = association;
		
		if(!isValid())
			throw new IllegalArgumentException("Association that was going to be created is invalid");
		
		if(association.equals("<>-->")){
			options = "(cascade = CascadeType.ALL)";
			type = "composition";
		}
		else if(association.equals("<->->")){
			options = "(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})";
			type = "agregation";
		}
		else if(bRole.equals("1")){
			options = "(optional=false)";
			type = "generalization";
		}
	}
	
	// Getters & setters ---------------------
	public String getaMultiplicity() {
		return aMultiplicity;
	}

	public String getbMultiplicity() {
		return bMultiplicity;
	}

	public String getaRole() {
		return aRole;
	}

	public String getbRole() {
		return bRole;
	}

	public String getAssociation() {
		return association;
	}

	public String getOptions() {
		return options;
	}
	
	/**
	 * Get association type represented as string, i.e. OneToOne or Composition
	 * @return association type as string
	 * */
	public String getType(){
		if(!type.isEmpty())
			return type;
		
		if(aMultiplicity.equals("0") || aMultiplicity.equals("1"))
			type+="OneTo";
		else
			type+="ManyTo";
		
		if(bMultiplicity.equals("0") || bMultiplicity.equals("1"))
			type+="One";
		else
			type+="Many";
		
		return type;
	}
	
	// Association business logic ------------
	/**
	 * Checks if an association is inefficient or not.
	 * 
	 * @param associationType - association to be checked
	 * @return true if association is inefficient, false otherwise
	 * */
	public Boolean isInefficient(){
		Boolean res = false;
		String associationType;
		
		associationType = aMultiplicity+association+bMultiplicity;
		
		if(	associationType.equals("0---->+")	||  // 1 -> 0..*
			associationType.equals("0---->*")	||	// 1 -> 1..*
			associationType.equals("1---->+")	||	// 0..1 -> 0..*
			associationType.equals("1---->*"))		// 0..1 -> 1..*
				res = true;
		
		return res;
	}

	// Ancillary methods ---------------------
	private void checkAssociationMultiplicity(String multiplicity){
		if(		!multiplicity.equals("0") && !multiplicity.equals("1") && 
				!multiplicity.equals("+") && !multiplicity.equals("*")	)
			throw new IllegalArgumentException("Invalid association multiplicity '"+multiplicity+"'");
	}
	
	private void checkAssociatonType(String association){
		if( 	!association.equals("---->") &&
				!association.equals("<--->") &&
				!association.equals("<>-->") &&
				!association.equals("<->->") &&
				!association.equals(">--->"))
			throw new IllegalArgumentException("Invalid association type '"+association+"'");
	}
	
	/**
	 * Checks if the association is a valid association, i.e. composition with 0..* multiplicity 
	 * in both association ends isn't a valid association
	 * 
	 * @param association - association to check
	 * @return true if association is valid, else otherwise
	 * */
	private Boolean isValid(){
		Boolean res = true;
		
		if(association.equals("<>-->")){
			if(!aMultiplicity.equals('*') || !bMultiplicity.equals('*'))
				res = false;
		}
		else if(association.equals("<->->")){
			if(!aMultiplicity.equals('1') || !bMultiplicity.equals('*'))
				res = false;
		}
		else if(association.equals(">--->")){
			if(!aMultiplicity.equals('1') || !bMultiplicity.equals('1'))
				res = false;
		}
		
		return res;
	}
}
