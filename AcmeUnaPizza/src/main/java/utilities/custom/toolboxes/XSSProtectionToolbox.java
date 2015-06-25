package utilities.custom.toolboxes;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import utilities.internal.FileManager;

/**
 * The hacking protection tool-box helps to ensure project XSS hacking protection. It can also protect
 * all String fields automatically. 
 * */
public class XSSProtectionToolbox {

	// Attributes --------------------------------------------------------
	private String entityName;
	
	// Constructor -------------------------------------------------------
	public XSSProtectionToolbox( String entityName ){
		super();
		
		setEntityName( entityName );
	}
	
	// Getters and setters -----------------------------------------------
	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		if( entityName == null )
			throw new IllegalArgumentException( "entity name must not be null" );
		else if( entityName.length()==0 )
			throw new IllegalArgumentException( "entity name must not be empty" );
		
		this.entityName = entityName;
	}

	// Validation functionality ------------------------------------------
	/**
	 * Checks all entities looking for String fields to prevent XSS attacks.
	 * 
	 * @param verbose - true if susceptible fields have to be displayed, else false. 
	 * @return List of all String fields susceptible to XSS attacks, or null if an error occurs.
	 **/
	@SuppressWarnings("rawtypes")
	public List<String> safeHtmlCheckout(Boolean verbose){
		List<String> res = new ArrayList<String>();
		Method[] entityMethods;
		Class entity;
		
		try {
			entity = Class.forName("domain."+this.entityName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException( "ERROR: class domain."+this.entityName+" wasn't found (ClassNotFoundException)" );
		}
		
		entityMethods = entity.getDeclaredMethods();
		for( int i=0; i<entityMethods.length; i++ ){
			Method method;
			String fieldName = this.entityName+".";
			String methodName;
			
			method = entityMethods[i];
			methodName = method.getName();
			if(methodName.contains("get") && method.getReturnType().equals(String.class)){
				fieldName += methodName.substring(3);
				
				if( method.getAnnotation(javax.validation.constraints.Pattern.class) == null )
					if( method.getAnnotation(org.hibernate.validator.constraints.SafeHtml.class) == null ){
						if( verbose) System.out.println("+WARNING: field "+fieldName+" is susceptible to XSS attacks!");
						res.add(this.entityName+"."+method.getName());
					}
			}
		}
		
		return res;
	}
	
	/**
	 * Looks for all String fields susceptible to XSS attacks and add to them an SafeHtml annotation.
	 * 
	 * @param verbose - true if fields that were protected have to be displayed, else false.
	 * */
	public void safeHtmlProtection(Boolean verbose){
		List<String> susceptibleFields;
		String annotationPattern = "@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)";
		String[] necessaryImports = {"import org.hibernate.validator.constraints.SafeHtml;",
									"import org.hibernate.validator.constraints.SafeHtml.WhiteListType;"};
		
		susceptibleFields = safeHtmlCheckout(false);
		
		for( int i=0; i<susceptibleFields.size(); i++ ){
			String entityName, methodName;
			FileManager fileMngr;
			String srcCode;
			
			// This block loads the entity source code ---------------------------------------------------------
			entityName = susceptibleFields.get(i);
			entityName = entityName.substring( 0, entityName.indexOf('.') );
			fileMngr = new FileManager("src\\main\\java\\domain\\"+entityName+".java");
			if( !fileMngr.getFile().exists() ){
				throw new RuntimeException(entityName+" source code couldn't be loaded (.java file not found)");
			}
			srcCode = fileMngr.readFile();

			for( int j=0; j<necessaryImports.length; j++){
				if( !srcCode.contains(necessaryImports[j]) ){
					srcCode = srcCode.replaceFirst("import", necessaryImports[j]+"\nimport");
					if( verbose ){
						String s = necessaryImports[j];
						s = s.substring( s.lastIndexOf('.')+1, s.length()-1 );
						System.out.println(" added "+s+" annotation to "+entityName);
					}
				}
			}
			
			methodName = susceptibleFields.get(i);
			methodName = methodName.substring( methodName.indexOf('.')+1 );
			srcCode = srcCode.replace("public String "+methodName, annotationPattern+"\n\tpublic String "+methodName);
			
			fileMngr.writeFile(srcCode);
			if( verbose ) System.out.println( entityName+" sucessfully protected" );
		}
	}
}
