package databases.normalization;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Manages input and output of the application. It reads and writes
 * text files with the given functional dependency sets.
 *
 */
public class FileManager {

	/**
	 * Creates a FunctionalDependencySet object from a text file. The text file 
	 * must have the following format:
	 * {a->b, b->c, etc...}
	 * @param filename the name of the file that contains the set to be loaded
	 * @return the set read from the text file
	 */
	public static FunctionalDependencySet loadFunctionalDependencySet(String filename){
		FunctionalDependencySet fds = null;
		try {
	        BufferedReader in = new BufferedReader(new FileReader(filename));
	        String str;
	        while ((str = in.readLine()) != null) {
	        	fds = processFDSet(str);
	        }
	        in.close();
	    } catch (IOException e) {
	    	System.out.println("Couldn't read the file");
	    }
		return fds;
	}
	
	private static FunctionalDependencySet processFDSet(String str){
		FunctionalDependencySet result = new FunctionalDependencySet();
		str = str.substring(1, str.length() - 1);
		String[] fds = str.split(",");
		for(String s: fds){
			String fullFDs[] = s.trim().split("->");
			result.add(new FunctionalDependency(fullFDs[0], fullFDs[1]));
		}
		return result;
	}
	
	/**
	 * Writes to the given text file the given functional dependency set.
	 * @param fds the set to be written
	 * @param filename the name of the file in which the set will be written
	 * @return true if writing the set was successful, false otherwise
	 */
	public static boolean saveFunctionalDependencySet(FunctionalDependencySet fds, String filename){
		boolean saved = false;
		try {
	        BufferedWriter out = new BufferedWriter(new FileWriter(filename));
	        out.write(fds.toString());
	        out.close();
	        saved = true;
	    } catch (IOException e) {
	    	System.out.println("Couldn't write the file");
	    }
		
		return saved;
	}
}
