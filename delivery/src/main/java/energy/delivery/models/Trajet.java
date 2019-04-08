package energy.delivery.models;

/**
 * 
 * @author Julien Mugnier - Baptiste Rambaud
 *
 * The distance and the time between two coordinates
 *
 */
public class Trajet {

	double distance;
	double time;
	int delivery;
	Coordinate departureCoordinate;
	Coordinate arrivalCoordinate;
	
	public Trajet(double distance, double time, Coordinate departureCoordinate, Coordinate arrivalCoordinate, int delivery) {
		this.delivery = delivery;
		this.distance = distance;
		this.time = time;
		this.departureCoordinate = departureCoordinate;
		this.arrivalCoordinate = arrivalCoordinate;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public Coordinate getDepartureCoordinate() {
		return departureCoordinate;
	}

	public void setDepartureCoordinate(Coordinate departureCoordinate) {
		this.departureCoordinate = departureCoordinate;
	}

	public Coordinate getArrivalCoordinate() {
		return arrivalCoordinate;
	}

	public void setArrivalCoordinate(Coordinate arrivalCoordinate) {
		this.arrivalCoordinate = arrivalCoordinate;
	}	
	
	public int getDelivery() {
		return this.delivery;
	}
}
