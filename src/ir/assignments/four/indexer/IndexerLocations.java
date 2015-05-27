package ir.assignments.four.indexer;

public class IndexerLocations {
	
	/** file dump path **/
	public static final String fileDump = "testFiles/";
	//public static final String fileDump = "FileDump/";
	
	/** csv path **/
	public static final String csvPath = "csv/";
	
	/** term to term id path **/
	public static final String termToTermIdCSV = csvPath + "termToTermId.csv";
	
	/** term id to term frequency path **/
	public static final String termIdToTermFrequencyCSV = csvPath + "termIdToTermFrequency.csv";
	
	/** doc id to term id path **/
	public static final String docIdToTermIdCSV = csvPath + "docIdToTermId.csv";
	
	/** doc id to url path **/
	public static final String docIdToUrlCSV = csvPath + "docIdToUrl.csv";
	
	/** doc id to term id to tfidf path **/
	public static final String docIdToTermIdToTFIDFCSV = csvPath + "docIdToTermIdToTFIDF.csv";
	
}
