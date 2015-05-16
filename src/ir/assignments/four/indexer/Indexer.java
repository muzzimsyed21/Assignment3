package ir.assignments.four.indexer;

import ir.assignments.four.util.Util;

import java.io.File;
import java.util.Map;
import java.util.List;
import java.util.Set;

public class Indexer {

	/** toggle true to recreate index **/
	private final static boolean CREATEFLAG = false;

	/** term to term id map **/
	public static Map<String, Integer> termToTermIdMap;

	/** term id to term map **/
	private static Map<Integer, String> termIdToTermMap;

	/** term id to term frequency map **/
	private static Map<Integer, Integer> termIdToTermFrequencyMap;

	/** doc id to term id map **/
	public static Map<Integer, List<Integer>> docIdToTermIdsMap;

	/** term id to doc id map **/
	private static Map<Integer, Set<Integer>> termIdToDocIdMap;

	/** doc id to url map **/
	public static Map<Integer, String> docIdToUrlMap;

	public static void main(String[] args) {
		List<File> files = Util.getFilesInPath(IndexerLocations.fileDump);
		
		if (CREATEFLAG) {
			termToTermIdMap = CreateIndex.createTermToTermIdMap(files);
			termIdToTermMap = LoadIndex.loadTermIdToTermMap(termToTermIdMap);
			termIdToTermFrequencyMap = CreateIndex.createTermIdToTermFrequencyMap(files, termToTermIdMap);
			docIdToTermIdsMap = CreateIndex.createDocIdToTermIdsMap(files, termToTermIdMap);
			termIdToDocIdMap = LoadIndex.loadTermIdToDocIdMap(docIdToTermIdsMap, termToTermIdMap.size());
			docIdToUrlMap = CreateIndex.createDocIdToURLMap(files);

			// save maps to .csv
			SaveIndex.saveTermToTermIdMap(termIdToTermMap, IndexerLocations.termToTermIdCSV);
			SaveIndex.saveTermIdToTermFrequencyMap(termIdToTermFrequencyMap, IndexerLocations.termIdToTermFrequencyCSV);
			SaveIndex.saveDocIdToTermIdsMap(docIdToTermIdsMap, IndexerLocations.docIdToTermIdCSV);
			SaveIndex.saveDocIdToUrlMap(docIdToUrlMap, IndexerLocations.docIdToUrlCSV);

		} else {
			termToTermIdMap = LoadIndex.loadTermToTermIdMap(IndexerLocations.termToTermIdCSV);
			termIdToTermMap = LoadIndex.loadTermIdToTermMap(termToTermIdMap);
			termIdToTermFrequencyMap = LoadIndex.loadTermIdToTermFrequencyMap(IndexerLocations.termIdToTermFrequencyCSV);
			docIdToTermIdsMap = LoadIndex.loadDocIdToTermIdsMap(IndexerLocations.docIdToTermIdCSV);
			termIdToDocIdMap = LoadIndex.loadTermIdToDocIdMap(docIdToTermIdsMap, termToTermIdMap.size());
			docIdToUrlMap = LoadIndex.loadDocIdToUrlMap(IndexerLocations.docIdToUrlCSV);
		}

		/*
		System.out.println(termToTermIdMap.size());
		System.out.println(termIdToTermMap.size());
		System.out.println(termIdToTermFrequencyMap.size());
		System.out.println(docIdToTermIdMap.size());
		System.out.println(termIdToDocIdMap.size());
		System.out.println(docIdToUrlMap.size());
		
		System.out.println(docIdToTermIdMap.get(2));
		System.out.println(termIdToDocIdMap.get(0));
		System.out.println(docIdToUrlMap.get(0));
		*/

		System.out.println(getTermId("navigation"));
		System.out.println(getDocsWithTerm("ics"));
		System.out.println(getTermFrequencyInDoc(9, "ics"));

		System.out.println("DONE");
	}

	/** return term frequency of term in doc **/
	public static int getTermFrequencyInDoc(int docID, String term) {
		List<Integer> termIDs = docIdToTermIdsMap.get(docID);
		int termID = getTermId(term);
		
		if (termIDs != null && termID != -1) {
			int count = 0;
			for (int id : termIDs) {
				if (id == termID) {
					++count;
				}
			}
			return count;
		} else {
			return -1;
		}
	}
	
	/** returns term id of term **/
	public static int getTermId(String term) {
		if (termToTermIdMap.containsKey(term)) {
			return termToTermIdMap.get(term);
		} else {
			return -1;
		}
	}

	/** return set of all docs containing term **/
	public static Set<Integer> getDocsWithTerm(String term) {
		int termID = getTermId(term);
		if (termID != -1) {
			return termIdToDocIdMap.get(termID);
		}
		return null;
	}

}
