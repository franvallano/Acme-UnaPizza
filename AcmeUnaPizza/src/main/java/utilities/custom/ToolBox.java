package utilities.custom;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import utilities.custom.toolboxes.ModelTool;
import utilities.custom.toolboxes.ServicesToolbox;
import utilities.custom.toolboxes.Tool;
import utilities.internal.FileManager;

public class ToolBox {

	/*
	 * Objetivos:
	 * 
	 * MODELO DE DOMINIO Y PERSISTENCIA
	 * - Generar entidades (usar fichero de configuracion entities.config)
	 * - Validar modelo de dominio y de persistencia
	 * - Operaciones con entidades
	 * 		-- Generar nueva entidad (solo generar clase, anotaciones de clase y estructura)
	 * 		-- Validar entidad concreta (debe introducirse el nombre)
	 * 		-- Añadir asociacion a una entidad (debe introducirse el nombre del origen, el nombre del destino y el tipo de asociacion)
	 * 
	 * BASE DE DATOS
	 * - Configurar nueva base de datos
	 * - Crear nueva base de datos
	 * 
	 * POPULATE DATABASE
	 * - Autogenerar XML de una entidad o datatype (leer clase, extraer atributos y generar XML con valores por defecto cuando
	 * 		los atributos no son asociaciones)
	 * 
	 * REPOSITORIOS
	 * - Generar repositorios para todas las entidades
	 * - Generar repositorio (debe indicarse la entidad para el que se genera el repositorio)
	 * - Validar repositorios
	 * - Validar un repositorio en concreto (debe indicarse el repositorio a validar)
	 * 
	 * SERVICIOS
	 * - Generar servicios para todos los repositorios
	 * - Generar servicio (debe indicarse el repositorio)
	 * - Validar servicios (controlar que un servicio implemente todos los metodos de un repositorio)
	 * - Validar un servicio en concreto (debe indicarse el servicio a validar)
	 * 
	 * CONVERTIDORES
	 * - Generar y configurar los convertidores (un par para cada entidad)
	 * 
	 * CONTROLADORES
	 * - Generar un controlador (debe indicarse el RequestMapping)
	 * - Añadirle metodos a un controlador (debe indicarse el metodo a añadir y el nombre del controlador)
	 * 
	 * VISTAS
	 * - Generar nueva carpeta de vistas (generar tiles y i18n&l10n)
	 * - Añadir vista de listado, edicion (create, edit, save, remove) o dashboard. Si se añaden metodos de edicion autogenerar
	 * 		createEditionModelAndView
	 * - Comprobar los codigos de i18n&l10n de las paginas (extraer los codigos de las paginas .jsp y buscarlos en las de 
	 * 		i18n&l10n, si no se encuentra alguno preguntar por el contenido y añadirla)
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
		Tool tool;
		
		//	TODO: extraer la funcionalidad para ejecutar los toolboxes mediante parametros e incluirlos dentro de la clase
		// 	del toolbox.
		
		if( command.equals("model") ){
			tool = new ModelTool();
			
			tool.execute(parameters);
		}
		else if( command.equals("db") || command.equals("database") ){
			throw new UnsupportedOperationException("Not implemented yet");
		}
		else if( command.equals("populate") ){
			throw new UnsupportedOperationException("Not implemented yet");
		}
		else if( command.equals("repo") || command.equals("repository") ){
			throw new UnsupportedOperationException("Not implemented yet");
		}
		
		else if( command.equals("service") ) executeServiceToolbox(parameters);
		
		else if( command.equals("converter") ){
			throw new UnsupportedOperationException("Not implemented yet");
		}
		else if( command.equals("controller") ){
			throw new UnsupportedOperationException("Not implemented yet");
		}
		else if( command.equals("view") ){
			throw new UnsupportedOperationException("Not implemented yet");
		}
		else if( command.equals("help") ){
			printHelp();
			System.out.println("\n");
		}
		else{
			System.out.println("Command '"+command+"' doesn't exists");
			printHelp();
			System.out.println("\n");
		}
	}

	/**
	 * print help message
	 * */
	private static void printHelp(){
		System.out.println("HELP!");
		
		System.out.println(	"Available ommands: \n"+
							"  model 				- utilities related to domain and persistence models\n"+
							"  database (or db) 	- utilities related to database configuration\n"+
							"  populate				- utilities related to the populateDatabase.xml file\n"+
							"  repository (or repo)	- utilities related to the repositories\n"+
							"  service				- utilities related to the services\n"+
							"  converter			- utilities related to the converters\n"+
							"  controller			- utilities related to the controllers\n"+
							"  view					- utilities related to the view\n"+
							"  quit					- quit and exit\n"+
							"  help					- print help message");
	}
	
	/*
	 * - Generar servicios para todos los repositorios
	 * - Generar servicio (debe indicarse la entidad)
	 * - Validar servicios (controlar que un servicio implemente todos los metodos de un repositorio)
	 * - Validar un servicio en concreto (debe indicarse el servicio a validar)
	 * */
	
	/**
	 * Execute services toolbox functionality
	 * 
	 * @param params - parameters passed to services toolbox
	 * */
	private static void executeServiceToolbox(String[] params){
		if( params.length == 0 ){
			params = new String[1];
			params[0] = "-help";
		}
		if( params.length > 2 )
			throw new IllegalArgumentException("Invalid number of params (expected 1 or 2)");
		
		if( !params[0].equals("-generate") && !params[0].equals("-validate") && !params[0].equals("-help"))
			throw new IllegalArgumentException("'"+params[0]+"' is an invalid command");
		
		ServicesToolbox toolbox = new ServicesToolbox();;
		
		if( params.length == 1 ){
			if( params[0].equals("-help") ){
				System.out.println(	"Services Toolbox HELP:\n"+
									"  generate [entity name] - generates a service for the given entity name or for all entities is no name is given\n"+
									"  validate [service name] - validates the given service or all services if no one is given\n"+
									"  help - print this message");
			}
			FileManager fileMngr;
			String[] objects;
			File directory;
			
			if( params[0].equals("-generate") ){
				fileMngr = new FileManager(Constants.entitiesFolder);
				directory = fileMngr.getFile();
				objects = directory.list();
				
				for( int i=0; i<objects.length; i++){
					String object = objects[i];
					object = object.substring(0, object.length()-5);
					
					if( !object.equals("DomainEntity") )	toolbox.generateService( object );
				}
			}
			
			else {
				fileMngr = new FileManager(Constants.servicesFolder);
				directory = fileMngr.getFile();
				objects = directory.list();
				List<String> errors = new ArrayList<String>();
				
				for( int i=0; i<objects.length; i++){
					String object = objects[i];
					object = object.substring(0, object.length()-5);
					
					toolbox.setServiceName( object );
					for( String error : toolbox.performCheckouts() )
						errors.add( error );
				}
				
				if( !errors.isEmpty() )
					printErrors( errors );
			}
		}
		
		else{
			toolbox.setServiceName( params[1]+"Service" );
			if( params[0].equals("-generate") )
				toolbox.generateService( params[1] );
			else{ 
				List<String> errors;
				
				errors = toolbox.performCheckouts();
				if( !errors.isEmpty() ){
					printErrors( errors );
				}
				
			}
		}
	} //ServiceToolbox
	
	/**
	 * Prints all error messages.
	 * 
	 * @param errors - error messages to be printed
	 * */
	private static void printErrors( List<String> errors ){
		System.out.println("ERRORS FOUND: ");
		for( String error : errors ){
			System.out.print("\t");
			System.out.println(error);
		}
	}
	
}
