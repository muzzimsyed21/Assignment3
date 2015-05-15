package ir.assignments.four.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;
import java.util.Map;
import java.util.Scanner;

public class ICSDumpDatabase {
	
	private String username; 
	private String password; 	
	private String databaseName = null;
	private Connection connection = null; 

	public ICSDumpDatabase(String username, String password, String databaseName) {
		this.username = username;
		this.password = password; 
		this.databaseName = databaseName;
	}

	public void create() {

		try {
			 
			initDb();
			setConnectionAfterDatabaseCreation(); 
			initTermToTermIdTable(); 
			initDocIdToTermIdTable(); 
			initDocIDToUrlIdTable();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			//e.printStackTrace();
		}

	}
	
	private void initDb(){
		
		try {
			
			Connection connection = getConnection();
			String qCreateDb = String.format("create database if not exists %s;", this.databaseName);
			PreparedStatement statement = connection.prepareStatement(qCreateDb);
			statement.executeUpdate();
			connection.close();
			System.out.println("Initialized database");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		

	}
	

	private void initTermToTermIdTable() throws SQLException {
		
		PreparedStatement statement;
		String qCreateTblTerms = "CREATE TABLE IF NOT EXISTS icsdump.termtotermid "
				+ "(termid INT NOT NULL,"
				+ "term VARCHAR(64),"
				+ "PRIMARY KEY (termid));"; 	
		statement = this.connection.prepareStatement(qCreateTblTerms);
		statement.executeUpdate();
		System.out.println("Initialized termToTermId table");
		
	}

	private void initDocIdToTermIdTable() throws SQLException {

		PreparedStatement statement;
		
		String qCreateTblTerms = "CREATE TABLE IF NOT EXISTS icsdump.docidtotermid "
				+ "(docid INT NOT NULL,"
				+ "termid INT,"
				+ "PRIMARY KEY (docid),"
				+ "FOREIGN KEY (termid) REFERENCES termtotermid(termid));"; 
		
		statement = this.connection.prepareStatement(qCreateTblTerms);
		statement.executeUpdate();
		System.out.println("Initialized DocIdToTermId table");
		
	}

	private void initDocIDToUrlIdTable() throws SQLException {

		PreparedStatement statement;
		
		String qCreateTblTerms = "CREATE TABLE IF NOT EXISTS icsdump.docidtourlid "
				+ "(urlid INT NOT NULL,"
				+ "docid INT,"
				+ "PRIMARY KEY (urlid),"
				+ "FOREIGN KEY (docid) REFERENCES docidtotermid(docid));"; 
		
		
		statement = this.connection.prepareStatement(qCreateTblTerms);
		statement.executeUpdate();
		System.out.println("Initialized DocIdToUrlId table");
	}
	
	//create insert functions
	
	private Connection getConnection() throws SQLException{
		
		
		Connection connection= DriverManager.getConnection("jdbc:mysql://localhost/?user=" 
		+this.username + "&password=" + this.password);
		
		return connection; 
	}
	
	private void setConnectionAfterDatabaseCreation() throws SQLException{
		
		
		this.connection= DriverManager.getConnection("jdbc:mysql://localhost/"+ this.databaseName 
				+ "?user=" + this.username + "&password=" + this.password);
	}

}