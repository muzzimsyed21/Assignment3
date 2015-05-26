package ir.assignments.four.database;

import java.sql.SQLException;

import ir.assignments.four.indexer.Indexer;

public class LoadDatabase {

	public static void main(String[] args) throws SQLException {

		// initialize indexer maps
		Indexer.init();

		// construct and initiate database
		ICSDumpDatabase termDatabase = new ICSDumpDatabase("root", "Password1", "ICSDump");
		termDatabase.createDatabase(); //COMMENT THIS OUT AFTER FIRST RUN
		termDatabase.createTables(); //COMMENT THIS OUT AFTER FIRST RUN
		
		//termDatabase.setConnectionAfterDatabaseCreation(); //UNCOMMENT THIS AFTER FIRST RUN
		// store TermToTermId
		//termDatabase.insertTermToTermIdTable(Indexer.getTermToTermIdMap());

		// store TermIdToTermFreq
		//termDatabase.insertTermIdToTermFrequencyTable(Indexer.getTermIdToTermFrequencyMap());

		// store DocIdToTermId and 
		//termDatabase.insertDocIdsToTermIdTables(Indexer.getDocIdToTermIdsMap());

		// store TermIdToDocId
		//termDatabase.insertTermIdToDocIdTables(Indexer.getTermIdToDocIdMap());
		
		// store DocIdToUrl 
		//termDatabase.insertDocIDToUrlTable(Indexer.getDocIdToUrlMap()); //NEEDS CONSTRAINTS UPDATED

		//close connection 
		termDatabase.close();
	}

}
