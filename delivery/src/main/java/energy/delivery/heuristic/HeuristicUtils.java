package energy.delivery.heuristic;

import energy.delivery.models.Client;
import energy.delivery.models.EntryData;
import energy.delivery.models.Trajet;

/**
 * 
 * @author Julien Mugnier - Baptiste Rambaud
 *
 */
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
	
	public static Trajet getNewTraject(Client departureClient, Client ArrivalClient, EntryData data) {
		Double distance = HeuristicUtils.getDistanceFromMatrixPosition(departureClient, ArrivalClient, data);
		Double time = HeuristicUtils.getTimeFromMatricPosition(departureClient, ArrivalClient, data);
		
		return new Trajet(distance, time, departureClient.getCoordinate(), ArrivalClient.getCoordinate());
	}
}
