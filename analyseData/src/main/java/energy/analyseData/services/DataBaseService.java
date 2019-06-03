package energy.analyseData.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import model.Data;

public class DataBaseService {
	
	private static DataBaseService instance = null;
	private Connection connection = null;

	private DataBaseService() {	
		
	}
	
	public static DataBaseService getInstance() {
		if(instance == null) {
			instance = new DataBaseService();
		}
		return instance;
	}
	
	private void openConnection() {
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
	}
	
	private void insertData(List<Data> dataList) {
		openConnection();
		try {
			connection.setAutoCommit(false);
		
			for(Data data : dataList) {
				Statement stmt = null;
				stmt = connection.createStatement();
				
//		        String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
//		            + "VALUES (1, 'Paul', 32, 'California', 20000.00 );";
//		        stmt.executeUpdate(sql);
			      
				connection.commit();
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		closeConnection();
	}
}
