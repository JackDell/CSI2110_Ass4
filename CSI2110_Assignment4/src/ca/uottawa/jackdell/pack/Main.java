package ca.uottawa.jackdell.pack;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	private static int numVerticies;
	private static int numEdges;

	private static List<Vertex> vertexes;
	private static List<Edge> edges;

	private static final Map<Integer, String> stations = new HashMap<>();

	// The main function to be called
	public static void main(String[] args) {
		vertexes = new ArrayList<>();
		edges = new ArrayList<>();
		readMetro("/Users/Jack/workspace/CSI2110_Ass4/CSI2110_Assignment4/src/ca/uottawa/jackdell/pack/metro.txt");
		metroTest();
	}

	// Methods
	public static void readMetro(String fileName) {
		List<String> lines = new ArrayList<>();
		// Saving every line in the file as a string in the lines array
		try {
			lines = Files.readAllLines(java.nio.file.Paths.get(fileName), StandardCharsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean firstLine = true;
		boolean dataSwitch = false;
		
		for(String line : lines) {
			if(firstLine) {
				String[] info = line.split(" ", 2);
				// Removing invisible character from info[0] to fix parsing error
				info[0] = info[0].substring(1, 4);
				
				numVerticies = Integer.parseInt(info[0]);
				numEdges = Integer.parseInt(info[1]);
				firstLine = false;
				continue;
			}
			
			// Testing to see if the current line is the dataSwitch line
			if(line.contains("$")) {
				dataSwitch = true;
				continue;
			}
			
			if(!dataSwitch) {
				String[] info = line.split(" ", 2);
				vertexes.add(new Vertex(info[1], Integer.parseInt(info[0])));
			}
			else {
				String[] info = line.split(" ");
				int sourceId = Integer.parseInt(info[0]);
				int destinationId = Integer.parseInt(info[1]);
				int travelTime = Integer.parseInt(info[2]);
				
				edges.add(new Edge(getVertexById(sourceId), getVertexById(destinationId), travelTime));
			}
		}
	}
	
	private static Vertex getVertexById(int id) {
		for(Vertex v : vertexes) {
			if(v.getId() == id) return v;
		}
		return null;
	}
	
	/**
	 * Function to test the functionallity of the ParisMetro class and ShortestRouteFinder class
	 */
	public static void metroTest() {
		ParisMetro metro = new ParisMetro(vertexes, edges);
		ShortestRunTimeFinder srtf = new ShortestRunTimeFinder(metro);
		srtf.init(vertexes.get(0));
		// Getting the shortest path, but this path will contain duplicate stations
		List<Vertex> path = srtf.getPath(vertexes.get(30));
		
		// Creating a list to hold all the non-duplicate stations
		List<Vertex> pathWithoutDuplicates = new ArrayList<>();
		List<Edge> pathInEdges = new ArrayList<>();
		// Adding the starting element to the list, it will never qualify as a duplicate in the following loop
		pathWithoutDuplicates.add(vertexes.get(0));
		
		for(int i = 0; i < path.size(); i++) {
			// First item is already added, continue
			if(i == 0) {
				continue;
			}
			
			// If the station name is the same as the one before it, do not add it to the list
			if(path.get(i).getStationName().equals(path.get(i - 1).getStationName())) {
				continue;
			}
			
			// Adding the non-duplicate station to the path
			pathWithoutDuplicates.add(path.get(i));
			pathInEdges.add(getEdge(path.get(i - 1).getId(), path.get(i).getId()));
		}
		
		// Printing path to console
		for(Vertex v : pathWithoutDuplicates) {
			System.out.println(v.getId() + ", " + v.getStationName());
		}
		
		int i = 1;
		int totalTravelTime = 0;
		for(Edge e : pathInEdges) {
			System.out.println(i++ + ". (" + e.getSource().getId() + ": " + e.getSource().getStationName() + 
					", " + e.getDestination().getId() + ": " + e.getDestination().getStationName() + 
					", Travel Time: " + e.getTravelTime() + ")");
			totalTravelTime += e.getTravelTime();
		}
		System.out.println("Total travel time from " + vertexes.get(0).getStationName() + " to " + vertexes.get(30).getStationName() + " is: " + totalTravelTime);
	}
	
	private static Edge getEdge(int sourceId, int destinationId) {
		Vertex source = getVertexById(sourceId);
		Vertex destination = getVertexById(destinationId);
		
		for(Edge e : edges) {
			if(e.getSource().equals(source) && e.getDestination().equals(destination)) {
				return e;
			}
		}
		
		return null;
	}
}
