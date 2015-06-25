package utilities.custom.validators;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.annotation.IncompleteAnnotationException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import utilities.internal.FileManager;

/**
 * Perform multiple validations according to D&T lecturers notes how to do, advices, restrictions, etc.
 * 
 * @author David Glez
 * (August 2014)
 * */
public class DomainModelValidator {
	
	// Attributes -------------------------------------------------------------------------
	private String className;
	private Method[] classMethods;
	private File domainPackage;
	
	// Constructor ------------------------------------------------------------------------
	public DomainModelValidator(String className){
		super();
		
		setClassName(className);
	}
	
	// Getters and setters ----------------------------------------------------------------
	public String getClassName(){
		return this.className;
	}
	
	public void setClassName(String className){
		if(className == null)
			throw new IllegalArgumentException("class name must not be null");
		else if(className.length()==0)
			throw new IllegalArgumentException("class name must not be empty");
		
		this.className = className;
	}

	// Checkouts methods ------------------------------------------------------------------
	@SuppressWarnings("rawtypes")
	/**
	 * Performs all checkouts to the given class.
	 * 
	 * @return a String list with all errors found
	 * */
	public List<String> performCheckouts(){
		Class domainClass;
		String sourceCode;
		List<String> res = new ArrayList<String>();
		FileManager fileMngr;

		// Initialization
		fileMngr = new FileManager("src\\main\\java\\domain\\"+this.className+".java");
		sourceCode = fileMngr.readFile();
		
		try{
			domainClass = Class.forName("domain."+this.className);
		}
		catch (Throwable oops){
			res.add("ERROR: Couldn't load "+this.className+" !!!!\n");
			return res;
		}
		
		this.classMethods = domainClass.getMethods();
		
		this.domainPackage = new File("src\\main\\java\\domain");
		
		// Perform checkouts
		res.addAll( annotationsCheckouts(domainClass) );
		res.addAll( checkImplementation(sourceCode, domainClass) );
		res.addAll( checkRelationships(domainClass) );
		
		return res;
	}
	
	@SuppressWarnings("rawtypes")
	private List<String> annotationsCheckouts(Class domainClass){
		List<String> res = new ArrayList<String>();
		Annotation[] annotations;
		/* flags for not found class annotations, index meanings are: 0=entity, 1=datatype, 2=accessType. If set, 
		   that annotation hasn't been found */
		int[] notFound = {1, 1, 1};	
		List<String> optionalAttributes = new ArrayList<String>();
		List<String> temporalAttributes = new ArrayList<String>();
		
		
		// Class annotations checkouts ---------------------------------------------------------------------------
		annotations = domainClass.getAnnotations();
		for(int i=0; i<annotations.length; i++){
			Annotation annotation = annotations[i];
			
			if		(annotation instanceof javax.persistence.Embeddable) notFound[1] = 0;
			else if (annotation instanceof javax.persistence.Entity){
				notFound[0] = 0;
				if (notFound[1] == 0) 
					res.add("- IMPLEMENTATION ERROR: found @Embeddable and @Entity in class "+domainClass.getName());
			}
			else if (annotation instanceof javax.persistence.Access) notFound[2] = 0;
		}
		
		if( notFound[0] == 1 && notFound[1] == 1 )
			res.add("- IMPLEMENTATION ERROR: neither @Entity nor @Embeddable were found in class "+domainClass.getName());
		if( notFound[2] == 1)
			res.add("- IMPLEMENTATION ERROR: access type annotation (@Access) wasn't found in class "+domainClass.getName());
		
		// Check date attributes annotations -----------------------------------------------------------------
		for( int i=0; i<classMethods.length; i++ ){
			Method method = classMethods[i];
			if( method.getReturnType().equals(java.util.Date.class) ){
				Annotation[] methodAnnotations = method.getAnnotations();
				Boolean annotationFound = false;
				Boolean hasDatetimeFormat = false;
				
				for ( int j=0; j<methodAnnotations.length; j++){
					Annotation annotation = methodAnnotations[j];
					if( annotation instanceof javax.persistence.Temporal ){
						try{
							annotationFound = true;
							((javax.persistence.Temporal) annotation).value();
						}
						catch(IncompleteAnnotationException oops){
							temporalAttributes.add("- TEMPORAL ATTRIBUTE ERROR: "+method.getName().substring(3)+
									" @Temporal annotation is incomplete.");
						}
					}
					
					else if ( annotation instanceof org.springframework.format.annotation.DateTimeFormat )
						hasDatetimeFormat = true;
				}
				
				if( !annotationFound ){
					String msg = "- TEMPORAL ATTRIBUTE ERROR: "+method.getName().substring(3)+
							" is temporal but hasn't @Temporal annotation";
					if( !hasDatetimeFormat )
						msg += " nor @DateTimeFormat annotation";
					
					res.add(msg);
				}
				else if( !hasDatetimeFormat )
					res.add("- TEMPORAL ATTRIBUTE ERROR: "+method.getName().substring(3)+
							" is temporal but hasn't @DateTimeFormat annotation");
			}
		}
		if( temporalAttributes.size()>0 ) res.addAll(temporalAttributes);
		
		// Find optional attributes ---------------------------------------------------------------------------------
		for( int i=0; i<classMethods.length; i++ ){
			Method method = classMethods[i];
			Annotation[] methodAnnotations = method.getAnnotations();
			
			for ( int j=0; j<methodAnnotations.length; j++ ){
				if ( methodAnnotations[j] instanceof javax.persistence.Transient)
					optionalAttributes.add(method.getName());
			}
		}
		
		if (optionalAttributes.size() > 0){
			String message = "+ Found ";
			for ( int i=0; i<optionalAttributes.size(); i++ ){
				if ( i%5 == 0 && i>0 ) message += "\n  ";
				message += optionalAttributes.get(i).substring(3);
				if ( i<optionalAttributes.size()-1 ) message += ", ";
			}
			message += " as optional attributes. Are you sure they are optional? If they aren't persistent, are their computation implemented?";
			
			res.add(message);
		}
		
		return res;
	}
	
