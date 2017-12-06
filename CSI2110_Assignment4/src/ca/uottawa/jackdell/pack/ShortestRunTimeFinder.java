package ca.uottawa.jackdell.pack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShortestRunTimeFinder {

	private final List<Vertex> vertexes;
	private final List<Edge> edges;

	private List<Vertex> settled;
	private List<Vertex> unSettled;
	
	private Map<Vertex, Vertex> predecessors;
	private Map<Vertex, Integer> distance;
	
	public ShortestRunTimeFinder(ParisMetro metro) {
		this.vertexes = new ArrayList<Vertex>(metro.getVertexes());
		this.edges = new ArrayList<Edge>(metro.getEdges());
	}
	
	public void init(Vertex source) {
		this.settled = new ArrayList<>();
		this.unSettled = new ArrayList<>();
		this.distance = new HashMap<Vertex, Integer>();
		this.predecessors = new HashMap<Vertex, Vertex>();
		
		this.distance.put(source, 0);
		unSettled.add(source);
		while(unSettled.size() != 0) {
			Vertex node = getMinimun(unSettled);
			settled.add(node);
			unSettled.remove(node);
			this.findMinDistances(node);
		}
		
	}
	
	private void findMinDistances(Vertex vertex) {
		List<Vertex> adjacentVertexes = this.getNeighbors(vertex);
		
		for(Vertex v : adjacentVertexes) {
			if(this.getShortestDistance(v) > this.getShortestDistance(vertex) + this.getDistance(vertex, v)) {
				this.distance.put(v, getShortestDistance(vertex) + getDistance(vertex, v));
				this.predecessors.put(v, vertex);
				unSettled.add(v);
			}
		}
	}
	
	private List<Vertex> getNeighbors(Vertex vertex) {
		List<Vertex> neighbors = new ArrayList<>();
		for(Edge e : this.edges) {
			if(e.getSource().equals(vertex) && !isSettled(e.getDestination())) {
				neighbors.add(e.getDestination());
			}
		}
		
		return neighbors;
	}

	private int getDistance(Vertex vertex, Vertex target) {
		for(Edge e : this.edges) {
			if(e.getSource().equals(vertex) && e.getDestination().equals(target)) {
				return e.getTravelTime();
			}
		}
		
		throw new RuntimeException();
	}
	
	private int getShortestDistance(Vertex destination) {
		if(distance.containsKey(destination)) {
			return distance.get(destination);
		}

		return Integer.MAX_VALUE;
	}
	
	private Vertex getMinimun(List<Vertex> vertexes) {
		Vertex min = null;
		for(Vertex v : vertexes) {
			if(min == null) {
				min = v;
			}
			else {
				if(this.getShortestDistance(v) < this.getShortestDistance(min)) {
					min = v;
				}
			}
		}
		
		return min;
	}
	
	private boolean isSettled(Vertex vertex) {
		return settled.contains(vertex);
	}
	
	public List<Vertex> getPath(Vertex target) {
        List<Vertex> path = new ArrayList<Vertex>();
        Vertex step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }
	
}
