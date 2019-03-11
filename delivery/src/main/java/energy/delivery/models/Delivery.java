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
	long totalDistance;
	long totalTime;
	List<Trajet> trajetList;	
}
