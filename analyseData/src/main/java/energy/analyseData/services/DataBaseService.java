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
	/* ids */
	
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
