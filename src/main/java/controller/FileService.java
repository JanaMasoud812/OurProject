package controller;


import java.io.*;
import java.util.*;


public class FileService {
	
	public List<String> readFile(String filename){
		List<String> lines = new ArrayList<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
				
			}
			reader.close();

			
		}
		
		
		
		catch(Exception e){
			
			System.out.println("Error reading file");
			
		
		}
		return lines;
	}
	

	
	public void writeFile(String filename, List<String> Lines) {
		
		try {
			BufferedWriter writer = new BufferedWriter (new FileWriter(filename));
			
			for (String line : Lines) {
				
				writer.write(line);
				writer.newLine();
			}
			
			writer.close();
			
			
			
			
		}
		
		catch(Exception e) {
			
			System.out.println("Error writing file");
			
			
		}
	}
		
	public void appendFile(String filename, String line) {
		
		try {
			BufferedWriter writer = new BufferedWriter (new FileWriter(filename, true));
            writer.write(line);
            writer.newLine();
            writer.close();
			
		}
		
		catch(Exception e) {
			
			System.out.println("Error appending file");
		}
		
		
	
		
		
	}	
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

