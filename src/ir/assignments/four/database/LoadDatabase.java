package ir.assignments.four.database;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.assignments.four.indexer.CreateIndex;
import ir.assignments.four.indexer.Indexer;
import ir.assignments.four.indexer.IndexerLocations;
import ir.assignments.four.indexer.LoadIndex;
import ir.assignments.four.util.Util;

public class LoadDatabase {

	public static void main(String[] args) throws SQLException {
		List<File> files = Util.getFilesInPath(IndexerLocations.fileDump);

		//construct and initiate database
		ICSDumpDatabase termDatabase = new ICSDumpDatabase("root", "Password1", "ICSDump");
		termDatabase.create(); //database name => "ICSDump" call this only once!
		
		
		//store TermToTermId and TermIdToTerm
		termDatabase.insertTermAndTermIDTables(CreateIndex.createTermToTermIdMap(files));
		
		//store TermIdToTermFreq --How to store this one given Map<Integer, List<Integer>> as input.
		//termDatabase.insertTermIdToTermFrequencyTable(CreateIndex.createDocIdToTermIdsMap(files, Indexer.termToTermIdMap));
		
		//store DocIdToTermId and TermIdToDocId ------(GIVING A NULLPTR ERROR WHEN GETTING THIS INDEX) -----
		//termDatabase.insertDocIdsAndTermIdTables(CreateIndex.createDocIdToTermIdsMap(files, Indexer.termToTermIdMap)); 
		
		//store DocIdToUrl 
		termDatabase.insertDocIDToUrlTable(CreateIndex.createDocIdToURLMap(files)); //NEEDS CONSTRAINTS UPDATED
		
		//close connection 
		termDatabase.close();
	}

}
