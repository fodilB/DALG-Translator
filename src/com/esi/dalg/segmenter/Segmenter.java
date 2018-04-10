package com.esi.dalg.segmenter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

import com.esi.dalg.data.DalgUtils;


public class Segmenter {
    
    private DalgUtils dataLoader ;
    
    //private  HashMap<String, String> dictionary_adj ;
    
    private  HashMap<String, String> dictionary_verbs;
    
    private  HashMap<String, Integer> hPrefixes;
    
    private  HashMap<String, Integer> hSuffixes;
    
	private String present_prefixes[];
	
	private String past_suffixes[];
	
	private PartitionScorer partitionScorer;
	
	public Segmenter() {

	  dataLoader = DalgUtils.getInstance();
	  
	  this.hPrefixes = dataLoader.hPrefixes;
	  
	  this.hSuffixes = dataLoader.hSuffixes;
	  
	 // this.dictionary_adj = dataLoader.dictionary_adj;
	  
	  this.dictionary_verbs = dataLoader.dictionary_verbs;
	  
	  this.present_prefixes = dataLoader.present_prefixes;
	  
	  this.past_suffixes = dataLoader.past_suffixes;
	  
     }
			
	
   public ArrayList<String> getAllPossiblePartitionsOfString(String s) {
    	
        ArrayList<String> output = new ArrayList<String>();
        
        if (s.length() > 20) output.add(s);
        
        else
        {
            s = s.trim();

            if (s.length() > 0) 
            {
            	
                String fullPartition = s.substring(0, 1);

                for (int i = 1; i < s.length(); i++) fullPartition += "," + s.substring(i, i+1);
                  
                String Chaine = fullPartition.replace(",", "+").replaceAll("\\++", "+");
                
                String correctFullPartition = getProperSegmentation(Chaine);
                
                String[] parts = (" " + correctFullPartition + " ").split(";");
                
                if (!output.contains(correctFullPartition)) {
                	
                    if (parts[1].length() != 1 || s.length() == 1) output.add(correctFullPartition);
                }
                
                if (fullPartition.contains(",")) output = getSubPartitions(fullPartition, output);

            }
        }
        
        return output;
    }
    
	
    public String getProperSegmentation(String input) {
   
        if (!input.contains("+")) return ";" + input + ";";
        
        String output = "";
        
        String[] word = input.split("\\+");
        
        String currentPrefix = "";
        
        String currentSuffix = "";
        
        int iValidPrefix = -1;
        
        while (iValidPrefix + 1 < word.length && this.hPrefixes.containsKey(word[iValidPrefix + 1])) {
            
        	iValidPrefix++;
        }

        int iValidSuffix = word.length;

        while (iValidSuffix > Math.max(iValidPrefix, 0)  
        		&& iValidSuffix > 1 
        		&& (this.hSuffixes.containsKey(word[iValidSuffix - 1])
                || word[iValidSuffix - 1].equals("_"))) {
        	
            iValidSuffix--;
        }
        
        for (int i = 0; i <= iValidPrefix; i++) {
            currentPrefix += word[i] + "+";
        }
        
        String stemPart = "";
        
        for (int i = iValidPrefix + 1; i < iValidSuffix; i++) {
            stemPart += word[i];
        }

        if (iValidSuffix == iValidPrefix) {
            iValidSuffix++;
        }
        
        for (int i = iValidSuffix; i < word.length; i++) {
            currentSuffix += "+" + word[i];
        }
        
       
        
        output = currentPrefix + ";" + stemPart + ";" + currentSuffix;
       
        
        return output.replace("++", "+");
    } 
    
    
    private ArrayList<String> getSubPartitions(String s, ArrayList<String> output){
      
            String[] parts = s.split(",");
            
            for (int i = 0; i < parts.length - 1; i++)
            {
                String ss = "";
                
	                for (int j = 0; j < i; j++) {
	                	
	                    if (j == 0) ss = parts[j];
	                    
	                    else ss += "," + parts[j];
	                }

	                if (i == 0) ss = parts[i] + parts[i+1]; 
	                
	                else ss += "," + parts[i] + parts[i+1];
                

                for (int k = i + 2; k < parts.length; k++) ss += "," + parts[k];
                
                String correctFullPartition = getProperSegmentation(ss.replace(",", "+").replaceAll("\\++", "+"));
 
                if (!output.contains(correctFullPartition)) {
                	
                    output.add(correctFullPartition);
                    
                    if (ss.contains(",")) output = getSubPartitions(ss, output);
                }
            }

        return output;
    }
    
    
    public TreeMap<Double, String[]> mostLikelyPartition(String word, int numberOfSolutions) {
    	
        word = word.trim();
        
        ArrayList<String> possiblePartitions = getAllPossiblePartitionsOfString(word);
        
        TreeMap<Double, String[]> scores = new TreeMap<Double, String[]>();
        	
            for (String p : possiblePartitions)
            {
                
                String[] parts = (" " + p + " ").split(";");
                
                if (parts.length == 3)
                {
                    if (parts[0].trim().length() == 0 && parts[1].trim().length() == 0)
                    {
                       
                    } else {
                    	
                    	String Result = scorePartition(parts);
                    	
                        double score = Double.parseDouble(Result.substring(Result.indexOf(":")+1, Result.length()-1)) ;
                        
                        String newStem= Result.substring(0,Result.indexOf(":"));
                        
                        if(!parts[1].equals(newStem)) parts[1] = newStem;
                        
                        
                        while (scores.containsKey(score)) score += 0.00001;
                        
                        scores.put(score, parts);
                        
                        System.out.println(Arrays.toString(parts)+":"+score);
                    }
                }
            }
            
            
        
        
        int scoresSize = scores.size() - numberOfSolutions;
        
        TreeMap<Double, String[]> scoresFinal = new TreeMap<Double, String[]>();
        
        int i = 0;
        
        for (double d : scores.keySet())
        {
            if (i >= scoresSize) scoresFinal.put(d, scores.get(d));
            
            i++;
        }
        
        
        
        return scoresFinal;
    }
    
    public String scorePartition(String[] parts) {
    	
        double score  =   0;
        
        String prefix = parts[0].trim();
        
        String suffix = parts[2].trim();
        
        String stem   = parts[1].trim();
        
        String [] prefixes = prefix.split("\\+");
        
        String [] suffixes = suffix.split("\\+");
        
   
        if (prefixes.length>1) score += prefixes.length;
            
        if (suffixes.length>1) score += suffixes.length;
        
        boolean presentVerb = this.contain(present_prefixes, prefixes);
        
        boolean pastVerb = this.contain(past_suffixes, suffixes);
        
        
        if(presentVerb || pastVerb) {
        
	         if (this.dictionary_verbs.containsKey(stem)) {  score += 3; System.out.println("Ok");}
	         
	         else {
	        	
	        	partitionScorer = presentVerb == true 
	        								    ?
	        			          new PartitionScorer(new PresentVerb())
	        			          				:
	        			          new PartitionScorer(new PastVerb());
	        			
	        	String newStem = partitionScorer.scorePartition(stem);

	        	stem =  newStem.substring(0,newStem.indexOf(":"));
	        	
	        	score += Double.parseDouble(newStem.substring(newStem.indexOf(":")+1, newStem.length())) ;
	        }
         
        }
        
        return stem+":"+score;
    	
    }
    
    public boolean contain (String[] A, String[] B) {
    	
    	for (String a : A) {
    		
    		for (String b: B) {
    			
    			if(a.equals(b)) return true;
    		}
    		
    	}
    	return false;
    }
    
    

}


