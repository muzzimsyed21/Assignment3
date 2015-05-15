package ir.assignments.four.database;

<<<<<<< HEAD
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.assignments.four.indexer.CreateIndex;
import ir.assignments.four.indexer.IndexerLocations;
import ir.assignments.four.indexer.LoadIndex; 
import ir.assignments.four.util.Util;

=======
>>>>>>> a261fe4200189ed365d73385f290f7478e720a3b
public class LoadDatabase {

	public static void main(String[] args) {
		//List<File> files = Util.getFilesInPath(IndexerLocations.fileDump);

		//construct and initiate database
		ICSDumpDatabase termDatabase = new ICSDumpDatabase("root", "Password1", "ICSDump");
		termDatabase.create(); //database name => "ICSDump" call this only once!
		termDatabase.close();
		//store termToTermId (both ways)
<<<<<<< HEAD
		Map<String, Integer> termIdToTermMap = LoadIndex.loadTermToTermIdMap(IndexerLocations.termToTermIdCSV); 
		termDatabase.insertTermToTermIdTable(termIdToTermMap); 
		
		
=======

>>>>>>> a261fe4200189ed365d73385f290f7478e720a3b
		//store DocIdToTermId (both ways)

		//store DocIDToUrl (both ways) 

	}

}
