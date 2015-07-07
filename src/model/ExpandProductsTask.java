package model;

import java.io.File;
import java.util.Vector;

import javafx.concurrent.Task;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ExpandProductsTask extends Task<Vector<Product>> {
	
	Vector<Product> prods; 
	String supplier;
	
	public ExpandProductsTask(Vector<Product> prods, String supName){
		this.prods = prods;
		this.supplier = supName;
	}//end of constructor
	
	
	@Override
	protected Vector<Product> call() throws Exception {
		System.out.println("ExpandProductsTask.call :: Thread Started");
		IceCatHelper ice = new IceCatHelper(); 
		XmlParser xPar = new XmlParser();
		File xml ;
		String weight;
		String descr = null;
		int i = 0;
		FileHelper fh = new FileHelper();
		NodeList descPL ;
		NodeList picPL ;
		String picName ;
		String extraPic;
		String picURL="";
		String extraPicURL="";
		
		
		Vector<Product> output = new Vector<Product>();
		boolean add = false ;
		
		//create and intiallize the ftp connection to upload the files
		FtpHelper ftp = new FtpHelper();
		ftp.connectAndInit();
		
		int progress = 0;
		
		System.out.println("ExpandProducts.call::  will expand " + prods.size() + "products");
		
		for(Product prod : prods){
			updateProgress(progress*1, prods.size());
			
			
			extraPic = prod.getAddPic(); //GET the extra pictures either from the product or iceCat
			
			prod.setStStatus("2-3 ημέρες");
			prod.setStatus("1" ); 
			prod.setTax_class("κλάση I 23%~26%");
			
			if(!fh.belongs(prod.getModel() , FileHelper.getExecFolder() +"/valid.prod")){
				try{
					add = true;
					xml = ice.saveXmlToFile("iceXml", ice.getProductXml(prod.getEan()));
					Node prodN =  xPar.getNode(xml, "Product").item(0);
					
					//check if the xml file we get contains any info.
					//else store the product to the not found file
					if( !xPar.getValue("Code",prodN).equals("-1")){
						
						weight = xPar.getParAtt(xml, "ProductFeature",   "Presentation_Value", "Name", "Value", "Βάρος");
						//System.out.println("finished "+ i + " found weight " + weight);
						descPL =  xPar.getNode(xml, "SummaryDescription");
						picPL = xPar.getNode(xml,"Product");
						picName = null;
						descr = null;
						
						if(descPL != null) descr = xPar.getValue("LongSummaryDescription" , descPL.item(0));
						if(picPL != null) {
							picURL = xPar.getValue("LowPic",picPL.item(0));
							extraPicURL =  xPar.getValue("HighPic",picPL.item(0));
							
							picName  = picURL;
							extraPic = extraPicURL;
							//System.out.println("extra pic found "+extraPic);
							
							if(picName != null && !picName.equals("")){
								String[] nameArr = picName.split("/");
								picName = nameArr[nameArr.length-1];
							}else{
								fh.addErrLog("no PICTURE found in iceCate for: " + prod.toString(supplier), "errorProd.log");
							}

							if(extraPic!= null && !extraPic.equals("")){
								String[] nameArr = extraPic.split("/");
								extraPic = nameArr[nameArr.length-1];
							}
							
						}//end if picPL is not null
						
						if(weight == null ){
							fh.addErrLog("no WEIGHT found in iceCate for: " + prod.toString(supplier), "errorProd.log");
							prod.setWeight("10kg");
						}else{
							prod.setWeight(weight);
						}//end of setting the weight of an object
	
						Double var1 = FileHelper.getPriceConfig(supplier).get(0);
						Double var2 = FileHelper.getPriceConfig(supplier).get(1);
						Double var3 = FileHelper.getPriceConfig(supplier).get(2);
						Double var4 = FileHelper.getPriceConfig(supplier).get(3);
						Double var5 = FileHelper.getPriceConfig(supplier).get(4);
						Double kiloP = FileHelper.getPriceConfig(supplier).get(5);
						prod.setDoublePrice(prod.getPrice(var1,var2,var3,var4,var5,kiloP));
					
						
						if(descPL == null || descr == null) {
							fh.addErrLog("no Description found in iceCate for: " + prod.toString(supplier) 
										+" of Supplier: " + supplier, "errorProd.log" );
						}else{
							prod.setDescription(descr);
						}
						
						if(picName != null){
							if (i < 10){ //TODO remove this if 
								
								File dir = new File(FileHelper.getExecFolder() +"/pictures");
								File extraDir = new File(FileHelper.getExecFolder() +"/pictures/high");
								
								try{
									if(!dir.exists() || !extraDir.exists()){
										dir.mkdir();
										extraDir.mkdir();
									}
									 //System.out.println("DIR created");
									 String picPath =  FileHelper.getExecFolder() +"/pictures/"+ picName;
									String extraPicPath = FileHelper.getExecFolder() +"/pictures/high/"+ extraPic;
									 
									ice.savePicturesToDrive(picPath,picURL);
									ice.savePicturesToDrive(extraPicPath,extraPicURL);
									
									String newPicName = prod.getModel().trim() +".jpg";
									
									ftp.uploadFile(new File(picPath), "/public_html/image/data/csvpic", newPicName);
									ftp.uploadFile(new File(extraPicPath), "/public_html/image/data/csvpic/high",
																																			newPicName);
								
									picName = "csvpic/" +  prod.getModel().trim() +".jpg";
									prod.setPic(picName);
									
								}catch(Exception e){e.printStackTrace();}
								
							}//end if i <10
						}//end if picName is not null
		
					
						fh.saveValidProd(prod.getModel());
					}else{
						
						Double var1 = FileHelper.getPriceConfig(supplier).get(0);
						Double var2 = FileHelper.getPriceConfig(supplier).get(1);
						Double var3 = FileHelper.getPriceConfig(supplier).get(2);
						Double var4 = FileHelper.getPriceConfig(supplier).get(3);
						Double var5 = FileHelper.getPriceConfig(supplier).get(4);
						Double kiloP = FileHelper.getPriceConfig(supplier).get(5);
						prod.setDoublePrice(prod.getPrice(var1,var2,var3,var4,var5,kiloP));
						
						String[] nameArr = prod.getPic().split("/");
						if(prod.getPic() != null && !prod.getPic().equals("") && nameArr.length >= 1){
							System.out.println("ExpandProductsTask:: i need no icecate downloading...");
							picName = nameArr[nameArr.length -1];
							picURL = prod.getPic();
							saveAndUploadPic(picName, extraPic, picURL, 
									extraPicURL, prod, ice, ftp);
							//once it is uploaded then we store to the product object the correct path for the server
							//prod.setPic("csvpic/" +prod.getModel().trim() +".jpg" );
						}//end if the product has a picture in it
						
						
						fh.saveNotFound(prod ,supplier);
						//add = false; //if the iceCatXml does not contain the file it
												 //should not be added
					}//end if the product was not found inside ICeCAT
					
					i++;
				}catch(Exception e){
					i++;
					e.printStackTrace();
				}//end of catching the exception
				//System.out.println("ProductHelper.expandProducts processed : " + i + "products");
				
				if(add){ 
					output.add(prod); //ADD the product to the output
					fh.saveParsedProduct(supplier, prod);
					//System.out.println("ProductHelper.expandProducts processed :  ADDED" + prod.toString(supplier) );
				}
			}//end if the product does nto exist in teh vaildate file 			
		
			updateMessage("Processing product "+ prod.getpName());
			progress++;	
		}//end of looping through the products
		
		//System.out.println("ExpandPRoductsTask.call :: output size before validation " + output.size());
		output = fh.validateSupplierProductList(output, supplier);
		return output;
	
	}//end of call
	
	
	
	/**
	 * 
	 * @param picName the name of the picture
	 * @param extraPic the name of the extrapicture
	 * @param picURL the location of the picture
	 * @param extraPicURL the location of the extra picture
	 * @param prod the Prodcut that has these images
	 * @param ice an IceCatHelper object
	 * @param ftp an FTP Helper object
	 */
	private void saveAndUploadPic(String picName, String extraPic,
			String  picURL, 	String  extraPicURL, Product prod, 
			IceCatHelper ice, FtpHelper ftp) {

		if(picName != null){
				File dir = new File(FileHelper.getExecFolder() +"/pictures");
				File extraDir = new File(FileHelper.getExecFolder() +"/pictures/high");
				
				try{
					if(!dir.exists() || !extraDir.exists()){
						dir.mkdir();
						extraDir.mkdir();
					}
					 //System.out.println("DIR created");
					 String picPath =  FileHelper.getExecFolder() +"/pictures/"+ picName;
					//String extraPicPath = FileHelper.getExecFolder() +"/pictures/high/"+ extraPic;
					 
					ice.savePicturesToDrive(picPath,picURL);
					//ice.savePicturesToDrive(extraPicPath,extraPicURL);
					
					String newPicName = prod.getModel().trim() +".jpg";
					
					ftp.uploadFile(new File(picPath), "/public_html/image/data/csvpic", newPicName);
					//ftp.uploadFile(new File(extraPicPath), "/public_html/image/data/"+ 
					//																															supplier+"/high", newPicName);
				
					picName = "csvpic/" + newPicName;
					prod.setPic(picName);
					
				}catch(Exception e){e.printStackTrace();}
		}
	}//end of saveAndUploadPic
	
	
	
	
}//end of class
