package energy.delivery.importData;

import java.util.ArrayList;
import java.util.List;

import energy.delivery.models.TypeImport;

/**
 * 
 * @author Julien Mugnier - Baptiste Rambaud
 *
 * Format doit être comme suit :
 * |x x x x
 * |x x x x
 * |x x x x
 * |x x x x
 * 
 * seulement les valeurs séparées par UN espace
 * taille colone = taille ligne
 * 
 */
public class ImportMatrix extends AbstractImportData {

	public ImportMatrix(String path) {
		super(path);
	}

	public Object importData(TypeImport typeImport) {
		List<List<Double>> result = new ArrayList<List<Double>>();
		
		String[] values;
		if(typeImport.equals(TypeImport.DISTANCES)){
			values = getData().split("   ");
		}
		else{
			values = getData().split(" ");
		}
		
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
