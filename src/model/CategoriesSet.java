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
			if(c.getName().trim().equals(name.trim())){
				return c;
			}
		}
		return null;
	}//end of getCategory
	
	
	/**
	 * @param escapeLocalCate, denotes whether or not the local categories have to be escaped
	 * @param name, the name of the foreign category whose native one we are looking for
	 * @return the name of the native category if it exists, else null
	 */
	public String getContainingCat(String name, boolean escapeLocalCat){
		String res=null;
		//System.out.println("CategorySet.getCategory:: Looking for category" + name);
		
		if(name!= null){
			for(CategoryMap cm : this){
				//System.out.println("CategorySet.getCategory:: Looking for category" + name + " in " + cm.getName());;
				if(cm.hasCategory(name.trim(),escapeLocalCat)){
					res = cm.getName().trim();
					//System.out.println("CategorySet.getCategory:: Looking for category" + name + " FOUND");
					break;
				}//end if the cm has the given foreign category
				
			}//end of looping through the categories map
		}
		return res;
	}//end of getMatchingCategory

	
	
}//end of class
