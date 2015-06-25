package utilities.custom.toolboxes;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import utilities.internal.FileManager;

public class FormObjectToolbox {

	// Attributes ---------------------------------------------------------------------------------
	private String entityName;
	
	// Constructor --------------------------------------------------------------------------------
	public FormObjectToolbox( String entityName ){
		setEntityName( entityName );
	}
	
	// Getters and setters ------------------------------------------------------------------------
	public String getEntityName(){
		return this.entityName;
	}
	
	public void setEntityName( String entityName ){
		if( entityName == null )
			throw new IllegalArgumentException("El nombre de la entidad no puede ser null");
		else if( entityName.isEmpty() )
			throw new IllegalArgumentException("El nombre de la entidad no puede estar vacio");
		else{
			File entitiesPkg = new File("src\\main\\java\\domain");
			Boolean exists = false;
			
			for( String s : entitiesPkg.list() ){
				if( s.equals( entityName+".java" )){
					exists = true;
					break;
				}
			}
			
			if( !exists )
				throw new IllegalArgumentException("La entidad "+entityName+" no existe en el paquete 'domain'");
		}
		
		this.entityName = entityName;
	}
	
	// Functionality methods ----------------------------------------------------------------------
	/**
	 * Generates a new form object class (it will be stored in 'forms' package).
	 * 
	 * @throws SecurityException - if a reflection API security violation occurs
	 * @throws NoSuchMethodException - Some of the entity methods could not be loaded by the reflection API
	 * */
	public void generate() throws NoSuchMethodException, SecurityException{
		List<String> notRelationships = new ArrayList<String>(); // Fields that doesn't implement entity relationships
		
		notRelationships = findEntityFields();
		
		generateFormObjectSrcCode( notRelationships );
		System.out.println("Form object for "+this.entityName+" has been successfully generated. DON'T FORGET TO ADD ALL NECESSARY ANNOTATIONS!!!");
	}
	
	/**
	 * Check if given form object has, at least, all fields that the corresponding entity has.
	 *
	 * @return true if form object is valid, else false
	 * @throws ClassNotFoundException	- if there aren't form objects corresponding to the entity
	 * */
	@SuppressWarnings("rawtypes")
	public boolean validate() throws ClassNotFoundException{
		Boolean res;
		List<String> entityFields = new ArrayList<String>();
		List<String> formObjectFields = new ArrayList<String>();
		Field[] aux;
		Class formObject;
		
		// Load both objects field names
		entityFields = findEntityFields();
		for( int i=0; i<entityFields.size(); i++ ){
			String fieldData = entityFields.get(i);
			String[] s = fieldData.split("-");
			
			entityFields.set( i, s[1] );
		}
		formObject = Class.forName("forms."+entityName+"Form");
		aux = formObject.getDeclaredFields();
		for( int i=0; i<aux.length; i++ ){
			Field field = aux[i];
			
			formObjectFields.add( field.getName() );
		}
		
		entityFields.removeAll( formObjectFields );
		if( !entityFields.isEmpty() )
			res = false;
		else
			res = true;
		
		return res;
	}
	
	/**
	 * Give to the entity service all necessary methods to manage entity form object.
	 * */
	public void enhanceService(){
		List<String> entityFields;
		String generatorPattern = 	"\tpublic "+this.entityName+"Form getFormObject( "+this.entityName+" arg ){\n"+
									"\t\t"+this.entityName+"Form res;\n\n"+
									"\t\tres = new "+this.entityName+"Form();\n"+
									"<method>\n"+
									"\t\treturn res;\n\t}";
		String reconstructPattern = "\tpublic "+this.entityName+" reconstruct( "+this.entityName+"Form arg ){\n"+
									"\t\t"+this.entityName+" res;\n\n"+
									"\t\tif( arg.getId() != 0 )\n"+
									"\t\t\tres = "+this.entityName.toLowerCase()+"Repository.findOne(arg.getId());\n"+
									"\t\telse\n"+
									"\t\t\tres = create();\n\n"+
									"<method>\n"+
									"\n\t\treturn res;\n\t}";
		String methodPatter = "\t\tres.set<field>(arg.get<field>());\n";
		String codeToAdd = "", srcCode;
		FileManager fileMngr;
		
		entityFields = findEntityFields();
		fileMngr = new FileManager("src\\main\\java\\services\\"+capitalize( this.entityName )+"Service.java");
		
		//Generate new code ------------------------------------------------------------------------------------
		codeToAdd += generatorPattern;
		for( String fieldData : entityFields ){
			String fieldName;
			String newMethod = methodPatter;
			fieldName = fieldData.split("-")[1];
			
			newMethod = newMethod.replaceAll( "<field>", capitalize(fieldName) );
			
			codeToAdd = codeToAdd.replace( "<method>", newMethod+"<method>" );
		}
		codeToAdd = codeToAdd.replace( "<method>" , "" );
		
		codeToAdd += "\n\n"+reconstructPattern;
		for( String fieldData : entityFields ){
			String fieldName;
			String newMethod = methodPatter;
			fieldName = fieldData.split("-")[1];
			newMethod = newMethod.replaceAll( "<field>", capitalize(fieldName) );
			
			codeToAdd = codeToAdd.replace( "<method>", newMethod+"<method>" );
		}
		codeToAdd = codeToAdd.replace( "<method>" , "" );
		
		//Write new code ---------------------------------------------------------------------------------------
		srcCode = fileMngr.readFile()+"-";
		srcCode = srcCode.replaceAll("}\\n-", "\t//Form object related methods -----------------------------------------------------------\n"+
					codeToAdd+"\n}");
		srcCode = srcCode.replaceFirst("import","import forms."+this.entityName+"Form;\nimport");
		
		fileMngr.writeFile( srcCode );
		System.out.println(this.entityName+"Service fully enhanced");
	}
	
