package utilities.custom.toolboxes;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import utilities.custom.validators.DomainModelValidator;
import utilities.internal.FileManager;

/**
 * Tool that give all functionality related to domain and persistence models.
 * */
public class ModelTool implements Tool{

	 /* MODELO DE DOMINIO Y PERSISTENCIA
	 * - Generar entidades (usar fichero de configuracion entities.config) (OK)
	 * - Validar modelo de dominio y de persistencia (OK)
	 * - Operaciones con entidades
	 * 		-- Generar nueva entidad (solo generar clase, anotaciones de clase y estructura) (OK)
	 * 		-- Validar entidad concreta (debe introducirse el nombre) (OK)
	 * 		-- Añadir asociacion a una entidad (debe introducirse el nombre del origen, el nombre del destino y el tipo de asociacion)
	 */
	
	@Override
	public void execute(String[] parameters) {
		if( checkParameters(parameters)){
			if(parameters[0].equals("-generate"))	generate(parameters);
			else if(parameters[0].equals("-validate")){	
				try{
					validate(parameters);	
				}
				catch(ClassNotFoundException oops){
					System.err.println("ERROR: could not validate entity due to the entity class could not be found");
				}
			}
			else associate(parameters);
		}
		else	printHelp();
	}

	@Override
	public boolean checkParameters(String[] parameters) {
		Boolean res = true;
		
		if( parameters.length == 0 )	res = false;
		else if( !parameters[0].equals("-generate") && !parameters[0].equals("-validate") && !parameters[0].equals("-associate") )	
					res = false;
		else if( parameters[0].equals("-associate") && parameters.length < 4 )	res = false;
		
		return res;
	}

	@Override
	public void printHelp() {
			System.out.println(	"HELP!\n\t"+
								"-help						- 	Prints this help\n\t"+
								"-generate <entityName>		-	Generate a new entity\n\t"+
								"-generate [fileURL]		-	Generate entities basing on specified file or src/main/java/domain/entities.conf\n\t"+
								"-validate [entityName]		-	Validate the given entity or all entities if no one is given\n\t"+
								"-associate <entityA> <associationType> <entityB> [roleA roleB] - Associate two entities");
	}

	
	// Tool functionality ---------------------------------------------------------------------------
	private void generate( String[] params ){
		String configFilePath = null, config;
		FileManager fileMngr;
		
		// Load template & config files
		if(params.length == 1)				configFilePath = "src\\main\\java\\domain\\entities.config";
		else if(params[1].contains("\\"))	configFilePath = params[1]; //TODO: comprobar en que direccion tiene las barras cuando el path se pasa como parametro
		
		// Generate multiple entities from configuration file
		if(configFilePath != null){
			Collection<String> entities = new HashSet<String>();
			String[] configFileLines;
			
			fileMngr = new FileManager(configFilePath);
			config = fileMngr.readFile();
			configFileLines = config.split("\\n");
			
			// Load entities names
			for(int i=0; i<configFileLines.length; i++){
				String line = configFileLines[i];
				
				if(line.charAt(0)!='#'){
					String[] lineComponents = line.split(" ");
					entities.add(lineComponents[0]);
					entities.add(lineComponents[2]);
				}
			}
			
			for(String entityName : entities)	generateEntity(entityName);
		}
		else generateEntity(params[1]);
	}
	
	/**
	 * Performs entity validations. Prints all error messages to standard output.
	 * Also, automatically, protects all entities against XSS attacks
	 * @throws ClassNotFoundException - Entity to be validated cannot be found
	 * */
	private void validate( String[] params ) throws ClassNotFoundException{
		if( params.length == 2 ){
			DomainModelValidator validator;
			List<String> errors;
			XSSProtectionToolbox XSSprotection;
			String className = params[1];
			
			XSSprotection = new XSSProtectionToolbox( className );
			validator = new DomainModelValidator( className );
			
			try{
				errors = validator.performCheckouts();
				if(XSSprotection.safeHtmlCheckout(true).size()>0)
					errors.add(className+" has fields susceptible to XSS attacks (read above)");
				
				if( !errors.isEmpty() ){
					System.out.println("ERRORS FOUND INTO "+className+": "+errors.size()+" ERRORS!");
					
					for( String msg : errors )	System.out.println(msg);
					XSSprotection.safeHtmlProtection(false);
					System.out.println(className+" protected against XSS attacks");
				}
				else	System.out.println( "NO ERRORS FOUND INTO "+className );
			}
			catch(Exception oops){
				System.err.println("There was an error trying to validate "+params[1]);
			}
		}
		else{
			File entitiesDirectory;
			String[] entitiesFiles;
			List<String> classNames = new ArrayList<String>();
			
			
			// Get all entities class name
			entitiesDirectory = new File( "src/main/java/domain" );
			entitiesFiles = entitiesDirectory.list();
			
			for( int i=0; i<entitiesFiles.length; i++ ){
				String fileName = entitiesFiles[i];
				
				if( !fileName.contains( "DomainEntity" ))	classNames.add( fileName.replace( ".java", "" ));
			}
			
			for( String className : classNames ){
				String[] parameter = new String[2];
				parameter[0] = "-validate";
				parameter[1] = className;
				
				validate( parameter );
			}
		}
	}

