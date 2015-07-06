package tests;

import java.io.File;
import model.FileHelper;
import model.FtpHelper;

import org.junit.Before;
import org.junit.Test;

public class FtpHelperTester {

	FtpHelper fh ;
	
	@Before
	public void setUpTests(){
		 fh = new FtpHelper();
	}// setUpTests
	
	
	@Test
	public void testUploadFile() {
		fh.connectAndInit();
		String fPath  = (new FileHelper()).getExecFolder();
		File f = new File(fPath+"/pic2.jpg");
			
		fh.uploadFile(f, "/public_html/image/data/testImageUploader" , "newimg.jpg");
		System.out.println("the path is " + fPath);
		fh.closeConnection();
	}//end of testIceCatHelper
	
	
	
	
}//end of class
