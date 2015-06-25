package utilities.custom.toolboxes;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import utilities.internal.FileManager;

public class RepositoriesToolbox {

	// Attribute -------------------------------------------------------------------------------------------
	private String repositoryName;
	private File repositoriesPackage;
	
	// Constructor -----------------------------------------------------------------------------------------
	public RepositoriesToolbox(){
		super();
		
		repositoriesPackage = new File("src\\main\\java\\repositories");
		
		if( !repositoriesPackage.exists() )
			throw new IllegalArgumentException( "package with the specified name doesn't exists" );
		else if ( !repositoriesPackage.isDirectory() )
			throw new IllegalArgumentException( "specified path isn't a directory, so neither a package");
	}

	// Getters and setters ---------------------------------------------------------------------------------
	public String getRepositoryName() {
		return repositoryName;
	}

	public void setRepositoryName(String repositoryName) {
		if( repositoryName == null )
			throw new IllegalArgumentException("repository name must not be null");
		else if( repositoryName.length() == 0 )
			throw new IllegalArgumentException("repository name must not be empty");
		
		this.repositoryName = repositoryName;
	}
	
	// Functionality methods -------------------------------------------------------------------------------
	@SuppressWarnings("rawtypes")
	public void cleanup(){
		List<String> repoNames = new ArrayList<String>();
		List<String> serviceNames = new ArrayList<String>();
		File servicesPkg = new File("src\\main\\java\\services");
		
		for( File f : repositoriesPackage.listFiles() ){
			String fileName = f.getName();
			fileName = fileName.substring( 0, fileName.indexOf( '.' ));
			fileName = fileName.replaceAll("Repository", "");
			
			repoNames.add( fileName );
		}
		
		for( File f : servicesPkg.listFiles() ){
			String fileName = f.getName();
			fileName = fileName.substring( 0, fileName.indexOf( '.' ));
			fileName = fileName.replaceAll("Service", "");
			
			serviceNames.add(fileName);
		}
		
		repoNames.removeAll(serviceNames);
		
		for( String s : repoNames ){
			Class repo;
			
			try{
				repo = Class.forName( "repositories."+s+"Repository" );
				removeRepository( repo );
			}catch (ClassNotFoundException e) {
				throw new RuntimeException( "Tried to load repository "+s+" but it wasn't found" );
			}
		}
	}
	
	public void generateRepository( String entityName ){
		String templatesPath = "src\\main\\resources\\code_templates";
		FileManager fileManager = new FileManager(templatesPath+"\\interface.txt");
		String template;
		String repositoriesPath = "src\\main\\java\\repositories";
		
		template = fileManager.readFile();
		
		fileManager.setFilePath(repositoriesPath+"\\"+entityName+"Repository.java");
		
		String code = "";
		
		code = template.replace("%entity%", entityName);
		
		fileManager.writeFile(code);
		
		if( !fileManager.getFile().exists() )
			throw new RuntimeException("El fichero del repositorio no se ha generado");
	}
	
	@SuppressWarnings("rawtypes")
	public List<String> performCheckouts(){
		if( this.repositoryName == null )
			throw new RuntimeException("El nombre del repositorio no puede ser null, utilice setRepositoryName()");
		
		List<String> res = new ArrayList<String>();
		Class repository;
		Annotation[] annotations;
		Method[] repoMethods;
		Boolean checkoutOk;
		Class[] extendedInterfaces;
		
		try{
			repository = Class.forName("repositories."+this.repositoryName);
			
			if(! repository.isInterface() ){
				res.add("ERROR: specified repository isn't an interface");
				return res;
			}
			
		} catch ( ClassNotFoundException e ){
			res.add("ERROR: no repository found with name "+this.repositoryName);
			return res;
		}
		
		// Check repository annotations ---------------------------------------------------------------
		checkoutOk = false;
		annotations = repository.getAnnotations();
		for( int i=0; i<annotations.length; i++ ){
			Annotation annotation = annotations[i];
			
			if( annotation instanceof org.springframework.stereotype.Repository )
				checkoutOk = true;
		}
		
		if( !checkoutOk )
			res.add("- ANNOTATION ERROR: repository isn't annotated with @Repository annotation");
		
		// Check repository extended interfaces --------------------------------------------------------
		checkoutOk = false;
		extendedInterfaces = repository.getInterfaces();
		for( int i=0; i<extendedInterfaces.length; i++ ){
			Class extendedInterface = extendedInterfaces[i]; 
			
			if ( extendedInterface.toString().contains("org.springframework.data.jpa.repository.JpaRepository") )
				checkoutOk = true;
		}
		
		if( !checkoutOk )
			res.add("- INTERFACE ERROR: repository doesn't extends JpaRepository interface");
		
		// Check repository queries methods -------------------------------------------------------------
		repoMethods = repository.getDeclaredMethods();
		for( int i=0; i<repoMethods.length; i++ ){
			Method method = repoMethods[i];

			if( method.getAnnotation( org.springframework.data.jpa.repository.Query.class )==null )
				res.add("- QUERY ERROR: method "+method.getName()+" isn't annotated with @Query annotation");
		}
		
		return res;
	}
	
	@SuppressWarnings("rawtypes")
	private void removeRepository( Class repository ){
		String packagePath, repositoryName;
		File serviceFile;
		
		packagePath = this.repositoriesPackage.getAbsolutePath();
		repositoryName = repository.getName();
		repositoryName = repositoryName.substring( repositoryName.lastIndexOf( '.' )+1 );
		serviceFile = new File(packagePath+"//"+repositoryName+".java");
		
		serviceFile.delete();
	}
}
