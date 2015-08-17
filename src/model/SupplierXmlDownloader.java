package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;


public class SupplierXmlDownloader {

	private  final String username ;
	private final  String password;
	private final String  url;
	HttpURLConnection connection;
	
	public SupplierXmlDownloader(String user, String pass, String url){
		this.username = user;
		this.password = pass;
		this.url = url;
		try{
			  URL localUrl=new URL(this.url);
		      connection=(HttpURLConnection)localUrl.openConnection();
		     
		      
			 if(username != null && !username.equals("")&& this.password!= null && !this.password.equals("") ){
				 String base64encodedUsernameAndPassword = IceCatHelper.base64Encode(username + ":" + password);
				 connection.setRequestProperty("Authorization", "Basic " + base64encodedUsernameAndPassword);
			 }//end if the supplier requires authentication
		}catch(Exception e){e.printStackTrace();}
	}//end of constructor
	
	
	
	/**
	 * 
	 * @return a String representation of the given URL
	 */
	public String getUrlAsString(){
		   try{  
			   initConnection();
		      BufferedReader in=
		    		  new BufferedReader(new InputStreamReader(connection.getInputStream()));
		      String line,response="";
		      while((line=in.readLine())!=null){
		         response+=(line+"\n");
		      }
		      in.close();
		     return response.trim();
		   }catch(Exception e){
			   e.printStackTrace();}
		   return  null;
	}//end of getUrlAsString
	
	
	/**
	 *  Reads the contents of the Url  and saves it as the File /temp.xml
	 *  @return a file called temp.xml that contains the contents of the url
	 */
	public  File getUrl(){
		   
		   try{  
			   initConnection();
		      BufferedReader in=
		    		  new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF8"));
		      //System.out.println("Supxmldown:: " + connection.getContentType());
		     // System.out.println("Supxmldown:: " + connection.getContentEncoding());
		      
		      String line,response="";
		     // int i= 0;
		      line=in.readLine();
		      while(line!=null){
			     response+=(line+"\n");
			     line=in.readLine();
		      	}
		      in.close();
		     // System.out.println(response);
		      CharsetEncoder encoder = Charset.forName("UTF-8").newEncoder();
		     
		      File f = new File(FileHelper.getExecFolder()+"/temp.xml");
		     FileOutputStream fos = new FileOutputStream(f,false);
		     OutputStreamWriter out = new OutputStreamWriter(fos,encoder);
			//try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(suppliers, false)))) {
				out.append(response);   
			    out.flush();
			    out.close();
		     
			    
		      return f;
		   }catch(Exception e){e.printStackTrace();}
		   return  null;
		}//end of getUrl
	
	
	
	
	/**
	 * intitallizes t he connection
	 */
	private void initConnection() throws ProtocolException{
		  connection.setRequestMethod("GET");
	      //connection.setRequestProperty("HOST", "example.com");
	      connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:16.0) Gecko/20100101 Firefox/16.0");
	      connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	     // connection.setRequestProperty("Accept-Language", "tr-TR,tr;q=0.8,en-US;q=0.5,en;q=0.3");
	      //connection.setRequestProperty("Accept-Charset", "UTF-8");
	      connection.setDoInput(true);
	      connection.setDoOutput(true);
	      
	     
	}//end of initConnection
	
	
	
	
	
}//end of class
