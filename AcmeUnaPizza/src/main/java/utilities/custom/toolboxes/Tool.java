package utilities.custom.toolboxes;

/**
 * Interface that all toolbox tools must implement.
 * */
public interface Tool {

	/**
	 * Executes the tool functionality based on the given parameters.
	 * @param parameters - parameters to be used by the tool.
	 * */
	void execute( String[] parameters );
	
	/**
	 * Checks the given parameters.
	 * @param parameters - parameters to be checked.
	 * @return true if parameters fits any of the tool functionality parameters, false otherwise.
	 * */
	boolean checkParameters( String[] parameters );
	
	/**
	 * Prints a help message explaining how to use the tool.
	 * */
	void printHelp();
	
}
