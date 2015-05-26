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
	private static Map<String, Integer> termToTermIdMap;

	/** term id to term map **/
	private static Map<Integer, String> termIdToTermMap;

	/** term id to term frequency map **/
	private static Map<Integer, Integer> termIdToTermFrequencyMap;

	/** doc id to term id map **/
	private static Map<Integer, List<Integer>> docIdToTermIdsMap;

	/** term id to doc id map **/
	private static Map<Integer, Set<Integer>> termIdToDocIdsMap;

	/** doc id to url map **/
	private static Map<Integer, String> docIdToUrlMap;

	/** doc id to term id to TFIDF map **/
	private static Map<Integer, Map<Integer, Double>> docIdToTermIdToTFIDFMap;

	public static void init() {
		List<File> files = Util.getFilesInPath(IndexerLocations.fileDump);

		if (CREATEFLAG) {
			termToTermIdMap = CreateIndex.createTermToTermIdMap(files);
			termIdToTermMap = LoadIndex.loadTermIdToTermMap(termToTermIdMap);
			termIdToTermFrequencyMap = CreateIndex.createTermIdToTermFrequencyMap(files,
					termToTermIdMap);
			docIdToTermIdsMap = CreateIndex.createDocIdToTermIdsMap(files, termToTermIdMap);
			termIdToDocIdsMap = LoadIndex.loadTermIdToDocIdMap(docIdToTermIdsMap,
					termToTermIdMap.size());
			docIdToUrlMap = CreateIndex.createDocIdToURLMap(files);
			docIdToTermIdToTFIDFMap = CreateIndex.createDocIdToTermIdToTFIDFMap(docIdToTermIdsMap,
					termIdToDocIdsMap, termIdToTermFrequencyMap);

			// save maps to .csv
			SaveIndex.saveTermToTermIdMap(termIdToTermMap, IndexerLocations.termToTermIdCSV+"demo");
			SaveIndex.saveTermIdToTermFrequencyMap(termIdToTermFrequencyMap,
					IndexerLocations.termIdToTermFrequencyCSV+"demo");
			SaveIndex.saveDocIdToTermIdsMap(docIdToTermIdsMap, IndexerLocations.docIdToTermIdCSV+"demo");
			SaveIndex.saveDocIdToUrlMap(docIdToUrlMap, IndexerLocations.docIdToUrlCSV+"demo");
			SaveIndex.saveDocIdToTermIdToTFIDFMap(docIdToTermIdToTFIDFMap,
					IndexerLocations.docIdToTermIdToTFIDFCSV+"demo");
			
		} else {
			/*
			termToTermIdMap = LoadIndex.loadTermToTermIdMap(IndexerLocations.termToTermIdCSV);
			termIdToTermMap = LoadIndex.loadTermIdToTermMap(termToTermIdMap);
			termIdToTermFrequencyMap = LoadIndex.loadTermIdToTermFrequencyMap(IndexerLocations.termIdToTermFrequencyCSV);
			docIdToTermIdsMap = LoadIndex.loadDocIdToTermIdsMap(IndexerLocations.docIdToTermIdCSV);
			termIdToDocIdsMap = LoadIndex.loadTermIdToDocIdMap(docIdToTermIdsMap,
					termToTermIdMap.size());
			*/
			docIdToUrlMap = LoadIndex.loadDocIdToUrlMap(IndexerLocations.docIdToUrlCSV);
			//docIdToTermIdToTFIDFMap = LoadIndex.loadDocIdToTermIdToTFIDFMap(IndexerLocations.docIdToTermIdToTFIDFCSV);
			
		}
	}

	public static void main(String[] args) {
		long startTime = System.nanoTime();
		
		init();

		System.out.println(termToTermIdMap.size());
		/*
		System.out.println(termIdToTermMap.size());
		System.out.println(termIdToTermFrequencyMap.size());
		System.out.println(docIdToTermIdsMap.size());
		System.out.println(termIdToDocIdsMap.size());
		System.out.println(docIdToUrlMap.size());
		System.out.println(docIdToTermIdToTFIDFMap.size());
		for (String key : termToTermIdMap.keySet()) {
			System.out.println(key + ": " + termToTermIdMap.get(key));
		}
		
		/*
		System.out.println(docIdToTermIdsMap.get(2));
		System.out.println(termIdToDocIdMap.get(0));
		System.out.println(docIdToUrlMap.get(0));
		
		System.out.println(getTermId("navigation"));
		System.out.println(getDocsWithTerm("ics"));
		System.out.println(getTermFrequencyInDoc(9, "ics"));
		System.out.println(docIdToTermIdToTFIDFMap.get(2).values());
		*/

		System.out.println("DONE");
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
		System.out.println(duration / 1000000);
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
			return termIdToDocIdsMap.get(termID);
		}
		return null;
	}

	public static Map<String, Integer> getTermToTermIdMap() {
		return termToTermIdMap;
	}

	public static Map<Integer, String> getTermIdToTermMap() {
		return termIdToTermMap;
	}

	public static Map<Integer, Integer> getTermIdToTermFrequencyMap() {
		return termIdToTermFrequencyMap;
	}

	public static Map<Integer, List<Integer>> getDocIdToTermIdsMap() {
		return docIdToTermIdsMap;
	}

	public static Map<Integer, Set<Integer>> getTermIdToDocIdMap() {
		return termIdToDocIdsMap;
	}

	public static Map<Integer, String> getDocIdToUrlMap() {
		return docIdToUrlMap;
	}

	public static Map<Integer, Map<Integer, Double>> getDocIdToTermIdToTFIDFMap() {
		return docIdToTermIdToTFIDFMap;
	}

}
