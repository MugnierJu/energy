package energy.delivery.heuristic;

import energy.delivery.models.Client;
import energy.delivery.models.EntryData;

public class HeuristicUtils {

	private HeuristicUtils() {}
	
	public static Double getDistanceFromMatrixPosition(Client clientOne, Client clientTwo, EntryData data) {
		
		Double distance = data.getDistanceMatrix().get(clientOne.getMatrixIndex()).get(clientTwo.getMatrixIndex()); 
		return Math.abs(distance);
	}
	
	public static Double getTimeFromMatricPosition(Client clientOne, Client clientTwo, EntryData data) {
		
		Double time  = data.getTimeMatrix().get(clientOne.getMatrixIndex()).get(clientTwo.getMatrixIndex()); 
		
		return time;
	}
	
}
