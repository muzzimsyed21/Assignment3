package ir.assignments.four.indexer;

import ir.assignments.four.util.Util;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Indexer {

	/** toggle true to recreate index **/
	private final static boolean CREATEFLAG = false;
	
	/** term to term id map **/
	private static HashMap<String, Integer> termToTermIdMap;

	/** term id to term map **/
	private static HashMap<Integer, String> termIdToTermMap;

	/** term id to term frequency map **/
	private static HashMap<Integer, Integer> termIdToTermFrequencyMap;

	/** doc id to term id map **/
	private static HashMap<Integer, List<Integer>> docIdToTermIdMap;

	/** term id to doc id map **/
	private static HashMap<Integer, Set<Integer>> termIdToDocIdMap;
	
	/** doc id to url map **/
	private static HashMap<Integer, String> docIdToUrlMap;

	public static void main(String[] args) {
		List<File> files = Util.getFilesInPath(IndexerLocations.fileDump);

		if (CREATEFLAG) {
			termToTermIdMap = CreateIndex.createTermToTermIdMap(files);
			termIdToTermMap = LoadIndex.loadTermIdToTermMap(termToTermIdMap);
			termIdToTermFrequencyMap = CreateIndex.createTermIdToTermFrequencyMap(files, termToTermIdMap);
			docIdToTermIdMap = CreateIndex.createDocIdToTermIdMap(files, termToTermIdMap);
			termIdToDocIdMap = LoadIndex.loadTermIdToDocIdMap(docIdToTermIdMap, termToTermIdMap.size());
			docIdToUrlMap = CreateIndex.createDocIdToURLMap(files);
			
			// save maps to .csv
			SaveIndex.saveTermToTermIdMap(termToTermIdMap, termIdToTermMap, IndexerLocations.termToTermIdCSV);
			SaveIndex.saveTermIdToTermFrequencyMap(termIdToTermFrequencyMap, IndexerLocations.termIdToTermFrequencyCSV);
			SaveIndex.saveDocIdToTermIdMap(docIdToTermIdMap, IndexerLocations.docIdToTermIdCSV);
			SaveIndex.saveDocIdToUrlMap(docIdToUrlMap, IndexerLocations.docIdToUrlCSV);
			
		} else {
			termToTermIdMap = LoadIndex.loadTermToTermIdMap(IndexerLocations.termToTermIdCSV);
			termIdToTermMap = LoadIndex.loadTermIdToTermMap(termToTermIdMap);
			termIdToTermFrequencyMap = LoadIndex.loadTermIdToTermFrequencyMap(IndexerLocations.termIdToTermFrequencyCSV);
			docIdToTermIdMap = LoadIndex.loadDocIdToTermIdMap(IndexerLocations.docIdToTermIdCSV);
			termIdToDocIdMap = LoadIndex.loadTermIdToDocIdMap(docIdToTermIdMap, termToTermIdMap.size());
			docIdToUrlMap = LoadIndex.loadDocIdToUrlMap(IndexerLocations.docIdToUrlCSV);
		}
		
		System.out.println(termToTermIdMap.size());
		System.out.println(termIdToTermMap.size());
		System.out.println(termIdToTermFrequencyMap.size());
		System.out.println(docIdToTermIdMap.size());
		System.out.println(termIdToDocIdMap.size());
		System.out.println(docIdToUrlMap.size());
		
		System.out.println(docIdToTermIdMap.get(2));
		System.out.println(termIdToDocIdMap.get(0));
		System.out.println(docIdToUrlMap.get(0));
		
		System.out.println("DONE");
	}

}
