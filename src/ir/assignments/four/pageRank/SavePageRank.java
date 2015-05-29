package ir.assignments.four.pageRank;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

public class SavePageRank {
	
	/** save outNodes map **/
	public static void saveNodesMap(Map<Integer, Set<Integer>> nodes, String path) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new File(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		StringBuilder s;
		for (int i = 0; i < nodes.size(); ++i) {
			s = new StringBuilder();
			s.append(i + ",");
			for (int j : nodes.get(i)) {
				s.append(j + ",");
			}
			s = s.delete(s.length() - 1, s.length());
			writer.println(s.toString());
		}

		if (writer != null) {
			writer.close();
		}
	}
		
	/** save pageRank map **/
	public static void savePageRankScoresMap(Map<Integer, Double> pageRankScores, String path) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new File(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		for (int docId = 0; docId < pageRankScores.size(); ++docId) {
			writer.println(pageRankScores.get(docId));
		}

		if (writer != null) {
			writer.close();
		}		
	}
	
}
