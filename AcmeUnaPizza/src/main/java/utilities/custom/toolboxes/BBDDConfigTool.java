package utilities.custom.toolboxes;

import utilities.internal.FileManager;

public class BBDDConfigTool implements Tool{

	private String command, parameter;
	
	public BBDDConfigTool(String[] parameters){
		if(checkParameters(parameters)){
			this.command = parameters[0];
			this.parameter = parameters[1];
		}
		else{
			System.out.println("Unknown command! Usage:");
			printHelp();
		}
	}
	
	public BBDDConfigTool(){
		throw new IllegalArgumentException("You must use String[] params constructor");
	}
	
	@Override
	public void execute(String[] parameters) {
		if(checkParameters(parameters)){
			if(command.equals("-create")) createDatabase(parameter);
			else	configurate(parameter);
		}
	}

	@Override
	public boolean checkParameters(String[] parameters) {
		Boolean res = false;
		
		if(parameters.length==2){
			if( parameters[0].equals("-create") ) res = true;
			else if( parameters[0].equals("-config") || parameters[0].equals("-configurate") )
				res = true;
		}
		
		return res;
	}

	@Override
	public void printHelp() {
		System.out.println(	"HELP!\n\t"+
				"-help						- 	Prints this help\n\t"+
				"-create <databaseName>		-	Creates a new database\n\t"+
				"-config <databaseName>		-	Configurates a database\n\t" +
				"-configurate <databaseName>-	same as -config");
	}

	// Business logic methods -----------------------------
	private void createDatabase(String dbName){
		throw new UnsupportedOperationException("not supported yet");
	}
	
	private void configurate(String name){
		FileManager fileMngr;
		String filePath, fileContent;
		
		filePath = "src\\main\\resources\\META-INF\\persistence.xml";
		fileMngr = new FileManager(filePath);
		fileContent = fileMngr.readFile();
		fileContent = fileContent.replaceAll("%databaseName%", name);
		fileMngr.writeFile(fileContent);
		
		filePath = "src\\main\\resources\\spring\\config\\data.xml";
		fileMngr.setFilePath(filePath);
		fileContent = fileMngr.readFile();
		fileContent = fileContent.replaceAll("%databaseName%", name);
		fileMngr.writeFile(fileContent);

		filePath = "src\\main\\java\\utilities\\DatabaseConfig.java";
		fileMngr.setFilePath(filePath);
		fileContent = fileMngr.readFile();
		fileContent = fileContent.replaceAll("%databaseName%", name);
		fileMngr.writeFile(fileContent);
	}
}
