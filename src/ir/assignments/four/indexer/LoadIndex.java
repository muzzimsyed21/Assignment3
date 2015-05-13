package ir.assignments.four.indexer;

import ir.assignments.four.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
			for (String s : Util.tokenize(fileString)) {
				map.put(s, index++);
			}
		}

		return map;
	}

	/** load term id to term map from map **/
	public static HashMap<Integer, String> loadTermIdToTermMap(HashMap<String, Integer> termToTermIdMap) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();

		for (String key : termToTermIdMap.keySet()) {
			map.put(termToTermIdMap.get(key), key);
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
			for (String s : Util.tokenize(fileString)) {
				map.put(index++, Integer.parseInt(s));
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

		int currentIndex = -1;
		if (fileString != null) {
			boolean first;
			for (String split : fileString.split("\n")) {
				first = true;
				for (String s : Util.tokenize(split)) {
					if (first) {
						currentIndex = Integer.parseInt(s);
						map.put(currentIndex, new ArrayList<Integer>());
						first = false;
					} else {
						map.get(currentIndex).add(Integer.parseInt(s));
					}
				}
			}
			
		}

		return map;
	}
	
	/** load doc id to term id map from map **/
	public static HashMap<Integer, Integer> loadTermIdToDocIdMap(HashMap<Integer, List<Integer>> docIdToTermIdMap) {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		// TODO

		return map;
	}

	/** load doc id to url from file **/
	public static HashMap<Integer, String> loadDocIdToUrlMap(String path) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		
		// TODO

		return map;
	}
	
}
