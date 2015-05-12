package ir.assignments.four.indexer;

import ir.assignments.four.domain.FileDumpObject;
import ir.assignments.four.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateIndex {

	/** path to all docs **/
	//public static String DOCFILEPATH = "testFiles/";
	public static String DOCFILEPATH = "FileDump/";

	public static void main(String[] args) {
		List<File> files = Util.getFilesInPath(DOCFILEPATH);
		
		HashMap<String, Integer> termToTermIdMap = termToTermId(files);
		HashMap<Integer, String> termIdToTermMap = termIdToTerm(termToTermIdMap);
		
		// save maps to .csv
		SaveIndex.savetermToTermIdMap(termToTermIdMap, termIdToTermMap, "termToTermId.csv");
	}

	/** return map of term to term id **/
	private static HashMap<String, Integer> termToTermId(List<File> files) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		int index = 0;
		int processed = 0;
		for (File file : files) {
			FileDumpObject fdo = fileToFDO(file);
			if (fdo != null) {
				for (String token : Util.tokenizeFileDumpObject(fdo)) {
					if (!map.containsKey(token)) {
						map.put(token, index++);
					}
				}
				++processed;
			}
			fdo = null;
			
			if (processed % 1000 == 0) {
				System.out.println(processed);
			}
		}

		return map;
	}

	/** returns map of term id to term **/
	private static HashMap<Integer, String> termIdToTerm(HashMap<String, Integer> termToTermIdMap) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();

		for (String key : termToTermIdMap.keySet()) {
			map.put(termToTermIdMap.get(key), key);
		}

		return map;
	}
	
	private static FileDumpObject fileToFDO(File file) {
		try {
			return new FileDumpObject(new JSONObject(Util.readFile(file)));
		} catch (JSONException | IOException e) {
			//e.printStackTrace();
		}
		return null;
	}
}
