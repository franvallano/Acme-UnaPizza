package utilities.custom.toolboxes;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import utilities.internal.FileManager;

public class RepositoriesTool implements Tool{

	@Override
	public void execute(String[] parameters) {
		if(checkParameters(parameters)){
			if(parameters[0].equals("-generate")){
				if(parameters.length == 2) generate(parameters[1]);
				else generate();
			}
			else{
				if(parameters.length == 2) validate(parameters[1]);
				else validate();
			}
		}
		else printHelp();
	}

	@Override
	public boolean checkParameters(String[] parameters) {
		Boolean res = true;
		
		if(parameters.length > 2) res = false;
		else if(!parameters[0].equals("-generate") && !parameters[0].equals("-validate"))
			res = false;
		
		return res;
	}

	@Override
	public void printHelp() {
		System.out.println(	"HELP!\n\t"+
				"-help						- 	Prints this help\n\t"+
				"-generate [entityName]		-	Generate a repository for all entities or specified entity\n\t"+
				"-validate [repositoryName]	-	Validate the specified repository or all repositories if no one is specified");
	}

	// Business logic ---------------------------------------------------
	private void generate(String entityName){
		String templatesPath = "src\\main\\resources\\code_templates";
		FileManager fileManager = new FileManager(templatesPath+"\\interface.txt");
		String template;
		String interfacesPath = "src\\main\\java\\repositories";
		String code = "";
		
		template = fileManager.readFile();
		fileManager.setFilePath(interfacesPath+"\\"+entityName+"Repository.java");
		code = template.replace("%entity%", entityName);
		fileManager.writeFile(code);
	}
	
	private void generate(){
		Set<String> aux = new HashSet<String>();
		Set<String> entities = new HashSet<String>();
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
		entities.addAll(aux);
		for (String s : aux){
			FileManager fileManager = new FileManager(directory.getAbsolutePath());
			@SuppressWarnings("static-access")
			String classPath = fileManager.getFilePath()+fileManager.getPathSeparator()+s+".java";
			fileManager.setFilePath(classPath);
			
			String fileContent = fileManager.readFile();
			if (! fileContent.contains("@Entity"))
				entities.remove(s);
		}
		
		for(String entityName : entities){
			generate(entityName);
		}
	}
	
	private void validate(String repositoryName){
		List<String> res;
		RepositoriesToolbox validator = new RepositoriesToolbox();
		
		validator.setRepositoryName( repositoryName );
		
		System.out.println("VALIDATING "+repositoryName+" -----------------------------------");
		
		res = validator.performCheckouts();
		
		for( String msg:res ){
			if(msg.charAt(0)=='-')
				System.out.println(msg);
			else
				System.out.println(msg);
		}
	}
	
	private void validate(){
		List<String> repositoriesNames = new ArrayList<String>();
		String repositoriesPath = "src\\main\\java\\repositories";
		File reposDir = new File(repositoriesPath);
		
		for (File f : reposDir.listFiles()){
			String name = f.getName();
			
			name = name.substring(0, name.indexOf("."));
			repositoriesNames.add(name);
		}
		
		for(String repoName : repositoriesNames)
			validate(repoName);
	}
	
}
