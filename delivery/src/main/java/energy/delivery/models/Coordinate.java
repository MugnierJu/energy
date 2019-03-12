package energy.delivery.models;

/**
 * 
 * @author Julien Mugnier - Baptiste Rambaud
 *
 */
public class Coordinate {
	
	double x;
	double y;
	
	public Coordinate(double x,double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	
}
