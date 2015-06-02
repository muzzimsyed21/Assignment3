package ir.assignments.four.pageRank;

import ir.assignments.four.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class LoadPageRank {
	
	/** load node map **/
	public static Map<Integer, Set<Integer>> loadNodeMap(String path) {
		Map<Integer, Set<Integer>> map = new TreeMap<Integer, Set<Integer>>();

		String fileString = null;
		try {
			fileString = Util.readFile(new File(path));
		} catch (IOException e) {
		}

		int docId = 0;
		if (fileString != null) {
			boolean first;
			for (String split : fileString.split("\n")) {
				first = true;
				for (String termId : Util.tokenize(split)) {
					if (first) {
						docId = Integer.parseInt(termId);
						map.put(docId, new TreeSet<Integer>());
						first = false;
					} else {
						map.get(docId).add(Integer.parseInt(termId));
					}
				}
			}
		}

		return map;
	}
	
	/** load page rank scores map **/
	public static Map<Integer, Double> loadPageRankScoresMap(String path) {
		Map<Integer, Double> map = new TreeMap<Integer, Double>();

		String fileString = null;
		try {
			fileString = Util.readFile(new File(path));
		} catch (IOException e) {
		}

		int docID = 0;
		if (fileString != null) {
			boolean first = true;
			for (String score : Util.tokenize(fileString)) {
				if (first) {
					map.put(docID++, Double.parseDouble(score));
					first = false;
				} else {
					first = true;
				}
			}
		}

		return map;
	}
}