	// Ancillaty methods ---------------------------------------------------------------------------
	private void generateFormObjectSrcCode( List<String> fields ){
		FileManager fileMngr;
		String getPattern = "\tpublic $type$ get$methodName$(){"+
				"\n\t return this.$fieldName$;"+
			"\n\t}\n";
		String setPattern = "\tpublic void set$methodName$( $type$ $fieldName$ ){"+
				"\n\t this.$fieldName$ = $fieldName$;"+
			"\n\t}\n";
		String srcCode		 = "package forms;\n\n"+
								"//@TODO: add neccesary imports (try ctrl+left mayus+o shortcut!)\n"+
								"public class "+this.entityName+"Form {\n"+
								"\t// Attributes ---------------------------------------------------\n"+
								"$fields$\n"+
								"\t// Getters and setters ------------------------------------------\n"+
								"\t//@TODO: copy field annotations from entity source code\n"+
								"$methods$"+
								"}";
		
		for( String field : fields){
			String type, name, getter, setter;
			
			// Replace source code 'holes' with the corresponding data
			type = field.substring( 0, field.indexOf('-'));
			type = type.substring( type.lastIndexOf('.')+1 );
			name = field.substring( field.indexOf('-')+1 );
			getter = getPattern.replaceAll("\\$type\\$", type);
			getter = getter.replaceAll("\\$fieldName\\$", name );
			getter = getter.replaceAll("\\$methodName\\$", capitalize( name ));
			setter = setPattern.replaceAll("\\$type\\$", type);
			setter = setter.replaceAll("\\$fieldName\\$",  name );
			setter = setter.replaceAll("\\$methodName\\$", capitalize( name ));
			
			srcCode = srcCode.replace("$fields$", "\tprivate "+type+" "+name+";"+"\n$fields$");
			srcCode = srcCode.replace("$methods$", getter+setter+"\n$methods$");
		}
		
		srcCode = srcCode.replaceAll("\\$\\w*\\$", "");

		fileMngr = new FileManager("src\\main\\java\\forms\\"+this.entityName+"Form.java");
		fileMngr.writeFile( srcCode );
	}
	
	/**
	 * Put string first character to upper case
	 * */
	private String capitalize( String str ){
		String res = "";
		
		res = str.substring(0, 1);
		res = res.toUpperCase();
		res += str.substring(1);
		
		return res;
	}

	@SuppressWarnings("rawtypes")
	private List<String> findEntityFields(){
		Class entity;
		Field[] entityFields;
		List<String> res = new ArrayList<String>(); // Fields that doesn't implement entity relationships
		
		try {
			entity = Class.forName("domain."+this.entityName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Entity "+this.entityName+" could not be loaded (ClassNotFoundException)");
		}
		
		entityFields = entity.getDeclaredFields();
		for( int i=0; i<entityFields.length; i++ ){
			Field field = entityFields[i];
			String fieldData = "";
			
			if( !field.getType().equals( java.util.Collection.class ) ){
				if( field.getType().toString().equals("class [B") )	//field.getType for byte[] type returns a weird String, maybe a bug?
					fieldData += "byte[]-"+field.getName();
				else
					fieldData += field.getType()+"-"+field.getName();
				
				res.add( fieldData );
			}
		}
		
		res.add("int-id");
		res.add("int-version");
		
		return res;
	}
}
