package energy.delivery.models;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Julien Mugnier - Baptiste Rambaud
 *
 * Represent a vehicle delivery for a day
 *
 */
public class Delivery {

	double totalDistance;
	double totalTime;
	int totalDelivery;
	List<Trajet> trajetList;
	List<String> tour;
	
	public Delivery() {
		trajetList = new ArrayList<Trajet>();
		totalDistance = 0;
		totalTime = 0;
		totalDelivery= 0;
	}

	public double getTotalDistance() {
		return totalDistance;
	}
	public void setTotalDistance(double totalDistance) {
		this.totalDistance = totalDistance;
	}
	public double getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(double totalTime) {
		this.totalTime = totalTime;
	}
	public List<Trajet> getTrajetList() {
		return trajetList;
	}
	public void setTrajetList(List<Trajet> trajetList) {
		this.trajetList = trajetList;
	}
	public int getTotalDelivery() {
		return this.totalDelivery;
	}
	
	public void addTrajet(Trajet trajet) {
		this.trajetList.add(trajet);
		this.totalDistance += trajet.distance;
		this.totalTime += trajet.time;
		
		//Ajout des contraintes
		double deliveryTime = 0;
		if(!trajet.getDepartureClient().isWarehouse) {
			deliveryTime = (5*60.0)+(10*trajet.getDepartureClient().getRequest());
		}
		this.totalTime += deliveryTime;
		
		this.totalDelivery += trajet.getDelivery();
	}
	
	/**
	 * Utiliser cette méthode uniquement lorsque le delivery est terminer
	 */
	public void generateTour() {
		tour = new ArrayList<String>();
		
		for(Trajet trajet : trajetList) {
			if(trajet.getDepartureClient().isWarehouse) {
				tour.add("R");
			}else {
				tour.add(String.valueOf(trajet.getDepartureClient().getMatrixIndex()));
			}
		}
		tour.add("R");
	}
}
