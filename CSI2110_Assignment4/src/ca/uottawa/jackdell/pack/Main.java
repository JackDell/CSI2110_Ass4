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
		
		if(args.length == 1) {
			sameLine(getVertexById(Integer.parseInt(args[0])));
		}
		else if(args.length == 2) {
			shortestPath(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		}
		else if(args.length == 3) {
			System.out.println("Could not figure out how to implement, sorry :(");
		}
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
	
	private static List<Edge> getEdges(Vertex source) {
		List<Edge> returnList = new ArrayList<>();
		
		for(Edge e : edges) {
			if(e.getSource().equals(source)) returnList.add(e);
		}
		
		return returnList;
	}
	
	public static void sameLine (Vertex currStation){
    	System.out.print(currStation.getId() + " ");
    	sameLine(currStation, currStation, currStation.getId() + ", ");
    }
    private static String sameLine(Vertex currStation, Vertex prevStation, String list){
    	
    	ArrayList<Edge> currentEdges = (ArrayList<Edge>) getEdges(currStation);
    	
		if (currentEdges.size() == 1 && !(prevStation.equals(currStation))) return list;
		
		for(int i=0; i < currentEdges.size(); i++){
			if(!(currentEdges.get(i).getTravelTime() == -1 || currentEdges.get(i).getDestination().equals(prevStation))){
				list += currentEdges.get(i).getDestination().getStationName() + ", ";
				System.out.print(currentEdges.get(i).getDestination().getId() + " ");
				sameLine(currentEdges.get(i).getDestination(), currStation, list);
			}
		}
		return "";
	}
    
    public static void shortestPath(int sourceId, int destinationId) {
    	Vertex source = getVertexById(sourceId);
    	Vertex destination = getVertexById(destinationId);
    	
    	ParisMetro metro = new ParisMetro(vertexes, edges);
		ShortestRunTimeFinder srtf = new ShortestRunTimeFinder(metro);
		srtf.init(source);
		// Getting the shortest path, but this path will contain duplicate stations
		List<Vertex> path = srtf.getPath(destination);
		List<Edge> pathInEdges = new ArrayList<>();
		
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
			pathInEdges.add(getEdge(path.get(i - 1).getId(), path.get(i).getId()));
		}
		
		int i = 1;
		int totalTravelTime = 0;
		for(Edge e : pathInEdges) {
			int travelTime = e.getTravelTime();
			if(travelTime == -1) travelTime = 90;
			
			System.out.println(i++ + ". (" + e.getSource().getId() + ": " + e.getSource().getStationName() + 
					", " + e.getDestination().getId() + ": " + e.getDestination().getStationName() + 
					", Travel Time: " + travelTime + ")");
			totalTravelTime += travelTime;
		}
		System.out.println("Total travel time from " + source.getStationName() + " to " + destination.getStationName() + " is: " + totalTravelTime);
    }
}