	@SuppressWarnings("rawtypes")
	private List<String> checkRelationships(Class domainClass){
		List<String> res = new ArrayList<String>();
		List<Class> entities = new ArrayList<Class>();
		Object testingObject;
		
		// Load testing object
		try {
			testingObject = domainClass.newInstance();
		} catch (InstantiationException e) {
			res.add("ERROR: error while instantiating domain object (Instantiation error)");
			return res;
		} catch (IllegalAccessException e) {
			res.add("ERROR: error while instantiating domain object (Illegal access error)");
			return res;
		}
		
		// Look for entities
		for (File f : domainPackage.listFiles()){
			String className;
			Class classObject;
			Annotation[] classAnnotations;
			
			className = f.getName().substring(0, f.getName().indexOf('.'));
			try {
				classObject = Class.forName("domain."+className);
			} catch (ClassNotFoundException e) {
				res.add("ERROR: error loading a class during datatypes search");
				e.printStackTrace();
				return res;
			}
			
			classAnnotations = classObject.getAnnotations();
			for ( int i=0; i<classAnnotations.length; i++){
				if( classAnnotations[i] instanceof javax.persistence.Entity)
					entities.add(classObject);
			}
		}
		
		// Associations initialization checkouts --------------------------------------------------------------------------
		for( int i=0; i<classMethods.length; i++ ){
			Method method = classMethods[i];
			if( method.getReturnType().toString().equals( "interface java.util.Collection" )){
				try {
					if( method.invoke(testingObject) == null )
						res.add("- IMPLEMENTATION ERROR: "+method.getName().substring(3)+" collection isn't initialized in class constructor");
				} catch (Exception e) {
					res.add("ERROR: error invoking a method during association initialization checkouts");
					return res;
				}
			}
		}
		
		// Associations getters annotations checkouts --------------------------------------------------------------------
		for( int i=0; i<classMethods.length; i++){
			Method method = classMethods[i];
			Boolean valid = false, notNull = false, optional = false, annotated = false;
			Class returnParameter = method.getReturnType();
			
			if( entities.contains(returnParameter) || returnParameter.toString().contains("Collection") ){
				Annotation[] methodAnnotations = method.getAnnotations();
				String errorMsg = "- RELATIONSHIP IMPLEMENTATION ERROR: "+method.getName().substring(3);
				
				for( int j=0; j<methodAnnotations.length; j++){
					Annotation annotation = methodAnnotations[j];
					
					if( annotation instanceof javax.validation.Valid) valid = true;
					else if( annotation instanceof javax.validation.constraints.NotNull) notNull = true;
					else if( annotation.toString().contains("optional=true") ) optional = true;
					if( annotation instanceof javax.persistence.OneToMany || annotation instanceof javax.persistence.OneToOne ||
						annotation instanceof javax.persistence.ManyToOne || annotation instanceof javax.persistence.ManyToMany )
						annotated = true;
				}
				
				if( !annotated ) errorMsg += ", hasn't any relationship annotation";
				if( !valid ) errorMsg += ", hasn't @Valid annotation";
				if( notNull && optional ) errorMsg += ", has @NotNull and it's an optional relationship";
				else if( !notNull && !optional ) errorMsg += ", hasn't @NotNull annotation";
				
				if( !annotated || !valid || (notNull && optional) || (!notNull && !optional) ) res.add(errorMsg);
			}
			
		}
		
		return res;
	}
	
