package utilities.custom.toolboxes;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

import utilities.internal.FileManager;

public class ViewsTool implements Tool{

	// Fields
	private static final String viewsFoldersPath = "src/main/webapp/views";
	private static final String viewsTemplatesPath = "src/main/resources/code_templates/views";
	
	// Tool interface functionality
	@Override
	public void execute(String[] parameters) {
		String command, folderName, viewType;
		
		if(checkParameters(parameters)){
			command = parameters[0];
			folderName = parameters[1];
			
			if(command.equals("-create")) create(folderName);
			else if(command.equals("-config")) configurate(folderName);
			else if(command.equals("-check")){
				if(parameters.length == 3) checkInternationalization(folderName, true);
				else checkInternationalization(folderName, false);
			}
			else{ //-add command
				viewType = parameters[2];
				
				addView(folderName, viewType);
			}
		}
		else
			printHelp();
	}

	@Override
	public boolean checkParameters(String[] parameters) {
		Boolean res = true;
		String command;
		
		if(parameters.length<2) res = false;
		else{
			command = parameters[0];
			if(!command.equals("-create") && !command.equals("-config") &&
					!command.equals("-check") && !command.equals("-add"))
				res = false;
			else if (command.equals("-add")){
				if(parameters.length != 3)	res = false;
				else if (!parameters[2].equals("list") && !parameters[2].equals("edit"))	res = false;
			}
		}
		
		return res;
	}

	@Override
	public void printHelp() {
		System.out.println(	"HELP!\n\t"+
				"-help					        - 	Prints this help\n\t"+
				"-create <folderName>	        -	Creates view folder an configures it\n\t"+
				"-config <folderName>	        -	Configurates a view folder\n\t"+
				"-check <folderName> [-i]       -   Checks i18n&l10n messages, in an interactive way if -i flag is used\n\t"+
				"-add <folderName> <list|edit>  -  Add a list or edition view");
	}

	// Business logic methods -----------------------
	/**
	 * Writes configuration for a given view folder
	 * @param name - name of the view folder (most times it is the entity name).
	 * */
	private void configurate(String name){
		if(viewFolderExists(name)){
			String tilesConfigFilePath = "src/main/resources/spring/config/tiles.xml";
			String messagesConfigFilePath = "src/main/resources/spring/config/i18n-l10n.xml";
			String fileContent, config, closingTags;
			FileManager fileMngr = new FileManager("");
			
			// Configure i18n&l10n
			fileMngr.setFilePath(messagesConfigFilePath);
			fileContent = fileMngr.readFile();
			config = "<value>/views/"+name+"/messages</value>";
			closingTags = fileContent.substring(fileContent.lastIndexOf("</value>")+8);
			fileContent = fileContent.substring(0, fileContent.lastIndexOf("</value>")+8);
			fileContent += "\n"+config;
			fileContent += closingTags;
			fileMngr.writeFile(fileContent);
			
			// Configure tiles
			fileMngr.setFilePath(tilesConfigFilePath);
			fileContent = fileMngr.readFile();
			config = "<value>/views/"+name+"/tiles.xml</value>";
			closingTags = fileContent.substring(fileContent.lastIndexOf("</value>")+8);
			fileContent = fileContent.substring(0, fileContent.lastIndexOf("</value>")+8);
			fileContent += "\n"+config;
			fileContent += closingTags;
			fileMngr.writeFile(fileContent);
		}
		else{
			System.out.println("ERROR: cannot configure folder '"+name+"'. It doesn't exists");
		}
	}

	private void create(String name){
		FileManager fileMngr = new FileManager(viewsFoldersPath+"/"+name);
		String content;
		
		fileMngr.getFile().mkdir();
		
		// Create tiles files
		fileMngr.setFilePath(viewsTemplatesPath+"/tiles.txt");
		content = fileMngr.readFile();
		fileMngr.setFilePath(viewsFoldersPath+"/"+name+"/tiles.xml");
		fileMngr.writeFile(content);
		fileMngr.setFilePath(viewsFoldersPath+"/"+name+"/tiles_es.xml");
		fileMngr.writeFile(content);
		
		// Copy i18n&l10n files
		fileMngr.setFilePath(viewsTemplatesPath+"/messages.txt");
		content = fileMngr.readFile();
		fileMngr.setFilePath(viewsFoldersPath+"/"+name+"/messages.properties");
		fileMngr.writeFile(content);
		fileMngr.setFilePath(viewsTemplatesPath+"/messages_es.txt");
		content = fileMngr.readFile();
		fileMngr.setFilePath(viewsFoldersPath+"/"+name+"/messages_es.properties");
		fileMngr.writeFile(content);
		
		// Configurate folder view
		configurate(name);
	}
	
	private void addView(String name, String viewName){
		String content;
		FileManager fileMngr;
		
		String tilePattern = "\n<definition name=\""+name+"/"+viewName+"\" extends=\"master.page\">"+	
				"\n\t<put-attribute name=\"title\" value=\""+viewName+"\" />"+
				"\n\t<put-attribute name=\"body\" value=\"/views/"+name+"/"+viewName+".jsp\" />"+
				"\n</definition>";
		
		// Generate view file
		fileMngr = new FileManager(viewsFoldersPath+"/plantilla/"+viewName+".jsp");
		content = fileMngr.readFile();
		fileMngr.setFilePath(viewsFoldersPath+"/"+name+"/"+viewName+".jsp");
		fileMngr.writeFile(content);
		
		// Add tile definition
		fileMngr.setFilePath(viewsFoldersPath+"/"+name+"/tiles.xml");
		content = fileMngr.readFile();
		content = content.substring(0, content.lastIndexOf("</")-1);
		content += tilePattern;
		content += "\n</tiles-definitions>";
		fileMngr.writeFile(content);
		
		fileMngr.setFilePath(viewsFoldersPath+"/"+name+"/tiles_es.xml");
		content = fileMngr.readFile();
		content =content.substring(0, content.lastIndexOf("</")-1);
		content += tilePattern;
		content += "\n</tiles-definitions>";
		fileMngr.writeFile(content);
	}
	
