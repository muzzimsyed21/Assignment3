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
		// TODO Auto-generated method stub
		//List<File> files = Util.getFilesInPath(IndexerLocations.fileDump);

		//construct and initiate database
		ICSDumpDatabase termDatabase = new ICSDumpDatabase("root", "Password1", "ICSDump"); 
		termDatabase.create(); //database name => "ICSDump" call this only once!
		
		//store termToTermId (both ways)
		Map<String, Integer> termIdToTermMap = LoadIndex.loadTermToTermIdMap(IndexerLocations.termToTermIdCSV); 
		termDatabase.insertTermToTermIdTable(termIdToTermMap); 
		
		
		//store DocIdToTermId (both ways)
		
		//store DocIDToUrl (both ways) 
		
	

	}

}
