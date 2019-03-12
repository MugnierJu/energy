package energy.delivery.service;

import java.util.ArrayList;
import java.util.List;

import energy.delivery.importData.ImportCoord;
import energy.delivery.importData.ImportDemand;
import energy.delivery.importData.ImportMatrix;
import energy.delivery.models.Client;
import energy.delivery.models.Coordinate;
import energy.delivery.models.EntryData;

public class ImportService {

	private ImportService(){}
	
	public static EntryData getData() {
		
		EntryData data = new EntryData();
		String resourcesPath = System.getProperty("user.dir")+PropertiesService.getInstance().getProperty("fileRepository");
					
		//Import Dist matrix
		ImportMatrix distMatrixImport = new ImportMatrix(resourcesPath+PropertiesService.getInstance().getProperty("distancesFile"));
		data.setDistanceMatrix((List<List<Double>>) distMatrixImport.importData());
		
		//Import Time matrix
		ImportMatrix timeMatrixImport = new ImportMatrix(resourcesPath+PropertiesService.getInstance().getProperty("timeFile"));
		data.setTimeMatrix((List<List<Double>>) timeMatrixImport.importData());
		
		//Import Client
		ImportCoord coordinateImport = new ImportCoord(resourcesPath+PropertiesService.getInstance().getProperty("coodinatesFile"));
		ImportDemand demandImport = new ImportDemand(resourcesPath+PropertiesService.getInstance().getProperty("demandFile"));
		
		List<Client> clientList = new ArrayList<Client>();
		List<Integer> demandeList = (List<Integer>) demandImport.importData();
		List<Coordinate> coordinateList = (List<Coordinate>) coordinateImport.importData();
		for(int i = 0; i< demandeList.size(); i++) {
			Client newClient = new Client(coordinateList.get(i), demandeList.get(i));
			clientList.add(newClient);
		}
		
		
		
		return data;
	}
}
