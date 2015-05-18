package ir.assignments.four.database;

import java.sql.SQLException;

import ir.assignments.four.indexer.Indexer;

public class LoadDatabase {

	public static void main(String[] args) throws SQLException {

		// initialize indexer maps
		Indexer.init();

		// construct and initiate database
		ICSDumpDatabase termDatabase = new ICSDumpDatabase("root", "Password1", "ICSDump");
		termDatabase.create(); //database name => "ICSDump" call this only once!

		// store TermToTermId
		termDatabase.insertTermToTermIdTables(Indexer.getTermToTermIdMap());

		// store TermIdToTermFreq
		termDatabase.insertTermIdToTermFrequencyTable(Indexer.getTermIdToTermFrequencyMap());

		// store DocIdToTermId and 
		termDatabase.insertDocIdsAndTermIdTables(Indexer.getDocIdToTermIdsMap());

		// store TermIdToDocId
		termDatabase.insertTermIdToDocIdTables(Indexer.getTermIdToDocIdMap());
		
		// store DocIdToUrl 
		termDatabase.insertDocIDToUrlTable(Indexer.getDocIdToUrlMap()); //NEEDS CONSTRAINTS UPDATED

		//close connection 
		termDatabase.close();
	}

}
