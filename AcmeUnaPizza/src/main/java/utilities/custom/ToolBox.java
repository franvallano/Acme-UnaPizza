package utilities.custom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import utilities.custom.toolboxes.BBDDConfigTool;
import utilities.custom.toolboxes.ControllersTool;
import utilities.custom.toolboxes.ConvertersTool;
import utilities.custom.toolboxes.DBPopulationTool;
import utilities.custom.toolboxes.ModelTool;
import utilities.custom.toolboxes.RepositoriesTool;
import utilities.custom.toolboxes.ServicesTool;
import utilities.custom.toolboxes.Tool;
import utilities.custom.toolboxes.ViewsTool;

public class ToolBox {

	/*
	 * Objetivos:
	 * 
	 * MODELO DE DOMINIO Y PERSISTENCIA 
	 * - Operaciones con entidades
	 * 		-- Añadir asociacion a una entidad (debe introducirse el nombre del origen, el nombre del destino y el tipo de asociacion)
	 * 
	 * BASE DE DATOS
	 * - Crear nueva base de datos
	 * */
	
	public static void main(String[] args){
		BufferedReader userInput;
		String command;
		String[] aux;
		String[] callParameters;
		
		userInput = new BufferedReader(new InputStreamReader(System.in));
		while(true){
			System.out.print("toolbox> ");
			try{
				command = userInput.readLine();
			}
			catch(IOException oops){
				System.out.println(	"Sorry, there was a problem with standard input stream :(");
				break;
			}
		
		aux = parseParameters(command);
		if( aux == null ) break;
		
		// Extract call parameters
		callParameters = new String[aux.length-1];
		for(int i=1; i<aux.length; i++){
			callParameters[i-1] = aux[i];
		}
		
		executeToolbox(aux[0], callParameters);
		}
	}
	
	/**
	 * Parse introduced param into an array containing the utility to execute and the parameters introduced
	 * 
	 * @param - command command to execute
	 * @return array of Strings with the parsed command or null if user introduced 'quit' command
	 * */
	private static String[] parseParameters(String command){
		String[] res;
		String[] aux;
		
		command = " "+command;
		aux = command.split(" ");
		res = new String[aux.length-1];
		for(int i=1; i<aux.length; i++){
			res[i-1] = aux[i];
		}
		
		if( res.length==1 && res[0].equals("quit") )	// user introduced quit command
			return null;
		
		return res;
	}

	
	/**
	 * Launch a command.
	 * 
	 * @param command - command to execute
	 * @param parameters - parameters passed to the command
	 * */
	private static void executeToolbox(String command, String[] parameters){
		Tool tool = null;
		
		//	TODO: extraer la funcionalidad para ejecutar los toolboxes mediante parametros e incluirlos dentro de la clase
		// 	del toolbox.
		
		if( command.equals("model") ) tool = new ModelTool();
		else if( command.equals("db") || command.equals("database") ) tool = new BBDDConfigTool(parameters);
		else if( command.equals("populate") ) tool = new DBPopulationTool();
		else if( command.equals("repo") || command.equals("repository") )
			tool = new RepositoriesTool();
		else if( command.equals("service") ) tool = new ServicesTool();
		else if( command.equals("converter") ) tool = new ConvertersTool();
		else if( command.equals("controller") ) tool = new ControllersTool();
		else if( command.equals("view") ) tool = new ViewsTool();
		else if( command.equals("help") ){
			printHelp();
			System.out.println("\n");
		}
		else{
			System.out.println("Command '"+command+"' doesn't exists");
			printHelp();
			System.out.println("\n");
		}
		
		if(tool != null)
			tool.execute(parameters);
	}

	/**
	 * print help message
	 * */
	private static void printHelp(){
		System.out.println("HELP!");
		
		System.out.println(	"Available commands: \n"+
							"  model                - utilities related to domain and persistence models\n"+
							"  database (or db)     - utilities related to database configuration\n"+
							"  populate             - utilities related to the populateDatabase.xml file\n"+
							"  repository (or repo) - utilities related to the repositories\n"+
							"  service              - utilities related to the services\n"+
							"  converter            - utilities related to the converters\n"+
							"  controller           - utilities related to the controllers\n"+
							"  view                 - utilities related to the view\n"+
							"  quit                 - quit and exit\n"+
							"  help                 - print help message");
	}
}
