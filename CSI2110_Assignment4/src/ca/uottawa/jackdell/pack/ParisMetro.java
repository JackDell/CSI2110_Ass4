package ca.uottawa.jackdell.pack;

import java.util.ArrayList;
import java.util.List;

public class ParisMetro {

	private final List<Vertex> vertexes;
	private final List<Edge> edges;
	
	public ParisMetro() {
		this.vertexes = new ArrayList<>();
		this.edges = new ArrayList<>();
	}
	
	public List<Vertex> getVertexes() {
		return this.vertexes;
	}
	
	public List<Edge> getEdges() {
		return this.edges;
	}
}
