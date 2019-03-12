package energy.delivery.importData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author Julien Mugnier - Baptiste Rambaud
 *
 */
public class ImportDemand extends AbstractImportData {

	public ImportDemand(String path) {
		super(path);
	}

	public Object importData() {
		List<Integer> result = new ArrayList<Integer>();
		
		String[] values = getData().split(" ");
		
		for(String value : values) {
			result.add(Integer.valueOf(value));
		}
		
		return result;
	}
}
