package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
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
	 * Also this method compresses the images, after downloading them if they are more than 400Kb
	 * @param savePath, a String denoting where to save the picutres
	 * @return, a String denoting the path in the local drive where the picture was saved
	 * othewise null is returned
	 */
	public String savePicturesToDrive( String savePath, String picURL, boolean compress){
		
		//XmlParser pars = new XmlParser();
		//NodeList nList = pars.getNode(xml, "Product");
		
		//String picURL = pars.getAttribute(nList.item(0),"HighPic");
		try{
			URL website = new URL(picURL);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream(savePath);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
			
			if(compress){
				File picFile = new File(savePath);
				long fileSizeInBytes = picFile.length();
				// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
				long fileSizeInKB = fileSizeInBytes / 1024;
			
				if(fileSizeInKB > 400){
					BufferedImage bufferedImage = null;
					try {
						bufferedImage = ImageIO.read(picFile);
						OutputStream ifos = new FileOutputStream(savePath);
						writeJPG(bufferedImage, ifos, 0.3f);
					} catch (IOException e) {e.printStackTrace();}

				}//end if the picutre is more than 200KB
			}//end if compresss
			
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
	
	
	/**
	 * Takes a image and creates a compressed version of it using the outputStream
	 * @param bufferedImage, the image
	 * @param outputStream, the Outputstream to which the compressed image will be saved
	 * @param quality, the quality of the compression
	 * @throws IOException
	 */
	public static void writeJPG( BufferedImage bufferedImage,  
																OutputStream outputStream,	    float quality) throws IOException
	{
		    Iterator<ImageWriter> iterator =
		        ImageIO.getImageWritersByFormatName("jpg");
		    ImageWriter imageWriter = iterator.next();
		    ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
		    imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		    imageWriteParam.setCompressionQuality(quality);
		    ImageOutputStream imageOutputStream =
		        new MemoryCacheImageOutputStream(outputStream);
		    imageWriter.setOutput(imageOutputStream);
		    IIOImage iioimage = new IIOImage(bufferedImage, null, null);
		    imageWriter.write(null, iioimage, imageWriteParam);
		    imageOutputStream.flush();
		    
		}//end of writeJPG
	
	
}//end of class
