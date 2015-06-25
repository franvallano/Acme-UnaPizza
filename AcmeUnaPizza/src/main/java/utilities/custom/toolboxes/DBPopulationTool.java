package utilities.custom.toolboxes;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import utilities.internal.FileManager;

public class DBPopulationTool implements Tool{

	// Fields ---------------------------------------------------------
	private static final String entitiesPath = "src/main/java/domain/";
	private static final String entitiesPkg = "domain";
	private static final String populateDatabasePath = "src/main/resources/PopulateDatabase.xml";
	private static Map<String, Integer> beanCounter = new HashMap<String, Integer>(); //Used for counting number of beans of each entity that has been created
	
	// Tool methods ---------------------------------------------------
	@Override
	public void execute(String[] parameters) {
		String command;
		
		if(checkParameters(parameters)){
			command = parameters[0];
			
			if(command.equals("-populate")){
				if(parameters.length == 1) populate(2);
				else populate(new Integer(parameters[1]));
			}
			else{
				if(parameters.length == 2) create(parameters[1], 2);
				else create(parameters[1], new Integer(parameters[2]));
			}
		}
		else printHelp();
	}

	@Override
	public boolean checkParameters(String[] parameters) {
		Boolean res = true;
		
		if(parameters.length == 1) res = false;
		else if(parameters[0].equals("-populate")){
			if(parameters.length>2) res = false;
		}
		else if(parameters[0].equals("-create")){
			if(parameters.length>3) res = false;
		}
		else res = false;
		
		return res;
	}

	@Override
	public void printHelp() {
		System.out.println(	"HELP!\n\t"+
				"-populate [number]            - 	Creates beans for every entity, the number of beans per entity is specified as parameter (by default it is 2)\n\t"+
				"-create <entityName> [number] -	Creates beans for the given entity, the number of beans is specified as parameter (by default it is 2)");
	}

	// Business logic methods ----------------------------------------
	/**
	 * Generate beans for all existing entities
	 * 
	 * @param num - number of beans to create per entity
	 * */
	private void populate(int num){ //TODO: deberia comprobar las entidades que son heredadas por otras, para no crear supertipos innecesarios
		File entitiesDir = new File(entitiesPath);
		String[] entities = entitiesDir.list();
		String entity;
		
		for(int i=0; i<entities.length; i++){
			entity = entities[i];
			entity = entity.substring(0, entity.indexOf(".")); //removes .java file extension
			
			if(!entity.contains("DomainEntity")){
				create(entity, num);
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	/**
	 * Creates new beans into populateDatabase.xml file
	 * 
	 * @param entityName - name of the entity whose beans are going to be created
	 * @param num - number of beans to be created
	 * */
	private void create(String entityName, int num){
		Class entityClass;
		String bean;
		
		try{
			entityClass = Class.forName(entitiesPkg+"."+entityName);
			
			for(int i=0; i<num; i++){
				bean = createBean(entityClass);
				writeBean(bean);
			}
		}
		catch(ClassNotFoundException oops){
			System.out.println("ERROR: Entity '"+entityName+"' could not be found into entities package");
		}
	}
	
	// Ancillary methods ---------------------------------------------
	@SuppressWarnings("rawtypes")
	private String createBean(Class entityClass){
		String res;
		String classCanonicalName = entityClass.getCanonicalName();
		String className = classCanonicalName.substring(classCanonicalName.lastIndexOf(".")+1);
		Integer counter = 1;
		Field[] entityFields;
		Field field;
		Class entitySuperClass;
		
		if(beanCounter.containsKey(classCanonicalName)){
			counter = beanCounter.get(classCanonicalName)+1;
			beanCounter.put(classCanonicalName, counter);
		}
		else beanCounter.put(classCanonicalName, counter);
		
		res = "\t<bean id=\""+className+counter+"\" class=\""+classCanonicalName+"\">";
		
		// Loop over entity fields and create attributes and references tags
		entityFields = entityClass.getDeclaredFields();
		for(int i=0; i<entityFields.length; i++){
			field = entityFields[i];
			
			res += generateBeanAttribute(field);
		}
		
		// Loop over entity superclass fields and create attributes and references tags
		entitySuperClass = entityClass.getSuperclass();
		
		if(! entitySuperClass.getCanonicalName().contains("DomainEntity")){
			entityFields = entitySuperClass.getDeclaredFields();
			
			for(int i=0; i<entityFields.length; i++){
				field = entityFields[i];
				
				res += generateBeanAttribute(field);
			}
		}
		
		res += "\t\n</bean>";
		
		return res;
	}
	
	/**
	 * Writes a bean into populateDatabase.xml
	 * 
	 * @param bean - code of the bean to be written
	 * */
	private void writeBean(String bean){
		FileManager fileMngr = new FileManager(populateDatabasePath);
		String content;
		
		content = fileMngr.readFile();
		
		content = content.substring(0, content.lastIndexOf("</")); //Removes </beans> tag (document closing tag)
		content += bean;
		content += "\n</beans>";
		
		fileMngr.writeFile(content);
	}
	
	/**
	 * Checks if a datatype is an entity. Used into bean creation to create reference instead of attribute tags.
	 * 
	 * @param typeName - canonical name of the type to check
	 * */
	private Boolean isEntity(String typeCanonicalName){
		Boolean res;
		
		if(typeCanonicalName.contains(entitiesPkg) || typeCanonicalName.contains("security")){
			try{
				Class.forName(typeCanonicalName);
				
				res = true;
			}
			catch(ClassNotFoundException oops){
				res = false;
			}
		}
		else res = false; 
		
		return res;
	}
	
	private String generateBeanAttribute(Field field){
		String res = "";
		
		if(isEntity(field.getType().getCanonicalName())) res+="\n\t\t<property name=\""+field.getName()+"\" ref=\" \"/>";
		else{
			if(field.getType().isAssignableFrom(java.util.Collection.class)){
				res += "\n\t\t<property name=\""+field.getName()+"\">";
				res += "\n\t\t\t<list>";
				res += "\n\t\t\t<!-- TODO: FILL ME! -->";
				res += "\n\t\t\t</list>";
				res += "\t\t\n</property>";
			}
			
			else res += "\n\t\t<property name=\""+field.getName()+"\" value=\" \"/>";
		}
		
		return res;
	}
	
}
