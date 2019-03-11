package energy.delivery.importData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ImportDemand implements ImportDataInterface {

	public void importData(String path) {
		String filePath = path;
		try {
			Scanner scanner = new Scanner(new File(filePath));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				System.out.println(line);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
