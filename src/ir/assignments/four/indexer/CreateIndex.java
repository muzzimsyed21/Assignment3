package ir.assignments.four.indexer;

import ir.assignments.four.domain.FileDumpObject;
import ir.assignments.four.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import createDatabase.ICSDumpDatabase;

public class CreateIndex {

	public static void main(String[] args) {
		List<File> files;
		HashMap<String, Integer> termToTermIdMap;
		HashMap<Integer, String> termIdToTermMap;

		/*
		files = Util.getFilesInPath(IndexerLocations.fileDump);
		termToTermIdMap = createTermToTermIdMap(files);
		termIdToTermMap = createTermIdToTermMap(termToTermIdMap);

		// save maps to .csv
		SaveIndex.savetermToTermIdMap(termToTermIdMap, termIdToTermMap, IndexerLocations.termToTermIdCSV);
		*/

		termToTermIdMap = LoadIndex.loadTermToTermId(IndexerLocations.termToTermIdCSV);
		termIdToTermMap = LoadIndex.loadTermIdToTerm(termToTermIdMap);

		System.out.println(termToTermIdMap.size());
		System.out.println(termIdToTermMap.size());
		
		ICSDumpDatabase database = new ICSDumpDatabase(termIdToTermMap); 
		database.create();
		
	}

	/** return map of term to term id **/
	private static HashMap<String, Integer> createTermToTermIdMap(List<File> files) {
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

	/** returns map of term id to term **/
	private static HashMap<Integer, String> createTermIdToTermMap(
			HashMap<String, Integer> termToTermIdMap) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();

		for (String key : termToTermIdMap.keySet()) {
			map.put(termToTermIdMap.get(key), key);
		}

		return map;
	}

	/** returns map of term id to term frequency **/
	private static HashMap<Integer, Integer> createTermIdToTermFrequency(List<File> files) {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

		return map;
	}
	
	/** returns map of doc id to term id **/
	private static HashMap<Integer, Integer> createDocIdToTermId(List<File> files) {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

		return map;
	}
	
	/** returns map of doc id to url **/
	private static HashMap<Integer, String> createDocIdToURL(List<File> files) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();

		return map;
	}

	/** convert File to FileDumpObject **/
	private static FileDumpObject fileToFDO(File file) throws JSONException, IOException {
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
			// TODO: IMPLEMENT HERE!
		}
		fdo = null;
	}
	*/
}
