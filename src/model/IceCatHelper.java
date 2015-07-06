package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import javax.xml.bind.DatatypeConverter;


import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.xml.XmlPage;


public class IceCatHelper {

	private  final  static String username = "ioanniszervos";
	private final static String password = "6392884a";
	WebClient webClient;
	
	/**
	 *  intializes the IceCatHelper object by generating a WebClient object 
	 *  with the required authorization header to connect to IceCat.biz
	 */
	public IceCatHelper(){
		//first we initialize the WebClient
		 webClient = new WebClient();	
		 String base64encodedUsernameAndPassword = base64Encode(username + ":" + password);
		 webClient.addRequestHeader("Authorization", "Basic " + base64encodedUsernameAndPassword);
	}//end of constructor

	
	/**
	 * @return the webClient of the object
	 */
	public WebClient getClient(){
		 return webClient; 
	}//end of makeIceCatClient
	
	
	/**
	 * @param ean a String denoting the EAN code of the product
	 * @return an XmlPage containing the information IceCat has for that
	 * product (if it has any)
	 */
	public XmlPage getProductXml(String ean){
		try {
			XmlPage page = getClient().getPage("http://data.icecat.biz/xml_s3/xml_server3.cgi?ean_upc="
					+ean+";lang=EL;output=productxml");
			return page;
		} catch (FailingHttpStatusCodeException | IOException e) {
				e.printStackTrace();
				return null;
		}//end of catch
	}//end of getProductXml
	
	
	/**
	 * 
	 * @param filePath, the path were we wish to save the given xmlPageto
	 * @param page, an xmlPage object we want to save as a text file
	 * @return the File object which contains as text the xml Page
	 */
	public File  saveXmlToFile(String filePath, XmlPage page){
		try{
			 String pageXml = page.asXml();
			 File f = new File(filePath);
			 
			 PrintWriter out = new PrintWriter(f);
			 out.println(pageXml);
			 out.close();
			 return f;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}//end of catch
	}//end of saveXmlToFile

	
	/**
	 * 
	 * @param savePath, a String denoting where to save the picutres
	 * @return, a String denoting the path in the local drive where the picture was saved
	 * othewise null is returned
	 */
	public String savePicturesToDrive( String savePath, String picURL){
		
		//XmlParser pars = new XmlParser();
		//NodeList nList = pars.getNode(xml, "Product");
		
		//String picURL = pars.getAttribute(nList.item(0),"HighPic");
		try{
			URL website = new URL(picURL);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream(savePath);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
			return savePath;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("IcatHelper.savePicturesToDrive:: error downloading file " +  picURL);
			return null;
		}//end of catch
	}//end of savePicturesToDrive
	
	
	
	
	
	
	

	/**
	 * return the Daily xml file  which contains the updates of icecat
	 */
	public  void getDailyFile(){
		 try{
			 XmlPage page = getClient().getPage("https://data.icecat.biz/export/level4/EL/");
			 String pageXml = page.asXml();
			 PrintWriter out = new PrintWriter("daily.xml");
			 out.println(pageXml);
			 out.close();
		 }catch(Exception e){e.printStackTrace();}
	}//end of getFile
	
	
	/**
	 * @param stringToEncode
	 * @return an ecoded version of the given string based on a 64 bit algorithm
	 */
	private static String base64Encode(String stringToEncode)
	 {
	    return DatatypeConverter.printBase64Binary(stringToEncode.getBytes());
	 }//end of base64Encode
	
	
	
	
	
}//end of class
