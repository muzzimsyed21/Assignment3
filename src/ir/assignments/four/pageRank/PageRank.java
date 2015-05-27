package ir.assignments.four.pageRank;

import ir.assignments.four.domain.FileDumpObject;
import ir.assignments.four.indexer.IndexerLocations;
import ir.assignments.four.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.json.JSONException;

public class PageRank {
	
	public static Map<Integer, Set<Integer>> outNodes;
	public static Map<Integer, Set<Integer>> inNodes;
	
	public static void init() {
		List<File> files = Util.getFilesInPath(IndexerLocations.fileDump);
		
		outNodes = new TreeMap<Integer, Set<Integer>>();
		inNodes = new TreeMap<Integer, Set<Integer>>();
		
		int count = 0;
		
		for (File file : files) {
			FileDumpObject fdo = null;
			try {
				fdo = Util.fileToFDO(file);
			} catch (JSONException | IOException e) {
			}

			if (fdo != null) {
				System.out.println(fdo.getUrl());
				System.out.println(fdo.getLinks());
			}
			fdo = null;
		}
	}

	public static Set<Integer> getOutNodes(int docId) {
		return outNodes.get(docId);
	}

	public static Set<Integer> getInNodes(int docId) {
		return inNodes.get(docId);
	}

	public static void main(String[] args) {
		init();
	}

}
