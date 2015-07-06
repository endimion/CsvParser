package tests;

import java.io.File;

import model.IceCatHelper;

import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.xml.XmlPage;

public class IceCatHelperTester {

	
	@Before
	public void setUpTests(){
		
	}// setUpTests
	
	
	@Test
	public void testIceCatHelper() {
		IceCatHelper ice = new IceCatHelper();
		
		 XmlPage p = ice.getProductXml("4547410093988");
		 ice.saveXmlToFile("test1.xml", p);
		 
		
		  p = ice.getProductXml("4548736014183");
		 ice.saveXmlToFile("test2.xml", p);
	}//end of testIceCatHelper
	
	
	@Test
	public void testSavePicturesToDrive(){
		File xml = new File("test2.xml");
		IceCatHelper ice = new IceCatHelper();
		//ice.savePicturesToDrive(xml, "pic2.jpg");
		
		
	}//end of testSavePicturesToDrive
	
	
	
	
	
	
}//end of class
