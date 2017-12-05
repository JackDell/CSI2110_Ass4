package ca.uottawa.jackdell.pack;

public class Edge {
	
	private Vertex source;
	private Vertex destination;
	private int travelTime;
	
	public Edge(Vertex source, Vertex destination, int travelTime) {
		this.source = source;
		this.destination = destination;
		this.travelTime = travelTime;
	}
	
	public Vertex getSource() {
		return source;
	}
	
	public Vertex getDestination() {
		return destination;
	}
	
	public int getTravelTime() {
		return travelTime;
	}
	
}
