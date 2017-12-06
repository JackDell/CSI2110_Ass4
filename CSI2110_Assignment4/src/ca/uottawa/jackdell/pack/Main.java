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
		readMetro("/Users/Jack/School/Workspace/CSI2110/CSI2110_Ass4/CSI2110_Assignment4/src/ca/uottawa/jackdell/pack/metro.txt");
	
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
}
