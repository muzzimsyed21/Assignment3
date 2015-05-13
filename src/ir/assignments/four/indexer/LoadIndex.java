package ir.assignments.four.indexer;

import ir.assignments.four.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoadIndex {

	/** load term to term id map from file **/
	public static HashMap<String, Integer> loadTermToTermIdMap(String path) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();

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
	public static HashMap<Integer, String> loadTermIdToTermMap(
			HashMap<String, Integer> termToTermIdMap) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();

		for (String term : termToTermIdMap.keySet()) {
			map.put(termToTermIdMap.get(term), term);
		}

		return map;
	}

	/** load term id to term map from file **/
	public static HashMap<Integer, Integer> loadTermIdToTermFrequencyMap(String path) {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

		String fileString = null;
		try {
			fileString = Util.readFile(new File(path));
		} catch (IOException e) {
		}

		int index = 0;
		if (fileString != null) {
			for (String termID : Util.tokenize(fileString)) {
				map.put(index++, Integer.parseInt(termID));
			}
		}

		return map;
	}

	/** load doc id to term id map from file **/
	public static HashMap<Integer, List<Integer>> loadDocIdToTermIdMap(String path) {
		HashMap<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();

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
	public static HashMap<Integer, Set<Integer>> loadTermIdToDocIdMap(
			HashMap<Integer, List<Integer>> docIdToTermIdMap, int mapSize) {
		HashMap<Integer, Set<Integer>> map = new HashMap<Integer, Set<Integer>>();

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
	public static HashMap<Integer, String> loadDocIdToUrlMap(String path) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();

		// TODO

		return map;
	}

}
