package energy.analyseData.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
	
	/*--------------------------Script--------------------------*/
	
	public void insertData(List<Data> dataList, String houseName ) {
		openConnection();
		try {
			connection.setAutoCommit(false);
			
			//Si nouvelle maison, on la crée
			if(!getHouses().contains(houseName)) {
				Statement stmt = null;
				stmt = connection.createStatement();
				
				String sql = "INSERT INTO MAISON (ID_MAISON) "
			            + "VALUES ("+houseName+");";
		        stmt.executeUpdate(sql);
		        stmt.close();
			      
				connection.commit();
			}else {
			//Sinon ne prend que les data non insérées
				//TODO ne prendre que les data non insérées
			}
		
			for(Data data : dataList) {
				
				//Get Date id
				int dateId = 0;
				
				//Frigo
				Statement stmt = null;
				stmt = connection.createStatement();
		        String sql = "INSERT INTO FRIGO (id_frigo,value_frigo,id_date,id_maison) "
		            + "VALUES (1, "+data.getFreezer()+","+dateId+", '"+houseName+"');";
		        stmt.executeUpdate(sql);
				connection.commit();
				
				
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		closeConnection();
	}
	
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
	
	/*--------------------------Util--------------------------*/

	public long getTime() {
		return end-start;
	}
	
}
