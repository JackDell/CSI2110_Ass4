package ca.uottawa.jackdell.pack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {

	private static int numVerticies;
	private static int numEdges;
	
	private static final Map<Integer, String> stations = new HashMap<>();
	
	// The main function to be called
	public static void main(String[] args) {
		
	}
	
	// Methods
	public static void readMetro(String fileName) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(fileName));
			String line = reader.readLine();
			boolean firstLine = true;
			boolean dataSwitch = false;
			while(line != null) {
				// Testing if it is reading the first line, i == 0
				if(firstLine) {
					String[] info = line.split(" ");
					numVerticies = Integer.parseInt(info[0]);
					numEdges = Integer.parseInt(info[1]);
					firstLine = false;
					continue;
				}
				else if(line.equals("$")) {
					dataSwitch = true;
					continue;
				}
				
				if(dataSwitch) {
					String[] info = line.split(" ");
				}
				else {
					String[] info = line.split(" ", 2);
					stations.put(Integer.parseInt(info[0]), info[1]);
				}
				
			}
			reader.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
