import java.util.*;
import java.io.*;

public class proteinSequenceDuplicateChecker{

	static Map<String, Integer> sequenceMap = new HashMap<String, Integer>();
	static Integer duplicateCount = 0;

	public static void main(String[] args) {
		String sequence = "";

		if(args.length == 0){
			System.out.println("Expected: proteinSequence <DataBase1> <DataBase2> ..");
			System.exit(0);
		}

		//Goes through each DB given in command line arg. and adds to map to check for duplicates
		for(int i = 0; i < args.length; i++){
			//Read each line of input database
			try (BufferedReader br = new BufferedReader(new FileReader(args[i]))) {
			    String line;
			    while ((line = br.readLine()) != null) {
			    	//Check if it is a valid line
			    	if(!line.isEmpty()){
			    		sequence += line;
			    	}
			    	//New line - ready for hashmap
			    	else{
			    		addToHashMap(sequence);
			    		sequence = "";
			    	}
			    }
			} catch(FileNotFoundException excpetion){
				System.err.println("FileNotFoundException: " + excpetion.getMessage());
			} catch(IOException excpetion){
				System.err.println("IOException: " + excpetion.getMessage());
			}
		}

		//print hashmap to file
		printHashMap();
		System.out.println("Number of duplicates: "+duplicateCount);
	}

	static private void addToHashMap(String inputSequence){
		Integer value = sequenceMap.get(inputSequence);
		if (value != null) {
		    sequenceMap.put(inputSequence, (value+1));
		} else {
		    // No such key
		    sequenceMap.put(inputSequence, 1);
		}
	}

	static private void printHashMap(){
		//Prints HashMapToOutput
		try (PrintWriter writeOut = new PrintWriter("DuplicateSequences.txt", "UTF-8")){
			for (Map.Entry<String, Integer> entry : sequenceMap.entrySet()) {
				if(entry.getValue() > 1){
					duplicateCount++;
					writeOut.println("Sequence is duplicated this many times: " + entry.getValue());
					writeOut.println(entry.getKey());
					writeOut.println();
				}
			}
     		writeOut.close();
		} catch(FileNotFoundException excpetion){
 			System.err.println("FileNotFoundException: " + excpetion.getMessage());
		} catch(UnsupportedEncodingException excpetion){
			System.err.println("UnsupportedEncodingException: " + excpetion.getMessage());
		}
	}
}