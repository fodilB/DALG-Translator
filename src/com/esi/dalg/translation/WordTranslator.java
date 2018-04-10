package com.esi.dalg.translation;

import java.util.List;

import com.esi.dalg.data.DalgUtils;

public abstract class WordTranslator {
	
	protected DalgUtils dataLoader;
	
	protected boolean singular  = false;
	
	protected boolean plural    = false;
	
    protected boolean masculin  = false;
    
    protected boolean feminine  = false;
    
    protected boolean motakalim = false;
    

	public    abstract String  translate(String[] segmentedWord);
	
	protected abstract boolean checkPlural(List<String> partOfString) ;
    
	protected abstract boolean checkSingular(List<String> partOfString) ;
	
	protected abstract boolean checkMasculin(List<String> partOfString) ;
	
	protected abstract boolean checkFeminine(List<String> partOfString);
	
	protected abstract boolean checkMotakalim(List<String> partOfString);
	
	protected abstract String  prefixePart(List<String>  Preffixes);
	
	protected abstract String  suffixePart(List<String>  Suffixes);
	
	
	protected boolean contain (String[] A, List<String> B) {
    	
    	for (String a : A) {
    		
    		for (String b: B) {
    			
    			if(a.equals(b)) return true;
    		}
    		
    	}
    	
    	return false;
    }
	
	
	protected boolean containElement (String[] A, String B) {
    	
    	for (String a : A) 
    			
    			if(a.equals(B)) return true;
    	
    	return false;
    }


}
