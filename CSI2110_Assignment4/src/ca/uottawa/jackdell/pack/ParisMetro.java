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
		List<Integer> distance = new ArrayList<>();
		List<Boolean> visited = new ArrayList<>();
		Queue<Vertex> vertexQueue = new PriorityQueue<>();
		vertexQueue.add(getVertexById(sourceId));
		
		// Filling the distance list with max integer values to begin with
		for(int i = 0; i < numVertexes; i++) {
			distance.add(i, Integer.MAX_VALUE);
		}
		distance.set(sourceId, 0);
		
		
		while(visited.size() < numVertexes) {
			Vertex node = vertexQueue.remove();
			
			boolean firstLoop = true;
			Edge minEdge;
			
			// Finding the next station with the smallest travel time
			for(Edge e : metroConnections.get(node)) {
				if(firstLoop) {
					minEdge = e;
					firstLoop = false;
					return;
				}
				if(e.getTravelTime() < minEdge.getTravelTime()) {
					min = e;
				}
			}
			
			
		}
	}
	
	private static Vertex getVertexById(int id) {
		for(Vertex v : metroConnections.keySet()) {
			if(v.getId() == id) return v;
		}
		
		return null;
	}
}
