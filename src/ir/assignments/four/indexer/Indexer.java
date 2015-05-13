package ir.assignments.four.indexer;

import ir.assignments.four.util.Util;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class Indexer {

	public static void main(String[] args) {
		List<File> files;
		HashMap<String, Integer> termToTermIdMap;
		HashMap<Integer, String> termIdToTermMap;
		HashMap<Integer, Integer> termIdToTermFrequencyMap;

		/*
		files = Util.getFilesInPath(IndexerLocations.fileDump);

		termToTermIdMap = CreateIndex.createTermToTermIdMap(files);
		termIdToTermMap = CreateIndex.createTermIdToTermMap(termToTermIdMap);
		termIdToTermFrequencyMap = CreateIndex.createTermIdToTermFrequency(files, termToTermIdMap);

		// save maps to .csv
		SaveIndex.saveTermToTermIdMap(termToTermIdMap, termIdToTermMap, IndexerLocations.termToTermIdCSV);
		SaveIndex.saveTermIdToTermFrequencyMap(termIdToTermFrequencyMap, IndexerLocations.termIdToTermFrequencyCSV);
		*/

		termToTermIdMap = LoadIndex.loadTermToTermId(IndexerLocations.termToTermIdCSV);
		termIdToTermMap = LoadIndex.loadTermIdToTerm(termToTermIdMap);
		termIdToTermFrequencyMap = LoadIndex.loadTermIdToTermFrequency(IndexerLocations.termIdToTermFrequencyCSV);

		System.out.println(termToTermIdMap.size());
		System.out.println(termIdToTermMap.size());
		System.out.println(termIdToTermFrequencyMap.size());
	}

}
