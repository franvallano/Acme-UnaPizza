package utilities;

/**
 * Custom class that represents exception to be thrown when a entity has been hacked.
 * */
public class EntityHackingException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	//Constructor ---------------------------------------------
	public EntityHackingException(){
		super();
	}
	
	public EntityHackingException(String message) {
		super(message);
	}
}
