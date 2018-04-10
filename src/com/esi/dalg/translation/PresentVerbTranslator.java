package com.esi.dalg.translation;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PresentVerbTranslator extends VerbTranslator{
	
	private List<String> arraySuffixes ;
			
	private List<String> arrayReplaceSuffixes ;
			
	private List<String> arrayPrefixes ;
					
	private List<String> arrayReplacePrefixes ;
	
	private List<String> fi3lNa9is ;
	
	private List<String> fi3lAdjwaf ;
	
	private String plural_suffix_present[];
	
	private String singlur_suffix_present[]; 
	
	private String masculine_prefix_present[];
	
	private String feminine_prefix_present[];
	
	private String motakalim_prefix_present[];
	
	public  PresentVerbTranslator() {
		
		arraySuffixes        = new ArrayList<String>(Arrays.asList("","كم","هم","نا","ها","ني","و","ل","ه","ك","ي"));

	    arrayReplaceSuffixes = new ArrayList<String>(Arrays.asList("","كم","هم","نا","ها","ني","ون"," ل","ه","ك","ي"));

	    arrayPrefixes        = new ArrayList<String>(Arrays.asList("","ا","ي","ت","ن"));
			
	    arrayReplacePrefixes = new ArrayList<String>(Arrays.asList("","ا","ي","ت","ن"));
	    
	    fi3lNa9is            = new ArrayList<String>(Arrays.asList("غطا","جرا","خبا","غنا","مشا","شرا"));
	     //نحا,سقسا
	    
	    fi3lAdjwaf           = new ArrayList<String>(Arrays.asList("قال","قاد"));
		 
		this.plural_suffix_present    = this.dataLoader.plural_suffix_present;
		 
		this.singlur_suffix_present   = this.dataLoader.singlur_suffix_present;
		 
		this.masculine_prefix_present = this.dataLoader.masculine_prefix_present;
		 
		this.feminine_prefix_present  = this.dataLoader.feminine_prefix_present;
		 
		this.motakalim_prefix_present = this.dataLoader.motakalim_prefix_present;
	}

	@Override
	public String translate(String[] segmentedWord) {
		
        String prefix = segmentedWord[0].trim();
        
        String suffix = segmentedWord[2].trim();
        
        String stem   = segmentedWord[1].trim();
        
        
        List<String> arrayPrefixes = new  ArrayList<>(Arrays.asList(prefix.split("\\+")));
        
        arrayPrefixes.remove("");
        
        
        List<String> arraySuffixes = new ArrayList<>(Arrays.asList(suffix.split("\\+")));
        
        arraySuffixes.remove("");
        
        
        this.plural    = this.checkPlural(arraySuffixes);
        
        this.feminine  = this.checkFeminine(arrayPrefixes);
        
        this.singular  = this.checkSingular(arraySuffixes);
        
        this.masculin  = this.checkMasculin(arrayPrefixes);
        
        this.motakalim = this.checkMotakalim(arrayPrefixes);  
        
        arraySuffixes  = arraySuffixes.size()!=0
				              ?
				        this.setSuffixes(arraySuffixes , stem)
				              :
				        arraySuffixes;

		arrayPrefixes  = arrayPrefixes.size() !=0
								?
						this.setPrefixes(arrayPrefixes , stem)
								:
						arrayPrefixes;
        

        prefix = prefix.equals("")==true? prefix : prefixePart(arrayPrefixes);
        
        suffix = suffix.equals("")==true? suffix : suffixePart(arraySuffixes);
        
        stem   = this.dictionary_verbs.containsKey(stem) == true ?dictionary_verbs.get(stem): stem;
        
        if(stem.length()==3) stem = this.Vi3lAdjwaf(stem);
        	
		return prefix + stem + suffix;
	}
	
	
	@Override
	protected String prefixePart(List<String> preffixes) {
		
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
		
		if (this.contain(this.plural_suffix_present,suffixe)) 
			
				return true;
		
		return false;
	}
	
	
	

	@Override
	protected boolean checkSingular(List<String> suffixe) {
		
		if (this.contain(this.singlur_suffix_present,suffixe)) 
			
			return true;
	
	    return false;
	}
	
	
	

	@Override
	protected boolean checkMasculin(List<String> prefixe) {
		
		if (this.contain(this.masculine_prefix_present,prefixe)) 
			
			return true;
	
	    return false;
	}
	
	
	

	@Override
	protected boolean checkFeminine(List<String> prefixe) {
		
		if (this.contain(this.feminine_prefix_present,prefixe)) 
			
			return true;
	
	    return false;
	}
	
	
	@Override
	protected boolean checkMotakalim(List<String> prefixe) {
		
		if (this.contain(this.motakalim_prefix_present,prefixe)) 
			
			return true;
	
	    return false;
	}
	
	
	private List<String> setPrefixes(List<String> preffixes,String stem) {
		
		if(!this.plural && this.motakalim) 
			
			preffixes.set(preffixes.indexOf("ن"), "ا") ;
		
		return preffixes;
	}
	
	private List<String> setSuffixes(List<String> suffixes,String stem) {
		
		if (suffixes.indexOf("ي") != -1)  {
			
			 if(((this.motakalim  && !this.plural)||(this.singular&&this.masculin)) && !this.fi3lNa9is.contains(stem)){
				//System.out.println("Ok");
				 suffixes.set(suffixes.indexOf("ي"), "");
					
				}
		}
		
		if(suffixes.indexOf("و") != -1) {
			
			if(this.motakalim && this.plural && this.fi3lNa9is.contains(stem) ) {
	
				suffixes.set(suffixes.indexOf("و"), "ي");
			}else if(this.motakalim && this.plural) suffixes.set(suffixes.indexOf("و"), "");
		
		}
		


		return suffixes;
	}

	//exemple   خاف لا لا //قاد== يقود
	private String Vi3lAdjwaf(String stem) {
		
		

		String [] stemCaracters = stem.split("");
		
		if(fi3lAdjwaf.contains(stem) && stemCaracters[1].equals("ا")) {
			System.out.println("Hello World");

			stem = stemCaracters[0]+"و"+stemCaracters[2];
		}
			

		return stem;
	}



}
