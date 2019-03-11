package energy.delivery.models;

/**
 * 
 * @author Julien Mugnier - Baptiste Rambaud
 *
 */
public class Coordinate {
	
	long x;
	long y;
	
	public Coordinate(long x,long y) {
		this.x = x;
		this.y = y;
	}
	
	public long getX() {
		return x;
	}
	public void setX(long x) {
		this.x = x;
	}
	public long getY() {
		return y;
	}
	public void setY(long y) {
		this.y = y;
	}
	
	
}
