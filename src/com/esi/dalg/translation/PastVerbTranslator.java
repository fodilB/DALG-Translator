package com.esi.dalg.translation;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PastVerbTranslator extends VerbTranslator {

	private List<String> arraySuffixes ;
	
	private List<String> arrayReplaceSuffixes ;
			
	private List<String> arrayPrefixes ;
					
	private List<String> arrayReplacePrefixes ;
	
	private String plural_suffix_past[];
	
	private String singlur_suffix_past[]; 
	
	private String masculine_suffix_past[];
	
	private String feminine_suffix_past[];
	
	private String motakalim_suffix_past[];
	
	public  PastVerbTranslator() {
		
		arraySuffixes        = new ArrayList<String>(Arrays.asList("م","ت","كم","هم","نا","ها","ني","تو","و","ل","ه","ك","ي"));

	    arrayReplaceSuffixes = new ArrayList<String>(Arrays.asList("م","ت","كم","هم","نا","ها","ني","تم","وا"," ل","ه","ك","ي"));

	    arrayPrefixes        = new ArrayList<String>(Arrays.asList());
			
	    arrayReplacePrefixes = new ArrayList<String>(Arrays.asList());
	    
		this.plural_suffix_past    = this.dataLoader.plural_suffix_past;
		 
		this.singlur_suffix_past   = this.dataLoader.singlur_suffix_past;
		 
		this.masculine_suffix_past = this.dataLoader.masculine_suffix_past;
		 
		this.feminine_suffix_past  = this.dataLoader.feminine_suffix_past;
		 
		this.motakalim_suffix_past = this.dataLoader.motakalim_suffix_past;
	}

	@Override
	public String translate(String[] segmentedWord) {
		
        String prefix = segmentedWord[0].trim();
        
        String suffix = segmentedWord[2].trim();
        
        String stem   = segmentedWord[1].trim();
        
        
        ArrayList<String> arrayPrefixes = new  ArrayList<>(Arrays.asList(prefix.split("\\+")));
        
        arrayPrefixes.remove("");
        
        
        ArrayList<String> arraySuffixes = new ArrayList<>(Arrays.asList(suffix.split("\\+")));
        
        arraySuffixes.remove("");
        
        
        this.plural = this.checkPlural(arraySuffixes);

        prefix = prefix.equals("")==true? prefix : prefixePart(arrayPrefixes);  
        
        suffix = suffix.equals("")==true? suffix : suffixePart(arraySuffixes);

        stem   = this.dictionary_verbs.containsKey(stem) == true ?dictionary_verbs.get(stem): stem;

		
        return prefix + stem + suffix;
	}
	
	@Override
	protected String prefixePart(List<String>  preffixes) {
		
		String preffix = "";
		
		for(int i=0 ; i<preffixes.size() ; i++) 
			
			      preffix += this.arrayReplacePrefixes.get(arrayPrefixes.indexOf(preffixes.get(i)));
		
		return preffix;	
	}

	@Override
	protected String suffixePart(List<String> suffixes) {
		
		String suffixe = "";
		
		for(int i=suffixes.size()-1 ; i>=0 ; i--) 
						
				suffixe=this.arrayReplaceSuffixes.get(arraySuffixes.indexOf(suffixes.get(i)))+suffixe;
		
		return suffixe;
	}

	@Override
	protected boolean checkPlural(List<String> suffixe) {
			
		if (this.contain(this.plural_suffix_past,suffixe)){
			if (suffixe.contains("ت")) 
				   suffixe.set(suffixe.indexOf("و"),"م");
			
			return true;
		} 
	
		return false;
	}

	@Override
	protected boolean checkSingular(List<String> suffixe) {
		
		if (this.contain(this.singlur_suffix_past,suffixe))
			
			return true;

	    return false;
	}
	

	@Override
	protected boolean checkMasculin(List<String> prefixe) {
		
		if (this.contain(this.masculine_suffix_past,prefixe)) 
			
			return true;
	
	    return false;
	}
	

	@Override
	protected boolean checkFeminine(List<String> prefixe) {
		
		if (this.contain(this.feminine_suffix_past,prefixe)) 
			
			return true;
	
	    return false;
	}
	
	protected boolean checkMotakalim(List<String> prefixe) {
		
		if (this.contain(this.motakalim_suffix_past,prefixe)) 
			
			return true;
	
	    return false;
	}

	
	
	
}
