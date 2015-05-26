package ir.assignments.four.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ICSDumpDatabase {

	private String username;
	private String password;
	private String databaseName = null;
	private Connection connection = null;
	private int batchLoadSize;

	public ICSDumpDatabase(String username, String password, String databaseName) {
		this.username = username;
		this.password = password;
		this.databaseName = databaseName;
		this.batchLoadSize = 1000;
	}

	public void createDatabase() {
		try {

			initDb();
			setConnectionAfterDatabaseCreation();

		} catch (SQLException e) {
			//e.printStackTrace();
		}
	}
	
	public void createTables(){
		
		try {
			
			initTermIdToTermTable();
			initTermIdToTermFrequency();
			initDocIdToTermIdTable();
			initTermIdToDocIdTable();
			initDocIdToUrlTable();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	private void initTermIdToTermTable() throws SQLException {
		PreparedStatement statement;

		String qCreateTblTerms = "CREATE TABLE IF NOT EXISTS icsdump.termidtoterm "
				+ "(termid INT NOT NULL," + "term VARCHAR(64)," + "PRIMARY KEY (termid));";

		statement = this.connection.prepareStatement(qCreateTblTerms);
		statement.executeUpdate();
		System.out.println("Initialized termIdToTerm table");
	}

	private void initTermIdToTermFrequency() throws SQLException {
		PreparedStatement statement;

		String qCreateTblTerms = "CREATE TABLE IF NOT EXISTS icsdump.termidtotermfrequency "
				+ "(termid INT NOT NULL," + "termFrequency INT NOT NULL,"
				+ "FOREIGN KEY (termid) REFERENCES termidtoterm(termid));";

		statement = this.connection.prepareStatement(qCreateTblTerms);
		statement.executeUpdate();
		System.out.println("Initialized termIdToTermFrequency table");
	}

	private void initDocIdToTermIdTable() throws SQLException {
		PreparedStatement statement;

		String qCreateTblTerms = "CREATE TABLE IF NOT EXISTS icsdump.docidtotermid"
				+ "(docid INT NOT NULL," + "termid INT);";
		
		//," + "PRIMARY KEY (docid),"+ "FOREIGN KEY (termid) REFERENCES termtotermid(termid)
		statement = this.connection.prepareStatement(qCreateTblTerms);
		statement.executeUpdate();
		System.out.println("Initialized DocIdToTermId table");
	}

	private void initTermIdToDocIdTable() throws SQLException {
		PreparedStatement statement;

		String qCreateTblTerms = "CREATE TABLE IF NOT EXISTS icsdump.termidtodocid "
				+ "(termid INT NOT NULL," + "docid INT NOT NULL);";

		//"+ "FOREIGN KEY (termid) REFERENCES docidtotermid(termid)
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

	public int insertTermIdToTermTable(Map<String, Integer> map) {

		final String termIdToTermQuery = "INSERT INTO termidtoterm (termid, term) VALUES(?,?)";
		PreparedStatement insert = null;

		int result = 0;
		try {
			 
			int currentBatchSize = 0; 
			insert = this.connection.prepareStatement(termIdToTermQuery);

			for (String m : map.keySet()) {
				insert.setInt(1, map.get(m));
				insert.setString(2, m);
				insert.addBatch();
				
				if (++currentBatchSize % this.batchLoadSize == 0){
					System.out.println("adding " + currentBatchSize);
					insert.executeBatch(); 
					insert.clearBatch();
				}

			}

			insert.executeBatch();
			insert.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("TermIdToTerm Table Stored");

		return result;
	}

	public int insertTermIdToTermFrequencyTable(Map<Integer, Integer> map) {

		final String termIdToTermFreqQuery = "INSERT INTO termidtotermfrequency"
				+ "(termid,termFrequency) VALUES (?,?);";

		PreparedStatement insert = null;
		int result = 0;

		try {
			
			int currentBatchSize = 0;
			insert = this.connection.prepareStatement(termIdToTermFreqQuery);

			for (Integer m : map.keySet()) {
				insert.setInt(1, m);
				insert.setInt(2, map.get(m));
				insert.addBatch();

				if (++currentBatchSize % this.batchLoadSize == 0){		
					insert.executeBatch(); 
					insert.clearBatch();
				}
			}

			insert.executeBatch();
			insert.close();
			this.connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("TermIdToTermFrequency Table Stored");

		return result;

	}

	public int insertDocIdsToTermIdTables(Map<Integer, List<Integer>> map) {

		final String DocIdToTermIdQuery = "INSERT INTO docidtotermid"
				+ "(docid,termid) VALUES (?,?);";

		PreparedStatement insert = null;

		int result = 0;

		//System.out.println(map); 
		try {

			insert = this.connection.prepareStatement(DocIdToTermIdQuery);
			int currentBatchSize = 0;
			
			for (Integer docID : map.keySet()) {
				
				for (Integer termID : map.get(docID)) {
					
					insert.setInt(1, docID);
					insert.setInt(2, termID);
					insert.addBatch();

					if (++currentBatchSize % this.batchLoadSize == 0){
						insert.executeBatch(); 
						insert.clearBatch();
					}
					
				}
			}

			insert.executeBatch();
			insert.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("DocIdToTermID Table Stored");

		return result;
	}

	public int insertTermIdToDocIdTables(Map<Integer, Set<Integer>> map) {

		final String TermIdToDocIdQuery = "INSERT INTO termidtodocid"
				+ "(termid,docid) VALUES (?,?);";

		PreparedStatement insert = null;
		int result = 0;

		//System.out.println(map); 
		try {

			insert = this.connection.prepareStatement(TermIdToDocIdQuery);
			int currentBatchSize = 0;
			
			for (Integer termID : map.keySet()) {
				for (Integer docID : map.get(termID)) {
					insert.setInt(1, termID);
					insert.setInt(2, docID);
					insert.addBatch();
					
					if (++currentBatchSize % this.batchLoadSize == 0){
						insert.executeBatch(); 
						insert.clearBatch();
					}
				}
				
			}

			insert.executeBatch();
			insert.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("TermIDToDocId Table Stored");

		return result;

	}

	public int insertDocIDToUrlTable(Map<Integer, String> map) {

		final String termIdToTermFreqQuery = "INSERT INTO docidtourl" + "(docid,url) VALUES (?,?);";

		PreparedStatement insert = null;
		int result = 0;

		System.out.println(map);
		try {

			insert = this.connection.prepareStatement(termIdToTermFreqQuery);
			int currentBatchSize = 0;

			for (Integer m : map.keySet()) {

				insert.setInt(1, m);
				insert.setString(2, map.get(m));
				insert.addBatch();
				
				if (++currentBatchSize % this.batchLoadSize == 0){
					insert.executeBatch(); 
					insert.clearBatch();
				}

			}

			insert.executeBatch();

			insert.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("DocIdToUrl Table Stored");

		return result;
	}

	private Connection getInitialConnection() throws SQLException {
		String connectionString = "jdbc:mysql://localhost/?user="
				+ this.username + "&password=" + this.password;
		//System.out.println(connectionString); 
		Connection connection = DriverManager.getConnection(connectionString);
		return connection;
	}

	public void setConnectionAfterDatabaseCreation() throws SQLException {
		
		String connectionString = "jdbc:mysql://localhost/" + this.databaseName
				+ "?user=" + this.username + "&password=" + this.password + "&rewriteBatchedStatements=true"; 
		//System.out.println(connectionString); 
		this.connection = DriverManager.getConnection(connectionString);
		
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