	@SuppressWarnings("rawtypes")
	private List<String> checkImplementation(String sourceCode, Class domainClass){
		List<String> res = new ArrayList<String>();
		Method[] classMethods;
		Field[] classFields;
		List<Class> datatypes = new ArrayList<Class>();
		Boolean clashFound = false;
		
		
		// Field getters and setters checkouts ----------------------------------------------------------------------------
		classFields = domainClass.getDeclaredFields();
		classMethods = domainClass.getMethods();
		for( int i=0; i<classFields.length; i++ ){
			Boolean hasGetter = false;
			Boolean hasSetter = false;
			String fieldName = classFields[i].getName();
			String errorMsg = "- IMPLEMENTATION ERROR: "+fieldName+" hasn't: ";
			
			for( int j=0; j<classMethods.length; j++ ){
				Method method = classMethods[j];
				fieldName = fieldName.substring(0, 1).toUpperCase().concat(fieldName.substring(1));
				
				if( method.getName().equals("get"+fieldName))	hasGetter = true;
				if( method.getName().equals("set"+fieldName))	hasSetter = true;
				
			}
			
			if (!hasGetter) errorMsg += "- Getter ";
			if (!hasSetter) errorMsg += "- Setter ";
			
			if( !hasGetter || !hasSetter ) res.add(errorMsg);
		}
		
		// Datatype attribute name clashes checkout --------------------------------------------------------
		// Look for datatypes
		for (File f : domainPackage.listFiles()){
			String className;
			Class classObject;
			Annotation[] classAnnotations;
			
			className = f.getName().substring(0, f.getName().indexOf('.'));
			try {
				classObject = Class.forName("domain."+className);
			} catch (ClassNotFoundException e) {
				res.add("ERROR: error loading a class during datatypes search");
				return res;
			}
			
			classAnnotations = classObject.getAnnotations();
			for ( int i=0; i<classAnnotations.length; i++){
				if( classAnnotations[i] instanceof javax.persistence.Embeddable)
					datatypes.add(classObject);
			}
		}
		
		// Look for attribute name clashes
		for( int i=0; i<classFields.length; i++ ){
			Field field = classFields[i];
			
			for( Class datatype:datatypes ){
				if( !domainClass.equals(datatype) ){
					Field[] datatypeFields = datatype.getDeclaredFields();
					
					for( int j=0; j<datatypeFields.length; j++ ){
						if( field.getName().equals(datatypeFields[j].getName()) ){
							res.add("- ATTRIBUTE NAME CLASH: found "+field.getName()+" in "+domainClass.getCanonicalName()+" and "+datatype.getCanonicalName());
							clashFound = true;
						}
					}
				}
			}
		}
		if( clashFound ) res.add("+ Note: table row names can be changed using class annotation @AttributeOverrides:\n"+
									"@AttributeOverrides({\n\t@AttributeOverride(name=\"<Attribute name>\",\n\t"+
									"column=@Column(name=\"<New name to avoid clashes>\"))\n})");
		
		return res;
	}
	
}
