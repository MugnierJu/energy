package energy.delivery.models;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Julien Mugnier - Baptiste Rambaud
 *
 */
public class EntryData {
	
	List<List<Double>> timeMatrix;
	List<List<Double>> distanceMatrix;
	List<Client> clientList;
	Vehicle vehicleStat;
	
	public EntryData(List<List<Double>> timeMatrix, List<List<Double>> distanceMatrix, List<Client> clientList,
			Vehicle vehicleStat) {
		super();
		this.timeMatrix = timeMatrix;
		this.distanceMatrix = distanceMatrix;
		this.clientList = clientList;
		this.vehicleStat = vehicleStat;
	}
	
	public EntryData(Vehicle vehicleStat) {
		this.timeMatrix = new ArrayList<List<Double>>();
		this.distanceMatrix = new ArrayList<List<Double>>();
		this.clientList = new ArrayList<Client>();
		this.vehicleStat = vehicleStat;
	}
	
	public EntryData() {}
	
	public List<List<Double>> getTimeMatrix() {
		return timeMatrix;
	}
	public void setTimeMatrix(List<List<Double>> timeMatrix) {
		this.timeMatrix = timeMatrix;
	}
	public List<List<Double>> getDistanceMatrix() {
		return distanceMatrix;
	}
	public void setDistanceMatrix(List<List<Double>> distanceMatrix) {
		this.distanceMatrix = distanceMatrix;
	}
	public List<Client> getClientList() {
		return clientList;
	}
	public void setClientList(List<Client> clientList) {
		this.clientList = clientList;
	}
	public Vehicle getVehicleStat() {
		return vehicleStat;
	}
	public void setVehicleStat(Vehicle vehicleStat) {
		this.vehicleStat = vehicleStat;
	}	
}
