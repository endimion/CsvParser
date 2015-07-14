package model;

import java.io.File;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.Vector;

import javafx.concurrent.Task;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ExpandProductsTask extends Task<Vector<Product>> {
	
	Vector<Product> prods; 
	String supplier;
	private final static String fileSep = FileSystems.getDefault().getSeparator();
	String progressMessage;
	
	public ExpandProductsTask(Vector<Product> prods, String supName){
		this.prods = prods;
		this.supplier = supName;
		progressMessage="";
	}//end of constructor
	
	
	@Override
	protected Vector<Product> call() throws Exception {
		System.out.println("ExpandProductsTask.call :: Thread Started");
		IceCatHelper ice = new IceCatHelper(); 
		XmlParser xPar = new XmlParser();
		File xml ;
		String weight;
		String descr = null;
//		int i = 0;
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
		
		
		File old = new File(FileHelper.getExecFolder() +fileSep+"config"
																								+fileSep+supplier+".prods");
		
		Vector<Product> oldProds = ProductHelper.getOldProdFromFile(old);
		HashMap<String, Product> oldProdsMap = FileHelper.turnProdVectToMap(oldProds);
		
		//System.out.println("ExpandProducts.call::  will expand " + prods.size() + "products");
		
		for(Product prod : prods){
			updateProgress(progress*1, prods.size());
			progressMessage ="Processing product "+ progress +" from " +prods.size();
			updateMessage(progressMessage);
			
			extraPic = prod.getAddPic(); //GET the extra pictures either from the product or iceCat
			
			prod.setStStatus("2 - 3 Ημέρες");
			prod.setStatus("1" ); 
			prod.setTax_class("Κλάση Ι (23% ~ 16%)");
			
			Double var1 = FileHelper.getPriceConfig(supplier).get(0);
			Double var2 = FileHelper.getPriceConfig(supplier).get(1);
			Double var3 = FileHelper.getPriceConfig(supplier).get(2);
			Double var4 = FileHelper.getPriceConfig(supplier).get(3);
			Double var5 = FileHelper.getPriceConfig(supplier).get(4);
			Double kiloP = FileHelper.getPriceConfig(supplier).get(5);
			prod.setDoublePrice(prod.getPrice(var1,var2,var3,var4,var5,kiloP,
															FileHelper.getRemoveVAT(supplier)));
			
			if(!isPreviouslyParsed(prod,oldProdsMap)){
				
					if(!fh.belongs(prod.getModel() , FileHelper.getExecFolder() +fileSep+"valid.prod")){
						try{
							add = true;
							xml = ice.saveXmlToFile(FileHelper.getExecFolder()+fileSep+"iceXml", ice.getProductXml(prod.getEan()));
							if(xml == null){System.out.println("ExpandProduuctsTask:: AAAAA!!!");}
							Node prodN =  xPar.getNode(xml, "Product").item(0);
							
							//check if the xml file we get contains any info.
							//else store the product to the not found file
							if( !xPar.getValue("Code",prodN).equals("-1")){
								if(xPar.getValue("ErrorMessage",prodN).contains("IP address is not included")){
									progressMessage += "\n IP is not on ICECAT";
									updateMessage(progressMessage);
								}else{
									progressMessage += "\n Product found in IceCat!";
									updateMessage(progressMessage);
								}//end if the errorMessage does not inform us that the IP is not on icecat
								
								weight = xPar.getParAtt(xml, "ProductFeature",   "Presentation_Value", "Name", "Value", "Βάρος");
								//System.out.println("finished "+ i + " found weight " + weight);
								descPL =  xPar.getNode(xml, "SummaryDescription");
								picPL = xPar.getNode(xml,"Product");
								picName = null;
								descr = null;
								
								if(descPL != null) descr = xPar.getValue("LongSummaryDescription" , descPL.item(0));
								if(picPL != null) {
									
									 picURL =  xPar.getValue("HighPic",picPL.item(0));
									try{
										Node extraPicN = xPar.getNode(xml, "ProductPicture").item(0);
										extraPicURL= xPar.getValue("Pic",extraPicN);
									}catch(Exception e){e.printStackTrace(); 
										System.out.println("ExpandProducts.call:: additional picture not found");
									}
									 
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
			
								if(descPL == null || descr == null) {
									fh.addErrLog("no Description found in iceCate for: " + prod.toString(supplier) 
												+" of Supplier: " + supplier, "errorProd.log" );
								}else{
									prod.setDescription(descr);
								}
								
								if(picName != null){
									//if (i < 10){ //TODO remove this if 
										
										File dir = new File(FileHelper.getExecFolder() +fileSep+"pictures");
										File extraDir = new File(FileHelper.getExecFolder() +fileSep+"pictures"+fileSep+"high");
										
										try{
											if(!dir.exists() || !extraDir.exists()){
												dir.mkdir();
												extraDir.mkdir();
											}
											 //System.out.println("DIR created");
											//TODO 
											String picPath =  FileHelper.getExecFolder() +fileSep+"pictures"+fileSep+ picName;
											String extraPicPath=""; 
											 if(extraPic != null && !extraPic.equals(""))extraPicPath= FileHelper.getExecFolder() +fileSep+"pictures"+fileSep+"high"+fileSep+ extraPic;
											 
											ice.savePicturesToDrive(picPath,picURL,true);
											 if(extraPic != null && !extraPic.equals("")) ice.savePicturesToDrive(extraPicPath,extraPicURL,true);
											
											String newPicName = prod.getModel().trim() +".jpg";
											
											ftp.uploadFile(new File(picPath), "/public_html/image/data/csvpic", newPicName);
											if(extraPic != null && !extraPic.equals(""))ftp.uploadFile(new File(extraPicPath), "/public_html/image/data/csvpic/high",
																																					newPicName);
										
											picName = "data/csvpic/" +  prod.getModel().trim() +".jpg";
											prod.setPic(picName);
											if(extraPic != null && !extraPic.equals("")) prod.setAddPic("data/csvpic/high/" + prod.getModel().trim() +".jpg");
											
										}catch(Exception e){e.printStackTrace();}
										
									//}//end if i <10
								}//end if picName is not null
				
							
								fh.saveValidProd(prod.getModel());
							}else{
								progressMessage += "\n Product  NOT found in IceCat!";
								updateMessage(progressMessage);
								
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
							
							//i++;
						}catch(Exception e){ 	e.printStackTrace();  	}//end of catching the exception
						
						
						if(add){ 
							output.add(prod); //ADD the product to the output
							fh.saveParsedProduct(supplier, prod);
							//System.out.println("ProductHelper.expandProducts processed :  ADDED" + prod.toString(supplier) );
						}
					}//end if the product does NOT exist in the vaildate file 
			}//end if the product has NOT been parsed by the program in a previous run
			else{
				//if it has been parsed in a previous program run then it should be added to the output!!!
				output.add(oldProdsMap.get(prod.getModel()));
			}//end if the product already exists in the validate file
		
			//updateMessage("Processing product "+ prod.getpName());
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

		if(picName != null && !picName.equals("")){
				File dir = new File(FileHelper.getExecFolder() +fileSep+"pictures");
				File extraDir = new File(FileHelper.getExecFolder() +fileSep+"pictures"+fileSep+"high");
				
				try{
					if(!dir.exists() || !extraDir.exists()){
						dir.mkdir();
						extraDir.mkdir();
					}
					 //System.out.println("DIR created");
					 String picPath =  FileHelper.getExecFolder() +fileSep+"pictures"+fileSep+ picName;
					//TODO clean up
					 String extraPicPath="";
					 if(!extraPic.equals("")){ 
						 extraPicPath = FileHelper.getExecFolder() +fileSep+"pictures/high"+fileSep+ extraPic;
						 ice.savePicturesToDrive(extraPicPath,extraPicURL,true);
					 }//end if extraPic is not null
					 
					ice.savePicturesToDrive(picPath,picURL,true);
					
					
					String newPicName = prod.getModel().trim() +".jpg";
					
					ftp.uploadFile(new File(picPath), "/public_html/image/data/csvpic", newPicName);
					 if(!extraPic.equals("")){ 
						 ftp.uploadFile(new File(extraPicPath), 
								 "/public_html/image/data/csvpic/high", newPicName);
					 }//end if extraPic is not null
					picName = "data/csvpic/" + newPicName;
					
					String addPicName = "data/csvpic/high/"+newPicName;
					prod.setPic(picName);
					if(!extraPic.equals("")) prod.setAddPic(addPicName);
					
				}catch(Exception e){e.printStackTrace();}
		}
	}//end of saveAndUploadPic
	
	

	
	/**
	 * Checks if the given product has been perviously parsed by the program
	 * @param prod 
	 * @param supplier
	 * @return
	 */
	public boolean isPreviouslyParsed(Product prod, HashMap<String, Product> oldProdsMap){
		
		if(!oldProdsMap.containsKey(prod.getModel())){
			System.out.println("ExpandProductsTask.isPreviouslyParsed:: product not found");
			return false;
		}else{
			Product existingProd = oldProdsMap.get(prod.getModel());
			return existingProd.compareProduct(prod);
		}//check if the product has been previously parsed
		
	}//end if isPeriviouslyParsed
	
	
	
	
	
}//end of class
