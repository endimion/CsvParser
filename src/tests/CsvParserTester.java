package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.Vector;

import model.CsvParser;
import model.IceCatHelper;
import model.ParsedRow;

import org.junit.Before;
import org.junit.Test;

public class CsvParserTester {

	File csvFile;
	
	@Before
	public void setUpTests(){
		csvFile = new File("src/testFiles/test1.csv");
		if(csvFile == null) System.out.println("file not found!!!");
	}// setUpTests

	

		
	@Test
	public void testFileToVect() {
		Vector<ParsedRow> parsed = CsvParser.fileToVect(
					new File("src/testFiles/difox_price_list-18584472.CSV"), ";");
		assertNotNull("parsed is not null",parsed);
		//assertEquals(parsed.size(),36635);
		ParsedRow p = parsed.get(0);
		
		assertEquals( p.getElement("item number"),"103154");
		assertEquals(p.getElement("article description"),"Fujifilm Instax Mini Hello Kitty Set");
		
		p = parsed.get(27); 
		assertEquals( p.getElement("item number"),"100242");
		p = parsed.get(75); 
		assertEquals( p.getElement("item number"),"766633");
		System.out.println(p.getElement("article description"));
	
	}//end testFile


	
	@Test
	public void testIceCatHelper() {
		IceCatHelper ice = new IceCatHelper();
		ice.getDailyFile();
		
	}//end of testIceCatHelper
	
	
}
