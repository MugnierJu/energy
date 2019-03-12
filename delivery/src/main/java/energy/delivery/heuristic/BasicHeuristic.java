package energy.delivery.heuristic;

import java.util.List;

import energy.delivery.comparator.ClientRequestComparator;
import energy.delivery.models.Delivery;
import energy.delivery.models.EntryData;

public class BasicHeuristic implements HeuristicInterface {

	public List<Delivery> process(EntryData data) throws Exception {
		
		data.getClientList().sort(new ClientRequestComparator());

		checkConstraint(data);	
		
		//TODO finish the heuristic
		
		return null;
	}

	public void checkConstraint(EntryData data) throws Exception {
		// the max size of a delivery should be inferior to the capacity of the vehicule
		if(data.getClientList().get(data.getClientList().size()-1).getRequest() <= data.getVehiculeStat().getCapacity()) {
			throw new Exception("Request size too hight");
		}
	}

}
