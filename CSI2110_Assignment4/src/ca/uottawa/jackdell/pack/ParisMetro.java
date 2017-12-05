package ca.uottawa.jackdell.pack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParisMetro {

	private static Map<Integer, ArrayList<Integer>> metroRelations;
	private static Map<Integer, String> metroStations;
	
	public ParisMetro() {
		metroRelations = new HashMap<>();
		metroStations = new HashMap<>();
	}
	
}
