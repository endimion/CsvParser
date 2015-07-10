package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;


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
	 *  Reads the contents of the Url  and saves it as the File /temp.xml
	 *  @return a file called temp.xml that contains the contents of the url
	 */
	public  File getUrl(){
		   
		   try{  
		   
			   connection.setRequestMethod("GET");
		      //connection.setRequestProperty("HOST", "example.com");
		      connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:16.0) Gecko/20100101 Firefox/16.0");
		      connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		      connection.setRequestProperty("Accept-Language", "tr-TR,tr;q=0.8,en-US;q=0.5,en;q=0.3");
		      connection.setDoInput(true);
		      connection.setDoOutput(true);
		      BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
		      String line,response="";
		      while((line=in.readLine())!=null)
		         response+=(line+"\n");
		      in.close();
		     // System.out.println(response);
		     
		     File f = new File(FileHelper.getExecFolder()+"/temp.xml");
			 PrintWriter out = new PrintWriter(f);
			 out.println(response);
			 out.close();
			 
		      return f;
		   }catch(Exception e){e.printStackTrace();}
		   return  null;
		}//end of getUrl
	
	
	
	
	
	
	
	
}//end of class
