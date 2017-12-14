//Programmer: Daniel Constantine
//Date: December 9, 2017
//Description: simulate how MARIE Simulator works by taking instruction in assembly language from a text and display the output

import java.io.*;
import java.util.*;

public class marieApp {
	
	public static HashMap<Integer, Integer> dataSet = new HashMap<>(); //hashmap for the data set
	public static LinkedHashMap executeInstruction = new LinkedHashMap(); //hashmap for storing and executing marie instruction
	public static Scanner reader = new Scanner(System.in); //for getting user input
	
	public static void main(String[] args) {
		int AC = 0;	//accumulator
        String fileName = "marie-instruction.txt"; // The name of the file to open.       
        String line = null; //this will reference one line at a time
        
        try {
            FileReader fileReader = new FileReader(fileName); //reads text files
            BufferedReader bufferedReader = new BufferedReader(fileReader);  //wrap FileReader in BufferedReader.
            while( ((line = bufferedReader.readLine()) != null)) { //reading the text files line by line until reach the end of the line
            	if(line.isEmpty()) //if encounter blank line, continue to next line
            		continue;
            	checkString(line); //process the line, store data into dataSet or store instruction into executeInstruction
            }             
            bufferedReader.close(); //close files.       
            executeMarie(AC); //execute the MARIE instruction stored in executeInstruction
            reader.close(); //close scanner
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");                
        }
	}
	
	//process the string in each line, determine if it's instruction or data declaration and store it into the hashmap or linkedhasmap
	public static void checkString(String line) {
    	String[] parts = line.split(" "); //split the string when it finds white space
    	int size = parts.length; //find the size of the array
    	if(size == 3) { // in case three variables, it is an instruction
    		marieSimulator(parts[1], parts[2]); //100 LOAD 102 --> store LOAD and 102 into the linkedhashmap
    	} else { // in case two variables only means data declaration
    		if(parts[1].toLowerCase().equals("halt")) //make exception when HALT occur
    			marieSimulator(parts[1], parts[0]); 
    		else if(parts[1].toLowerCase().equals("input")) //make exception when INPUT occur
    			marieSimulator(parts[1], parts[0]); 
    		else if(parts[1].toLowerCase().equals("output")) //make exception when OCCUR occur
    			marieSimulator(parts[1], parts[0]); 
    		else  //else means data
    			 insertData(parts[0], parts[1]);
    	}
	}
	
	public static void marieSimulator(String key, String value) {
		executeInstruction.put(key, Integer.valueOf(value)); //store data into the LinkedHashMap
	}
	
	public static void insertData(String key, String value) {
		int hexToDec = Integer.parseInt(value, 16); 	//convert the second value into decimal from hexadecimal
		dataSet.put(Integer.valueOf(key), hexToDec); 	//store data into the HashMap
	}


	//executing file in the LinkedHashMap
	public static void executeMarie(int AC) {
        Iterator it =  executeInstruction.entrySet().iterator();
        
		while(it.hasNext()) { //keep running until the last data in the linkedhashmap
			Map.Entry pair = (Map.Entry)it.next();
			if(pair.getKey().toString().toLowerCase().equals("load")) { //LOAD function
				int loadValue = dataSet.get(pair.getValue());
				System.out.println("Loading the data " + loadValue + " and storing it inside the AC\n");
				AC = loadValue;
			}
			if(pair.getKey().toString().toLowerCase().equals("add")) { //ADD function
				int addingValue = dataSet.get(pair.getValue());
				System.out.println("Adding the value in AC with " + addingValue + "\n");
				AC += addingValue;
			}
			if(pair.getKey().toString().toLowerCase().equals("subt")) { //SUBT function
				int subtValue = dataSet.get(pair.getValue());
				System.out.println("Subtracting the value in AC with " + subtValue + "\n");
				AC -= subtValue;
			}
			if(pair.getKey().toString().toLowerCase().equals("store")) { //STORE function
				System.out.println("Storing the value in AC into the address " + pair.getValue() + "\n");
				insertData(pair.getValue().toString(), String.valueOf(AC));
			}
			if(pair.getKey().toString().toLowerCase().equals("clear")) { //CLEAR function
				System.out.println("Clearing the accumulator. \n");
				AC = 0;
			}
			if(pair.getKey().toString().toLowerCase().equals("input")) { //INPUT function
				System.out.println("Please enter a decimal: ");
				int n = reader.nextInt();
				AC = n;
			}
			if(pair.getKey().toString().toLowerCase().equals("output")) { //OUTPUT function
				System.out.println("The value in AC is " + AC + "\n");
			}
			if(pair.getKey().toString().toLowerCase().equals("halt")) { //HALT function
				System.out.println("Stopping the program.");
				System.out.println("The value in AC is " + AC + " in decimal.\n");
				break;
			}
		}
		System.out.println("Thank you for using Marie Simulator");
	}
}
