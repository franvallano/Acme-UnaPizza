package utilities.custom.toolboxes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import utilities.internal.FileManager;

public class ServicesTool implements Tool{

	@Override
	public void execute(String[] parameters) {
		String command;
		FormObjectToolbox formObjectToolbox;
	
		if(checkParameters(parameters)){
			command = parameters[0];
			
			if(command.equals("-generate")){
				if(parameters.length == 2) generate(parameters[1]);
				else generate();
			}
			else if(command.equals("-validate")){
				if(parameters.length == 2) validate(parameters[1]);
				else validate();
			}
			else if(command.equals("-cleanup"))
				cleanup();
			else{
				try {
					formObjectToolbox = new FormObjectToolbox(parameters[1]);
					
					if(command.equals("-enhance")){
						formObjectToolbox.generate();
						formObjectToolbox.enhanceService();
					}
					else{
						System.out.print(parameters[1]+" form object is ");
						if(formObjectToolbox.validate()) 	System.out.println("OK");
						else								System.out.println("INVALID (some parameters are lost)");
					}
				} catch (Exception e) {
					System.out.println("ERROR: There was an error working with the form object of "+parameters[1]);
				}
			}
		}
		else printHelp();
	}

	@Override
	public boolean checkParameters(String[] parameters) {
		Boolean res = true;
		
		if(parameters.length > 2) res = false;
		else if((parameters[0].equals("-enhance") || parameters[0].equals("-validateform")) && parameters.length<2)
			res = false;
		else if(!parameters[0].equals("-generate") &&
				!parameters[0].equals("-validate") &&
				!parameters[0].equals("-cleanup") &&
				!parameters[0].equals("-enhance") &&
				!parameters[0].equals("-validateform"))
			res = false;
		
		return res;
	}

	@Override
	public void printHelp() {
		System.out.println(	"HELP!\n\t"+
				"-help						- 	Prints this help\n\t"+
				"-cleanup                   -   Removes all unused services\n\t"+
				"-generate [entityName]		-	Generate a service for all entities or specified entity\n\t"+
				"-validate [serviceName]	-	Validate the specified service or all services if no one is specified\n\t"+
				"-enhance <serviceName>     -   Enhance a service with form object capability\n\t"+
				"-validateform <serviceName>-   Validate the form object of a service");
	}

	// Business logic ---------------------------------------
	private void generate(String entityName){
		String templatesPath = "src\\main\\resources\\code_templates";
		FileManager fileManager = new FileManager(templatesPath+"\\service.txt");
		String template;
		String servicesPath = "src\\main\\java\\services";
		String code = "";
		
		template = fileManager.readFile();
		fileManager.setFilePath(servicesPath+"\\"+entityName+"Service.java");
		code = template.replace("%entity%", entityName);
		code = code.replace("%entity_lowcase%", entityName.toLowerCase());
		fileManager.writeFile(code);
	}
	
	private void generate(){
		List<String> servicesNames = new ArrayList<String>();
		File repositoriesDir = new File("src\\main\\java\\repositories");
		
		for (File f : repositoriesDir.listFiles()){
			String name = f.getName();
			
			name = name.substring(0, name.indexOf("."));
			servicesNames.add(name);
		}
		
		for(String serviceName : servicesNames){
			generate(serviceName.substring(0, serviceName.length()-10));
		}
	}
	
	private void validate(String serviceName){
		List<String> res;
		ServicesToolbox validator = new ServicesToolbox();
		
		validator.setServiceName( serviceName );
		
		System.out.println("VALIDATING "+serviceName+" -----------------------------------");
		
		res = validator.performCheckouts();
		
		for( String msg:res ){
			if(msg.charAt(0)=='-')
				System.out.println(msg);
			else
				System.out.println(msg);
		}
	}
	
	private void validate(){
		List<String> servicesNames = new ArrayList<String>();
		String servicesPath = "src\\main\\java\\services";
		File servicesDir = new File(servicesPath);
		
		for (File f : servicesDir.listFiles()){
			String name = f.getName();
			
			name = name.substring(0, name.indexOf("."));
			servicesNames.add(name);
		}
		
		for(String serviceName : servicesNames){
			validate(serviceName);
		}
	}
	
	@SuppressWarnings("rawtypes")
	private void cleanup(){
		List<String> serviceNames = new ArrayList<String>();
		File servicesPackage = new File("src\\main\\java\\services");
		
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
	
	@SuppressWarnings("rawtypes")
	private void removeService( Class service ){
		String packagePath, serviceName;
		File serviceFile;
		File servicesPackage = new File("src\\main\\java\\services");
		
		packagePath = servicesPackage.getAbsolutePath();
		serviceName = service.getName();
		serviceName = serviceName.substring( serviceName.lastIndexOf( '.' )+1 );
		serviceFile = new File(packagePath+"//"+serviceName+".java");
		
		serviceFile.delete();
	}
}
