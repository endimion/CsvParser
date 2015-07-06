package tests;

import java.io.File;
import java.util.Vector;

import model.ParsedRow;
import model.XmlParser;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class XmlParserTester {

	@Before
	public void setUpTests(){
		
	}// setUpTests
	
	
	@Test
	public void testIceCatHelper() {
		XmlParser xmlP = new XmlParser();
		//xmlP.
		// ice.saveXmlToFile("test1.xml", p);
		 NodeList  nList = xmlP.getNode(new File("test1.xml"), "Name");
		 System.out.println( "the String of the attribute Value is "+ xmlP.getAttribute(nList.item(0),"Value" ));
		
		  nList = xmlP.getNode(new File("test1.xml"), "Product");
		  System.out.println( "the String of the attribute HighPic is "+ xmlP.getAttribute(nList.item(0),"HighPic" ));
		 
		  nList = xmlP.getNode(new File("orico.xml"), "product");
		  Node firstProduct = nList.item(0);
		  System.out.println("the description of the first item of the orico.xml file is " + 
				  			xmlP.getChild(firstProduct, "description").getTextContent());
		  
		  firstProduct = nList.item(1);
		  System.out.println("the description of the Second item of the orico.xml file is " + 
				  			xmlP.getChild(firstProduct, "description").getTextContent());
		  
		  System.out.println("retriving tag content with getValue " + xmlP.getValue("description", firstProduct));
		  
		  nList = xmlP.getNode(new File("test1.xml"), "Product");
		  firstProduct = nList.item(0);
		  System.out.println("retriving attribute content with getValue " + xmlP.getValue("HighPic", firstProduct));
		  
		  String display = xmlP.getParAtt(new File("iceXml"), 
				  "ProductFeature",   "Presentation_Value", "Name", "Value", "Βάρος");
		  System.out.println("  ADADF " + display);
		  
	}//end of testIceCatHelper
	
	
	@Test
	public void testGetFileToVect(){
		XmlParser xmlP = new XmlParser();
		Vector<String> keys = new Vector<String>();
		keys.add("ean");
		keys.add("mpn");
		keys.add("description");
		
		Vector<ParsedRow> prs = xmlP.getFileToVect(new File("orico.xml"), "product", keys);
		
		for(ParsedRow r : prs){
			System.out.println("the data of the row : ean" +
		r.getElement("ean") + ", mpn: " + r.getElement("mpn") + 
		", description:" + r.getElement("description"));
		}
	}//end of test
	
	
	
	
	
	
	
}//end of class
