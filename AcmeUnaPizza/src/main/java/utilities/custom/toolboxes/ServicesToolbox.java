package utilities.custom.toolboxes;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import utilities.internal.FileManager;

public class ServicesToolbox {
	
	// Attributes ---------------------------------------------------------------
	private File servicesPackage;
	private String serviceName;
	
	// Constructor --------------------------------------------------------------
	public ServicesToolbox(){
		servicesPackage = new File("src\\main\\java\\services");
		
		if( !servicesPackage.exists() )
			throw new IllegalArgumentException( "package with the specified name doesn't exists" );
		else if ( !servicesPackage.isDirectory() )
			throw new IllegalArgumentException( "specified path isn't a directory, so neither a package");			
	}
	
	// Getters and setters --------------------------------------------------------
	public String getServiceName(){
		return this.serviceName;
	}
	
	public void setServiceName(String serviceName){
		if( serviceName == null )
			throw new IllegalArgumentException( "service name must not be null" );
		else if( serviceName.length()==0 )
			throw new IllegalArgumentException( "service name must not be empty" );
		
		this.serviceName = serviceName;
	}
	
	// Functionality methods ----------------------------------------------------
	@SuppressWarnings("rawtypes")
	public void cleanup(){
		List<String> serviceNames = new ArrayList<String>();
		
		for( File f : servicesPackage.listFiles() ){
			String fileName = f.getName();
			
			serviceNames.add( fileName.substring( 0, fileName.indexOf( '.' )));
		}
		
		for( String serviceName : serviceNames ){
			Class service;
			int attributeNum, methodNum;
			
			try {
				service = Class.forName( "services."+serviceName );
			} catch (ClassNotFoundException e) {
				throw new RuntimeException( "Tried to load service "+serviceName+" but it wasn't found" );
			}
			
			attributeNum = service.getDeclaredFields().length;
			if( attributeNum == 0 )
					removeService( service );
			else{
				methodNum = service.getMethods().length;
				if( methodNum == 0 )
					removeService( service );
			}
		}
	}

	//TODO: Reemplazar los fallos silenciosos cuando no se encuentra la clase o la clase es un datatype por excepciones 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void generateService(String entityName){
		try{
			Class entityClass = Class.forName("domain."+entityName);
			if( entityClass.getAnnotation(javax.persistence.Entity.class) == null )
				return;
		}
		catch( ClassNotFoundException oops ){
			return;
		}
		
		String templatesPath = "src\\main\\resources\\code_templates";
		FileManager fileManager = new FileManager(templatesPath+"\\service.txt");
		String template;
		String servicesPath = "src\\main\\java\\services";
		
		template = fileManager.readFile();
		
		fileManager.setFilePath(servicesPath+"\\"+entityName+"Service.java");
		String code = template;
		
		code = code.replace("%entity%", entityName);
		code = code.replace("%entity_lowcase%", entityName.toLowerCase()); // Used into service variable name
		
		fileManager.writeFile(code);
		
		if( !fileManager.getFile().exists() )
			throw new RuntimeException("El fichero del servicio no se ha generado");
	}
	
	// Checkouts methods -----------------------------------------------------------------
	/**
	 * Checkouts a service.
	 * 
	 * @return a list of all errors found.
	 * */
	@SuppressWarnings("rawtypes")
	public List<String> performCheckouts(){
		if( this.serviceName == null )
			throw new RuntimeException("El nombre del servicio a comprobar no puede ser null, utilice setServiceName()");
		
		List<String> res = new ArrayList<String>();
		Class service;
		
		try{
			service = Class.forName("services."+this.serviceName);
			
		} catch ( ClassNotFoundException e ){
			res.add("ERROR: no repository found with name "+this.serviceName);
			return res;
		}
		
		res.addAll(checkServiceAnnotations(service));
		res.addAll(checkAutowiredFields(service));
		
		return res;
	}

	// Ancillary methods ---------------------------------------------------------
	@SuppressWarnings("rawtypes")
	private List<String> checkServiceAnnotations(Class service){
		List<String> res = new ArrayList<String>();
		Annotation[] annotations;
		Boolean transactionalAnnotation = false, serviceAnnotation = false;
		
		annotations = service.getAnnotations();
		for( int i=0; i<annotations.length; i++ ){
			Annotation annotation = annotations[i];
			
			if( annotation instanceof org.springframework.transaction.annotation.Transactional )
				transactionalAnnotation = true;
			else if( annotation instanceof org.springframework.stereotype.Service )
				serviceAnnotation = true;
		}
		
		if( !transactionalAnnotation ){
			String msg; 
			
			msg = "- ANNOTATION ERROR: service "+service.getCanonicalName()+" hasn't @Transactional annotation";
			if( !serviceAnnotation )
				msg += " nor @Service annotation";
			
			res.add(msg);
		}
		else if( !serviceAnnotation )
			res.add( "- ANNOTATION ERROR: service "+service.getCanonicalName()+" hasn't @Service annotation" );
		
		return res;
	}
	
	@SuppressWarnings("rawtypes")
	private List<String> checkAutowiredFields(Class service){
		List<String> res = new ArrayList<String>();
		Field[] serviceFields;
		
		try{
			serviceFields = service.getDeclaredFields();
			for( int i=0; i<serviceFields.length; i++){
				Field field = serviceFields[i];
				
				if( field.getAnnotation( org.springframework.beans.factory.annotation.Autowired.class ) == null )
					res.add("- AUTOWIRING ERROR: field "+field.getName()+" hasn't @Autowired annotation");
			}
		}
		catch(NoClassDefFoundError oops){
			res.add("- FIELD ERROR: "+service.getName()+" has fields of non-existent types (check service fields for errors)");
		}
		
		return res;
	}
	
	@SuppressWarnings("rawtypes")
	private void removeService( Class service ){
		String packagePath, serviceName;
		File serviceFile;
		
		packagePath = this.servicesPackage.getAbsolutePath();
		serviceName = service.getName();
		serviceName = serviceName.substring( serviceName.lastIndexOf( '.' )+1 );
		serviceFile = new File(packagePath+"//"+serviceName+".java");
		
		serviceFile.delete();
	}
}
