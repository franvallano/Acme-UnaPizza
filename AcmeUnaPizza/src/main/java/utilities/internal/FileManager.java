package utilities.internal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Manages basic file I/O.
 * 
 * @author David Glez.
 * */
public class FileManager {

	// Attributes ----------------------------------------------
	private String filePath;
	
	// Constructor ---------------------------------------------
	public FileManager(String filePath){
		this.filePath=filePath;
	}
	
	// Getters and setters -------------------------------------
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public static String getNewLine() {
		String OS = System.getProperty("os.name").toLowerCase();
		String res = "";
		
		if(OS.indexOf("win") >= 0)
			res = "\n\r";
		else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") >= 0)
			res = "\n";
		
		return res;
	}

	public static String getPathSeparator() {
		String OS = System.getProperty("os.name").toLowerCase();
		String res = "";
		
		if(OS.indexOf("win") >= 0)
			res = "\\";
		else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") >= 0)
			res = "//";
		
		return res;
	}

	public File getFile(){
		return new File(filePath);
	}
	
	// Other methods --------------------------------------------
	/**
	 * Reads a file.
	 * 
	 * @return file content*/
	public String readFile(){
		String res = "";
		
		File log = new File(filePath);
		BufferedReader bf = null;
		
		try{
			bf = new BufferedReader(new FileReader(log));
			String line = bf.readLine();
		
		
			while (line != null){
				res += line+"\n";
				line = bf.readLine();
			}
		
			return res;
		}
		catch(Exception oops){
			System.err.println("There was an error reading the file "+oops.getStackTrace());
			return "";
		}
		finally{
			try {
				bf.close();
			} catch (IOException e) {
				System.err.println("There was an error closing the read Buffer "+e.getStackTrace());
			}
		}
	}

	/**
	 * Write a file. If the file doesn't exist it will be created and then written.
	 * Be careful! Previous file content will be deleted!
	 * 
	 * @param content - content to be written.
	 * */
	public void writeFile(String content){
		File file = new File(filePath);
		
		BufferedWriter bw = null;
		
		try{
			if (!file.exists())
				file.createNewFile();
			
			bw = new BufferedWriter(new FileWriter(file));
			bw.write(content);
		}
		catch (Exception oops){
			System.err.print("There was an error writing the file "+oops.getStackTrace());
		}
		finally{
			try{
				bw.close();
			}	
			catch(IOException oops){
				System.err.print("There was an error closing the write buffer "+oops.getStackTrace());
			}
		}
	}
	
	/**
	 * Empties a file.
	 * */
	public void emptyFile(){
		writeFile("");
	}
	
}
