package ir.assignments.four.indexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SaveIndex {

	/** save term to term id map **/
	public static void saveTermIdToTermFrequencyMap(Map<Integer, Integer> termIdToTermFrequencyMap,
			String path) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new File(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < termIdToTermFrequencyMap.size(); ++i) {
			writer.println("" + termIdToTermFrequencyMap.get(i));
		}

		if (writer != null) {
			writer.close();
		}
	}

	/** save term to term id map **/
	public static void saveTermToTermIdMap(Map<Integer, String> termIdToTermMap, String path) {
		if (!(termIdToTermMap instanceof TreeMap)) {
			System.err.println("SaveError (saveTermToTermIdMap): termIdToTermMap must be type TreeMap");
			return;
		}

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new File(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		for (int termID : termIdToTermMap.keySet()) {
			writer.println(termIdToTermMap.get(termID));
		}

		if (writer != null) {
			writer.close();
		}
	}

	/** save doc id to term id map **/
	public static void saveDocIdToTermIdsMap(Map<Integer, List<Integer>> docIdToTermIdsMap,
			String path) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new File(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		StringBuilder s;
		for (int i = 0; i < docIdToTermIdsMap.size(); ++i) {
			s = new StringBuilder();
			s.append(i + ",");
			for (int j : docIdToTermIdsMap.get(i)) {
				s.append(j + ",");
			}
			s = s.delete(s.length() - 1, s.length());
			writer.println(s.toString());
		}

		if (writer != null) {
			writer.close();
		}
	}

	/** save doc id to url map **/
	public static void saveDocIdToUrlMap(Map<Integer, String> docIdToUrlMap, String path) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new File(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < docIdToUrlMap.size(); ++i) {
			writer.println(docIdToUrlMap.get(i));
		}

		if (writer != null) {
			writer.close();
		}
	}

	/** save doc id to term id to tfidf map **/
	public static void saveDocIdToTermIdToTFIDFMap(
			Map<Integer, Map<Integer, Double>> docIdToTermIdToTFIDFMap, String path) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new File(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		StringBuilder s;
		Map<Integer, Double> termIdtoTFIDF;
		for (int docId = 0; docId < docIdToTermIdToTFIDFMap.size(); ++docId) {
			termIdtoTFIDF = docIdToTermIdToTFIDFMap.get(docId);
			for (int termId : termIdtoTFIDF.keySet()) {
				s = new StringBuilder();
				s.append(docId + ",");
				s.append(termId + ",");
				s.append(termIdtoTFIDF.get(termId));
				writer.println(s.toString());
			}
		}
		
		if (writer != null) {
			writer.close();
		}
	}
}