	/**
	 * Checks i18n&l10n messages. Finds messages that are not defined in the configuration
	 * files and prompts user for the not defined ones (if interactive) or generate a default 
	 * message (if not interactive).
	 * 
	 * @param name - name of the view folder (most times it is the entity name).
	 * @param interactive - uses interactive mode or not.
	 * */
	private void checkInternationalization(String folderName, Boolean interactive){
		File dir = new File(viewsFoldersPath+"/"+folderName);
		String[] dirFiles;
		Collection<String> actual = null, past = null, aux;	// used for messages comparison
		Collection<String> viewsMessages = new HashSet<String>();
		String fileName, pastFileName = "";
		
		dirFiles = dir.list();
		// Loads all messages used into views (.jsp files)
		for(int i=0; i<dirFiles.length; i++){
			fileName = dirFiles[i];
			
			if(fileName.contains(".jsp"))	viewsMessages.addAll(searchMessagesIntoViewFile(folderName, fileName));
		}
		
		// Looks for differences between languages
		for(int i=0; i<dirFiles.length; i++){
			fileName = dirFiles[i];
			
			if(fileName.contains("messages")){
				actual = loadMessagesFromFile(folderName, fileName);
				//TODO: refactorizar, codigo repetido
				if(past != null){
					// Adds all messages defined into past i18n&l10n file and not defined into the actual one
					if(!actual.containsAll(past)){
						aux = new HashSet<String>(past);
						aux.remove("");
						aux.removeAll(actual);
						addAllMessages(folderName, fileName, aux);
					}
					
					// Adds all messages defined into actual i18n&l10n file and not defined into the past one
					if(!past.containsAll(actual)){
						aux = new HashSet<String>(actual);
						aux.remove("");
						aux.removeAll(past);
						addAllMessages(folderName, pastFileName, aux);
					}
				}
				// Adds all messages used into views but not defined into file
				if(!actual.containsAll(viewsMessages)){
					aux = new HashSet<String>(viewsMessages);
					aux.removeAll(actual);
					addAllMessages(folderName, fileName, aux);
				}
				
				past = new HashSet<String>(actual);
				pastFileName = fileName;
			}
		}
		
	}
	
	// Ancillary methods --------------------------
	private Boolean viewFolderExists(String name){
		Boolean res;
		FileManager fileMngr = new FileManager(viewsFoldersPath+"/"+name);
		
		res = fileMngr.getFile().exists();
		
		return res;
	}
	
	/**
	 * Loads all i18n&l10n messages from i18n&l10n properties files.
	 * Prints a warning message if duplicated i18n&l10n codes are found.
	 * 
	 * @param folderName - folder from where i18n&l10n messages has to be loaded
	 * @param fileName - name of the file containing the i18n&l10n messages (i.e. "messages.properties")
	 * */
	private Collection<String> loadMessagesFromFile(String folderName, String fileName){
		Collection<String> res = new HashSet<String>();
		FileManager fileMngr = new FileManager(viewsFoldersPath+"/"+folderName+"/"+fileName);
		String[] content;
		String aux, messageCode;
		
		// Loop over all file lines and add i18n&l10n codes
		content = fileMngr.readFile().split("\n");
		for(int i=0; i<content.length; i++){
			aux = content[i];
			if(!aux.isEmpty() && aux.charAt(0)!='#'){
				messageCode = aux.split("=")[0];
				messageCode = messageCode.trim();
				if(res.contains(messageCode)) System.out.println("WARNING! Duplicated i18n&l10n code '"+messageCode+"' into "+folderName+"/"+fileName);
				res.add(messageCode);	
			}
		}
		
		return res;
	}
	
	/**
	 * Searches and loads all i18n&l10n messages from a specified view.
	 * 
	 * @param folderName - folder where view file lives.
	 * @param viewName - name of the view where the search must be done (i.e. "list.jsp").
	 * */
	private Collection<String> searchMessagesIntoViewFile(String folderName, String viewName){
		Collection<String> res = new HashSet<String>();
		FileManager fileMngr = new FileManager(viewsFoldersPath+"/"+folderName+"/"+viewName);
		String content;
		int codeBeginIndex, codeEndIndex; //i18n&l10n start and end indexes
		
		//code=codigoi18n&l10n
		content = fileMngr.readFile();
		codeBeginIndex = content.indexOf("code=")+6;
		while(true){
			codeEndIndex = content.indexOf("\"", codeBeginIndex);
			res.add(content.substring(codeBeginIndex, codeEndIndex));
			codeBeginIndex = content.indexOf("code=", codeEndIndex)+6;
			
			if(codeBeginIndex<codeEndIndex) break;
		}
		
		return res;
	}
	
	private void addAllMessages(String folderName, String fileName, Collection<String> messages){
		Collection<String> existentMessages = loadMessagesFromFile(folderName, fileName);
		FileManager fileMngr = new FileManager(viewsFoldersPath+"/"+folderName+"/"+fileName);
		String content = fileMngr.readFile();
		String newMsg;
		
		for(String message:messages){
			if(! existentMessages.contains(message)){
				newMsg = message+" = ";
				if(message.contains("."))	newMsg += message.replaceAll("\\.", " ");
				else newMsg += message;
				
				content += "\n"+newMsg;
				System.out.println(" INFO: added message "+message+" to "+fileName);
			}
		}
		fileMngr.writeFile(content);
	}

}
