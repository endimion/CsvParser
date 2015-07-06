package tests;

import static org.junit.Assert.assertEquals;

import model.CategoriesSet;
import model.CategoryMap;
import model.FileHelper;
import model.SupplierCol;

import org.junit.Before;
import org.junit.Test;



public class FileHelperTester {

	FileHelper fh ;
	SupplierCol sup ;
	
	@Before
	public void setUpTests(){
		fh = new FileHelper();
		sup = new SupplierCol();
	}// setUpTests
	
	
	@Test
	public void testSaveSupplier() {

		sup.setItemNumber("item number");
		sup.setCategory("article group name");
		sup.setDescription("article description");
		sup.setSupItemNumber("supplier's item number");
		sup.setEan("EAN number 1");
		sup.setRetailPrice("retail price");
		sup.setAvailability("availability (tendency)");
		sup.setName("difox");
		sup.setIsAvailable("available");
		sup.setSeparator(";");
		
		fh.saveSupplier(sup);
	}//end of testIceCatHelper
	
	@Test
	public void testGetSuppliers(){
		sup = fh.getSuppliersCol().get(0);
		assertEquals(sup.getName(),"difox2");
		
		
		sup = fh.getSuppliersCol().get(1);
		assertEquals(sup.getName(),"telepart");
		assertEquals(sup.getIsAvailable(),"available");
		
		assertEquals(fh.getSuppliersCol().size(),3);
	}//end of testGetSuppliers
	
	
	
	
	@Test
	public void testSaveCategory(){
		CategoryMap cm = new CategoryMap();
		cm.setName("ΦΩΤΟΓΡΑΦΙΑ///ΜΗΧΑΝΕΣ///INSTANT ΜΗΧΑΝΕΣ");
		String match = "Instant cameras";
		cm.setMatchesArray(match.split("\n"));
	
		fh.saveCategory(cm);
		
		cm =new CategoryMap();
		cm.setName("ΦΩΤΟΓΡΑΦΙΑ///ΑΝΑΛΩΣΙΜΑ");
		match = "B&W film;"
				+ "Colour negative film;"
				+ "Colour negative film -professional-; "
				+ "Slide film;"
				+ "Slide film -professional-;"
				+ "Instant film;";
		cm.setMatchesArray(match.split(";"));
		fh.saveCategory(cm);
		
		CategoriesSet cs = fh.getCategories();
		assertEquals(cs.getContainingCat("Colour negative film"),"ΦΩΤΟΓΡΑΦΙΑ///ΑΝΑΛΩΣΙΜΑ");
		
	}//end of testGetSuppliers
	
	
	@Test
	public void testGetCategories(){
		CategoriesSet cs = fh.getCategories();
		
		for(CategoryMap cm : cs){
			System.out.println(cm.getName());
		}
	}//end of testGetSuppliers
	
	
	
	
	
	
	
	
}//end of class
