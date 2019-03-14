package energy.delivery.heuristic;

import java.util.ArrayList;
import java.util.List;

import energy.delivery.comparator.ClientRequestComparator;
import energy.delivery.models.Client;
import energy.delivery.models.Delivery;
import energy.delivery.models.EntryData;
import energy.delivery.models.Trajet;

public class BasicHeuristic implements HeuristicInterface {
	
	public List<Delivery> process(EntryData data) {
		
		data.getClientList().sort(new ClientRequestComparator());

		try {
			checkConstraint(data);

			List<Delivery> deliveryList = new ArrayList<Delivery>();
			
			buildHeuristic(deliveryList, data);
			
			return deliveryList;

		
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return null;
	}

	public void checkConstraint(EntryData data) throws Exception {
		// the max size of a delivery should be inferior to the capacity of the vehicule
		if(data.getClientList().get(data.getClientList().size()-1).getRequest() > data.getVehiculeStat().getCapacity()) {
			throw new Exception("Request size too hight");
		}
	}

	public void buildHeuristic(List<Delivery> deliveryList,EntryData data) {
		
		//first
		Delivery delivery = new Delivery();
		int deliverySize = data.getClientList().get(data.getClientList().size()-1).getRequest();
		Client previousClient = data.getClientList().get(data.getClientList().size()-1);
		data.getClientList().remove(data.getClientList().size()-1);

		//next
		EntryData workingData = data;
		for(int i = data.getClientList().size()-1; i >= 0; i--) {
			int newSize = deliverySize + data.getClientList().get(i).getRequest();
			if(newSize <= data.getVehiculeStat().getCapacity()) {
				deliverySize = newSize;

				Double distance = HeuristicUtils.getDistanceFromMatrixPosition(previousClient, data.getClientList().get(i), data);
				Double time = HeuristicUtils.getTimeFromMatricPosition(previousClient, data.getClientList().get(i), data);
				
				Trajet newTrajet = new Trajet(distance, time, previousClient.getCoordinate(), data.getClientList().get(i).getCoordinate());
				delivery.addTrajet(newTrajet);
				previousClient = data.getClientList().get(i);
				workingData.getClientList().remove(i);
			}
			if(newSize == data.getVehiculeStat().getCapacity() || i == 0) {
				deliveryList.add(delivery);
				break;
			}
		}
		
		if(data.getClientList().size() > 0) {
			buildHeuristic(deliveryList,workingData);
		}
		return;
	}
}
