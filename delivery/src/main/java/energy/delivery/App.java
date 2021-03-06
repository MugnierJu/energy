package energy.delivery;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import energy.delivery.heuristic.BasicHeuristic;
import energy.delivery.heuristic.HeuristicInterface;
import energy.delivery.heuristic.HeuristicUtils;
import energy.delivery.heuristic.SteepestDescentHeuristic;
import energy.delivery.heuristic.XVariationHeuristic;
import energy.delivery.heuristic.XVariationHeuristicDeterministic;
import energy.delivery.heuristic.XVariationHeuristicDeterministicFirstBest;
import energy.delivery.heuristic.XVariationHeuristicFirstBest;
import energy.delivery.models.Client;
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
	        
	        // Non déterministe - Toutes les solution parcoures - on prend la meilleure
//	        HeuristicInterface heuristicUndeterministicAllDataBrosed = new XVariationHeuristic();
//	        List<Delivery> deliveryList = heuristicUndeterministicAllDataBrosed.process(ImportService.getData());
	        
	        // Déterministe - Toutes les solution parcoures - on prend la meilleure
	        XVariationHeuristicDeterministic heuristicDeterministicAllDataBrosed = new XVariationHeuristicDeterministic();
	        List<Delivery> deliveryList = heuristicDeterministicAllDataBrosed.process(ImportService.getData());

	        // Non déterministe - On prend la première meilleur solution
//    		XVariationHeuristicFirstBest heuristicFirstBest = new XVariationHeuristicFirstBest();
//	        List<Delivery> deliveryList = heuristicFirstBest.process(ImportService.getData());
    		
    		// Déterministe - On prend la première meilleur solution

    		//XVariationHeuristicDeterministicFirstBest heuristicDeteministicFirstBest = new XVariationHeuristicDeterministicFirstBest();
	        //List<Delivery> deliveryList = heuristicDeteministicFirstBest.process(ImportService.getData());
	       
	        
	        int i=0;
	        
	        Gson gson = new Gson();
	        String json ="";
	        FileWriter fw=new FileWriter("C:\\wamp64\\www\\energyFront\\result.json"); 
        	json = gson.toJson(deliveryList);
        	fw.write(json);
        	fw.close();
	        
        	
	        System.out.println("Score : "+HeuristicUtils.evaluate(deliveryList, 0, ImportService.getData()));
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
}
