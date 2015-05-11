package ir.assignments.four.indexer;

import ir.assignments.four.util.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CreateIndex {

	/** path to all docs **/
	public static String DOCFILEPATH = "testFiles/";

	public static void main(String[] args) {
		List<File> files = Util.getFilesInPath(DOCFILEPATH);

		HashMap<String, Integer> termToTermIdMap = termToTermId(files);
		HashMap<Integer, String> termIdToTermMap = termIdToTerm(termToTermIdMap);
		save(termToTermIdMap, termIdToTermMap, "termToTermId.csv");
	}

	private static HashMap<String, Integer> termToTermId(List<File> files) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		int index = 0;
		for (File file : files) {
			for (String token : Util.tokenizeHTMLFile(file)) {
				if (!map.containsKey(token)) {
					map.put(token, index++);
				}
			}
		}

		return map;
	}

	private static HashMap<Integer, String> termIdToTerm(HashMap<String, Integer> termToTermIdMap) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();

		for (String key : termToTermIdMap.keySet()) {
			map.put(termToTermIdMap.get(key), key);
		}

		return map;
	}

	private static void save(HashMap<String, Integer> termToTermIdMap,
			HashMap<Integer, String> termIdToTermMap, String path) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new File(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		List<String> keys = new ArrayList<String>();
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
}
