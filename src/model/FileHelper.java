package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Vector;

public class FileHelper {
	
	private final static String supF ="suppliersCol.config"; 
	private final static String catF ="category.config";
	
	//private final static String errProdF ="errProd.log";
	
	public FileHelper(){}//end of constructor
	
	
	/**
	 * takes as input a supplier object and 
	 * adds its representation to the suppliersCol.config file 
	 * if a supplier with this name does not exist exist inside the file already
	 * else the values for that supplier are updated
	 * @param sup a supplier object
	 */
	public void saveSupplier(SupplierCol sup){
		
		String contFolder = getExecFolder();
		String supToString =  "name::"+sup.getName() +"&&" 
				+ "category::"+ sup.getCategory() + "&&"
				+"itemNumber::"+sup.getItemNumber()  + "&&"
				+"description::"+ sup.getDescription()  + "&&" 
				+ "supItemNumber::" + sup.getSupItemNumber() + "&&"
				+ "ean::"+sup.getEan() + "&&"
				+ "retPrice::"+sup.getRetailPrice() + "&&"
				+ "availability::" + sup.getAvailability() +"&&"
				+ "xml::" +sup.isXml() +"&&"
				+"available::"+sup.getIsAvailable()+"&&"
				+"separator::"+sup.getSeparator() +"&&"
				+"model::" + sup.getModelPrefix() + "&&"
				+ "manufact::" + sup.getManuf() + "&&"
				+ "itName::" + sup.getItemName() + "&&"
				+ "stockStatus::" + sup.getStockStat() + "&&"
				+ "status::" + sup.getStatus() + "&&"
				+ "addPic::"+ sup.getAddImg() + "&&"
				+ "taxClass::" + sup.getTaxClass()+ "&&"
				+ "mpn::" + sup.getMpn()+ "&&"+"img::"+sup.getImg() ;
		
		File suppliers = new File(contFolder +"/"+supF);
		if(!suppliers.exists() || suppliers.length() <= 0){
			//System.out.println("FileHelper.saveSupplier  file not found at " +contFolder + " and was created" );
			try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(suppliers, true)))) {
			    out.println(supToString);
			}catch (IOException e) { e.printStackTrace();}//end of catching the exception
		}//end if suppliers file does not exist or is empty
		else{
			try{
						String fileRead ="";
						FileInputStream fis = new FileInputStream(suppliers);
						InputStreamReader isr = new InputStreamReader(fis);
						BufferedReader br = new BufferedReader(isr); 
						String line = br.readLine();
						String[] attributes;
						String lineName = "";
						
						while(line != null){
							attributes = line.trim().split("&&");

							for(String at : attributes){
								//System.out.println("FileHelper.saveSupplier at value:  " + at);
								if(at.contains("::")){ 
									if(at.trim().split("::")[0].equals("name") ) lineName = at.trim().split("::")[1];
								}//end if attribute contains a ";" 
							}//end of looping through teh attributes array

							if(!lineName.equals(sup.getName()))	fileRead += line+'\n' ;
							line = br.readLine();
						}//end of looping through the lines of the file
						br.close();
						
						try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(suppliers, false)))) {
						    out.print(fileRead);
						    out.println(supToString);
						}catch (IOException e) { e.printStackTrace();}//end of catching the exception
			}catch(IOException e){e.printStackTrace();}
			
		}//end if the file already exists
	}//end of saveSupplier
	
	
	/**
	 * reads the contents of the file suppliersCol.config and parses it
	 * and returns its contents as a vector of suppliers
	 * @return a Vector<Suppliers> that contains the setting of the suppliers 
	 */
	public Vector<SupplierCol> getSuppliersCol(){
		File supFile = new File(getExecFolder() +"/"+supF);
		
		if(!supFile.exists() || supFile.length() <=0){
			System.out.println("FileHelper.getSuppliersCol:: filhelper.getSuppliers supFile does not exist");
			return new Vector<SupplierCol>();
		}else{
			Vector<SupplierCol> sups = new Vector<SupplierCol>();
			SupplierCol sup ;
			try{
				FileInputStream fis = new FileInputStream(supFile);
				InputStreamReader isr = new InputStreamReader(fis);
				BufferedReader br = new BufferedReader(isr); 
				String line = br.readLine();
				String[] attr ;
				
				while(line != null){
					attr = line.split("&&");
					sup = new SupplierCol();
					if(attr.length > 0) sups.add(sup);
					
					for(String attPair: attr){
						if(attPair.contains("::")){
							String[] attArray = attPair.trim().split("::");
							String attName =attArray[0]; 
							String attValue = (attArray.length > 1)? attArray[1]:"";
							switch(attName){
									case "name": 			sup.setName(attValue);
																			break;
									case "category": 		sup.setCategory(attValue);
																			break;
									case "itemNumber": 		sup.setItemNumber(attValue);
																					break;
									case "description": 		sup.setDescription(attValue);
																					break;
									case "supItemNumber": 		sup.setSupItemNumber(attValue);
																							break;
																							
									case "ean": 					sup.setEan(attValue);
																				break;
									case "retPrice": 			sup.setRetailPrice(attValue);
																				break;
									case "availability": 		sup.setAvailability(attValue);
																				break;
									case "xml": 					sup.setIsXml(Boolean.parseBoolean(attValue));
																				break;		
									case "available":			sup.setIsAvailable(attValue);			
																				break;
									case "separator":		sup.setSeparator(attValue);
									 											break;
									case "model":		       sup.setModelPrefix(attValue);			
																				break;
									case "manufact":		 	sup.setManuf(attValue);			
																				break;												
									case "itName":		 		sup.setItemName(attValue);			
																				break;
																				
									case "stockStatus":  sup.setStockStat(attValue);
																				break;
									case "status" : 				sup.setStatus(attValue);
																				break;
									case "img": 					sup.setImg(attValue);
																				//System.out.println("Ass");
																				break;
									case "addPic":				sup.setAddImg(attValue);
																				break;
									case "taxClass":			sup.setTaxClass(attValue);											
																				break;
									case "mpn":					sup.setMpn(attValue);											
																				break;
									default: break;
							}//end of switch
						}//end if attPair contains ";"
					}//end of looping through the attributes of a single line

					line = br.readLine();
				}//end of looping through the lines of the file
				br.close();
				
			}catch(IOException e) { e.printStackTrace();}
			
			return sups;
		}//end if the suppliers file contains data 
	}//end of getSuppliers
	
	
	
	
	/**
	 * saves the given CategoryMap to the category.config file 
	 * @param cm a CategoryMap
	 */
	public void saveCategory(CategoryMap cm ){
		File catFile = new File(getExecFolder() +"/"+catF);
		
		if(!catFile.exists()||catFile.length() <= 0){
			try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(catFile, true)))) {
				out.println(cm.toString());
			}catch (IOException e) { e.printStackTrace();}//end of catching the exception
		}else{
			try{
				FileInputStream fis = new FileInputStream(catFile);
				InputStreamReader isr = new InputStreamReader(fis);
				BufferedReader br = new BufferedReader(isr); 
				String line = br.readLine();
				String read = "";
				
				while(line != null){
					line = line.trim();
					String lineName = line.split(";")[0];
					
					if(!lineName.equals(cm.getName())){
						read += line +"\n";
					}
					line = br.readLine();
				}//end of looping through the lines
				br.close();
				try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(catFile, false)))) {
				    out.print(read);
				    out.println(cm.toString());
				}catch (IOException e) { e.printStackTrace();}//end of catching the exception
			}catch(Exception e){e.printStackTrace();}
		}//end if the catFile exists
	}//end of saveCategory

	
	/**
	 * 
	 * @return a CategoriesSet item containing all the 
	 * categoryMap items  of the category.config file
	 */
	public CategoriesSet getCategories(){
		CategoriesSet  catSet = new CategoriesSet();
		File catFile = new File(getExecFolder() +"/"+catF);
		
		try{
			FileInputStream fis = new FileInputStream(catFile);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr); 
			String line = br.readLine();
			CategoryMap cm;
			
			while(line != null){
				if(line.contains(";")){
					String name = line.split(";")[0];
					String rest = line.split(";")[1];
					
					cm = new CategoryMap();
					cm.setName(name);
					cm.setMatchesArray(rest.split("&&"));
					catSet.addMap(cm);
				}//end if line has a ;
				line = br.readLine();
			}//end of looping through the lines
			br.close();
		}catch(Exception e){e.printStackTrace();}
		
		return catSet;
	}//end of getCategories 
	

	/**
	 *  appends the given string to the given file
	 * @param s, a String
	 * @param fileN, a String denoting the name of the file we want to append to
	 * 
	 */
	public void addErrLog(String s, String fileN){
		File err = new File(getExecFolder() +"/"+fileN);
		
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(err, true)))) {
		    out.println(s);
		}catch (IOException e) { e.printStackTrace();}
	}//end of addErrLog
	
	
	
	/**
	 * Stores the given product ID (prodID) to the valid.prod file
	 * @param prodID
	 */
	public void saveValidProd(String prodID){
		File valid = new File(getExecFolder() +"/valid.prod"); 
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(valid, true)))) {
		    out.println(prodID);
		}catch (IOException e) { e.printStackTrace();}
		
	}//end of saveValidProd
	
	
	/**
	 * Stores the given product to the not found file
	 * @param prod
	 */
	public void saveNotFound(Product prod, String supplier){
		File valid = new File(getExecFolder() +"/notFound.prod"); 
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(valid, true)))) {
		    out.println("Supplier " + supplier + ": " +prod.toString());
		}catch (IOException e) { e.printStackTrace();}
		
	}//end of saveValidProd
	
	/**
	 * 
	 * @param formula a Vector of doubles containing the variables
	 * for calculating the price of a product. The size of this vector should be six!!
	 */
	public static void savePriceFormula(Vector<Double> formula, String supplierName){
		 
		if(formula!= null && formula.size() == 6){
			File priceFile = new File(getExecFolder() +"/config/price.config");
			String priceString = supplierName+";";
			for(Double d : formula){
				priceString += d+";";
			}//end of looping through the variables of the formula
			priceString = priceString.substring(0, priceString.length() -1);
			
			//apend the string at the end of the file
			try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(priceFile, true)))) {
				out.println(priceString);
			}catch (IOException e) { e.printStackTrace();}

		}//end if th e formula is not null and it has a size of 6
	}//end of saveValidProd
	
	
	/**
	 * Stores the given product to the not found category file
	 * @param prod
	 */
	public void saveNotFoundCategory(Product prod, String supplier){
		File valid = new File(getExecFolder() +"/notFoundCategory.prod"); 
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(valid, true)))) {
		    out.println("Suppliers " +supplier+ " Category : " + prod.getCategory() +" not found, in prod"
		    																+prod.toString());
		}catch (IOException e) { e.printStackTrace();}
		
	}//end of saveValidProd
	
	
	
	/**
	 * Saves the product to a file, which contains all the products of the given supplier we have already parsed
	 * @param supplier, the name of the supplierProducts
	 * @param prod, the product we want to save
	 */
	public void saveParsedProduct(String supplier, Product prod){
		File valid = new File(getExecFolder() +"/config/"+supplier+".prods"); 
		if(valid != null && valid.exists() && valid.length() > 0){
			 HashMap<String, Product> oldProdMap = turnProdVectToMap(ProductHelper.getOldProdFromFile(valid));
			if(!oldProdMap.containsKey(prod.getModel())){
				try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(valid, true)))) {
					out.println(prod.toCsv());
				}catch (IOException e) { e.printStackTrace();}
			}//if the product does not exist in the file we append it
			 
		}//end if we have a file which already contains the products parsed by the supplier
		else{
			try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(valid, true)))) {
				out.println(prod.toCsv());
			}catch (IOException e) { e.printStackTrace();}
		}//end if it is the first time we are parsing products from this supplier
		
	}//end of saveParsedProducts
	
	
	/**
	 *  takes as input a vector of Product objects and returns a HashMap where the model of the product is the key
	 * @param prodVect
	 * @return
	 */
	public static HashMap<String, Product> turnProdVectToMap(Vector<Product> prodVect){
		HashMap<String, Product> map = new HashMap<String,Product>(); 
		for(Product prod: prodVect){
			map.put(prod.getModel(), prod);
		}//end of looping through the products of the vector
		
		return map;
	}//end of turnProdVectToMap
	
	
	/**
	 * Takes as input a vector of products and a supplier name. It searches all the products which where previously 
	 * parsed by this supplier. IF a product Exists on the old list but not on the New one, then it is added to the result with
	 * a status attribute of 0, which indicates that the product is no longer valid
	 * @param prods
	 * @param supplier
	 * @return
	 */
	public Vector<Product> validateSupplierProductList(Vector<Product> prods, String supplier){
		
		File old = new File(getExecFolder() +"/config/"+supplier+".prods");
		HashMap<String, Product> newProds = turnProdVectToMap(prods);
		Vector<Product> oldProds = ProductHelper.getOldProdFromFile(old);
		
		Vector<Product> validatedProd =prods; 
		
		/* new Vector<Product>();
		for(Product prod : prods){
			validatedProd.add(prod);
		}//and all the new products to the output*/
		
		for(Product oldProd: oldProds){
			if(!newProds.containsKey(oldProd.getModel())){ 		
				// If an old product does not exist in the new ones then it should be made invalid
				Product invalidProd = oldProd;
				System.out.println("FileHelper.ValidateSupplierPRoductList:: works!!!");
				invalidProd.setStatus("0");
				validatedProd.add(invalidProd);
			}
		}//end of looping through the products
		
		return validatedProd;
	}//end of validateSupplierProductList
	
	
	
	
	/**
	 * 
	 * @param key a String denoting a key we are looking for in the file
	 * @param fp, the path of the file we are looking into 
	 * @return
	 */
	public boolean belongs(String key, String fp){
		File file = new File(fp);
		
		if(!file.exists() || file.length() <= 0) return false;
		
		try{
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr); 
			String line = br.readLine();
			
			while(line != null){
				if(line.trim().equals(key)){br.close(); return true;}
				line = br.readLine();
			}//end of looping through the lines
			br.close();
			
		}catch(Exception e){e.printStackTrace();}
		
		return  false;
	}//end of belongs
	
	/**
	 * reads the price config file that contains the 4 values for the variables
	 * that are used to calculate a products price
	 * and returns them as a vector
	 */
	public static Vector<Double> getPriceConfig(String supName){
		Vector<Double> prices = new Vector<Double>();
		File priceConf = new File(getExecFolder()+"/config/price.config");
		
		//System.out.println("Filehelper.getPriceConfig :: Fetching price!!");
		try{
			FileInputStream fis = new FileInputStream(priceConf);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr); 
			String line = br.readLine();
			
			while(line!= null){
				String[] parsedLine = line.split(";");
				if(parsedLine[0].equals(supName)){
					int i = 0;
					for(String item: parsedLine){
						try{
							if(i != 0)prices.add(Double.parseDouble(item.trim()));
						}catch(Exception e){e.printStackTrace();}
						i++;
					}//end of loo0ping through the elements of the parsedLine
				}//end if the name of the line is the prefix of the supplier
				line = br.readLine();
			}//end of looping through the lines
			br.close();
		}catch(Exception e){e.printStackTrace();}
		
		return prices;
	}//end of getPriceConfig
	
	
	/**
	 * 
	 * @return the folder in the operating system where
	 * this program is executed inside of (e.g. /home/nikos/Desktop)
	 */
	public static String getExecFolder(){
		try {
			return (new File(FileHelper.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath())).getParentFile().getPath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}//end of getExecFolder
	
	
	/**
	 * 
	 * @param name the name of the supplier we are looking for
	 * @return a SupplierCol object with the given name
	 */
	public SupplierCol getSupplierByName(String name){
	
		for(SupplierCol sup: getSuppliersCol()){
			if(sup!=null && sup.getName()!= null && name != null)
			if( sup.getName().trim().equals(name.trim())){
				return sup;
			}//end if
		}//end of looping through all the suppliers
		
		return null;
	}//end 
	
	
}//end of class
