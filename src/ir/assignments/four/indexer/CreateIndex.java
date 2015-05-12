package ir.assignments.four.indexer;

import ir.assignments.four.domain.FileDumpObject;
import ir.assignments.four.util.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateIndex {

	/** path to all docs **/
	public static String DOCFILEPATH = "testFiles/";

	public static void main(String[] args) {
		List<File> files = Util.getFilesInPath(DOCFILEPATH);
		
		HashMap<String, Integer> termToTermIdMap = termToTermId(files);
		HashMap<Integer, String> termIdToTermMap = termIdToTerm(termToTermIdMap);
		savetermToTermIdMap(termToTermIdMap, termIdToTermMap, "termToTermId.csv");
	}
	
	/** converts list of Files to list of FileDumpObjects **/
	private static List<FileDumpObject> filesToFDO(List<File> files) {
		ArrayList<FileDumpObject> list = new ArrayList<FileDumpObject>();
		
		for (File file : files) {
			try {
				list.add(new FileDumpObject(new JSONObject(Util.readFile(file))));
			} catch (JSONException | IOException e) {
				//e.printStackTrace();
			}
		}
		
		return list;
	}

	/** return map of term to term id **/
	private static HashMap<String, Integer> termToTermId(List<File> files) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		int index = 0;
		for (FileDumpObject fdo : filesToFDO(files)) {
			for (String token : Util.tokenizeFileDumpObject(fdo)) {
				if (!map.containsKey(token)) {
					map.put(token, index++);
				}
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

	/** save map of term to term id **/
	private static void savetermToTermIdMap(HashMap<String, Integer> termToTermIdMap,
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
