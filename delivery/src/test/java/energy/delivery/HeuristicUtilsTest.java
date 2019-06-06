package energy.delivery;

import java.util.ArrayList;
import java.util.List;

import energy.delivery.heuristic.HeuristicUtils;
import energy.delivery.models.Client;
import energy.delivery.models.Coordinate;
import energy.delivery.models.EntryData;
import junit.framework.TestCase;

public class HeuristicUtilsTest extends TestCase {

	public void testGetDistanceFromMatrixPosition() {
		Coordinate c1 = new Coordinate(10.0, 10.0);
		Client client1 = new Client(c1, 0);
		
		Coordinate c2 = new Coordinate(20.0, 10.0);
		Client client2 = new Client(c2, 1);
		
		EntryData data = new EntryData();
		List<Double> mat1 = new ArrayList<Double>();
		mat1.add(0.0);
		mat1.add(10.0);
		
		List<Double> mat2 = new ArrayList<Double>();
		mat2.add(10.0);
		mat2.add(0.0);
		
		List<List<Double>> finalMatrix = new ArrayList<List<Double>>();
		finalMatrix.add(mat1);
		finalMatrix.add(mat2);
		
		data.setDistanceMatrix(finalMatrix);
		
		assertEquals(10.0, HeuristicUtils.getDistanceFromMatrixPosition(client1, client2, data));
	}

	public void testGetTimeFromMatricPosition() {
		Coordinate c1 = new Coordinate(10.0, 10.0);
		Client client1 = new Client(c1, 0);
		
		Coordinate c2 = new Coordinate(20.0, 10.0);
		Client client2 = new Client(c2, 1);
		
		EntryData data = new EntryData();
		List<Double> mat1 = new ArrayList<Double>();
		mat1.add(0.0);
		mat1.add(10.0);
		
		List<Double> mat2 = new ArrayList<Double>();
		mat2.add(10.0);
		mat2.add(0.0);
		
		List<List<Double>> finalMatrix = new ArrayList<List<Double>>();
		finalMatrix.add(mat1);
		finalMatrix.add(mat2);
		
		data.setTimeMatrix(finalMatrix);
		
		assertEquals(10.0, HeuristicUtils.getTimeFromMatricPosition(client1, client2, data));
	}

	public void testGetNewTraject() {
		
	}

	public void testGetClientListsFromDeliveryList() {
		fail("Not yet implemented");
	}

	public void testEvaluate() {
		fail("Not yet implemented");
	}

	public void testGetTimeForADay() {
		fail("Not yet implemented");
	}

	public void testGetWarehouseIndex() {
		fail("Not yet implemented");
	}

}
