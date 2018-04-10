package com.esi.dalg.segmenter;

//Strategy Pattern

public class PartitionScorer {
	
	private StemScorer stemScorer;
	
	public PartitionScorer(StemScorer stemScorer) {
		
		this.stemScorer = stemScorer;
		
	}
	
	public String scorePartition(String stem) {
		return this.stemScorer.scorePartition(stem);
	}
	

}
