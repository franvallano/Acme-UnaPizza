package utilities.custom.toolboxes;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import utilities.internal.FileManager;

public class ControllersTool implements Tool{

	@Override
	public void execute(String[] parameters) {
		if(checkParameters(parameters)){
			if(parameters[0].equals("-generate"))
				if(parameters.length < 2) generateAll(); 
				else generate(parameters[1]);
			else if(parameters[0].equals("-add"))
				add(parameters[1], parameters[2]);
		}
		else printHelp();
	}

	@Override
	public boolean checkParameters(String[] parameters) {
		Boolean res = true;
		String param;
		
		if(parameters.length>0){
			param = parameters[0];
		
			if(param.equals("-generate")){
				if(parameters.length > 2) res = false;
			}
			else if(param.equals("-add")){
				if(parameters.length != 3) res = false;
			}
			else{
				res = false;
			}
		}
		else res = false;
		
			
		return res;
	}

	@Override
	public void printHelp() {
		System.out.println(	"HELP!\n\t"+
				"-help                          - Prints this help\n\t"+
				"-generate <RequestMapping>     - Generate a new controller\n\t"+
				"-generate                      - Generate a controller for every entity\n\t"+
				"-add <ControllerName> <method> - Add a method to the specified controller");
	}

	// Business logic ------------------------------------------------------------------------------
	private void generateAll(){
		String templatesPath = "src\\main\\resources\\code_templates";
		FileManager fileManager = new FileManager(templatesPath+"\\controller.txt");
		String template;
		String controllersPath = "src\\main\\java\\controllers";
		Set<String> entitiesNames;
		
		entitiesNames = findEntities();
		template = fileManager.readFile();
		
		for(String entity : entitiesNames){
			fileManager.setFilePath(controllersPath+"\\"+entity+"Controller.java");
			
			String code = template;
			
			code = code.replace("%entity%", entity);
			code = code.replace("%entity_lowcase%", entity.toLowerCase()); // Used into service variable name
			code = code.replace("%request_mapping%", entity.toLowerCase());
			
			fileManager.writeFile(code);
		}
	}
	
	private void generate(String requestMapping){ // entity/authority
		String entityLowcase, entity, template, name;
		String[] aux;
		String controllerPath = "src\\main\\java\\controllers\\";
		FileManager fileMngr = new FileManager("src\\main\\resources\\code_templates\\controller.txt");
		int entityPos = 0; // position of the entity being used by the controller
		
		// Load necessary data
		if(requestMapping.charAt(0)=='/') requestMapping = requestMapping.substring(1);
		aux = requestMapping.split("/");
		if(aux.length == 2) entityPos = 1;
		entityLowcase = aux[entityPos];
		entity = capitalize( entityLowcase );
		if(aux.length == 2) name = entity+capitalize( aux[entityPos-1] );
		else name = entity;
		controllerPath += name+"Controller.java";
		template = fileMngr.readFile();
		
		// Replace template variables
		template = template.replace("%entity%", entity);
		template = template.replace("%entity_lowcase%", entityLowcase);
		template = template.replace("%request_mapping%", requestMapping);
		template = template.replace("%name%", name);
		
		// Create and write file
		fileMngr.setFilePath(controllerPath);
		fileMngr.writeFile(template);
	}
	
	private void add(String controllerName, String method){
		String controllerCode, methodCode;
		String methodTemplatePath = "src\\main\\resources\\code_templates\\controller_methods\\"+method+".txt";
		String controllerPath = "src\\main\\java\\controllers\\"+controllerName+".java";
		String entity = controllerName.substring(0, controllerName.length()-10);
		FileManager fileMngr;
		
		if(checkMethodName(method)){
			// Load necessary data
			fileMngr = new FileManager(methodTemplatePath);
			methodCode = fileMngr.readFile();
			fileMngr.setFilePath(controllerPath);
			controllerCode = fileMngr.readFile();
			
			// Replace method code template variables
			methodCode = methodCode.replaceAll("%entity%", entity);
			methodCode = methodCode.replaceAll("%entity_lowcase%", entity.toLowerCase());
			
			// Add method to controller code
			controllerCode = controllerCode.substring(0, controllerCode.lastIndexOf('}')-1);
			controllerCode += methodCode+"\n}";
			
			// Write code to file
			fileMngr.writeFile(controllerCode);
		}
	}
	
	// Ancillary methods ---------------------------------------------------------------------------
	private String capitalize(String str){
		String res;
		
		res = str.substring(0, 1);
		res = res.toUpperCase();
		res += str.substring(1);
		
		return res;
	}
	
	public Set<String> findEntities(){
		Set<String> aux = new HashSet<String>();
		Set<String> res = new HashSet<String>();
		String entitiesPath = "src\\main\\java\\domain";
		File directory = new File(entitiesPath);
		
		// Find classes into domain directory
		for (File f : directory.listFiles()){
			String name = f.getName();
			
			// Filter files that aren't java classes
			if (name.contains(".java") && !name.contains("DomainEntity")){
				name = name.substring(0, name.indexOf("."));
				aux.add(name);
			}
		}
		
		// Filter java classes that aren't entities (datatypes)
		res.addAll(aux);
		for (String s : aux){
			FileManager fileManager = new FileManager(directory.getAbsolutePath());
			@SuppressWarnings("static-access")
			String classPath = fileManager.getFilePath()+fileManager.getPathSeparator()+s+".java";
			fileManager.setFilePath(classPath);
			
			String fileContent = fileManager.readFile();
			if (! fileContent.contains("@Entity"))
				res.remove(s);
		}
		
		return res;
	}
	
	/**
	 * Checks if method is available for adding it to a controller. If not, prints an error message.
	 * 
	 * @param methodName - name of the method to add.
	 * @return true if method is available, false otherwise.
	 * */
	private Boolean checkMethodName(String methodName){
		Boolean res;
		
		if(methodName.equals("create") || methodName.equals("delete") || methodName.equals("edit") ||
				methodName.equals("list") || methodName.equals("save")){
			res = true;
		}
		else{
			System.out.println("ERROR: method is not available, available methods are: create, delete, edit, list, save.");
			res = false;
		}
		
		return res;
	}
	
}
