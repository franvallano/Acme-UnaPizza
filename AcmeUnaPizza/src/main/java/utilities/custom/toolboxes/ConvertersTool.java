package utilities.custom.toolboxes;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import utilities.internal.FileManager;

public class ConvertersTool implements Tool{

	@Override
	public void execute(String[] parameters) {
		if(checkParameters(parameters)){
			if(parameters.length == 1) generateConverters();
			else generateConverter(parameters[1]);
		}
		else printHelp();
	}

	@Override
	public boolean checkParameters(String[] parameters) {
		Boolean res = true;
		
		if(parameters.length == 0) res = false;
		else if(parameters.length > 2) res = false;
		else if(!parameters[0].equals("-create")) res = false;
		
		return res;
	}

	@Override
	public void printHelp() {
		System.out.println(	"HELP!\n\t"+
				"-help						- 	Prints this help\n\t"+
				"-create 					-	Creates converters for existing entities\n\t"+
				"-create <entityName>		-	Creates converter for an entity");
	}
	
	// Business logic
	private void generateConverter(String entity){
		String templatesPath = "src\\main\\resources\\code_templates";
		FileManager fileManager = new FileManager(templatesPath+"\\toStringConverter.txt");
		String template;
		String convertersPath = "src\\main\\java\\converters";
		
		template = fileManager.readFile();
		
		fileManager.setFilePath(convertersPath+"\\"+entity+"ToStringConverter.java");
		
		String code = template;
		
		code = code.replace("%entity%", entity);
		code = code.replace("%entity_lowcase%", entity.toLowerCase()); // Used into service variable name
		
		fileManager.writeFile(code);
		
		fileManager.setFilePath(templatesPath+"\\toEntityConverter.txt");
		
		template = fileManager.readFile();
		
		fileManager.setFilePath(convertersPath+"\\StringTo"+entity+"Converter.java");
		
		code = template;
		
		code = code.replace("%entity%", entity);
		code = code.replace("%entity_lowcase%", entity.toLowerCase()); // Used into service variable name

		fileManager.writeFile(code);
		
		configurateConverter(entity);
	}
	
	private void generateConverters(){
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
		
		for(String entity : res)	generateConverter(entity);
	}
	
	/**
	 * Adds converter definition line in converters.xml configuration file
	 * */
	private void configurateConverter(String entity){
		String configurationFilePath = "src\\main\\resources\\spring\\config\\converters.xml";
		FileManager fileManager = new FileManager(configurationFilePath);
		String configuration;
		
		configuration = fileManager.readFile();
		
		// remove beans definition closing tag
		configuration = configuration.substring(0, configuration.indexOf("</util:list>"));
		
		// add converters beans
		configuration += "		<bean class=\"converters.StringTo"+entity+"Converter\" />\n";
		configuration += "		<bean class=\"converters."+entity+"ToStringConverter\" />\n";
		
		// add beans definition closing tags
		configuration += "	\n</util:list>\n</beans>";
		
		fileManager.writeFile(configuration);
	}
}
