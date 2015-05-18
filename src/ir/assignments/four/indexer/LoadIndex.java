package ir.assignments.four.indexer;

import ir.assignments.four.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class LoadIndex {

	/** load term to term id map from file **/
	public static Map<String, Integer> loadTermToTermIdMap(String path) {
		Map<String, Integer> map = new TreeMap<String, Integer>();

		String fileString = null;
		try {
			fileString = Util.readFile(new File(path));
		} catch (IOException e) {
		}

		int index = 0;
		if (fileString != null) {
			for (String term : Util.tokenize(fileString)) {
				map.put(term, index++);
			}
		}

		return map;
	}

	/** load term id to term map from map **/
	public static Map<Integer, String> loadTermIdToTermMap(Map<String, Integer> termToTermIdMap) {
		Map<Integer, String> map = new TreeMap<Integer, String>();

		for (String term : termToTermIdMap.keySet()) {
			map.put(termToTermIdMap.get(term), term);
		}

		return map;
	}

	/** load term id to term map from file **/
	public static Map<Integer, Integer> loadTermIdToTermFrequencyMap(String path) {
		Map<Integer, Integer> map = new TreeMap<Integer, Integer>();

		String fileString = null;
		try {
			fileString = Util.readFile(new File(path));
		} catch (IOException e) {
		}

		int termID = 0;
		if (fileString != null) {
			for (String termFreq : Util.tokenize(fileString)) {
				map.put(termID++, Integer.parseInt(termFreq));
			}
		}

		return map;
	}

	/** load doc id to term id map from file **/
	public static Map<Integer, List<Integer>> loadDocIdToTermIdsMap(String path) {
		Map<Integer, List<Integer>> map = new TreeMap<Integer, List<Integer>>();

		String fileString = null;
		try {
			fileString = Util.readFile(new File(path));
		} catch (IOException e) {
		}

		int docID = -1;
		if (fileString != null) {
			boolean first;
			for (String split : fileString.split("\n")) {
				first = true;
				for (String termID : Util.tokenize(split)) {
					if (first) {
						docID = Integer.parseInt(termID);
						map.put(docID, new ArrayList<Integer>());
						first = false;
					} else {
						map.get(docID).add(Integer.parseInt(termID));
					}
				}
			}

		}

		return map;
	}

	/** load doc id to term id map from map **/
	public static Map<Integer, Set<Integer>> loadTermIdToDocIdMap(
			Map<Integer, List<Integer>> docIdToTermIdMap, int mapSize) {
		Map<Integer, Set<Integer>> map = new TreeMap<Integer, Set<Integer>>();

		for (int termID = 0; termID < mapSize; ++termID) {
			map.put(termID, new HashSet<Integer>());
		}

		for (int docID = 0; docID < docIdToTermIdMap.size(); ++docID) {
			for (int termID : docIdToTermIdMap.get(docID)) {
				map.get(termID).add(docID);
			}
		}

		return map;
	}

	/** load doc id to url from file **/
	public static Map<Integer, String> loadDocIdToUrlMap(String path) {
		Map<Integer, String> map = new TreeMap<Integer, String>();

		String fileString = null;
		try {
			fileString = Util.readFile(new File(path));
		} catch (IOException e) {
		}

		int docID = 0;
		if (fileString != null) {
			for (String url : fileString.split("\n")) {
				map.put(docID++, url.trim());
			}
		}

		return map;
	}

	/** save doc id to term id to tfidf from file **/
	public static Map<Integer, Map<Integer, Double>> loadDocIdToTermIdToTFIDFMap(String path) {
		Map<Integer, Map<Integer, Double>> map = new TreeMap<Integer, Map<Integer, Double>>();

		String fileString = null;
		try {
			fileString = Util.readFile(new File(path));
		} catch (IOException e) {
		}

		int docID = 0;

		if (fileString != null) {
			/*
			for (String url : fileString.split("\n")) {
				map.put(docID++, url.trim());
			}
			*/
		}

		return map;
	}

}
