package utilities.custom.validators;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ServicesValidator {

	// Attributes ------------------------------------------------------------------------
	private String serviceName;
	
	// Getters and setters ---------------------------------------------------------------
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
	
	// Constructor -----------------------------------------------------------------------
	public ServicesValidator(String serviceName){
		super();
		
		setServiceName(serviceName);
	}
	
	// Checkouts methods -----------------------------------------------------------------
	@SuppressWarnings("rawtypes")
	public List<String> performCheckouts(){
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
		
		serviceFields = service.getDeclaredFields();
		for( int i=0; i<serviceFields.length; i++){
			Field field = serviceFields[i];
			
			if( field.getAnnotation( org.springframework.beans.factory.annotation.Autowired.class ) == null )
				res.add("- AUTOWIRING ERROR: field "+field.getName()+" hasn't @Autowired annotation");
		}
		
		return res;
	}
	
}
