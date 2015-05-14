package ir.assignments.four.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Scanner;

public class ICSDumpDatabase {

	private Map<Integer, String> termIdToTermMap;
	private String username; 
	private String password; 	
	private Connection connection;
	private String databaseName = null;

	public ICSDumpDatabase(String username, String password) {
		this.username = username;
		this.password = password; 
	}

	public void create() {

		try {

			connect();
			Statement statement = connection.createStatement();
			this.databaseName = "ICSDump";
			statement.executeUpdate("CREATE DATABASE " + databaseName);
			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}

	}

	public void insertTermToTermId(Map<String, Integer> map) {

		try {

			connect();
			Statement tableStatement = connection.createStatement(); 
			String tableName = "Terms"; 
			String table = "CREATE TABLE "+tableName+
				           "(id INTEGER NOT NULL, "+
				           "term VARCHAR(255));"; 
					    
			tableStatement.executeUpdate(table); 

			//			for (Integer id: termIdToTermMap.keySet()){
			//
			//	            String term = termIdToTermMap.get(id).toString(); 
			//	            
			//	            String query = "INSERT INTO "+tableName+" (id, term) VALUES (?, ?)";
			//	            PreparedStatement preparedStatement = connection.prepareStatement(query);
			//	            preparedStatement.setString(id, term);
			//			} 

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void insertTermIdToTerm(){

		try {

			connect();
			Statement tableStatement = connection.createStatement();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insertTermIdToTermFrequency(){
		
		try {

			connect();
			Statement tableStatement = connection.createStatement();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public void insertDocIdToTermId(){
		
		try {

			connect();
			Statement tableStatement = connection.createStatement();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public void insertTermIdToDocId(){

		try {

			connect();
			Statement tableStatement = connection.createStatement();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void insertDocIdToUrl(){
		
		try {

			connect();
			Statement tableStatement = connection.createStatement();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}


	private void connect() throws SQLException {

		if (this.databaseName == null) {

			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/?user="
					+ this.username + "&password=" + this.password);
			this.connection = connection;
		}

		else {

			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/"
					+ this.databaseName + "?user=" + this.username + "&password=" + this.password);
			this.connection = connection;
		}

	}

}