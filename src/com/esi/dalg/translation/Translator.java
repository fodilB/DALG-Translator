package com.esi.dalg.translation;


import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

import com.esi.dalg.data.DalgUtils;
import com.esi.dalg.segmenter.Segmenter;

public class Translator {
	
	private DalgUtils dataLoader ;
	    
    private  HashMap<String, String> dictionary_adj ;
    
    private  HashMap<String, String> dictionary_verbs;

	public static void main(String[] args) {
		
		System.err.println("Wait will system is initialized ...");
		
		Translator translator = new Translator();
		
		Segmenter segmenter = new Segmenter();
		
		System.err.println("System is ready");
		
		WordTranslator wordTranslator = new PresentVerbTranslator();
		
		String lemme ="تخيل";
		
		String [] words = {"ن"+lemme};
		
		for(String word:words) {
			
		System.out.println("Word "+word);
	
		int numberOfSolutions = 1;
				
		if(translator.dictionary_verbs.containsKey(word)) 
			
		  System.out.println("Le mot en entre est un verbe ,sa traduction en arabe est : "+translator.dictionary_verbs.get(word));
		
		else if (translator.dictionary_adj.containsKey(word)) 
			
		  System.out.println("Le mot en entre est un adjectif ,sa traduction en arabe est :"+translator.dictionary_adj.get(word));
		
		else {
			
		TreeMap<Double, String[]> mostLikelyPartition = segmenter.mostLikelyPartition(word, numberOfSolutions);
		 	
		String[] bestSegmentation = mostLikelyPartition.get(mostLikelyPartition.firstKey());
		
		System.out.println("Best Segmentation : "+Arrays.toString(bestSegmentation));	

		String translatedWord = wordTranslator.translate(bestSegmentation);
		
		System.out.println("Translated Word : "+translatedWord);
		}
		
		}

	}
	
	 
	
	public Translator() {
		

		this.dataLoader = DalgUtils.getInstance();
				
		this.dictionary_verbs = this.dataLoader.dictionary_verbs;
		
		this.dictionary_adj = this.dataLoader.dictionary_adj;
		
		
	}
	

	


}
