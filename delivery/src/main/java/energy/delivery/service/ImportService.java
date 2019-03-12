package energy.delivery.service;

import java.util.List;

import energy.delivery.importData.ImportMatrix;
import energy.delivery.models.EntryData;

public class ImportService {

	private ImportService(){}
	
	public static EntryData getData() {
		
		EntryData data = new EntryData();
		String resourcesPath = System.getProperty("user.dir")+PropertiesService.getInstance().getProperty("fileRepository");
					
		//Import Dist matrix
		ImportMatrix distMatrixImport = new ImportMatrix(resourcesPath+PropertiesService.getInstance().getProperty("distancesFile"));
		data.setDistanceMatrix((List<List<Double>>) distMatrixImport.importData());
		
		return data;
	}
}
