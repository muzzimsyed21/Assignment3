package ir.assignments.four.database;

public class LoadDatabase {

	public static void main(String[] args) {
		//List<File> files = Util.getFilesInPath(IndexerLocations.fileDump);

		//construct and initiate database
		ICSDumpDatabase termDatabase = new ICSDumpDatabase("root", "Password1", "ICSDump");
		termDatabase.create(); //database name => "ICSDump" call this only once!
		termDatabase.close();
		//store termToTermId (both ways)

		//store DocIdToTermId (both ways)

		//store DocIDToUrl (both ways) 

	}

}
