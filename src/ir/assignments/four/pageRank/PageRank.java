package ir.assignments.four.pageRank;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import ir.assignments.four.domain.FileDumpObject;

public class PageRank {
	public static FileDumpObject file; 
	public Map<Integer,
	Set<Integer>> outNodes;
	public Map<Integer, Set<Integer>> inNodes;
	
	public PageRank(){
		this.outNodes = new TreeMap<Integer, Set<Integer>>();
		this.inNodes = new TreeMap<Integer, Set<Integer>>();
		
	}
	
	public  Map<Integer, Set<Integer>> getOutNodeMaps(Map<String, Integer> docIdToUrlMap){
		
		return this.outNodes; 
		
	}
	
	public Map<Integer, Set<Integer>> getInNodes(){
		
		return this.inNodes; 
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
