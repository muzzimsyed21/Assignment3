package ir.assignments.four.indexer;

import ir.assignments.four.domain.FileDumpObject;
import ir.assignments.four.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateIndex {

	/** returns term to term id map **/
	public static Map<String, Integer> createTermToTermIdMap(List<File> files) {
		Map<String, Integer> map = new TreeMap<String, Integer>();

		int index = 0;
		for (File file : files) {
			FileDumpObject fdo = null;
			try {
				fdo = fileToFDO(file);
			} catch (JSONException | IOException e) {
			}

			if (fdo != null) {
				for (String token : Util.tokenizeFileDumpObject(fdo)) {
					if (!map.containsKey(token)) {
						map.put(token, index++);
					}
				}
			}
			fdo = null;
		}

		return map;
	}

	/** returns term id to term frequency map **/
	public static Map<Integer, Integer> createTermIdToTermFrequencyMap(List<File> files,
			Map<String, Integer> termToTermIdMap) {

		int mapSize = termToTermIdMap.size();

		Map<Integer, Integer> map = new TreeMap<Integer, Integer>();

		for (int i = 0; i < mapSize; ++i) {
			map.put(i, 0);
		}

		for (File file : files) {
			FileDumpObject fdo = null;
			try {
				fdo = fileToFDO(file);
			} catch (JSONException | IOException e) {
			}

			if (fdo != null) {
				for (String token : Util.tokenizeFileDumpObject(fdo)) {
					map.put(termToTermIdMap.get(token), map.get(termToTermIdMap.get(token)) + 1);
				}
			}
			fdo = null;
		}

		return map;
	}

	/** returns doc id to term id map **/
	public static Map<Integer, List<Integer>> createDocIdToTermIdsMap(List<File> files,
			Map<String, Integer> termToTermIdMap) {

		int mapSize = files.size();

		Map<Integer, List<Integer>> map = new TreeMap<Integer, List<Integer>>();

		for (int i = 0; i < mapSize; ++i) {
			map.put(i, new ArrayList<Integer>());
		}

		for (File file : files) {
			FileDumpObject fdo = null;
			try {
				fdo = fileToFDO(file);
			} catch (JSONException | IOException e) {
			}

			if (fdo != null) {
				for (String token : Util.tokenizeFileDumpObject(fdo)) {
					map.get(fdo.getId()).add(termToTermIdMap.get(token));
				}
			}
			fdo = null;
		}

		return map;
	}

	/** returns doc id to url map **/
	public static Map<Integer, String> createDocIdToURLMap(List<File> files) {
		Map<Integer, String> map = new TreeMap<Integer, String>();

		for (File file : files) {
			FileDumpObject fdo = null;
			try {
				fdo = fileToFDO(file);
			} catch (JSONException | IOException e) {
			}

			if (fdo != null) {
				map.put(fdo.getId(), fdo.getUrl());
			}
			fdo = null;
		}

		return map;
	}

	/** returns doc id to term id to tfidf map **/
	public static Map<Integer, Map<Integer, Double>> createDocIdToTermIdToTFIDFMap(
			List<File> files,
			Map<Integer, List<Integer>> docIdToTermIdsMap,
			Map<Integer, Set<Integer>> termIdToDocIdsMap,
			Map<Integer, Integer> termIdToTermFrequencyMap) {
		Map<Integer, Map<Integer, Double>> map = new TreeMap<Integer, Map<Integer, Double>>();

		int docId;
		for (File file : files) {
			FileDumpObject fdo = null;
			Map<Integer, Double> termIdToTDIDFMap = new TreeMap<Integer, Double>();
			try {
				fdo = fileToFDO(file);
			} catch (JSONException | IOException e) {
			}

			if (fdo != null) {
				docId = fdo.getId();
				int termFreq;
				for (int termId : docIdToTermIdsMap.get(docId)) {
					termFreq = 0;
					for (int termId2 : docIdToTermIdsMap.get(docId)) {
						if (termId == termId2) {
							++termFreq;
						}
					}
					termIdToTDIDFMap.put(termId, termFreq * (Math.log(termIdToDocIdsMap.get(termId).size())));
				}
				map.put(docId, termIdToTDIDFMap);
			}
			fdo = null;
		}

		return map;
	}

	/** convert File to FileDumpObject **/
	public static FileDumpObject fileToFDO(File file) throws JSONException, IOException {
		return new FileDumpObject(new JSONObject(Util.readFile(file)));
	}

	/** TEMPLATE! **/
	/*
	for (File file : files) {
		FileDumpObject fdo = null;
		try {
			fdo = fileToFDO(file);
		} catch (JSONException | IOException e) {
		}
		
		if (fdo != null) {
			// TODO
		}
		fdo = null;
	}
	*/
}
