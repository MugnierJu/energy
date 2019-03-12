package energy.delivery.importData;

import java.util.ArrayList;
import java.util.List;

public class ImportMatrix extends AbstractImportData {

	public ImportMatrix(String path) {
		super(path);
	}

	public Object importData() {
		
		List<List<Double>> result = new ArrayList<List<Double>>();
		String[] values = getData().split(" ");
		
		int lineSize = (int) Math.sqrt(values.length);
		for(int i = 0 ; i < lineSize; i++) {
			List<Double> matrixLine = new ArrayList<Double>();
			for(int j = i*lineSize; j < i*lineSize+lineSize; j++) {
				matrixLine.add(Double.valueOf(values[j]));
			}
			result.add(matrixLine);
		}
		return result;
	}
}
