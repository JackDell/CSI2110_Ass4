package ca.uottawa.jackdell.pack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParisMetro {

	private static int numVerticies;
	private static int numEdges;
	
	private static Map<Integer, List<Integer>> metroRelations;
	private static Map<Integer, String> metroStations;
	
	public ParisMetro() {
		metroRelations = new HashMap<>();
		metroStations = new HashMap<>();
		readMetro("metro.txt");
	}

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
					
					if(metroRelations.containsKey(Integer.parseInt(info[0]))) {
						metroRelations.get(Integer.parseInt(info[0])).add(Integer.parseInt(info[1]));
					}
					else {
						List<Integer> valueList = new ArrayList<>();
						valueList.add(Integer.parseInt(info[1]));
						metroRelations.put(Integer.parseInt(info[0]), valueList);
					}
					
				}
				else {
					String[] info = line.split(" ", 2);
					metroStations.put(Integer.parseInt(info[0]), info[1]);
				}
				
			}
			reader.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
