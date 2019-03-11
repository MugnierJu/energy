package energy.delivery.importData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class AbstractImportData {

	private String data;
	
	public AbstractImportData(String path){
		data = getDataFromFile(path);
	}
	
	abstract Object importData(String path);
	
	private String getDataFromFile(String path){
		String filePath = path;
		StringBuilder result = new StringBuilder();
		try {
			Scanner scanner = new Scanner(new File(filePath));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return result.toString();
	};
}
