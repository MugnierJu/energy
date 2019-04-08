package energy.delivery.heuristic;

import java.util.List;

import energy.delivery.models.Client;
import energy.delivery.models.Delivery;
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
	
	public static Trajet getNewTraject(Client departureClient, Client arrivalClient, EntryData data) {
		Double distance = HeuristicUtils.getDistanceFromMatrixPosition(departureClient, arrivalClient, data);
		Double time = HeuristicUtils.getTimeFromMatricPosition(departureClient, arrivalClient, data);
		
		return new Trajet(distance, time, departureClient.getCoordinate(), arrivalClient.getCoordinate(),arrivalClient.getRequest());
	}
	
	
	/**
	 * Evaluation function for the heuristic
	 * f : distance totale (km) + durée totale (s) / 600 + (nb véhicule -1)*500 + nb_constrainte_dist * 50000
	 * @param deliveryList
	 * @param brokenConstraint
	 * @param data
	 * @return
	 */
	public static float evaluate(List<Delivery> deliveryList,float brokenConstraint, EntryData data) {
		float totalTime = 0;
		float totalDist = 0;
		for(Delivery delivery : deliveryList) {
			totalDist += delivery.getTotalDistance();
			totalTime += delivery.getTotalTime();
		}
		
		int nbVehicle = getVehicleNumber(deliveryList, data);
		
		return totalTime+(totalDist/600)+(nbVehicle-1)*500+brokenConstraint*50000;
	}
	
	
	private static int getVehicleNumber(List<Delivery> deliveryList, EntryData data) {
		int nbVehicle = 0;
		
		while(deliveryList.size()>0) {
			if(getAVehicle(deliveryList, data)) {
				nbVehicle++;
			}else {
				//TODO : ici on a des livraisons qui prennent plus de temps que ce qu'il est possible de faire en 1 journée par un véhicule
				nbVehicle += deliveryList.size();
				break;
			}
		}
		
		return nbVehicle;
	}
	
	/**
	 * 
	 * @param deliveryList
	 * @param data
	 * @return
	 */
	private static boolean getAVehicle(List<Delivery> deliveryList, EntryData data) {
		float time = 0;
		boolean newVehicle = false;
		float timeForAday = getTimeForADay(data);
		for(Delivery delivery : deliveryList) {
			if(delivery.getTotalTime()+time < timeForAday) {
				time += delivery.getTotalTime();
				deliveryList.remove(delivery);
				newVehicle = true;
			}
		}
		return newVehicle;
	}
	
	/**
	 * Return the work time for a vehicle
	 * @param data
	 * @return
	 */
	public static float getTimeForADay(EntryData data) {
		return data.getVehicleStat().getEnd_time() - data.getVehicleStat().getStart_time();
	}
	
	
	public static int getWarehouseIndex(EntryData data) throws Exception{
		for(int i = 0; i< data.getClientList().size(); i++) {
			if(data.getClientList().get(i).isWarhouse()) {
				return i;
			}
		}
		throw new Exception("No warehouse");
	}
}
