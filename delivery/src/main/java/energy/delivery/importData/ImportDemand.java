package energy.delivery.importData;

import java.util.ArrayList;
import java.util.List;

import energy.delivery.models.TypeImport;

/**
 * 
 * @author Julien Mugnier - Baptiste Rambaud
 *
 */
public class ImportDemand extends AbstractImportData {

	public ImportDemand(String path) {
		super(path);
	}

	public Object importData(TypeImport typeImport) {
		List<Integer> result = new ArrayList<Integer>();
		
		String[] values = getData().split(" ");
		
		for(String value : values) {
			result.add(Integer.valueOf(value));
		}
		
		return result;
	}
}
