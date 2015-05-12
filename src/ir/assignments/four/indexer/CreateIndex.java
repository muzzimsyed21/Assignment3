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

	public static void main(String[] args) {
		List<File> files = Util.getFilesInPath(IndexerLocations.fileDump);

		HashMap<String, Integer> termToTermIdMap = createTermToTermIdMap(files);
		HashMap<Integer, String> termIdToTermMap = createTermIdToTermMap(termToTermIdMap);

		// save maps to .csv
		SaveIndex.savetermToTermIdMap(termToTermIdMap, termIdToTermMap, IndexerLocations.termToTermIdCSV);
	}

	/** return map of term to term id **/
	private static HashMap<String, Integer> createTermToTermIdMap(List<File> files) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		int index = 0;
		int processed = 0;
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
				++processed;
			}
			// clear fdo
			fdo = null;

			if (processed % 1000 == 0) {
				System.out.println(processed);
			}
		}

		return map;
	}

	/** returns map of term id to term **/
	private static HashMap<Integer, String> createTermIdToTermMap(HashMap<String, Integer> termToTermIdMap) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();

		for (String key : termToTermIdMap.keySet()) {
			map.put(termToTermIdMap.get(key), key);
		}

		return map;
	}

	/** convert File to FileDumpObject **/
	private static FileDumpObject fileToFDO(File file) throws JSONException, IOException {
		return new FileDumpObject(new JSONObject(Util.readFile(file)));
	}
}
