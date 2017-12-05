package ca.uottawa.jackdell.pack;

public class Node {

	private int id;
	private String stationName;
	private int travelToTime;
	
	public Node(int id, String stationName, int travelToTime) {
		this.id = id;
		this.stationName = stationName;
		this.travelToTime = travelToTime;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getStationName() {
		return this.stationName;
	}
	
	public int getTravelToTime() {
		return this.travelToTime;
	}
}