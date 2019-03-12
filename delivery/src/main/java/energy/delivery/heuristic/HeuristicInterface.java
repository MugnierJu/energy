package energy.delivery.heuristic;

import java.util.List;

import org.apache.log4j.Logger;

import energy.delivery.models.Delivery;
import energy.delivery.models.EntryData;
import energy.delivery.service.PropertiesService;

public interface HeuristicInterface {
	
	static Logger logger = Logger.getLogger(PropertiesService.class);

	public List<Delivery> process(EntryData data) throws Exception;
	
	void checkConstraint(EntryData data) throws Exception;
}
