package ir.assignments.four.pageRank;

import ir.assignments.four.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class LoadPageRank {
	
	/** load node map **/
	public static Map<String, Integer> loadNodeMap(String path) {
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
}
