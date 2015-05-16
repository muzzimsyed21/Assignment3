package ir.assignments.four.database;

import ir.assignments.four.indexer.IndexerLocations;
import ir.assignments.four.util.Util;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Map;
import java.util.Scanner;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

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
			initTermIdToTermTable(); 
			initTermIdToTermFrequency(); 
			initDocIdToTermIdTable();
			initTermIdToDocIdTable(); 
			initDocIdToUrlTable();
			
		} catch (SQLException e) {
			//e.printStackTrace();
		}
	}

	private void initDb() {
		try {
			
			Connection connection = getInitialConnection();
			String qCreateDb = String.format("CREATE DATABASE IF NOT EXISTS %s;", this.databaseName);
			PreparedStatement statement = connection.prepareStatement(qCreateDb);
			statement.executeUpdate();
			connection.close();
			System.out.println("Initialized database");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void initTermToTermIdTable() throws SQLException {
		PreparedStatement statement;

		String qCreateTblTerms = "CREATE TABLE IF NOT EXISTS icsdump.termtotermid "
				+ "(termid INT NOT NULL," + "term VARCHAR(64)," + "PRIMARY KEY (termid));";

		statement = this.connection.prepareStatement(qCreateTblTerms);
		statement.executeUpdate();
		System.out.println("Initialized termToTermId table");
	}
	
	private void initTermIdToTermTable() throws SQLException {
		PreparedStatement statement;

		String qCreateTblTerms = "CREATE TABLE IF NOT EXISTS icsdump.termidtoterm "
				+ "(termid INT NOT NULL," + "term VARCHAR(64)," 
				+ "FOREIGN KEY (termid) REFERENCES termtotermid(termid));";

		statement = this.connection.prepareStatement(qCreateTblTerms);
		statement.executeUpdate();
		System.out.println("Initialized termIdToTerm table");
	}
	
	private void initTermIdToTermFrequency() throws SQLException {
		PreparedStatement statement;

		String qCreateTblTerms = "CREATE TABLE IF NOT EXISTS icsdump.termidtotermfrequency "
				+ "(termid INT NOT NULL," + "termFrequency INT NOT NULL," 
				+ "FOREIGN KEY (termid) REFERENCES termtotermid(termid));";

		statement = this.connection.prepareStatement(qCreateTblTerms);
		statement.executeUpdate();
		System.out.println("Initialized termIdToTermFrequency table");
	}

	private void initDocIdToTermIdTable() throws SQLException {
		PreparedStatement statement;

		String qCreateTblTerms = "CREATE TABLE IF NOT EXISTS icsdump.docidtotermid "
				+ "(docid INT NOT NULL," + "termid INT," + "PRIMARY KEY (docid),"
				+ "FOREIGN KEY (termid) REFERENCES termtotermid(termid));";

		statement = this.connection.prepareStatement(qCreateTblTerms);
		statement.executeUpdate();
		System.out.println("Initialized DocIdToTermId table");

	}

	private void initTermIdToDocIdTable() throws SQLException {
		PreparedStatement statement;

		String qCreateTblTerms = "CREATE TABLE IF NOT EXISTS icsdump.termidtodocid "
				+ "(termid INT NOT NULL," + "docid INT NOT NULL," 
				+ "FOREIGN KEY (termid) REFERENCES docidtotermid(termid));";

		statement = this.connection.prepareStatement(qCreateTblTerms);
		statement.executeUpdate();
		System.out.println("Initialized TermIdToDocId table");

	}
	
	private void initDocIdToUrlTable() throws SQLException {
		PreparedStatement statement;

		String qCreateTblTerms = "CREATE TABLE IF NOT EXISTS icsdump.docidtourl "
				+ "(docid INT NOT NULL," + "url VARCHAR(255));";
		
		//,"+ "FOREIGN KEY (docid) REFERENCES docidtotermid(docid) <<==== UPDATE CONSTRAINTS
		statement = this.connection.prepareStatement(qCreateTblTerms);
		statement.executeUpdate();
		System.out.println("Initialized DocIdToUrl table");
	}

	public int insertTermAndTermIDTables(Map<String, Integer> map) {
		
		final String termToTermIdQuery = "INSERT INTO termtotermid" + "(term,termid) VALUES (?,?);";
		final String termIdToTermQuery = "INSERT INTO termidtoterm" + "(termid,term) VALUES (?,?);";
		
		PreparedStatement insert1 = null;
		PreparedStatement insert2 = null;
		
		
		int result = 0;
		try {

			insert1 = this.connection.prepareStatement(termToTermIdQuery);
			insert2 = this.connection.prepareStatement(termIdToTermQuery);
			
			for (String m : map.keySet()) {

				insert1.setString(1, m);
				insert1.setInt(2, map.get(m));
				insert1.addBatch();
				
				insert2.setInt(1,map.get(m)); 
				insert2.setString(2, m);
				insert2.addBatch();

			}

			insert1.executeBatch();
			insert2.executeBatch(); 

			insert1.close();
			insert2.close(); 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("TermToTermId and TermIDToTerm Tables Stored");

		return result;
	}
	
	public int insertTermIdToTermFrequencyTable(Map<Integer, List<Integer>> map){
		
		final String termIdToTermFreqQuery = "INSERT INTO termidtotermfrequency" + "(termid,termFrequency) VALUES (?,?);";		
		
		PreparedStatement insert = null;
		int result = 0;
		
		try {

			insert = this.connection.prepareStatement(termIdToTermFreqQuery);
			
			for (Integer m : map.keySet()) {

				insert.setInt(1, m);
				//insert.setInt(2, map.get(m));
				insert.addBatch();

			}

			insert.executeBatch();
			
			insert.close();

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("TermIdToTermFrequency Table Stored");

		return result;		
		
	}

	public int insertDocIdsAndTermIdTables(Map<Integer, List<Integer>> map) {

		final String DocIdToTermIdQuery = "INSERT INTO docidtotermid" + "(docid,termid) VALUES (?,?);";
		final String TermIdToDocIdQuery = "INSERT INTO termidtodocid" + "(termid,docid) VALUES (?,?);";
		
		PreparedStatement insert1 = null;
		PreparedStatement insert2 = null;
		
		int result = 0;
		
		//System.out.println(map); 
		try {

			insert1 = this.connection.prepareStatement(DocIdToTermIdQuery);
			insert2 = this.connection.prepareStatement(TermIdToDocIdQuery);
			
			for (Integer m : map.keySet()) {

				insert1.setInt(1, m);
				//insert1.setInt(2, map.get(m)); A List of Term Ids
				insert1.addBatch();
				
				//insert2.setInt(1,map.get(m)); A List of Term Ids
				insert2.setInt(2, m);
				insert2.addBatch();

			}

			insert1.executeBatch();
			insert2.executeBatch(); 

			insert1.close();
			insert2.close(); 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("DocIdToTermID and TermIDToDocId Tables Stored");

		return result;
		

	}

	public int insertDocIDToUrlTable(Map<Integer, String> map) {

		final String termIdToTermFreqQuery = "INSERT INTO docidtourl" + "(docid,url) VALUES (?,?);";		
		
		PreparedStatement insert = null;
		int result = 0;

		System.out.println(map); 
		try {

			insert = this.connection.prepareStatement(termIdToTermFreqQuery);
			
			for (Integer m : map.keySet()) {

				insert.setInt(1, m);
				insert.setString(2, map.get(m));
				insert.addBatch();

			}

			insert.executeBatch();
			
			insert.close();

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("DocIdToUrl Table Stored");

		return result;			
	}
	
	private Connection getInitialConnection() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/?user="
				+ this.username + "&password=" + this.password);
		return connection;
	}

	public void setConnectionAfterDatabaseCreation() throws SQLException {
		this.connection = DriverManager.getConnection("jdbc:mysql://localhost/" + this.databaseName
				+ "?user=" + this.username + "&password=" + this.password);
	}
	

	/** close connection object **/
	public void close() {
		try {
			if (this.connection != null) {
				this.connection.close();
			}
		} catch (SQLException e) {
		}
		this.connection = null;
	}

}