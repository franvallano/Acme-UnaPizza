package utilities.custom;

public class Test {

	public static void main(String[] args){
		String command = "model -create -param2 value2";
		String[] res;
		String[] aux;
		
		command = " "+command;
		aux = command.split(" ");
		res = new String[aux.length-1];
		for(int i=1; i<aux.length; i++){
			res[i-1] = aux[i];
		}
		
	}
	
}
