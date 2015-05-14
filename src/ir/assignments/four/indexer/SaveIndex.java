package ir.assignments.four.indexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	public static void saveTermToTermIdMap(Map<String, Integer> termToTermIdMap,
			Map<Integer, String> termIdToTermMap, String path) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new File(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		List<String> keys = new ArrayList<String>();
		// add keys to new list to save in sorted order
		for (int i = 0; i < termIdToTermMap.size(); ++i) {
			keys.add(termIdToTermMap.get(i));
		}

		for (String key : keys) {
			writer.println(key);
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
}
