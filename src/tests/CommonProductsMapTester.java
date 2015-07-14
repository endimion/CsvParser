package tests;

import model.CommonProductsMap;

import org.junit.Before;
import org.junit.Test;

public class CommonProductsMapTester {

	
	CommonProductsMap map;

	@Before
	public void setUpTests(){
		map = new CommonProductsMap();
	}// setUpTests
	
	@Test
	public void testGetMap(){
		System.out.println(map.getMap().get("8806086087124").size());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}//end of class
