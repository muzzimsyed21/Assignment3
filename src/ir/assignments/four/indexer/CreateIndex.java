package ir.assignments.four.indexer;

import ir.assignments.four.domain.FileDumpObject;
import ir.assignments.four.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateIndex {

	/** returns term to term id map **/
	public static HashMap<String, Integer> createTermToTermIdMap(List<File> files) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();

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
	public static HashMap<Integer, Integer> createTermIdToTermFrequencyMap(List<File> files,
			HashMap<String, Integer> termToTermIdMap) {

		int mapSize = termToTermIdMap.size();

		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>(mapSize);

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
	public static HashMap<Integer, List<Integer>> createDocIdToTermIdMap(List<File> files,
			HashMap<String, Integer> termToTermIdMap) {

		int mapSize = files.size();
		
		HashMap<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();

		for (int i = 0; i < mapSize; ++i) {
			map.put(i, new ArrayList<Integer>());
		}
		
		int index = 0;
		for (File file : files) {
			FileDumpObject fdo = null;
			try {
				fdo = fileToFDO(file);
			} catch (JSONException | IOException e) {
			}

			if (fdo != null) {
				for (String token : Util.tokenizeFileDumpObject(fdo)) {
					map.get(index).add(termToTermIdMap.get(token));
				}
				++index;
			}
			fdo = null;
		}

		return map;
	}

	/** returns doc id to url map **/
	public static HashMap<Integer, String> createDocIdToURLMap(List<File> files) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();

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
