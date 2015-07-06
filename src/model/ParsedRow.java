package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Vector;

public class ParsedRow implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5076519670755837906L;

	private Vector<String> keys;
	private HashMap<String,String> row;
	
	public ParsedRow(Vector<String> keys){
		this.keys = keys;
		row = new HashMap<String,String>();
	}
	
	public Vector<String> getKeys(){return keys;}
	public HashMap<String,String> getRow(){return this.row;}
	
	public void setKeys(Vector<String> k){this.keys = k;}
	
	public void setHashMap(HashMap<String,String> m){
		//no op
	}//end of setHashMap
	
	/**
	 *  takes a Vector<String> and if the size of it is correct then one by one
	 *  they are added to the hashmap row such that data(i) has the key(i)
	 * @param data a vector of String containing the data of a row
	 */
	public void setRowData(Vector<String> data){
		if( data == null || keys == null ){
			System.out.println("corrupted data");
		}else{
			//if(data.size()   != keys.size()) ; //System.out.println("mismach " + data.size() + " " +keys.size());
			//else{
			//	System.out.println("MATCH");
				for(int i = 0; i  < data.size(); i++){
					String key = keys.get(i);
					String value = data.get(i);
					row.put(key, value);
				}//end of for loop
			//}
		}//end of if the data is not corrupted
		
	}//end of setRowData
	
	/**
	 * 
	 * @param key
	 * @return the element of the row for that given key if it exists, else null
	 */
	public String getElement(String key){
		if(keys != null){
			//if(row.get(key.trim())== null){
				
				//System.out.println("ParsedRow.getElement : CANNOT FIND KEEY:" 
				//		+ key);
				//for(String k : keys){
				//	System.out.println("existing keys:" + k);
				//}
			//}
			return (row.get(key.trim())!= null)?row.get(key.trim()):"";
		}else{ 
			System.out.println("ParsedRow.getElement : key given is NULL" );
			return null;
		}
	}//end of getElement
	
	
	
}//end of class
