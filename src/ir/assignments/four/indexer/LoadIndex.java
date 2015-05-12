package ir.assignments.four.indexer;

import ir.assignments.four.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class LoadIndex {

	public static void main(String[] args) {
		HashMap<String, Integer> termToTermIdMap = loadTermToTermId(IndexerLocations.termToTermIdCSV);
		HashMap<Integer, String> termIdToTermMap = loadTermIdToTerm(termToTermIdMap);

		System.out.println(termToTermIdMap.size());
		System.out.println(termIdToTermMap.size());
	}

	/** load term to term id map from file **/
	private static HashMap<String, Integer> loadTermToTermId(String path) {
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

	/** load term id to term map from file **/
	private static HashMap<Integer, String> loadTermIdToTerm(
			HashMap<String, Integer> termToTermIdMap) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();

		for (String key : termToTermIdMap.keySet()) {
			map.put(termToTermIdMap.get(key), key);
		}

		return map;
	}

}
