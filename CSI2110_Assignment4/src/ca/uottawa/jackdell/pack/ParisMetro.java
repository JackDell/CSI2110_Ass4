package ca.uottawa.jackdell.pack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class ParisMetro {

	private static int numVertexes;
	private static int numEdges;
	private static Map<Vertex, List<Edge>> metroConnections;
	
	public ParisMetro() {
		metroConnections = new HashMap<>();
		readMap("metro.txt");
	}
	
	public static int getNumVertexes() {
		return numVertexes;
	}
	
	public static int getNumEdges() {
		return numEdges;
	}
	
	private static void readMap(String fileName) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(fileName));
			// Will only be true if it is the first line of the file we are reading
			boolean firstLine = true;
			// Will become true when the '$' is detected in the file we are reading from
			boolean dataSwitch = false;
			
			String line;
			while((line = reader.readLine()) != null) {
				if(firstLine) {
					String[] info = line.split(" ");
					numVertexes = Integer.parseInt(info[0]);
					numEdges = Integer.parseInt(info[1]);
					firstLine = false;
					continue;
				}
				
				// Checking if the line is the '$' line, indicating the following data is a different format
				if(line.contains("$")) {
					dataSwitch = true;
					continue;
				}
				
				// If the line is before the dataSwitch
				if(!dataSwitch) {
					// Splitting on only the first space
					String[] info = line.split(" ", 2);
					metroConnections.put(new Vertex(info[1], Integer.parseInt(info[0])), new ArrayList<>());
				}
				// If the line is after the dataSwitch
				else {
					String info[] = line.split("");
					int sourceId = Integer.parseInt(info[0]);
					int destinationId = Integer.parseInt(info[1]);
					int travelTime = Integer.parseInt(info[2]);
					
					Object[] keys = metroConnections.keySet().toArray();
					metroConnections.get(sourceId).add(new Edge((Vertex) keys[sourceId], (Vertex) keys[destinationId], travelTime));
				}
			}
			reader.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Integer> findShortestLine(int sourceId, int destinationId) {
		
		Map<Integer, Integer> infoMap = new HashMap<>();
		
		Queue<Vertex> vertexesToVisit = new PriorityQueue<>();
		Object[] keys = metroConnections.keySet().toArray();
		vertexesToVisit.add((Vertex) keys[sourceId]);
		
		while(!vertexesToVisit.isEmpty()) {
			Vertex source = vertexesToVisit.remove();
			
			for(Edge e : metroConnections.get(source)) {
				vertexesToVisit.add(e.getDestination());
			}
		}
		
		return null;
	}
}
