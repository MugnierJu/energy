package energy.delivery.heuristic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.rmi.CORBA.Util;

import org.apache.log4j.Logger;

import energy.delivery.comparator.ClientRequestComparator;
import energy.delivery.models.Client;
import energy.delivery.models.Delivery;
import energy.delivery.models.EntryData;
import energy.delivery.service.PropertiesService;

public class SteepestDescentHeuristic implements HeuristicInterface {
	
	List<Client> warehouses;
	private static Logger logger = Logger.getLogger(PropertiesService.class);

	public SteepestDescentHeuristic() {
		warehouses = new ArrayList<Client>();
	}

	public List<Delivery> process(EntryData data) throws Exception {
		data.getClientList().sort(new ClientRequestComparator());

		try {
			checkConstraints(data);

			List<Delivery> deliveryList = new ArrayList<Delivery>();
			
			buildHeuristic(deliveryList, data);
			
			return deliveryList;

		
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return null;
	}

	public void checkConstraints(EntryData data) throws Exception {
		// the max size of a delivery should be inferior to the capacity of the vehicle
		if(data.getClientList().get(data.getClientList().size()-1).getRequest() > data.getVehicleStat().getCapacity()) {
			throw new Exception("Request size too hight");
		}
		//Check if there is only one warehouse
		int warHouseNb = 0;
		for(Client client : data.getClientList()) {
			if(client.isWarhouse()) {
				warHouseNb++;
			}
			if(warHouseNb > 1) {
				throw new Exception("More than on warehouse");
			}
		}		
	}

	public void buildHeuristic(List<Delivery> deliveryList, EntryData data){
		
		//Obtenir une solution 0 correcte
		try {
			randomiseStartingSolutions(data);
		} catch (Exception e) {
			logger.warn("Erreur lors de la randomisation des données");
			e.printStackTrace();
		}
		
		//
		 
		
	}
	
	
	
	
	
	
	
	
	

	/**
	 * return a correct random Delivery set
	 * @param data
	 * @throws Exception 
	 */
	private List<Delivery> randomiseStartingSolutions(EntryData data) throws Exception {
		randomiseIndex(data);
		//Already passed clients
		List<Client> usedData = new ArrayList<Client>();
		List<Delivery> deliveryList = new ArrayList<Delivery>();
		
		int warehouseIndex = HeuristicUtils.getWarehouseIndex(data);
		
		while(usedData.size()+1 != data.getClientList().size()) {
			Client previousClient = data.getClientList().get(warehouseIndex);
			Delivery delivery = new Delivery();
			for(Client client : data.getClientList()) {
				if(areConstraintsRespected(previousClient,client,warehouseIndex,delivery,data) && usedData.contains(client)) {
					delivery.addTrajet(HeuristicUtils.getNewTraject(previousClient, client, data));
					usedData.add(client);
					previousClient = client;
				}
			}
			delivery.addTrajet(HeuristicUtils.getNewTraject(previousClient, data.getClientList().get(warehouseIndex), data));
			deliveryList.add(delivery);
		}
		return deliveryList;
	}
	
	
	public boolean areConstraintsRespected(Client client1, Client client2, int warehouseIndex, Delivery delivery, EntryData data) {
		//Conditions de refus de la contrainte :
		//Si la destination est un entrepot
		if(client2.isWarhouse()) {
			return false;
		}
		//Si la capacité du vehicule est plus faible que le total des commandes
		if(client2.getRequest()+delivery.getTotalDelivery() > data.getVehicleStat().getCapacity()) {
			return false;
		}
		// Si le vehicule ne peut pas parcourir autant de distance
		Double newDist = HeuristicUtils.getDistanceFromMatrixPosition(client1, client2, data);
		Double distToWarehouse = HeuristicUtils.getDistanceFromMatrixPosition(client2, data.getClientList().get(warehouseIndex), data);
		if(newDist+distToWarehouse+delivery.getTotalDistance() > data.getVehicleStat().getMax_dist()) {
			return false;
		}
		
		return true;
	}
	
	private void randomiseIndex(EntryData data) {
		EntryData randomisedData = new EntryData();
		
		while(data.getClientList().size() > 0) {
			Random r = new Random();
			int index = r.nextInt((data.getClientList().size()));
			
			randomisedData.getClientList().add(data.getClientList().get(index));
			randomisedData.getDistanceMatrix().add(data.getDistanceMatrix().get(index));
			randomisedData.getTimeMatrix().add(data.getTimeMatrix().get(index));
			
			data.getClientList().remove(index);
			data.getDistanceMatrix().remove(index);
			data.getTimeMatrix().remove(index);
		}
		data = randomisedData;
	}
}
