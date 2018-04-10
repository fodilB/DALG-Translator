package com.esi.dalg.translation;

import java.util.HashMap;

import com.esi.dalg.data.DalgUtils;

public abstract class AdjTranslator extends WordTranslator{
	
	protected  HashMap<String, String> dictionary_adj;
	
	public AdjTranslator() {
		
		 this.dataLoader = DalgUtils.getInstance();
		 
		 this.dictionary_adj = this.dataLoader.dictionary_adj;
		
	}

}

