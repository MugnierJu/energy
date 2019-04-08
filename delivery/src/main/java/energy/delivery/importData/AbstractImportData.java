package energy.delivery.importData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import energy.delivery.models.TypeImport;

/**
 * 
 * @author Julien Mugnier - Baptiste Rambaud
 *
 */
public abstract class AbstractImportData {

	private String data;
	protected String path;
	
	public AbstractImportData(String path){
		this.path = path;
		data = getDataFromFile(path);
	}
	
	abstract Object importData(TypeImport typeImport);
	
	private String getDataFromFile(String path){
		String filePath = path;
		StringBuilder result = new StringBuilder();
		try {
			Scanner scanner = new Scanner(new File(filePath));
			boolean isFisrtLine = true;
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if(!isFisrtLine) {
					result.append(" "+line);
				}else {
					isFisrtLine = false;
					result.append(line);
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return result.toString();
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	};
	
}
