package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FtpHelper {

	private FTPClient client = new FTPClient();
	
	
	
	
	
	public FtpHelper(){}//end of constructor
	
	/**
	 * intializes the connection to the ftp server
	 * and connects to it
	 */
	public void connectAndInit(){
		try{
			client.connect("ftp.ekos.gr");
		    client.login("it@ekos.gr", "aVF7@t0m!");
		    client.setFileType(FTP.BINARY_FILE_TYPE);
			client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
			client.setSoTimeout(10000);
			client.enterLocalPassiveMode();
		}catch(Exception e){e.printStackTrace();}
	}//end of connectAndInit
	
	/**
	 * logs out of the ftp server and disconnects
	 */
	public void closeConnection(){
		 try {
			client.logout();
			client.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}//end of closeConnection
	
	
	
	/**
	 * 
	 * @param picture a File object containing a picture
	 * @param folderPath the path of the folder in the server in which the picture must be save in 
	 * @param newPicName the name the picture will be stored to
	 * opens an ftp connection to a hard coded ftp client and 
	 * then uploads the given file (denoted by its path) to a folder given by folderPath on the server 
	 */
	public void uploadFile(File  picture, String folderPath, String newPicName){
		FileInputStream  fis = null;
		try {
			String filename = newPicName;//picture.getName();
			
			 fis = new FileInputStream(picture);
			 //if the directory does not exist on the server we have to create it
			 if(!  client.changeWorkingDirectory(folderPath)){
				 if (!client.makeDirectory(folderPath)) {
			          throw new IOException("Unable to create remote directory '"
			        		  + folderPath + "'.  error='" + client.getReplyString()+"'");
			        }//if we cannot create it we throw an exception
			 }//end if the folder does not exist

			 client.storeFile(folderPath+"/"+ filename, fis);
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
		        if (fis != null) {
		            fis.close();
		            System.out.println(" FtpHelper uploadFile: finished uploading");
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		    }//end of catch
		}//end of finally
	}//end of uploadFile
	
	
	
	
}//end of class
