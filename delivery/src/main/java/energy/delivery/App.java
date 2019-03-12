package energy.delivery;

import energy.delivery.heuristic.BasicHeuristic;
import energy.delivery.service.ImportService;
import energy.delivery.service.PropertiesService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        BasicHeuristic heuristic = new BasicHeuristic();
        heuristic.process(ImportService.getData());
    }
}
