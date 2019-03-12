package energy.delivery.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesService {
	private static Properties prop;

	private static PropertiesService instance = null;
	
	private static Logger logger = Logger.getLogger(PropertiesService.class);
	
	private PropertiesService() {
		prop = new Properties();
		try {
			instantiatePropertyFiles();
		} catch (Exception e) {
			logger.warn("Une erreur c'est produite lors de l'initilisation des propri�t�s");
			e.printStackTrace();
		}
	}
	
	public static PropertiesService getInstance() {
		if(instance == null) {
			instance =  new PropertiesService();
		}
		return instance;
	}
	
	private void instantiatePropertyFiles() throws Exception {
		InputStream inputStream = new FileInputStream(new File(System.getProperty("user.dir"), "config.properties"));
		prop.load(inputStream);
	}
	
	public String getProperty(String propName) {
		return prop.getProperty(propName);
	}
}
