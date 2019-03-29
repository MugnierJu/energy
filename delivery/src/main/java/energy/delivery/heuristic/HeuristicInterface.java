package energy.delivery.heuristic;

import java.util.List;

import org.apache.log4j.Logger;

import energy.delivery.models.Client;
import energy.delivery.models.Delivery;
import energy.delivery.models.EntryData;
import energy.delivery.service.PropertiesService;

/**
 * 
 * @author Julien Mugnier - Baptiste Rambaud
 *
 */
public interface HeuristicInterface {
	
	static Logger logger = Logger.getLogger(PropertiesService.class);

	/**
	 * Launch the process of the heuristic.
	 * Should first call checkConstraints method and then call buildHeuristic
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public List<Delivery> process(EntryData data) throws Exception;
	
	void checkConstraints(EntryData data) throws Exception;
	
	/**
	 * Method that centralize the treatments
	 * @param deliveryList
	 * @param data
	 */
	public void buildHeuristic(List<Delivery> deliveryList,EntryData data);
}
