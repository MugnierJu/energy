package energy.delivery.heuristic;

import java.util.ArrayList;
import java.util.List;

import energy.delivery.comparator.ClientRequestComparator;
import energy.delivery.models.Client;
import energy.delivery.models.Delivery;
import energy.delivery.models.EntryData;

/**
 * Simple Heuristic with only the size of the filling rate of the vehicles taking into account
 * @author Julien Mugnier - Baptiste Rambaud
 *
 */
public class BasicHeuristic implements HeuristicInterface {
	
	List<Client> warehouses;
	
	public BasicHeuristic() {
		warehouses = new ArrayList<Client>();
	}
	
	public List<Delivery> process(EntryData data) {
		
		data.getClientList().sort(new ClientRequestComparator());

		try {
			checkConstraints(data);
			
			//TODO à enlever voir buildHeuristic()
			warehouses.add(data.getClientList().get(0));
			data.getClientList().remove(0);

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

	public void buildHeuristic(List<Delivery> deliveryList,EntryData data) {
		
		//TODO: Completer ça quand l'import sera complet
		//On ne prend pas encore en compte dans l'import le fait que certains points sont des entrepots.
		//Pour l'instant, on triche en connaissant la place de l'entrepot dans la liste des données fournies telles qu'on les a triées
		//A faire : avoir un entrepot dynamique.
		
		//We start from the warhouse
		Delivery delivery = new Delivery();
		int deliverySize = 0;
		Client previousClient = warehouses.get(0);

		//Then we get all of the clients
		EntryData workingData = data;
		for(int i = data.getClientList().size()-1; i >= 0; i--) {
			int newSize = deliverySize + data.getClientList().get(i).getRequest();
			if(newSize <= data.getVehicleStat().getCapacity()) {
				deliverySize = newSize;
				delivery.addTrajet(HeuristicUtils.getNewTraject(previousClient, data.getClientList().get(i), data));
				previousClient = data.getClientList().get(i);
				workingData.getClientList().remove(i);
			}
			if(newSize == data.getVehicleStat().getCapacity() || i == 0) {
				deliveryList.add(delivery);
				break;
			}
		}
		
		//And we go back to the warehouse
		delivery.addTrajet(HeuristicUtils.getNewTraject(previousClient, warehouses.get(0), data));
		//If their is more client to deliver, we do another delivery
		if(data.getClientList().size() > 0) {
			buildHeuristic(deliveryList,workingData);
		}
		return;
	}
	
}
