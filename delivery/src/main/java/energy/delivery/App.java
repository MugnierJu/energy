package energy.delivery;

import java.util.ArrayList;
import java.util.List;

import energy.delivery.heuristic.BasicHeuristic;
import energy.delivery.heuristic.HeuristicInterface;
import energy.delivery.heuristic.HeuristicUtils;
import energy.delivery.heuristic.SteepestDescentHeuristic;
import energy.delivery.models.Delivery;
import energy.delivery.models.Trajet;
import energy.delivery.service.ImportService;

/**
 *
 * @author Julien Mugnier - Baptiste Rambaud
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	try {
	        HeuristicInterface heuristic = new SteepestDescentHeuristic();
	        
	        List<Delivery> deliveryList = heuristic.process(ImportService.getData());

	        int i=0;
//	        for(Delivery delivery : deliveryList) {
//	        	System.out.println("Livraison "+i);
//	        	i++;
//	        	for(Trajet t: delivery.getTrajetList()) {
//	        		System.out.println(t.getArrivalClient().getRequest());
//	        	}
//	        }
//	        System.out.println(HeuristicUtils.evaluate(deliveryList, 0, ImportService.getData()));
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
}
