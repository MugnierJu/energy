package energy.analyseData.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Data;

public class DataBaseService {
	
	private static DataBaseService instance = null;
	private Connection connection = null;
	private long start;
	private long end;

	private DataBaseService() {	
		
	}
	
	public static DataBaseService getInstance() {
		if(instance == null) {
			instance = new DataBaseService();
		}
		return instance;
	}

	/*--------------------------Connection--------------------------*/
	
	private void openConnection() {
		start = System.currentTimeMillis();
	      try {
	         Class.forName("org.postgresql.Driver");
	         connection = DriverManager
	            .getConnection("jdbc:postgresql://localhost:5432/energy",
	            "postgres", "admin");
	      } catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }
	      System.out.println("Opened database successfully");
	}
	
	private void closeConnection() {
		try {
			connection.close();
			System.out.println("Database closed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		end = System.currentTimeMillis();
	}
	
	/*--------------------------insertion--------------------------*/
	
	public void insertData(List<Data> dataList, String houseName) {
		openConnection();
		try {
			connection.setAutoCommit(false);
			
			//On insert les données que si on ne connait pas la maison
			if(!getHouses().contains(houseName)) {
				Statement stmt = null;
				stmt = connection.createStatement();
				
				String sql = "INSERT INTO MAISON (ID_MAISON) "
			            + "VALUES ("+houseName+");";
		        stmt.executeUpdate(sql);
			      			
				//On récupère l'id max de chaque applicance
				//TODO
				int idFrigo = getLastIdFrigo()+1;
				int idLampe = getLastIdLamp()+1;
				int idFour = getLastIdFour()+1;
				
				//Lampe
				//TODO
				int lampMoyenne = 0;
				int lampTotalDevice = 0;
				
				List<Integer> valuesLamp = new ArrayList<>();			
			
				for(Data data : dataList) {
					 
			        idFrigo++;
					idLampe++;
					idFour++;								
					
					//Frigo
					Statement stmtFrigo = null;
					stmtFrigo = connection.createStatement();
			        String sqlFrigo = "INSERT INTO FRIGO (id_frigo,value_frigo,date_frigo,id_maison) "
			            + "VALUES ("+idFrigo+", "+data.getFreezer()+",'"+new Timestamp(data.getDate().getTime())+"', '"+houseName+"');";
			        stmtFrigo.executeUpdate(sqlFrigo);
					
					//four
					Statement stmtFour = null;
					stmtFour = connection.createStatement();
			        String sqlFour = "INSERT INTO four (id_four,value_four,date_four,id_maison) "
			            + "VALUES ("+idFour+", "+data.getElectricCooker()+",'"+new Timestamp(data.getDate().getTime())+"', '"+houseName+"');";
			        stmtFour.executeUpdate(sqlFour);
			        
			        //lampe
			        boolean isLampActive = false;
			        if(data.getLamp() > 0) {
			        	isLampActive = true;
			        }
			        
					Statement stmtLampe = null;
					stmtLampe = connection.createStatement();
			        String sqlLampe = "INSERT INTO lamp_state (id_lamp,state_lamp,date_lamp,id_maison) "
			            + "VALUES ("+idLampe+", "+isLampActive+",'"+new Timestamp(data.getDate().getTime())+"', '"+houseName+"');";
			        stmtLampe.executeUpdate(sqlLampe);
			        valuesLamp.add(data.getLamp());
			        
					connection.commit();			
				}
				
				//maj des valeurs de la lampe
				int moyenne = 0;
				for(int value : valuesLamp) {
					moyenne+=value;
				}
				moyenne = moyenne/valuesLamp.size();
						
				int nvTotal = valuesLamp.size()+lampTotalDevice;
				int nvMoyenne =  ((lampMoyenne*lampTotalDevice)+(moyenne*valuesLamp.size()))/nvTotal;
				
				//TODO DROP LAMP_VALEUR
				
				Statement stmtLampe = null;
				int idLampValue = getLastIdLampValeur()+1;
				stmtLampe = connection.createStatement();
		        String sqlLampe = "INSERT INTO LAMP_VALEUR (id_lamp_valeur,valeur_moyenne,nombre_data,id_maison) "
		            + "VALUES ("+idLampValue+", "+nvMoyenne+","+nvTotal+", '"+houseName+"');";
		        stmtLampe.executeUpdate(sqlLampe);			
				connection.commit();			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		closeConnection();
	}
	
	/*--------------------------Récupération--------------------------*/
	
	private List<String> getHouses() {
		List<String> houses = new ArrayList<>();
		try {
    		Statement stmt = null;
			stmt = connection.createStatement();
			
	        ResultSet rs = stmt.executeQuery( "SELECT * FROM MAISON;" );
	        while ( rs.next() ) {
	        	String id = rs.getString("id_maison");
	        	houses.add(id);
	        }
	        
	        rs.close();
	        stmt.close();
	        
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return houses;
	}	
	
	private List<Data> getData(String houseId) {
		List<Data> newDataList = new ArrayList<>();
		
        try {
        	
    		Statement stmt = null;
			stmt = connection.createStatement();
			
	        //TODO
	        ResultSet rs = stmt.executeQuery( "SELECT * FROM COMPANY;" );
	        while ( rs.next() ) {
	           int id = rs.getInt("id");
	           String  name = rs.getString("name");
	           int age  = rs.getInt("age");
	           String  address = rs.getString("address");
	           float salary = rs.getFloat("salary");
	           System.out.println( "ID = " + id );
	           System.out.println( "NAME = " + name );
	           System.out.println( "AGE = " + age );
	           System.out.println( "ADDRESS = " + address );
	           System.out.println( "SALARY = " + salary );
	           System.out.println();
	        }
	        
	        rs.close();
	        stmt.close();
	        
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return newDataList;
	}
	/*--------------------------Q1--------------------------*/
	
	/**
	 * Récupère la consomation totale pour la période donnée
	 * @param appliance
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public String getTotalConsumptionForADate(String appliance,Date startDate,Date endDate, String idMaison) {
		openConnection();
		String SQL = "";
		String conso = "";
		if(appliance.equals(Constants.BD_APPLICANCE_LAMPE)) {
			
			//Toutes les 10 secondes entre la startDate et la endDate
			long tenSeconds = (endDate.getTime()-startDate.getTime())/100;
			int moyenneConsoLampe = 0;

			try {
			Statement stmt = null;
				stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery( "SELECT * FROM lamp_valeur;" );
		        while ( rs.next() ) {
		        	moyenneConsoLampe = rs.getInt("valeur_moyenne");
		        }
	        
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return (tenSeconds*moyenneConsoLampe)+"";
		}
		else if(appliance.equals(Constants.BD_APPLICANCE_FRIGO)) {
			SQL = "SELECT * FROM getConsoFrigoTotal (?,?,?)";
		}else if (appliance.equals(Constants.BD_APPLICANCE_FOUR)) {
			SQL = "SELECT * FROM getConsoFourTotal (?,?,?)";
		}
		
        try (
            PreparedStatement pstmt = connection.prepareStatement(SQL)) {
 
            pstmt.setTimestamp(1, new Timestamp(startDate.getTime()));
            pstmt.setTimestamp(2, new Timestamp(endDate.getTime()));
            pstmt.setString(3, idMaison);
            ResultSet rs = pstmt.executeQuery();
 
            while (rs.next()) {
            	conso = rs.getFloat(1)+"";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        closeConnection();
        return conso;
	}
	
	

	/**
	 * 
	 * @param appliance
	 * @param startDate
	 * @param endDate
	 * @param idMaison
	 * @return
	 */
	public String getMediumConsumptionForADate(String appliance,Date startDate,Date endDate, String idMaison) {
		openConnection();
		String SQL = "";
		String conso = "";
		if(appliance.equals(Constants.BD_APPLICANCE_LAMPE)) {
			
			int moyenneConsoLampe = 0;

			try {
				Statement stmt = null;
				stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery( "SELECT * FROM lamp_valeur;" );
		        while ( rs.next() ) {
		        	moyenneConsoLampe = rs.getInt("valeur_moyenne");
		        }
	        
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return (moyenneConsoLampe)+"";
		}
		else if(appliance.equals(Constants.BD_APPLICANCE_FRIGO)) {
			SQL = "SELECT * FROM getConsoFrigoMoyenne (?,?,?)";
		}else if (appliance.equals(Constants.BD_APPLICANCE_FOUR)) {
			SQL = "SELECT * FROM getConsoFourMoyennel (?,?,?)";
		}
		
       try (
           PreparedStatement pstmt = connection.prepareStatement(SQL)) {

           pstmt.setTimestamp(1, new Timestamp(startDate.getTime()));
           pstmt.setTimestamp(2, new Timestamp(endDate.getTime()));
           pstmt.setString(3, idMaison);
           ResultSet rs = pstmt.executeQuery();

           while (rs.next()) {
           	conso = rs.getFloat(1)+"";
           }
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }
       closeConnection();
       return conso;
	}
	
	public String getBiggestConsumptionApplicance(Date startDate, Date endDate) {
		openConnection();
		List<Integer> sommesList = new ArrayList<>();
		List<String> idMaisonList = new ArrayList<>();
		
		// FOUR
		try (PreparedStatement pst = connection.prepareStatement("select *" + 
					"			from (select sum(value_four) as sum_four, id_maison" + 
					"				 from four" + 
					"				 where date_four BETWEEN '"+new Timestamp(startDate.getTime())+"' AND '"+new Timestamp(endDate.getTime())+"'"+ 
					"				 group by id_maison" + 
					"				 order by (sum(value_four)) desc" + 
					"				 ) pp" + 
					"			limit 1;");
            ResultSet rs = pst.executeQuery()) {

	        while (rs.next()) {
	        	sommesList.add(rs.getInt(1));
		        idMaisonList.add(rs.getString(2));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//FRIGO
		try (PreparedStatement pst = connection.prepareStatement("select *" + 
					"			from (select sum(value_frigo) as sum_frigo, id_maison" + 
					"				 from frigo" + 
					"				 where date_frigo BETWEEN '"+new Timestamp(startDate.getTime())+"' AND '"+new Timestamp(endDate.getTime())+"'"+ 
					"				 group by id_maison" + 
					"				 order by (sum(value_frigo)) desc" + 
					"				 ) pp" + 
					"			limit 1;");
	        ResultSet rs = pst.executeQuery()) {
	
	        while (rs.next()) {
	        	sommesList.add(rs.getInt(1));
		        idMaisonList.add(rs.getString(2));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//LAMPE
		try (PreparedStatement pst = connection.prepareStatement("select *" + 
					"			from (select sum((valeur_moyenne*nombre_data)) as sum_lamp, id_maison" + 
					"				 from lamp_valeur" + 
					"				 group by id_maison"+ 
					"				 order by 1 desc" + 
					"				 ) pp" + 
					"			limit 1;");

	        ResultSet rs = pst.executeQuery()) {
	
	        while (rs.next()) {
	        	sommesList.add(rs.getInt(1));
		        idMaisonList.add(rs.getString(2));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String resultId = "";
		int resultValue = -1;
		String applianceName = "";
		for(int i =0; i < sommesList.size(); i++) {
			if(resultValue < sommesList.get(i)) {
				resultValue = sommesList.get(i);
				resultId = idMaisonList.get(i);
				if(i == 0) {
					applianceName = "Four";
				}else if(i == 1) {
					applianceName = "Frigo";
				}else if(i == 3) {
					applianceName = "Lampe";
				}
			}
		}
		
		closeConnection();
		return "Maison : "+resultId+" valeur : "+resultValue+" pour l'appliance : "+applianceName;
	}
	
	/**
	 * Renvoie les périodes pendant lesquels la consomation de la semaine 1 est plus élevée que la consomation de la semaine 2
	 * @param startDates1
	 * @param endDates1
	 * @param startDates2
	 * @param endDates2
	 * @param idMaison
	 * @return
	 */
	public List<Date> getPeriodOfUpperConsomation(Date startDates1, Date endDates1,Date startDates2, Date endDates2, String idMaison){
		openConnection();
		
		List<Double> consoS1 = new ArrayList<>();
		List<Date> dateList = new ArrayList<>();
		List<Double> consoS2 = new ArrayList<>();
		
		try (PreparedStatement pst = connection.prepareStatement("		SELECT value_four+value_frigo+valeur_moyenne AS sumTotal, date_four, date_frigo" + 
				"		FROM maison " + 
				"		JOIN frigo ON frigo.id_maison = maison.id_maison" + 
				"		JOIN four ON four.id_maison = maison.id_maison" + 
				"		JOIN lamp_valeur ON lamp_valeur.id_maison = maison.id_maison" + 
				"		WHERE maison.id_maison = '"+idMaison+"' AND date_four BETWEEN '"+new Timestamp(startDates1.getTime())+"' AND '"+new Timestamp(endDates1.getTime())+"'" + 
				"		AND date_frigo BETWEEN '"+new Timestamp(startDates1.getTime())+"' AND '"+new Timestamp(endDates1.getTime())+"'" + 
				"		AND date_frigo = date_four");
	
	        ResultSet rs = pst.executeQuery()) {
	
	        while (rs.next()) {
	        	consoS1.add(rs.getDouble(1));
	        	dateList.add(rs.getTimestamp(2));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try (PreparedStatement pst = connection.prepareStatement("		SELECT value_four+value_frigo+valeur_moyenne AS sumTotal, date_four, date_frigo" + 
				"		FROM maison " + 
				"		JOIN frigo ON frigo.id_maison = maison.id_maison" + 
				"		JOIN four ON four.id_maison = maison.id_maison" + 
				"		JOIN lamp_valeur ON lamp_valeur.id_maison = maison.id_maison" + 
				"		WHERE maison.id_maison = '"+idMaison+"' AND date_four BETWEEN '"+new Timestamp(startDates2.getTime())+"' AND '"+new Timestamp(endDates2.getTime())+"'" + 
				"		AND date_frigo BETWEEN '"+new Timestamp(startDates2.getTime())+"' AND '"+new Timestamp(endDates2.getTime())+"'" + 
				"		AND date_frigo = date_four");
	
	        ResultSet rs = pst.executeQuery()) {
	
	        while (rs.next()) {
	        	consoS2.add(rs.getDouble(1));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		List<Date> result = new ArrayList<>();
		
		for(int i=0; i < consoS1.size();i++) {
			if(consoS2.get(i) != null) {
				if(consoS1.get(i) < consoS2.get(i)) {
					result.add(dateList.get(i));
				}
			}
		}
		
		closeConnection();
		return result;
	}
	
	/*--------------------------ids--------------------------*/
	
	private int getLastIdFrigo() {
		
		String SQL = "SELECT * FROM getLastIdFrigo()";
	
	    try (
	        PreparedStatement pstmt = connection.prepareStatement(SQL)) {
	        ResultSet rs = pstmt.executeQuery();
	
	        while (rs.next()) {
	        	return rs.getInt(1);
	        	}
	    }catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	    return -1;
	}
	
	private int getLastIdFour() {
		
		String SQL = "SELECT * FROM getLastIdFour()";
	
	    try (
	        PreparedStatement pstmt = connection.prepareStatement(SQL)) {
	        ResultSet rs = pstmt.executeQuery();
	
	        while (rs.next()) {
	        	return rs.getInt(1);
	        	}
	    }catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	    return -1;
	}
	
	private int getLastIdLamp() {
		
		String SQL = "SELECT * FROM getLastIdLamp()";
	
	    try (
	        PreparedStatement pstmt = connection.prepareStatement(SQL)) {
	        ResultSet rs = pstmt.executeQuery();
	
	        while (rs.next()) {
	        	return rs.getInt(1);
	        	}
	    }catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	    return -1;
	}
	
	private int getLastIdLampValeur() {
		
		String SQL = "SELECT * FROM getLastIdLampValeur()";
	
	    try (
	        PreparedStatement pstmt = connection.prepareStatement(SQL)) {
	        ResultSet rs = pstmt.executeQuery();
	
	        while (rs.next()) {
	        	return rs.getInt(1);
	        	}
	    }catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	    return -1;
	}
	
	/*--------------------------Util--------------------------*/

	public long getTime() {
		return end-start;
	}
	
	public String getStringTime() {
		Date date = new Date(getTime());
		DateFormat formatter = new SimpleDateFormat("mm'm' ss's' SSS");
		return formatter.format(date);
	}
	
}
