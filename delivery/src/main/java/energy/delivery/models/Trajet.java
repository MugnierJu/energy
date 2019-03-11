package energy.delivery.models;

/**
 * 
 * @author Julien Mugnier - Baptiste Rambaud
 *
 * The distance and the time between two coordinates
 *
 */
public class Trajet {

	long distance;
	long time;
	Coordinate departureCoordinate;
	Coordinate arrivalCoordinate;
	
	public Trajet(long distance, long time, Coordinate departureCoordinate, Coordinate arrivalCoordinate) {
		super();
		this.distance = distance;
		this.time = time;
		this.departureCoordinate = departureCoordinate;
		this.arrivalCoordinate = arrivalCoordinate;
	}

	public long getDistance() {
		return distance;
	}

	public void setDistance(long distance) {
		this.distance = distance;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
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
}
