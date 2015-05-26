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
<<<<<<< .merge_file_a00188
=======
	private int batchLoadSize;
>>>>>>> .merge_file_a05492

	public ICSDumpDatabase(String username, String password, String databaseName) {
		this.username = username;
		this.password = password;
		this.databaseName = databaseName;
<<<<<<< .merge_file_a00188
	}

	public void create() {
=======
		this.batchLoadSize = 1000;
	}

	public void createDatabase() {
>>>>>>> .merge_file_a05492
		try {

			initDb();
			setConnectionAfterDatabaseCreation();
<<<<<<< .merge_file_a00188
			initTermToTermIdTable();
			//initTermIdToTermTable();
=======

		} catch (SQLException e) {
			//e.printStackTrace();
		}
	}
	
	public void createTables(){
		
		try {
			
			initTermToTermIdTable();
>>>>>>> .merge_file_a05492
			initTermIdToTermFrequency();
			initDocIdToTermIdTable();
			initTermIdToDocIdTable();
			initDocIdToUrlTable();
<<<<<<< .merge_file_a00188

		} catch (SQLException e) {
			//e.printStackTrace();
		}
	}

=======
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
>>>>>>> .merge_file_a05492
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

<<<<<<< .merge_file_a00188
	@SuppressWarnings("unused")
	private void initTermIdToTermTable() throws SQLException {
		PreparedStatement statement;

		String qCreateTblTerms = "CREATE TABLE IF NOT EXISTS icsdump.termidtoterm "
				+ "(termid INT NOT NULL," + "term VARCHAR(64),"
				+ "FOREIGN KEY (termid) REFERENCES termtotermid(termid));";

		statement = this.connection.prepareStatement(qCreateTblTerms);
		statement.executeUpdate();
		System.out.println("Initialized termIdToTerm table");
	}

=======
>>>>>>> .merge_file_a05492
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

<<<<<<< .merge_file_a00188
		String qCreateTblTerms = "CREATE TABLE IF NOT EXISTS icsdump.docidtotermid "
				+ "(docid INT NOT NULL," + "termid INT," + "PRIMARY KEY (docid),"
				+ "FOREIGN KEY (termid) REFERENCES termtotermid(termid));";

=======
		String qCreateTblTerms = "CREATE TABLE IF NOT EXISTS icsdump.docidtotermid"
				+ "(docid INT NOT NULL," + "termid INT);";
		
		//," + "PRIMARY KEY (docid),"+ "FOREIGN KEY (termid) REFERENCES termtotermid(termid)