	/**
	 * Add an association between two entities
	 * 
	 * @param params - params of the association, they are <EntityA> <AssociationType><EntityB> [roleA roleB] [options]
	 * */
	private void associate( String[] params ){
		throw new UnsupportedOperationException("Pendiente de implementar");
		
		/*TODO: todo el codigo que hay debajo funciona correctamente. Dividir el 
		 * fichero con el template de las asociaciones en varios ficheros, uno
		 * por cada tipo de asociacion para hacer mas facil la carga del codigo*/
		
//		String entityA, entityB, associationType, roleA, roleB, template, options = "";
//		FileManager fileMngr;
//		String[] associationsTemplates;
//		Association association;
//		
//		
//		// Initialization
//		entityA = params[1];
//		entityB = params[3];
//		associationType = params[2];
//		if(params.length>4){
//			roleA = params[4];
//			roleB = params[5];
//		}
//		else{
//			roleA = parse2FieldName(entityA);
//			roleB = parse2FieldName(entityB);
//		}
//		
//		// If association is bidirectional split it into 2 associations and make a recursive call
//		if(associationType.substring(1, 6).equals("<--->")){
//			List<String> callParams = new ArrayList<String>();
//			String aMultiplicity = associationType.charAt(0)+"";
//			String bMultiplicity = associationType.charAt(associationType.length()-1)+"";
//			
//			// Extract association 1 parameters
//			callParams.add(entityA);
//			callParams.add(aMultiplicity+"---->"+bMultiplicity);
//			callParams.add(entityB);
//			callParams.add(roleA);
//			callParams.add(roleB);
//			callParams.add("");
//			
//			// Extract association 2 parameters
//			callParams.add(entityB);
//			callParams.add(bMultiplicity+"---->"+aMultiplicity);
//			callParams.add(entityA);
//			callParams.add(roleB);
//			callParams.add(roleA);
//			callParams.add("");
//			
//			if(aMultiplicity.equals("1") || aMultiplicity.equals("0")){
//				if(bMultiplicity.equals("1") || bMultiplicity.equals("0"))
//					callParams.set(11, "(mappedBy = \"roleB\")");
//				else
//					callParams.set(5, "(mappedBy = \"roleA\")");
//			}
//			else if(bMultiplicity.equals("1") || bMultiplicity.equals("0"))
//				callParams.set(11, "(mappedBy = \"roleB\")");
//			
//			associate((String[])callParams.subList(0, 6).toArray());
//			associate((String[])callParams.subList(6, 12).toArray());
//		}
//		
//		if(params.length == 4 || params.length == 6){
//			options = params[params.length-1];
//		}
//		
//		association = new Association(associationType.charAt(0)+"",
//				associationType.charAt(6)+"", associationType.substring(1, 6),
//				roleA, roleB);
//		
//		if(association.isInefficient())	
//			System.out.println("WARNING!! ASSOCIATION "+entityA+" "+associationType+" "+entityB+" is INEFFICIENT!");
//		
//		
//		// Load association source code template
//		fileMngr = new FileManager("src/main/resources/code_templates/associations.txt");
//		template = fileMngr.readFile();
//		associationsTemplates = template.split("#");
//		//templates: OneToOne, OneToMany, ManyToOne, ManyToMany, Generalization
//		
//		// Load template
//		if( association.getType().equals(""))
//		
//		
//		System.out.println(template);
	}
	
	// Ancillary methods --------------------------------
	/**
	 * Generates an entity .java source code file
	 * 
	 * @param entityName - name of the entity to create
	 * */
	private void generateEntity(String entityName){
		FileManager fileMngr;
		String template, entitiesPkg;
		
		entitiesPkg = "src\\main\\java\\domain\\";
		fileMngr = new FileManager("src\\main\\resources\\code_templates\\entity.txt");
		template = fileMngr.readFile();
		
		template = template.replaceAll("%entity%", entityName);
		
		fileMngr.setFilePath(entitiesPkg+entityName+".java");
		fileMngr.writeFile(template);
	}
	
}