package energy.analyseData.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import model.Data;

public class ImportData {
	
	
	public static void importData(String path) {
		
		File rootFolder = new File(path);
		List<File> fileList = getFiles(rootFolder);
		filterFiles(fileList);
		
		List<Data> dataList = getData();
		
		
		
		for(File file : fileList) {
			System.out.println(file);
		}
		
	}
	
	private static List<Data> getData() {
		List<Data> dataList = new ArrayList<Data>();
		
		return dataList;
	}
	
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
	 * Retourne la lsite des fichiers pour un dossier
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
