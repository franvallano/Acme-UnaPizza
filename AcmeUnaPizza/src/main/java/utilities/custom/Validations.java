package utilities.custom;

import java.util.ArrayList;
import java.util.List;

import utilities.custom.toolboxes.RepositoriesToolbox;
import utilities.custom.validators.DomainModelValidator;
import utilities.custom.validators.ServicesValidator;

public class Validations {

	public static void main(String[] args){
		System.out.println("--- MODELS");
//		models();
		System.out.println("\n\n--- REPOSITORIES");
		
		repositories();
		System.out.println("\n\n--- SERVICES");
		
		services();
	}
	
	public static void models(){
		String[] classNames = {"Actor", "Administrator", "Comment", "CreditCard", "Customer", "Item", "Order", "Portioncashe", "ShoppingLine", "SoldItem"};
		
		for(int i=0; i<classNames.length; i++){
			String className = classNames[i];
			List<String> res;
			DomainModelValidator validator;

			System.out.println("VALIDATING "+className+" -----------------------------------");
			
			validator = new DomainModelValidator(className);
			
			res = validator.performCheckouts();
			
			for( String msg : res ){
				if(msg.charAt(0)=='-')
					System.err.println(msg);
				else
					System.out.println(msg);
			}
		}
	}
	
	public static void repositories(){
		String[] repositoryNames = {"ActorRepository", "AdministratorRepository", "CustomerRepository", "QuittanceRepository"};
		List<String> res;
		RepositoriesToolbox validator = new RepositoriesToolbox();
		
		for( int i=0; i<repositoryNames.length; i++ ){
			String repo = repositoryNames[i];
			validator.setRepositoryName( repo );
			
			System.out.println("VALIDATING "+repo+" -----------------------------------");
			
			res = validator.performCheckouts();
			
			for( String msg:res ){
				if(msg.charAt(0)=='-')
					System.err.println(msg);
				else
					System.out.println(msg);
			}
		}
	}
	
	public static void services(){
		String[] serviceNames = {"CustomerService", "QuittanceService"};
		List<String> res;
		
		for( int i=0; i<serviceNames.length; i++){
			String service = serviceNames[i];
			ServicesValidator validator = new ServicesValidator(service);
			
			System.out.println("VALIDATING "+service+" -----------------------------------");
			
			res = validator.performCheckouts();
			
			for( String msg:res ){
				if(msg.charAt(0)=='-')
					System.err.println(msg);
				else
					System.out.println(msg);
			}
			
			res = new ArrayList<String>();
		}
	}
}