>>>>>>> .merge_file_a05492
		statement = this.connection.prepareStatement(qCreateTblTerms);
		statement.executeUpdate();
		System.out.println("Initialized DocIdToTermId table");
	}

	private void initTermIdToDocIdTable() throws SQLException {
		PreparedStatement statement;

		String qCreateTblTerms = "CREATE TABLE IF NOT EXISTS icsdump.termidtodocid "
<<<<<<< .merge_file_a00188
				+ "(termid INT NOT NULL," + "docid INT NOT NULL,"
				+ "FOREIGN KEY (termid) REFERENCES docidtotermid(termid));";

=======
				+ "(termid INT NOT NULL," + "docid INT NOT NULL);";

		//"+ "FOREIGN KEY (termid) REFERENCES docidtotermid(termid)
>>>>>>> .merge_file_a05492
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

	public int insertTermToTermIdTable(Map<String, Integer> map) {

		final String termToTermIdQuery = "INSERT INTO termtotermid" + "(term,termid) VALUES (?,?);";
<<<<<<< .merge_file_a00188
		//final String termIdToTermQuery = "INSERT INTO termidtoterm" + "(termid,term) VALUES (?,?);";

		PreparedStatement insert1 = null;
		//PreparedStatement insert2 = null;

		int result = 0;
		try {

			insert1 = this.connection.prepareStatement(termToTermIdQuery);
			//insert2 = this.connection.prepareStatement(termIdToTermQuery);

			for (String m : map.keySet()) {

				insert1.setString(1, m);
				insert1.setInt(2, map.get(m));
				insert1.addBatch();

				//insert2.setInt(1, map.get(m));
				//insert2.setString(2, m);
				//insert2.addBatch();

			}

			insert1.executeBatch();
			//insert2.executeBatch();

			insert1.close();
			//insert2.close();
=======
		PreparedStatement insert = null;

		int result = 0;
		try {
			
			 
			int currentBatchSize = 0; 
			insert = this.connection.prepareStatement(termToTermIdQuery);

			for (String m : map.keySet()) {

				insert.setString(1, m);
				insert.setInt(2, map.get(m));
				insert.addBatch();
				
				if (++currentBatchSize % this.batchLoadSize == 0){
					
					insert.executeBatch(); 
					insert.clearBatch();
				}

			}

			insert.executeBatch();
			insert.close();
>>>>>>> .merge_file_a05492

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("TermToTermId Table Stored");

		return result;
	}

	public int insertTermIdToTermFrequencyTable(Map<Integer, Integer> map) {

		final String termIdToTermFreqQuery = "INSERT INTO termidtotermfrequency"
				+ "(termid,termFrequency) VALUES (?,?);";

		PreparedStatement insert = null;
		int result = 0;

		try {
<<<<<<< .merge_file_a00188

=======
			
			int currentBatchSize = 0;
>>>>>>> .merge_file_a05492
			insert = this.connection.prepareStatement(termIdToTermFreqQuery);

			for (Integer m : map.keySet()) {
				insert.setInt(1, m);
				insert.setInt(2, map.get(m));
				insert.addBatch();
<<<<<<< .merge_file_a00188
			}

			insert.executeBatch();

=======

				if (++currentBatchSize % this.batchLoadSize == 0){
					
					insert.executeBatch(); 
					insert.clearBatch();
				}
			}

			insert.executeBatch();
>>>>>>> .merge_file_a05492
			insert.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("TermIdToTermFrequency Table Stored");

		return result;

	}

<<<<<<< .merge_file_a00188
	public int insertDocIdsAndTermIdTables(Map<Integer, List<Integer>> map) {
=======
	public int insertDocIdsToTermIdTables(Map<Integer, List<Integer>> map) {
>>>>>>> .merge_file_a05492

		final String DocIdToTermIdQuery = "INSERT INTO docidtotermid"
				+ "(docid,termid) VALUES (?,?);";

		PreparedStatement insert = null;

		int result = 0;

		//System.out.println(map); 
		try {

			insert = this.connection.prepareStatement(DocIdToTermIdQuery);
<<<<<<< .merge_file_a00188

			for (Integer docID : map.keySet()) {
				for (Integer termID : map.get(docID)) {
					insert.setInt(1, docID);
					insert.setInt(2, termID);
					insert.addBatch();
=======
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
					
					
>>>>>>> .merge_file_a05492
				}
			}

			insert.executeBatch();
<<<<<<< .merge_file_a00188

			insert.close();
=======
			insert.close();
			
			
>>>>>>> .merge_file_a05492
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
<<<<<<< .merge_file_a00188

=======
>>>>>>> .merge_file_a05492
		int result = 0;

		//System.out.println(map); 
		try {

			insert = this.connection.prepareStatement(TermIdToDocIdQuery);
<<<<<<< .merge_file_a00188

=======
			int currentBatchSize = 0;
			
>>>>>>> .merge_file_a05492
			for (Integer termID : map.keySet()) {
				for (Integer docID : map.get(termID)) {
					insert.setInt(1, termID);
					insert.setInt(2, docID);
					insert.addBatch();
<<<<<<< .merge_file_a00188
				}
=======
					
					if (++currentBatchSize % this.batchLoadSize == 0){
						
						insert.executeBatch(); 
						insert.clearBatch();
					}
				}
				
>>>>>>> .merge_file_a05492
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
<<<<<<< .merge_file_a00188
=======
			int currentBatchSize = 0;
>>>>>>> .merge_file_a05492

			for (Integer m : map.keySet()) {

				insert.setInt(1, m);
				insert.setString(2, map.get(m));
				insert.addBatch();
<<<<<<< .merge_file_a00188
=======
				
				if (++currentBatchSize % this.batchLoadSize == 0){
					
					insert.executeBatch(); 
					insert.clearBatch();
				}
>>>>>>> .merge_file_a05492

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
<<<<<<< .merge_file_a00188
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/?user="
				+ this.username + "&password=" + this.password);
=======
		String connectionString = "jdbc:mysql://localhost/?user="
				+ this.username + "&password=" + this.password;
		//System.out.println(connectionString); 
		Connection connection = DriverManager.getConnection(connectionString);
>>>>>>> .merge_file_a05492
		return connection;
	}

	public void setConnectionAfterDatabaseCreation() throws SQLException {
<<<<<<< .merge_file_a00188
		this.connection = DriverManager.getConnection("jdbc:mysql://localhost/" + this.databaseName
				+ "?user=" + this.username + "&password=" + this.password);
	}

=======
		
		String connectionString = "jdbc:mysql://localhost/" + this.databaseName
				+ "?user=" + this.username + "&password=" + this.password; 
		//System.out.println(connectionString); 
		this.connection = DriverManager.getConnection(connectionString);
		
	}
>>>>>>> .merge_file_a05492
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