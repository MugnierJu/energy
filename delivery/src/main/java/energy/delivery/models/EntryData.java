package energy.delivery.models;

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
	Vehicule vehiculeStat;
	
	public EntryData(List<List<Double>> timeMatrix, List<List<Double>> distanceMatrix, List<Client> clientList,
			Vehicule vehiculeStat) {
		super();
		this.timeMatrix = timeMatrix;
		this.distanceMatrix = distanceMatrix;
		this.clientList = clientList;
		this.vehiculeStat = vehiculeStat;
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
	public Vehicule getVehiculeStat() {
		return vehiculeStat;
	}
	public void setVehiculeStat(Vehicule vehiculeStat) {
		this.vehiculeStat = vehiculeStat;
	}	
}
