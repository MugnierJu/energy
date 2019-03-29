package energy.delivery;

import java.util.List;

import energy.delivery.heuristic.BasicHeuristic;
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
        BasicHeuristic heuristic = new BasicHeuristic();
        List<Delivery> deliveryList = heuristic.process(ImportService.getData());
        
        for(Trajet t : deliveryList.get(0).getTrajetList()) {
        	System.out.println(t.getDistance());
        }
        
    }
}
