package ir.assignments.four.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

public class ICSDumpDatabase {

	private HashMap<Integer, String> termIdToTermMap;
	private Connection connection;
	private String databaseName = null;

	public ICSDumpDatabase(HashMap<Integer, String> termIdToTermMap) {

		this.termIdToTermMap = termIdToTermMap;
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

	public void insert() {

		try {

			connect();
			Statement tableStatement = connection.createStatement();

			/*			String tableName = "Terms"; 
					    String table = "CREATE TABLE "+tableName+
				                  		" (id INTEGER not NULL, "+
				                  		"term VARCHAR(255);"; 
					    
						tableStatement.executeUpdate(table); */

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

	private void connect() throws SQLException {

		Scanner input = new Scanner(System.in);
		String username;
		String password;

		System.out.print("Enter your mysql username");
		username = input.next();
		System.out.print("Enter your mysql password");
		password = input.next();

		if (this.databaseName == null) {

			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/?user="
					+ username + "&password=" + password);
			this.connection = connection;
		}

		else {

			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/"
					+ this.databaseName + "?user=" + username + "&password=" + password);
			this.connection = connection;
		}

	}

}