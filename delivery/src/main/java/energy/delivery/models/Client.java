package energy.delivery.models;

/**
 * 
 * @author Julien Mugnier - Baptiste Rambaud
 *
 */
public class Client {

	Coordinate coordinate;
	int request;
	
	public Client(Coordinate coordinate,int request) {
		this.coordinate = coordinate;
		this.request = request;
	}
	
	public Coordinate getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	public int getRequest() {
		return request;
	}
	public void setRequest(int request) {
		this.request = request;
	}
	
	
	
}
