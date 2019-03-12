package energy.delivery.models;

import java.util.List;

/**
 * 
 * @author Julien Mugnier - Baptiste Rambaud
 *
 * Represent a vehicle delivery for a day
 *
 */
public class Delivery {

	List<Coordinate> deliveryPoints;
	double totalDistance;
	double totalTime;
	List<Trajet> trajetList;	
}
