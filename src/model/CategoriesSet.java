package model;

import java.util.Vector;

public class CategoriesSet  extends Vector<CategoryMap>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3135219625897486156L;

	public CategoriesSet(){
		super();
	}
	
	public void addMap(CategoryMap m){
		this.add(m);
	}//end of addMap;
	
	
	/**
	 * 
	 * @param name the name of the category we are looking for
	 * @return a CategoryMap object with the name we are looking for
	 * if none is found null is returned
	 */
	public CategoryMap getCategory(String name){
		for(CategoryMap c: this){
			if(c.getName().equals(name)){
				return c;
			}
		}
		return null;
	}//end of getCategory
	
	
	/**
	 * 
	 * @param name, the name of the foreign category whose native one we are looking for
	 * @return the name of the native category if it exists, else null
	 */
	public String getContainingCat(String name){
		String res=null;
		if(name!= null){
			for(CategoryMap cm : this){
				if(cm.hasCategory(name.trim())){
					res = cm.getName().trim();
					break;
				}//end if the cm has the given foreign category
				
			}//end of looping through the categories map
		}
		return res;
	}//end of getMatchingCategory

	
	
}//end of class
