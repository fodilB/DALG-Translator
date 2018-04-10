package com.esi.dalg.data;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class DataManager {
	
	private FileInputStream fis;
	
	private FileOutputStream fos;
	
	private ObjectInputStream ois;
	
	private ObjectOutputStream oos;
	
	private final String Serialization_Folder = "Serialized_Dictionnaries/";
	
	private final String Dictionnaries        = "Dictionnaries/";
	

	public static void main(String[] args) {
			
		String      fileName      = "Dalg_verbe";
		
		DataManager manager       = new DataManager();		
		
		HashMap<String, String> dictionary = null;
		
		try {
			
			dictionary = (HashMap<String, String>) manager.dictionaryLoader(fileName);
			
			manager.serializeObject(fileName,dictionary);

		}catch (IOException e) {
			e.printStackTrace();	
		} 
		
	}
	
	

    public Map<String, String> dictionaryLoader(String text) throws IOException {	
        
        HashMap<String, String> dictionaryMap = new HashMap<String, String>();	
        
		BufferedReader lecteurAvecBuffer = null;
		
		text = this.Dictionnaries + text;
	    
		String ligne="";  
	    
		try {
			
		      lecteurAvecBuffer = new BufferedReader(new FileReader(text));
		     
	        }catch(FileNotFoundException exc) {	
	        	
	    	 System.err.println("Erreur d'ouverture du fichier : "+text);
	    }
	    
		
		while ((ligne = lecteurAvecBuffer.readLine()) != null) {
                 
            StringTokenizer str = new StringTokenizer(ligne);
            
			while (str.hasMoreTokens()) 
				
				 dictionaryMap.put(str.nextToken(), str.nextToken());
	    }

        return dictionaryMap;
     }
    
    public void serializeObject(String outputFile,Object object) throws IOException {
    	
    	outputFile = this.Serialization_Folder+outputFile;

        fos = new FileOutputStream(outputFile+".ser");
               
        oos = new ObjectOutputStream(fos);
               
        oos.writeObject(object);
               
        oos.close();
               
        fos.close();

    }
    
    
    public HashMap<String, String> deserializeObject(String fileName) throws IOException, ClassNotFoundException {
	 	   
    	  fileName = this.Serialization_Folder + fileName;   
    	
    	  fis = new FileInputStream(fileName+".ser");
	 	         
	 	  ois = new ObjectInputStream(fis);
	 	         
	 	  @SuppressWarnings("unchecked")
		  HashMap<String, String> map = (HashMap<String, String>)ois.readObject();
	 	         
	 	  ois.close();
	 	         
	 	  fis.close();
	 	      
	 	  return map;
    }

	public String getSerialization_Folder() {
		return Serialization_Folder;
	}

	public String getDictionnaries() {
		return Dictionnaries;
	}
}
