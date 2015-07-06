package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.MalformedInputException;
import java.util.Vector;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ProductHelper {

	public ProductHelper(){	}
	
	
	
	/**
	 * 
	 * @param f, a file (Csv, xml) containing the information provided by the supplier about the products 
	 * @param supplier, the name of the supplier providing the information
	 * @return a Vector<Product> containing the parsed contents of the given file as Product objects
	 */
	public static Vector<Product> getProdFromFile(File f, String supplier){
		
		Vector<Product> products = new Vector<Product>();
		FileHelper fh = new FileHelper();
		Vector<SupplierCol> suppliers = fh.getSuppliersCol();
		SupplierCol sup = null;
		
		for(SupplierCol s: suppliers){
			if(s.getName().equals(supplier)) sup = s;
		}//end of looping through the suppliers
		
		//after retrieving the supplier we know what columns to retrieve from the csv
		if(sup != null){
			Vector<ParsedRow> rows = null;
			CategoriesSet	catSet = fh.getCategories();
			String supAvail = sup.getIsAvailable();
			Vector<String> keys = sup.getColVector();
			
			if(!sup.isXml()){ //If the supplier gives a csv file
				 rows = CsvParser.fileToVect(f, sup.getSeparator());
			}//end if the supplier does not send info as xml
			else{
				XmlParser xmlp = new XmlParser();
				rows = xmlp.getFileToVect(f, "product", keys);
				//for(String key : keys){
				//	System.out.println("ProductHelper.getProdFromFile  key: " + key );
				//}//end of looping through the keys
				//System.out.println("ProductHelper.getProdFromFile "
				//		+ "xml parsed rows size: " + rows.size());
			}//end if supplier info is xml
				
				if(rows.size() >= 1)

					for(int i=0; i< rows.size();i++){
						ParsedRow r = rows.get(i);
						Product prod = new Product();
						
						
						// if the string denoting item availability is indeed a word and not a number
						if(!supAvail.contains(">=")){
							//if in this product the column denoting availability is equal
							// to what the supplier.config file denotes as available for this supplier
							// then the quantity of the product is set to 28 else it is set to 0
							if(sup.getAvailability()!= null && 
									//r.getElement(sup.getAvailability()) != null &&
									r.getElement(sup.getAvailability()).equals(supAvail) ){
								prod.setQuantity(28);
							}else{
								prod.setQuantity(0);
							}
						}else{
							String rest = supAvail.split(">=")[1].trim();
							//find the number part in the expression
							try{
									Integer threshold = Integer.parseInt(rest);
									Integer rowQuantity = Integer.parseInt(r.getElement(sup.getAvailability()));
									
									if(rowQuantity >= threshold ){
										prod.setQuantity(28);
									}else{
										prod.setQuantity(0);
									}
							}catch(Exception e){e.printStackTrace();}
						}//end if the supplier expression for available products is a numerical representation
						
						//Converting the foreign category read from the file to a native category
						String foreignCat = r.getElement(sup.getCategory());
						String nativeCat = catSet.getContainingCat(foreignCat);
						
						//if the nativeCategory is not null then the product is added to the output csv file
						if(nativeCat != null){
							prod.setCategory(nativeCat);
							prod.setDescription(r.getElement(sup.getDescription()));
							prod.setEan(r.getElement(sup.getEan()));
							prod.setNumber(r.getElement(sup.getItemNumber()));
							prod.setRPrice(r.getElement(sup.getRetailPrice()));
							prod.setSupNum(r.getElement(sup.getSupItemNumber()));
							
							//TODO fix this so that the price formula is correctly read for a file!!!!
							Double var1 = FileHelper.getPriceConfig(supplier).get(0);
							Double var2 = FileHelper.getPriceConfig(supplier).get(1);
							Double var3 = FileHelper.getPriceConfig(supplier).get(2);
							Double var4 = FileHelper.getPriceConfig(supplier).get(3);
							Double var5 = FileHelper.getPriceConfig(supplier).get(4);
							Double kiloP = FileHelper.getPriceConfig(supplier).get(5);
							
							prod.setDoublePrice(prod.getPrice(var1,var2,var3,var4,var5,kiloP));
							
							products.add(prod);
						}//end if the nativeCategory is not null
						else{
							// write and error log that there was no matching category for the product
							prod.setCategory(foreignCat);
							prod.setDescription(r.getElement(sup.getDescription()));
							prod.setEan(r.getElement(sup.getEan()));
							prod.setNumber(r.getElement(sup.getItemNumber()));
							prod.setRPrice(r.getElement(sup.getRetailPrice()));
							prod.setSupNum(r.getElement(sup.getSupItemNumber()));
							
							fh.saveNotFoundCategory(prod, supplier);
						}//end if the nativeCategory was null, i.e. the product does not fit into any category of our site
					}//end of looping through the parsed rows of the csv file
				
			
			
		}//if we found a supplier matching the requested one to read the file
		
		return products;
	}//end of getProdFromFile

	
	
	/**
	 * 
	 * @param f
	 * @param supplier
	 * @return
	 */
	public static Vector<Product> getOldProdFromFile(File f, String supplier){
		
		Vector<Product> res = new Vector<Product>();
		
		try {
			FileInputStream fis = new FileInputStream(f);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr); 
			String line = (br.readLine()).trim();
			
			line = br.readLine();
			
			while(line != null){
				line = line.trim();
				String[] lineArray = line.split(";");
				if(lineArray.length != 16){
					br.close();
					throw new MalformedInputException(16);
				}else{
					Product lineProd = new Product();
					lineProd.setModel(lineArray[0]);
					lineProd.setEan(lineArray[2]);
					lineProd.setMpn(lineArray[3]);
					lineProd.setpName(lineArray[4]);
					lineProd.setDescription(lineArray[5]);
					lineProd.setCategory(lineArray[6]);
					lineProd.setQuantity(Integer.parseInt(lineArray[7]));
					lineProd.setStStatus(lineArray[8]);
					lineProd.setStatus(lineArray[9]);
					lineProd.setPic(lineArray[10]);
					lineProd.setAddPic(lineArray[11]);
					lineProd.setManufact(lineArray[12]);
					lineProd.setDoublePrice(Double.parseDouble(lineArray[13]));
					lineProd.setTax_class(lineArray[14]);
					lineProd.setWeight(lineArray[15]);
				
					res.add(lineProd);
				}//if the size of the array is equal to 16 
				//res.add(parseLine(line, keys, separator));
				line = br.readLine();
			 }
			 br.close();
		}catch(Exception e){	e.printStackTrace();}
		
		return res;
	}//end of getOldProdFromFile
	
	
	
	public Vector<Product> expandProducts(Vector<Product> prods, String supplier){
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
		Vector<Product> output = new Vector<Product>();
		boolean add = false ;
		
		//create and intiallize the ftp connection to upload the files
		FtpHelper ftp = new FtpHelper();
		ftp.connectAndInit();
		
		for(Product prod : prods){
			if(!fh.belongs(prod.getNumber() , FileHelper.getExecFolder() +"/valid.prod")){
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
							picName = xPar.getValue("HighPic",picPL.item(0));
							//System.out.println(picName);
							if(picName != null && !picName.equals("")){
								String[] nameArr = picName.split("/");
								picName = nameArr[nameArr.length-1];
							}else{
								fh.addErrLog("no PICTURE found in iceCate for: " + prod.toString(), "errorProd.log");
								
							}
							
						}//end if picPL is not null
						
						if(weight == null ){
							fh.addErrLog("no WEIGHT found in iceCate for: " + prod.toString(), "errorProd.log");
						}else{
							prod.setWeight(weight);
						}//end of setting the weight of an object
						
						if(descPL == null || descr == null) {
							fh.addErrLog("no Description found in iceCate for: " + prod.toString() 
										+" of Supplier: " + supplier, "errorProd.log" );
						}else{
							prod.setDescription(descr);
						}
						
						if(picName != null){
							if (i < 10){ //TODO remove this if 
								ice.savePicturesToDrive( FileHelper.getExecFolder() +"/"+ picName, picName);
								String newPicName = prod.getModel() +".jpg";
								ftp.uploadFile(new File(FileHelper.getExecFolder() +"/"+ picName), 
															"/public_html/image/data/"+supplier,newPicName);
								System.out.println("ProductHelper.expandProducts picName value: " + picName);
								
								picName = supplier + "/" + picName;
								prod.setPic(picName);
							}//end if i <10
						}//end if picName is not null
		
					
						fh.saveValidProd(prod.getNumber());
					}else{
						fh.saveNotFound(prod ,supplier);
						add = false; //if the iceCatXml does not contain the file it
												 //should not be added
					}//end if the product was not found inside ICeCAT
					i++;
				}catch(Exception e){
					i++;
					e.printStackTrace();
				}//end of catching the exception
				System.out.println("ProductHelper.expandProducts processed : " + i + "products");
				
				if(add) output.add(prod); //ADD the product to the output
			}//end if the product does nto exist in teh vaildate file 			
		
		}//end of looping through the products
		
		return output;
	}//end of expandProducts
	
	
	
	
	
	
	
	
	
	
	
	
}//end of class
