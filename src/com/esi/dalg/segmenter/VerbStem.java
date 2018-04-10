package com.esi.dalg.segmenter;

import java.util.HashMap;

import com.esi.dalg.data.DalgUtils;

public abstract class VerbStem implements StemScorer {
	
	private DalgUtils dataLoader;
	
	protected  HashMap<String, String> dictionary_verbs;
	
	public VerbStem() {
		
		 dataLoader = DalgUtils.getInstance();
		 
		 this.dictionary_verbs = this.dataLoader.dictionary_verbs;
	}
}
