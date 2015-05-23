package ir.assignments.four.pageRank;

import java.util.Map;
import java.util.TreeMap;

import ir.assignments.four.domain.FileDumpObject;

public class PageRank {
	public static FileDumpObject file; 
	public Map<String, Integer> outNodes;
	public Map<String, Integer> inNodes;
	
	public PageRank(){
		this.outNodes = new TreeMap<String, Integer>();
		this.inNodes = new TreeMap<String, Integer>();
		
	}
	
	public Map<String, Integer> getOutNodeMaps(String url){
		
		return this.outNodes; 
		
	}
	
	public Map<String, Integer> getInNodes(String url){
		
		return this.inNodes; 
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
