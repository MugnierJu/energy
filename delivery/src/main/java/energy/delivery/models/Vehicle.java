package energy.delivery.models;
/**
 * 
 * @author Julien Mugnier - Baptiste Rambaud
 *
 */
public class Vehicle {

	int max_dist;
	int capacity;
	int charge_fast;
	int charge_medium;
	int charge_slow;
	//Temps en seconde
	int start_time;
	int end_time;
	
	public Vehicle(int max_dist, int capacity, int charge_fast, int charge_medium, int charge_slow, int start_time,
			int end_time) {
		super();
		this.max_dist = max_dist;
		this.capacity = capacity;
		this.charge_fast = charge_fast;
		this.charge_medium = charge_medium;
		this.charge_slow = charge_slow;
		this.start_time = start_time;
		this.end_time = end_time;
	}
	
	public int getMax_dist() {
		return max_dist;
	}
	public void setMax_dist(int max_dist) {
		this.max_dist = max_dist;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getCharge_fast() {
		return charge_fast;
	}
	public void setCharge_fast(int charge_fast) {
		this.charge_fast = charge_fast;
	}
	public int getCharge_medium() {
		return charge_medium;
	}
	public void setCharge_medium(int charge_medium) {
		this.charge_medium = charge_medium;
	}
	public int getCharge_slow() {
		return charge_slow;
	}
	public void setCharge_slow(int charge_slow) {
		this.charge_slow = charge_slow;
	}
	public int getStart_time() {
		return start_time;
	}
	public void setStart_time(int start_time) {
		this.start_time = start_time;
	}
	public int getEnd_time() {
		return end_time;
	}
	public void setEnd_time(int end_time) {
		this.end_time = end_time;
	}
}
