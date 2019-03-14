package energy.delivery.models;

/**
 * 
 * @author Julien Mugnier - Baptiste Rambaud
 *
 */
public class Client {

	Coordinate coordinate;
	int request;
	int matrixIndex;
	
	public Client(Coordinate coordinate,int request, int matrixIndex) {
		this.coordinate = coordinate;
		this.request = request;
		this.matrixIndex = matrixIndex;
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
	public int getMatrixIndex() {
		return matrixIndex;
	}
	public void setMatrixIndex(int matrixIndex) {
		this.matrixIndex = matrixIndex;
	}	
}
