package energy.delivery.importData;

import java.io.File;
import java.io.IOException;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;
import org.ini4j.InvalidFileFormatException;

import energy.delivery.models.Vehicule;

/**
 * 
 * @author Julien Mugnier - Baptiste Rambaud
 *
 * Fromat should be like this : 
 * 
 *  [Vehicle]
	max_dist = 250
	capacity = 100
	charge_fast = 60
	charge_midium = 180
	charge_slow = 480
	start_time = "7:00"
	end_time = "19:00"
 *
 */
public class ImportVehicle extends AbstractImportData {

	public ImportVehicle(String path) {
		super(path);
	}

	public Object importData() {
		
		try {			
			Ini ini = new Ini(new File(this.path));
			java.util.prefs.Preferences prefs = new IniPreferences(ini);
			
			String start = prefs.node("Vehicle").get("start_time","");
			String end = prefs.node("Vehicle").get("end_time","");
			start = start.replace("\"", "");
			end = end.replace("\"", "");
			System.out.println(end);
			String[] startTab = start.split(":");
			String[] endTab = end.split(":");
			
			int startTime = Integer.valueOf(startTab[0])*60*60+Integer.valueOf(startTab[1])*60;
			int endTime = Integer.valueOf(endTab[0])*60*60+Integer.valueOf(endTab[1])*60;;
			
			return new Vehicule(prefs.node("Vehicle").getInt("max_dist",-1), prefs.node("Vehicle").getInt("capacity",-1) ,prefs.node("Vehicle").getInt("charge_fast",-1), prefs.node("Vehicle").getInt("charge_midium",-1), prefs.node("Vehicle").getInt("charge_slow",-1),startTime,endTime);
			
		} catch (InvalidFileFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
