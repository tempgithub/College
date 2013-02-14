package dbBench2;

/*
 * Braden Licastro
 * 
 * CMPSC 380 Lab 3
 * Professor Kapfhammer
 *
 * Purpose: This program will take a list of phone numbers or names and
 * 			return those whose area code or name matches the argument provided.
 * 			Also takes an integer to set the number of runs to get average
 * 			run time for.
 *
 * Use: java B2_Procedural_LL file areaCodeOrName numRuns
 */

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class B2_Procedural_LL {

	private LinkedList<String> dBank;
	private ArrayList<String> matches;
	private Scanner file;

	public static void main(String[] args) {
		// Create the B1_Procedural object
		B2_Procedural_LL b2p = new B2_Procedural_LL();
		long compoundRuntime = 0;
		
		for(int i = 0; i < Integer.parseInt(args[2]); i++){
			
			// Record how long it takes to perform the procedures
			long startTime = System.nanoTime();
		
			// Move file contents to the ArrayList
			b2p.processFile(args[0]);
        
			// Select the correct matching algorithm
        	if(args[0].contains("Phone_Numbers")){
        		// Search for a matching string
        		b2p.parseDBank("^(" + args[1] + ")-\\d{3}-\\d{4}|(" + args[1] + ")$");
        	} else if(args[0].contains("Full_Names")){
        		// Search for a matching string
        		b2p.parseDBank("^(" + args[1] + ")[A-Za-z]*[.\\s][-'A-Za-z]+$");
        	} else {
        		System.out.println("Unable to parse data: Unknown collection.");
        	}

			// Calculate the elapsed time and print results.
			long elapsedTime = System.nanoTime() - startTime;
		
			compoundRuntime += elapsedTime/1000000;
			System.out.println("Lookup completed in " + elapsedTime/1000000 + "ms.");
		}
		
		// Print out the run average
		System.out.println("\nAverage run time: " + compoundRuntime/Integer.parseInt(args[2]) + "ms\n");
		
        // Print the search result 
		System.out.println("Results returned:");
        b2p.resultOut();
	}
	
	// Processes the file contents into a data structure
	public void processFile(String args){
		// Set up new file reader and ArrayList.
		try {
			file = new Scanner(new File(args));
		} catch (FileNotFoundException e) {
			System.out.println("The file path is incorrect.");
		}
		 	dBank = new LinkedList<String>();

		// Begin file parsing.
		try {
			while(file.hasNext()){
				dBank.add(file.nextLine());
			}
			
			// Stop parsing file
			file.close();
		} catch (NullPointerException e) {}		
	}

	// Takes a database query as input and parses the data.
	public ArrayList<String> parseDBank(String regex){
		// Create a Pattern object and temp store for matches
		Pattern p = Pattern.compile(regex);
		matches = new ArrayList<String>();

		// FIND MATCHES
		for(String current : dBank){
			if(p.matcher(current).matches()){
				matches.add(current);
			}
		}
		
		return matches;
	}
	
	// Prints the search results
	public void resultOut(){
		for(String data : matches){
				System.out.println(data);
		}
	}
}