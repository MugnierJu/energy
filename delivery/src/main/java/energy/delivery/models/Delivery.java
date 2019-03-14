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
	List<Trajet> trajetList;
	
	public Delivery() {
		trajetList = new ArrayList<Trajet>();
		totalDistance = 0;
		totalTime = 0;
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
	
	public void addTrajet(Trajet trajet) {
		this.trajetList.add(trajet);
		this.totalDistance += trajet.distance;
		this.totalTime += trajet.time;		
	}
}
