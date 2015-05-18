package ir.assignments.four.indexer;

import ir.assignments.four.util.Util;

import java.io.File;
import java.util.Map;
import java.util.List;
import java.util.Set;

public class Indexer {

	/** toggle true to recreate index **/
	private final static boolean CREATEFLAG = true;

	/** term to term id map **/
	private static Map<String, Integer> termToTermIdMap;

	/** term id to term map **/
	private static Map<Integer, String> termIdToTermMap;

	/** term id to term frequency map **/
	private static Map<Integer, Integer> termIdToTermFrequencyMap;

	/** doc id to term id map **/
	private static Map<Integer, List<Integer>> docIdToTermIdsMap;

	/** term id to doc id map **/
	private static Map<Integer, Set<Integer>> termIdToDocIdMap;

	/** doc id to url map **/
	private static Map<Integer, String> docIdToUrlMap;

	/** doc id to term id to TFIDF map **/
	private static Map<Integer, Map<Integer, Integer>> docIdToTermIdToTFIDFMap;

	public static void init() {
		List<File> files = Util.getFilesInPath(IndexerLocations.fileDump);

		if (CREATEFLAG) {
			termToTermIdMap = CreateIndex.createTermToTermIdMap(files);
			termIdToTermMap = LoadIndex.loadTermIdToTermMap(termToTermIdMap);
			termIdToTermFrequencyMap = CreateIndex.createTermIdToTermFrequencyMap(files,
					termToTermIdMap);
			docIdToTermIdsMap = CreateIndex.createDocIdToTermIdsMap(files, termToTermIdMap);
			termIdToDocIdMap = LoadIndex.loadTermIdToDocIdMap(docIdToTermIdsMap,
					termToTermIdMap.size());
			docIdToUrlMap = CreateIndex.createDocIdToURLMap(files);
			docIdToTermIdToTFIDFMap = CreateIndex.createDocIdToTermIdToTFIDFMap(files,
					docIdToTermIdsMap, termIdToTermFrequencyMap);

			// save maps to .csv
			SaveIndex.saveTermToTermIdMap(termIdToTermMap, IndexerLocations.termToTermIdCSV);
			SaveIndex.saveTermIdToTermFrequencyMap(termIdToTermFrequencyMap,
					IndexerLocations.termIdToTermFrequencyCSV);
			SaveIndex.saveDocIdToTermIdsMap(docIdToTermIdsMap, IndexerLocations.docIdToTermIdCSV);
			SaveIndex.saveDocIdToUrlMap(docIdToUrlMap, IndexerLocations.docIdToUrlCSV);
			SaveIndex.saveDocIdToTermIdToTFIDFMap(docIdToTermIdToTFIDFMap,
					IndexerLocations.docIdToTermIdToTFIDFCSV);

		} else {
			termToTermIdMap = LoadIndex.loadTermToTermIdMap(IndexerLocations.termToTermIdCSV);
			termIdToTermMap = LoadIndex.loadTermIdToTermMap(termToTermIdMap);
			termIdToTermFrequencyMap = LoadIndex.loadTermIdToTermFrequencyMap(IndexerLocations.termIdToTermFrequencyCSV);
			docIdToTermIdsMap = LoadIndex.loadDocIdToTermIdsMap(IndexerLocations.docIdToTermIdCSV);
			termIdToDocIdMap = LoadIndex.loadTermIdToDocIdMap(docIdToTermIdsMap,
					termToTermIdMap.size());
			docIdToUrlMap = LoadIndex.loadDocIdToUrlMap(IndexerLocations.docIdToUrlCSV);
			docIdToTermIdToTFIDFMap = LoadIndex.loadDocIdToTermIdToTFIDFMap(IndexerLocations.docIdToTermIdToTFIDFCSV);
		}
	}

	public static void main(String[] args) {

		init();

		/*
		System.out.println(termToTermIdMap.size());
		System.out.println(termIdToTermMap.size());
		System.out.println(termIdToTermFrequencyMap.size());
		System.out.println(docIdToTermIdsMap.size());
		System.out.println(termIdToDocIdMap.size());
		System.out.println(docIdToUrlMap.size());

		System.out.println(docIdToTermIdsMap.get(2));
		System.out.println(termIdToDocIdMap.get(0));
		System.out.println(docIdToUrlMap.get(0));

		System.out.println(getTermId("navigation"));
		System.out.println(getDocsWithTerm("ics"));
		System.out.println(getTermFrequencyInDoc(9, "ics"));
		*/
		System.out.println(docIdToTermIdToTFIDFMap.keySet());

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
		return termIdToDocIdMap;
	}

	public static Map<Integer, String> getDocIdToUrlMap() {
		return docIdToUrlMap;
	}

	public static Map<Integer, Map<Integer, Integer>> getDocIdToTermIdToTFIDFMap() {
		return docIdToTermIdToTFIDFMap;
	}

}
