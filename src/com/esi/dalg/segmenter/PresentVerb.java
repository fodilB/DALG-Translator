package com.esi.dalg.segmenter;


public class PresentVerb extends VerbStem {
	
	private String newStem ="";

	@Override
	public String scorePartition(String stem) {
		
		int score =0;

		if (Vi3lNa9is(stem)) {
			
			score=3;

			return this.newStem+":"+score;
			
		}else if(stem.length()==3 && Vi3lAdjwaf1(stem)) {
			
			score=3;

			return this.newStem+":"+score;
			
		}
										  
   
		return stem+":"+score;
	}
	
	private boolean Vi3lNa9is(String stem) {
		
		this.newStem = stem +"ا";
		
		if(this.dictionary_verbs.containsKey(this.newStem)) 
			
			return true;

		return false;
	}
	

	
	private boolean Vi3lAdjwaf1(String stem) {
		
		String [] stemCaracters = stem.split("");
	
	    newStem = stemCaracters[0] + "ا" + stemCaracters[2];
	    
	    if(this.dictionary_verbs.containsKey(this.newStem))
	    	    return true;

		return false;
	}

}
