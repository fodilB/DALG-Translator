package com.esi.dalg.translation;

import java.util.HashMap;

import com.esi.dalg.data.DalgUtils;

public abstract class VerbTranslator extends WordTranslator{
	
	protected  HashMap<String, String> dictionary_verbs;
	
	public VerbTranslator() {
		
		 this.dataLoader       = DalgUtils.getInstance();
		 
		 this.dictionary_verbs = this.dataLoader.dictionary_verbs;
		
	}

}
