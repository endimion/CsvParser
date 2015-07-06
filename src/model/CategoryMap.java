package model;

import java.util.Vector;



public class CategoryMap {

	private String name;
	private Vector<String> matches;
	
	
	public CategoryMap(){
		name = null;
		matches = new Vector<String>();
	}//end of constructor
	
	
	public void setName(String n){this.name = n;}
	public String getName(){return this.name;}
	
	public void setMatches(Vector<String> m){this.matches = m;}
	public Vector<String> getMatches(){return matches;}
	
	/**
	 * returns a String representation of this object
	 */
	public String toString(){
		if(name == null){
			return "";
		}else{
			String res = "";
			res += name +";";
			for(String match: getMatches()){
				res += match +"&&";
			}//end of looping through the matches
			if(res.endsWith("&&")){
				res = res.substring(0, res.length()-2); //remove the last &&
			}
			
			
			return res;
		}//end if name is not null
	}//end of toString

	
	/**
	 * adds the given array of strings as the mathces vector 
	 * @param match
	 */
	public void setMatchesArray(String[] match){
		Vector<String> m = new Vector<String>();
		for(String ma : match){
			m.add(ma);
		}//end of for
		
		setMatches(m);
	}//end of setMatchesArray
	
	/**
	 * 
	 * @param name the name of a foreign category
	 * @return true if the given name belongs to the foreign categories mapped to this Category
	 */
	public boolean hasCategory(String name){
		for(String fCat: getMatches()){
			if(fCat.trim().equals(name.trim())) return true;
		}//end of for loop
		return false;
	}//end of hasCategory
	
	
	
	
	
}//end of CategoryMap
