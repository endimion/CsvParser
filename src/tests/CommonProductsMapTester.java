package tests;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import model.CommonProductsHelper;
import model.CommonProductsHelper.PairOfString;
import model.Product;

import org.junit.Before;
import org.junit.Test;

public class CommonProductsMapTester {

	
	CommonProductsHelper map;

	@Before
	public void setUpTests(){
		map = new CommonProductsHelper();
	}// setUpTests
	
	
	public void testGetMap(){
		//System.out.println(map.getMap().get("8806086087124").size());
		HashMap<String,Vector<Product>> hashMap = map.getMap();
		// loop through the keys of the hashMap
		Iterator<Entry<String, Vector<Product>>> it = hashMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Vector<Product>> pair = (Map.Entry<String, Vector<Product>>)it.next();
			System.out.println("CommonProductController.buildCommonMap " + pair.getKey());
			it.remove(); // avoids a ConcurrentModificationException
		}//end of looping through the iterator of the hashMap
	}
	
	
	
	@Test
	public void testBuildSuppierPairs(){
		Vector<PairOfString> pairs = map.buildSuppierPairs();
		System.out.println("testBuildSuppierPairs : " + pairs.size());

		for(int i = 0; i < pairs.size(); i++){
			System.out.println("Suplier 1: " + pairs.get(i).getFirst() + " Second : " + pairs.get(i).getSecond());
		}
		
	}//end of testBuildSuppierPairs
	
	
	
	
	
	
	
	
	
	
	
}//end of class
