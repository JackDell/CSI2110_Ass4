package ca.uottawa.jackdell.pack;

public class Vertex {

	private String stationName;
	private int id;
	
	public Vertex(String stationName, int id) {
		this.stationName = stationName;
		this.id = id;
	}
	
	public String getStationName() {
		return this.stationName;
	}
	
	public int getId() {
		return this.id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(obj instanceof Vertex) {
			Vertex v = (Vertex) obj;
			if(v.getId() == this.id) {
				return true;
			}
		}
		return false;
	}
}
