package energy.analyseData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.IsoFields;
import java.util.Date;

import energy.analyseData.services.Constants;
import energy.analyseData.services.DataBaseService;
import energy.analyseData.services.ImportData;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ImportData.importData("C:\\Master\\M2\\Energy\\S2\\Testard\\IRISE\\irise-toBeExtracted\\csv");
        
        //Anciennes questions - test de récupération des données
//        Q1();
//        Q2();
//        Q3();
//        Q4();
        
        //Questions 2019
        q12019();
        q22019();
    }
    
    /**
     * Consommation de “l’appliance” x à la date d.
     * Ici appliance = frigo
     * Date = 02/12/1998
     * Maison = 2000918
     */
    private static void q12019() {
    	SimpleDateFormat format = new SimpleDateFormat( "yyyy/MM/dd" );
		try {
			Date startDate = format.parse( "1998/02/12" );
	    	Date endDate = format.parse( "1998/02/13" );
	    	String idMaison = "2000918";
	        String res = DataBaseService.getInstance().getTotalConsumptionForADate(Constants.BD_APPLICANCE_FRIGO, startDate, endDate, idMaison);
			System.out.println("Conso moyenne du "+format.format(startDate)+" au "+format.format(endDate) + " : "+res);
        
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * 
     */
    private static void q22019() {
    	SimpleDateFormat format = new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" );
		try {
			LocalDateTime now = LocalDateTime.now();
			Date startDate = format.parse( "1998/"+now.getMonthValue()+"/"+now.getDayOfMonth()+" "+now.getHour()+":"+now.getMinute()+":"+now.getSecond());
			Date endDate = null;
			if(now.getHour() == 23) {
	    		 endDate = format.parse( "1998/"+now.getMonthValue()+"/"+now.getDayOfMonth()+1+" 00:00:00");
	    	}else {
	    		 endDate = format.parse( "1998/"+now.getMonthValue()+"/"+now.getDayOfMonth()+" "+now.getHour()+1+":"+now.getMinute()+":"+now.getSecond());
	    	}
	    	String idMaison = "2000918";
	        String res = DataBaseService.getInstance().getTotalConsumptionForADate(Constants.BD_APPLICANCE_FRIGO, startDate, endDate, idMaison);
			System.out.println("Conso totale sur la dernière heure : "+res);
        
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    
    /*----------------------------------------------------------------*/
    
    
    /**
     * Pour un dispositif, rechercher la consommation a une date donńee
     */
    private static void Q1() {
    	SimpleDateFormat format = new SimpleDateFormat( "yyyy/MM/dd" );
		try {
			Date startDate = format.parse( "1998/02/12" );
	    	Date endDate = format.parse( "1998/02/15" );
	    	String idMaison = "2000918";
	        String res = DataBaseService.getInstance().getTotalConsumptionForADate(Constants.BD_APPLICANCE_FOUR, startDate, endDate, idMaison);
			System.out.println("Conso moyenne du "+format.format(startDate)+" au "+format.format(endDate) + " : "+res);
        
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * Consommation totale de chaque dispositif depuis le d ́ebut de l’anńee
     * --> On assumme que l'on est en l'an 1998 :)
     */
    private static void Q2() {
    	SimpleDateFormat format = new SimpleDateFormat( "yyyy/MM/dd" );
		try {
			LocalDateTime now = LocalDateTime.now();
			Date startDate = format.parse( "1998/01/01" );
	    	Date endDate = format.parse( "1998/"+now.getMonthValue()+"/"+now.getDayOfMonth());
	    	String idMaison = "2000918";
	        String res = DataBaseService.getInstance().getTotalConsumptionForADate(Constants.BD_APPLICANCE_FOUR, startDate, endDate, idMaison);
			System.out.println("Conso totale du "+format.format(startDate)+" a aujourd'hui : "+res);
        
		} catch (ParseException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Consommation moyenne pour chaque jour de la semaine
     * --> On assume que l'on veut la semaine courrante...
     */
    private static void Q3() {
    	//Jour de la semaine courrante de 1 à 7 (1 lundi, 7 dimanche), éviter le 31 d"cembre, on ne prend en compte que 1998 :)
    	int jourVoulu = 1;
		LocalDateTime now = LocalDateTime.now();
    	int semaine = now.get ( IsoFields.WEEK_OF_WEEK_BASED_YEAR );
    	boolean isDimanche = false;
    	if(jourVoulu == 7) {
    		isDimanche = true;
    	}
    	SimpleDateFormat format = new SimpleDateFormat( "yyyy/ww/u" );
		try {			
			Date startDate = format.parse( "1998/"+semaine+"/"+jourVoulu);
			Date endDate = null;
	    	if(isDimanche) {
		    	endDate = format.parse( "1998/"+(semaine+1)+"/1" );
	    	}else {
		    	endDate = format.parse( "1998/"+semaine+"/"+(jourVoulu+1));
	    	}
	    	String idMaison = "2000918";
	        String res = DataBaseService.getInstance().getMediumConsumptionForADate(Constants.BD_APPLICANCE_FRIGO, startDate, endDate, idMaison);
			System.out.println("Conso moyenne du "+format.format(startDate)+" au "+format.format(endDate) + " : "+res);
        
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * Consommation moyenne printemps/ ́et ́e/automne/hiver
     */
    private static void Q4() {
    	//Saisons : 1 = printemps, 2 = été, 3 = Automne, 4 = hiver
    	int saison = 1;

		SimpleDateFormat format = new SimpleDateFormat( "yyyy/MM/dd" );
		try {
			Date startDate = null;
			Date endDate = null;
			String saisonName = "";
			if(saison == 1) {
				startDate = format.parse( "1998/03/21" );
				endDate = format.parse( "1998/06/20" );
				saisonName = "le printemps";
			}else if(saison ==2) {
				startDate = format.parse( "1998/06/21" );
				endDate = format.parse( "1998/09/20" );
				saisonName = "l'été";
			}else if(saison == 3) {
				startDate = format.parse( "1998/09/21" );
				endDate = format.parse( "1998/12/20" );
				saisonName = "l'automne";
			}else {
				startDate = format.parse( "1998/12/21" );
				endDate = format.parse( "1998/03/20" );
				saisonName = "l'hiver";
			}
			

	    	String idMaison = "2000918";
	        String res = DataBaseService.getInstance().getMediumConsumptionForADate(Constants.BD_APPLICANCE_FRIGO, startDate, endDate, idMaison);
			System.out.println("Conso moyenne pour "+saisonName+" : "+res);
        
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
