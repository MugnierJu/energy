package energy.delivery.heuristic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import energy.delivery.comparator.ClientRequestComparator;
import energy.delivery.models.Client;
import energy.delivery.models.Delivery;
import energy.delivery.models.EntryData;
import energy.delivery.service.PropertiesService;

/**
 * 
 * @author Julien Mugnier - Baptiste Rambaud
 * 
 *
 */
public class XVariationHeuristicFirstBest implements HeuristicInterface {
	
	List<Client> warehouses;
	private static Logger logger = Logger.getLogger(PropertiesService.class);
	EntryData entryData;

	public XVariationHeuristicFirstBest() {
		warehouses = new ArrayList<Client>();
	}

	public List<Delivery> process(EntryData data) throws Exception {
		data.getClientList().sort(new ClientRequestComparator());

		try {
			checkConstraints(data);

			return buildHeuristic(data);
		
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

	public List<Delivery> buildHeuristic(EntryData data){
		List<Delivery> deliveryList = new ArrayList<Delivery>();
		
		//Obtenir une solution 0 correcte
		try {
			deliveryList = randomiseStartingSolutions(data);
			data = entryData;
		} catch (Exception e) {
			logger.warn("Erreur lors de la randomisation des données");
			e.printStackTrace();
		}
		
		try {
			//On trouve un premier voisinage;
			List<List<Delivery>> neighbours = getExchangeSmallestXNeighbours(deliveryList, data);
			
			//On trouve le meilleur voisin
			int index = getBestNeighbour(neighbours, data);
			float bestScore = HeuristicUtils.evaluate(neighbours.get(index), 0, data);	
			
			//On itère jusqu'a ce que le score n'évolue plus
			float newScore = -1;
			int newIndex = -1;
			List<List<Delivery>> newNeighbours = new ArrayList<List<Delivery>>();
			boolean isFinished = false;
			
			while(!isFinished){
				
				boolean hasUpdated = false;
				int listIndex = 0;
				for(List<Delivery> neighbour : neighbours) {
					float score = HeuristicUtils.evaluate( new ArrayList<Delivery>(neighbour), 0,data);
					if(score < bestScore) {
						index = listIndex;
						bestScore = score;
						hasUpdated = true;
						break;
					}
					listIndex++;
				}
				if(!hasUpdated){
					isFinished = true;
				}
			}
			
			deliveryList = neighbours.get(index);
			
		} catch (Exception e) {
			logger.warn("Erreur lors du calcul du voisinage");
			e.printStackTrace();
		}
		
		return deliveryList;
	}	
	
/**------------------------- EVALUATION DES SOLUTIONS -----------------------------**/

private int getBestNeighbour(List<List<Delivery>> neighbours,EntryData data) {	
	int retainedIndex = -1;
	float bestscore =  0;
	int index = 0;
	boolean first = true;
		
	for(List<Delivery> neighbour : neighbours) {
		float score = HeuristicUtils.evaluate( new ArrayList<Delivery>(neighbour), 0,data);
		if(first) {
			bestscore = score;
			retainedIndex = new Integer(index);
			first = false;
		}
		else if(bestscore > score || retainedIndex == -1) {
			bestscore = score;
			retainedIndex = new Integer(index);
		}
		index++;
	}
	return retainedIndex;
}
	
	
/**------------------------- VOISINAGE -----------------------------**/

	
	/**
	 * On construit un voisinage tel que un voisinage = S0 avec pour chaque élément 
	 * Cette fonction de construit que des voisinages respectant les contraintes
	 * @param deliveryList
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public List<List<Delivery>> getExchangeSmallestXNeighbours(List<Delivery> deliveryList,EntryData data) throws Exception{
		List<Client> clientList = HeuristicUtils.getClientListsFromDeliveryList(deliveryList);
		List<Client> newClientList = new ArrayList<Client>();
		List<List<Delivery>> neighbours = new ArrayList<List<Delivery>>();
		List<Integer> alreadySwappedClient = new ArrayList<Integer>();
		
		for(int i = 0;i < clientList.size();i++) {
			newClientList = new ArrayList<Client>(clientList);
			//On swap les clients 2 par 2
			
			int clientIndex = -1;
			double smallestDistance = -1;
			Boolean asChanged = false;
			for(int j = 0;j < clientList.size();j++) {
				if(!alreadySwappedClient.contains(j)) {
					double dist = data.getDistanceMatrix().get(i).get(j);
					if(smallestDistance == -1) {
						smallestDistance = data.getDistanceMatrix().get(i).get(j);
						clientIndex = j;
						asChanged = true;
					}
					if(dist < smallestDistance && smallestDistance != 0) {
						smallestDistance = dist;
						clientIndex = j;
						asChanged = true;
					}
				}
			}
			if(asChanged && i+1 < clientList.size()) {
				Client tmpClient = clientList.get(i+1);
				newClientList.set(i+1, clientList.get(clientIndex));
				newClientList.set(clientIndex, tmpClient);
				alreadySwappedClient.add(clientIndex);
				
				neighbours.add(buildDeliveryList(new ArrayList<Client>(newClientList),data));
			}
		}
		
		return neighbours;
	}
	
	private List<Delivery> buildDeliveryList(List<Client> newClientList,EntryData data) throws Exception {
		
		//Already passed clients
		List<Client> usedData = new ArrayList<Client>();
		List<Delivery> deliveryList = new ArrayList<Delivery>();
		
		int warehouseIndex = HeuristicUtils.getWarehouseIndex(data);
		
		while(usedData.size() != newClientList.size()) {
			
			boolean isAlgorithmWorking = false;;
			
			Client previousClient = data.getClientList().get(warehouseIndex);
			Delivery delivery = new Delivery();
			for(Client client : newClientList) {
				if(areConstraintsRespected(previousClient,client,warehouseIndex,delivery,data) && !usedData.contains(client)) {
					delivery.addTrajet(HeuristicUtils.getNewTraject(previousClient, client, data));
					usedData.add(client);
					previousClient = client;
					isAlgorithmWorking = true;
				}
			}
			delivery.addTrajet(HeuristicUtils.getNewTraject(previousClient, data.getClientList().get(warehouseIndex), data));
			deliveryList.add(delivery);
			if(!isAlgorithmWorking) {
				throw new Exception("Unable to process the algorithm");
			}
		}
		return deliveryList;
	}
	
/**------------------------- CONTRAINTE -----------------------------**/
	
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
	
/**------------------------- RANDOMISATION DE DEPART -----------------------------**/
	
	/**
	 * return a correct random Delivery set
	 * @param data
	 * @throws Exception 
	 */
	private List<Delivery> randomiseStartingSolutions(EntryData data) throws Exception {
		data = randomiseIndex(data);
		//Already passed clients
		List<Client> usedData = new ArrayList<Client>();
		List<Delivery> deliveryList = new ArrayList<Delivery>();
		
		int warehouseIndex = HeuristicUtils.getWarehouseIndex(data);
		
		while(usedData.size()+1 != data.getClientList().size()) {
			
			boolean isAlgorithmWorking = false;;
			
			Client previousClient = data.getClientList().get(warehouseIndex);
			Delivery delivery = new Delivery();
			for(Client client : data.getClientList()) {
				if(areConstraintsRespected(previousClient,client,warehouseIndex,delivery,data) && !usedData.contains(client)) {
					delivery.addTrajet(HeuristicUtils.getNewTraject(previousClient, client, data));
					usedData.add(client);
					previousClient = client;
					isAlgorithmWorking = true;
				}
			}
			delivery.addTrajet(HeuristicUtils.getNewTraject(previousClient, data.getClientList().get(warehouseIndex), data));
			deliveryList.add(delivery);
			if(!isAlgorithmWorking) {
				throw new Exception("Unable to process the algorithm");
			}
		}
		return deliveryList;
	}
	
	private EntryData randomiseIndex(EntryData data) {
		EntryData randomisedData = new EntryData(data.getVehicleStat());
		
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
		entryData = randomisedData;
		return randomisedData;
	}
}
