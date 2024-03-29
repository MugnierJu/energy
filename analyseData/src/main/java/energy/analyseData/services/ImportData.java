package energy.analyseData.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import model.Data;

public class ImportData {
	
	private static String SEPARATOR = ",";
	
	
	public static void importData(String path) {
		
		File rootFolder = new File(path);
		List<File> fileList = getFiles(rootFolder);
		filterFiles(fileList);
		
		for(File file : fileList) {		
			List<Data> dataList = getData(file);
			DataBaseService.getInstance().insertData(dataList, getHouseName(file));
			System.out.println("Temps : "+DataBaseService.getInstance().getStringTime());
		}
	}
	
	private static String getHouseName(File file) {
		String[] values = file.getName().split("-");
		return values[0];
	}
	
	/**
	 * Retourne la liste des données pour le fichier donné
	 * @param file
	 * @return
	 */
	private static List<Data> getData(File file) {
		List<Data> dataList = new ArrayList<Data>();
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss.S", Locale.FRANCE);

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			
		    String line;
		    boolean firstLine = true;
		    
		    int indexCooker = 0;
	    	int indexLamp = 0;
	    	int indexFreezer = 0;
		    
		    while ((line = br.readLine()) != null) {
		    	if(firstLine) {
		    		String[] values = line.split(SEPARATOR);

			        for(int i=0; i < values.length; i++) {
			        	//values[i] = values[i].replace("\"", "");
			        	//System.out.println(values[i]);
			        	if( indexFreezer== 0 && values[i].contains("freezer")) {
			        		indexFreezer = new Integer(i);
			        	}else if(indexFreezer== 0 && values[i].contains("Fridge")){
			        		indexFreezer = i;
			        	}
			        	if(indexCooker== 0 && values[i].contains("Cooker")) {
			        		indexCooker = i;
			        	}else if(indexCooker== 0 && values[i].contains("oven")) {
			        		indexCooker = i;
			        	}
			        	if(indexLamp== 0 && values[i].contains("lamp")) {
			        		indexLamp = i;
			        	}else if(indexLamp== 0 && values[i].contains("light")) {
			        		indexLamp = i;
			        	}
			        }
		    		firstLine = false;
		    	}else {
			        String[] values = line.split(SEPARATOR);

			        for(int i=0; i < values.length; i++) {
			        	values[i] = values[i].replace("\"", "");
			        }
			        
			        Data data = new Data(format.parse(values[0]), Integer.valueOf(values[indexCooker]), Integer.valueOf(values[indexFreezer]), Integer.valueOf(values[indexLamp]));
			        dataList.add(data);
		    	}
		    }
		    
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dataList;
	}
	
	/**
	 * Retourne les bons fichiers 
	 * @param files
	 */
	private static void filterFiles(List<File> files) {		
		ListIterator<File> iter = files.listIterator();
		while(iter.hasNext()){
			BufferedReader br;
			File currentFile = iter.next();
			try {
				br = new BufferedReader(new FileReader(currentFile));
				if(br.readLine() == null) {
					iter.remove();
				}else {
					if(currentFile.getName().contains("states")) {
						iter.remove();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				iter.remove();
			}
		}
	}
	
	/**
	 * Retourne la liste des fichiers pour un dossier
	 * @param folder
	 * @return
	 */
	private static List<File> getFiles(File folder){
		
		List<File> fileList = new ArrayList<File>();
		
		for (final File fileEntry : folder.listFiles()) {
	        if (!fileEntry.isDirectory()) {
	        	fileList.add(fileEntry);
	        }
	    }
		return fileList;
	}
}
