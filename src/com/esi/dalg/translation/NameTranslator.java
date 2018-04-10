package com.esi.dalg.translation;

import java.util.HashMap;

import com.esi.dalg.data.DalgUtils;

public abstract class NameTranslator extends WordTranslator{
	
	protected  HashMap<String, String> dictionary_name;
	
	public NameTranslator() {
		
		 this.dataLoader = DalgUtils.getInstance();
		 
		 this.dictionary_name = this.dataLoader.dictionary_name;
		
	}

}
