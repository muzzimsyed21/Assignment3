package ir.assignments.four.database;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.assignments.four.indexer.CreateIndex;
import ir.assignments.four.indexer.IndexerLocations;
import ir.assignments.four.indexer.LoadIndex;
import ir.assignments.four.util.Util;

public class LoadDatabase {

	public static void main(String[] args) {
		List<File> files = Util.getFilesInPath(IndexerLocations.fileDump);

		//construct and initiate database
		ICSDumpDatabase termDatabase = new ICSDumpDatabase("root", "Password1", "ICSDump");
		termDatabase.create(); //database name => "ICSDump" call this only once!
		termDatabase.close();
		
		
		//store TermToTermId and TermIdToTerm
		termDatabase.insertTermAndTermIDTables(CreateIndex.createTermToTermIdMap(files));
		
		//store TermIdToTermFreq
		//termDatabase.insertDocIdToTermIdTable(CreateIndex.createDocIdToTermIdsMap(files, termToTermIdMap));
		
		//store DocIdToTermId and TermIdToDocId
		//termDatabase.insertDocIDToUrlIdTable(CreateIndex.createDocIdToURLMap(files));
		
		//store DocIdToUrl 
		//termDatabase.
	}

}
