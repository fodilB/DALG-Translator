package com.esi.dalg.data;

import java.io.IOException;

import java.util.HashMap;


/* Singleton Pattern */

public class DalgUtils {
	
	private static DalgUtils dataLoader = new DalgUtils();
	
    private final String verbFileName = "Dalg_verbe";
    
    //private final String adjFileName = "Dalg_adj";
    
   // private final String nomFileName = "Dalg_nom";
	
    
    public  HashMap<String, String> dictionary_adj ;
    
    public  HashMap<String, String> dictionary_verbs;
    
    public  HashMap<String, String> dictionary_name;
    
    
    private DataManager manager ;
    
    
    public  HashMap<String, Integer> hPrefixes;
    
    public  HashMap<String, Integer> hSuffixes;
    
    
    public String[] demonstrative_pronouns_dalg = {"هذا","هذاك","هادي","هاديك","هادوك","هادو"};
    
    public String[] demonstrative_pronouns_msa  = {"هذا","ذلك|هذاك","هذه","تلك","اولئك","هؤلاء"};
    
    //Verb Prefixes , suffixes 
	public String[] suffixes        = {"كم","هم","نا","ها","ني","و","ل","ه","ك","ت","ي","تو"};
	
	public String[] replaceSuffixes = { "كم","هم","نا","ها","ني","ون"," ل","ه","ك","ت","ي","تم"};
	
	
	public String[] prefixes 	     = {"ي","ت","ن"};
			
	public String[] replacePrefixes  = {"ي","ت","ن"};
	
	//Used In segmentation
	public String[] present_prefixes = {"ي","ت","ن"};
	
	public String[] past_suffixes    = {"ت","نا","تو"};
	
	//  Present
	
	public String[] plural_suffix_present    = {"و"};
	
	public String[] singlur_suffix_present   = {"ي"};
	
	public String[] masculine_prefix_present = {"ي"};
	
	public String[] feminine_prefix_present  = {"ت"};
	
	public String[] motakalim_prefix_present = {"ن"};
	
    // Past 
	
	public String[] plural_suffix_past    = {"و"};
	
	public String[] singlur_suffix_past   = {"ي"};

	public String[] masculine_suffix_past = {"ي"};

	public String[] feminine_suffix_past  = {"ت"};

	public String[] motakalim_suffix_past = {"نا"};
	

	 public static DalgUtils getInstance(){
		     return dataLoader;
	}

    
	private DalgUtils() {	
    	
     hPrefixes = new HashMap<String, Integer>();
    	    
     hSuffixes = new HashMap<String, Integer>();
    	    
     dictionary_adj = new HashMap<String, String>();
    	    
     dictionary_verbs = new HashMap<String, String>();
     
     dictionary_name = new HashMap<String, String>();
     
     manager = new DataManager();

     this.loadData();
	}
	
	private void loadData() {
	     
	     for (int i = 0; i < prefixes.length; i++) hPrefixes.put(prefixes[i].toString(), 1);
	     
	     
	     for (int i = 0; i < suffixes.length; i++) hSuffixes.put(suffixes[i].toString(), 1);
	     
		   try {
			   
			dictionary_verbs = manager.deserializeObject(verbFileName);
				
			//dictionary_adj = manager.deserializeObject(adjFileName);
			
			//dictionary_noms = manager.deserializeObject(nomFileName);
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}

}
