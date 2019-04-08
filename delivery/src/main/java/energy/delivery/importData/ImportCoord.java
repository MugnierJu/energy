package energy.delivery.importData;

import java.util.ArrayList;
import java.util.List;

import energy.delivery.models.Coordinate;
import energy.delivery.models.TypeImport;

/**
 * 
 * @author Julien Mugnier - Baptiste Rambaud
 *
 */
public class ImportCoord extends AbstractImportData {
	
	public ImportCoord(String path) {
		super(path);
	}

	public Object importData(TypeImport typeImport) {
		List<Coordinate> result = new ArrayList<Coordinate>();
		
		String[] values = getData().split(",| ");		
		
		boolean isX = true;
		Double xValue = 0.0;
		for(String value : values) {
			if(isX) {
				xValue = Double.valueOf(value);
				isX = false;
			}else {
				result.add(new Coordinate(xValue, Double.valueOf(value)));
				isX = true;
			}
		}
		
		return result;
	}
	
	
}
