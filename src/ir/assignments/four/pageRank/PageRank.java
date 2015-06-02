package ir.assignments.four.pageRank;

import ir.assignments.four.domain.FileDumpObject;
import ir.assignments.four.indexer.IndexerLocations;
import ir.assignments.four.indexer.LoadIndex;
import ir.assignments.four.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.json.JSONException;

public class PageRank {

	/** toggle true to recreate page rank **/
	private final static boolean CREATEFLAG = false;

	public static Map<Integer, Set<Integer>> outNodes;
	public static Map<Integer, Set<Integer>> inNodes;
	public static Map<Integer, Double> pageRankScores;

	public static void init() {
		outNodes = new TreeMap<Integer, Set<Integer>>();
		inNodes = new TreeMap<Integer, Set<Integer>>();
		pageRankScores = new TreeMap<Integer, Double>();
		
		if (CREATEFLAG) {
			List<File> files = Util.getFilesInPath(IndexerLocations.fileDump);

			Map<Integer, String> docIdToUrlMap = LoadIndex.loadDocIdToUrlMap(IndexerLocations.docIdToUrlCSV);
			Map<String, Integer> urlToDocIdMap = LoadIndex.loadUrlToDocIdMap(docIdToUrlMap);

			for (int i = 0; i < docIdToUrlMap.size(); ++i) {
				outNodes.put(i, new TreeSet<Integer>());
				inNodes.put(i, new TreeSet<Integer>());
				pageRankScores.put(i, 0.0);
			}

			int count = 0;
			for (File file : files) {
				FileDumpObject fdo = null;
				try {
					fdo = Util.fileToFDO(file);
				} catch (JSONException | IOException e) {
				}

				if (fdo != null) {
					// create outNodes map
					for (String link : fdo.getLinks()) {
						if (urlToDocIdMap.containsKey(link)) {
							outNodes.get(fdo.getId()).add(urlToDocIdMap.get(link));
						}
					}
				}
				fdo = null;
				System.out.println(count++);
			}

			// create inNodes map
			for (int i = 0; i < docIdToUrlMap.size(); ++i) {
				for (int docId : outNodes.get(i)) {
					inNodes.get(docId).add(i);
				}
			}

			// accumulate quality scores
			Set<Integer> nodeSet;
			for (int i = 0; i < pageRankScores.size(); ++i) {
				nodeSet = getOutNodes(i);
				for (int docId : nodeSet) {
					pageRankScores.put(docId, pageRankScores.get(docId) + (1000 / nodeSet.size()));
				}
			}

			SavePageRank.saveNodesMap(outNodes, PageRankLocations.outNodesCSV);
			SavePageRank.saveNodesMap(inNodes, PageRankLocations.inNodesCSV);
			SavePageRank.savePageRankScoresMap(pageRankScores, PageRankLocations.pageRankCSV);
		} else {
			outNodes = LoadPageRank.loadNodeMap(PageRankLocations.outNodesCSV);
			inNodes = LoadPageRank.loadNodeMap(PageRankLocations.inNodesCSV);
			pageRankScores = LoadPageRank.loadPageRankScoresMap(PageRankLocations.pageRankCSV);
		}
		
		System.out.println(outNodes.size());
		System.out.println(inNodes.size());
		System.out.println(pageRankScores.size());
		
		System.out.println(outNodes.get(0));
		System.out.println(inNodes.get(0));
		System.out.println(pageRankScores.get(0));
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
