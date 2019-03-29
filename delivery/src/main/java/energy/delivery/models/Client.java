package energy.delivery.models;

/**
 * 
 * @author Julien Mugnier - Baptiste Rambaud
 * 
 * Class used in order to create a client or a wareHouse (Considering warehouse is just a special type of client)
 *
 */
public class Client {

	Coordinate coordinate;
	int request;
	int matrixIndex;
	boolean isWarehouse;
	
	/**
	 * Used to create a "real" client
	 * @param coordinate
	 * @param request
	 * @param matrixIndex
	 */
	public Client(Coordinate coordinate,int request, int matrixIndex) {
		this.coordinate = coordinate;
		this.request = request;
		this.matrixIndex = matrixIndex;
		isWarehouse = false;
	}
	
	/**
	 * Used to create a warehouse
	 * @param coordinate
	 * @param isWarehouse
	 * @param matrixIndex
	 */
	public Client(Coordinate coordinate, int matrixIndex) {
		this.coordinate = coordinate;
		this.matrixIndex = matrixIndex;
		isWarehouse = true;
		request = -1;
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
	public boolean isWarhouse() {
		return isWarehouse;
	}
}
