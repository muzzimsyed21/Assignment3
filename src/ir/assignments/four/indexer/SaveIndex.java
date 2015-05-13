package ir.assignments.four.indexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SaveIndex {

	/** save term to term id map **/
	public static void saveTermIdToTermFrequencyMap(
			HashMap<Integer, Integer> termIdToTermFrequencyMap, String path) {
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
	public static void saveTermToTermIdMap(HashMap<String, Integer> termToTermIdMap,
			HashMap<Integer, String> termIdToTermMap, String path) {
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
	public static void saveDocIdToTermIdMap(HashMap<Integer, List<Integer>> docIdToTermIdMap, String path) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new File(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		StringBuilder s;
		for (int i = 0; i < docIdToTermIdMap.size(); ++i) {
			s = new StringBuilder();
			s.append(i + ",");
			for (int j : docIdToTermIdMap.get(i)) {
				s.append(j + ",");
			}
			s = s.delete(s.length()-1, s.length());
			writer.println(s.toString());
		}

		if (writer != null) {
			writer.close();
		}
	}
	
	/** save doc id to url map **/
	public static void saveDocIdToUrlMap(HashMap<Integer, String> docIdToUrlMap, String path) {
		
		// TODO
		
	}
}
