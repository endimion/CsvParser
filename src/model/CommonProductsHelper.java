package model;

import java.io.File;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

public class CommonProductsHelper{


	private final static String fileSep = FileSystems.getDefault().getSeparator();
	
	HashMap<String,Vector<Product>> map ;
	Vector<PairOfString> suplPairs ;
	Vector<Vector<Product>>  commonProds;
	
	/**
	 * defines a HashMap of products by different suppliers
	 * which share the same key
	 */
	public CommonProductsHelper(){
		this.map =null;
		suplPairs = new Vector<PairOfString>();
		commonProds = new Vector<Vector<Product>>();
	}//end of constructor
	
	
	/**
	 * Returns the map containing the Products with matching Ean numbers
	 * as key. If the map is not initialized it initializes and then returns it
	 * @return the actual HashMap
	 */
	public HashMap<String,Vector<Product>>  getMap(){
		if(this.map != null){
			return this.map;
		}else{
			buildMap();
			return this.map;
		}
	}//end of getMap
	
	
	/**
	 * Adds the given product to the has map. If a product with the same EAN
	 * code already exists, then it is added to the vector of products for that EAN
	 * number. Else, a new such vector is created and added to the map.
	 * However, if no EAN is found then the product is not ADDED
	 * @param prod 
	 */
	public void addProductToMap(Product prod){
		String ean = prod.getEan();
		if(ean.trim().equals("")){
			
		}else{
			//System.out.println("CoomonProducts.addProductToMap:: EAN " + ean);
			Vector<Product> existingProds = 
				getProdsForEan(ean);
			existingProds.add(prod);
			
			//System.out.println("CoomonProducts.addProductToMap:: EAN " + ean + " has " 
				//	+ existingProds.size() + " other matches");
		}//end if product has a VALID  EAN
		
		
		
	}//end of addProductToMap
	
	/**
	 * Searches the HashMap for the given Ean code. If it exists in the map it
	 * returns the Vector<Products> that match the given Key, else
	 * a new Vector<Product> is returned
	 * @param ean
	 * @return
	 */
	public Vector<Product> getProdsForEan(String ean){
		Vector<Product> retrievedProds = this.map.get(ean); 
		
		if(retrievedProds!=null ){
			//System.out.println("CommonProducts.getProdsForEan::"
			//		+ " FOUND PRODUCTS FOR for " + ean);
			return retrievedProds;
		}else{
			Vector<Product> newProd = new Vector<Product>();
			
			//The products with empty ean are not added to the map
			if(!ean.trim().equals("")) this.map.put(ean.trim(), newProd);
			return newProd;
		}//end of else
		
	}//end of getProdsFroEan
	
	
	/* Initializes the HashMap. First we read all the files of the config 
	 * folder and look for any with the extension of .prods
	 * Then, these files are used to read the products from the supplier
	 * from previous runs of the application. Next, we loook for all these
	 * products for ones containing IDENTICAL NTO EMPTY EAN NUMBERS
	 * and these are used  to create pairs of <ean, Vector<Products>>
	 * 
	 */
	public void buildMap(){
		
		File confDir = new File(FileHelper.getExecFolder() + fileSep+"config");
		if(!confDir.exists()){
			//System.out.println("CommonProductsMap.build:: created config dir" );
			confDir.mkdir();
			this.map = new HashMap<String, Vector<Product>>();
		}else{
			
			this.map = new HashMap<String, Vector<Product>>();
			ArrayList<File> files = 
					new ArrayList<File>(Arrays.asList(confDir.listFiles()));
			
			for(File f: files){
				//in the next skip the . char has to be escaped because it denotes any character in a regular expression
				if(f.getName().split("\\.")[1].equals("prods")){
					//System.out.println("CommonProductsMap.build:: "
					//		+ "found a supplier product file " + f.getName() );
					
					String supplierName = f.getName().split("\\.")[0].trim();
					
					Vector<Product> oldProds = 
							ProductHelper.getOldProdFromFile(f);
					//System.out.println("CommonProductsMap.build:: "
					//		+ "file contains " + oldProds.size() +" items");
					//after retrieving all the old products from the file
					for(Product prod : oldProds){
						prod.setSupplierName(supplierName);
						addProductToMap(prod);
					}//end of looping through the old products of the supplier file
				}//end if the extension of the file is prods, i.e. a supplier parsed product file
			}//end of looping through the files of the config directory
			
			//System.out.println("CommonProductsMap.build:: "
			//		+ "after reading all supplier files if found map size of " + map.size()
		//+" and no EAN in " + noEanProds);
			
			
			Iterator<Entry<String, Vector<Product>>> it =map.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry<String, Vector<Product>> pair = (Map.Entry<String, Vector<Product>>)it.next();
				//String ean = pair.getKey();
				Vector<Product> eanProds = pair.getValue();
				if(eanProds.size() > 1){
					
					commonProds.addElement(eanProds);
				}
			}//end of iteriting through the prods of the map
		
		}//end if the configDir exists in the filesystem
		
	}//end of buildMap
	

	/**
	 *  builds the Vector<PairOfString> that contain the name 
	 *  of the suppliers that have products in common. 
	 */
	public Vector<PairOfString> getSuppierPairs(){
		
		buildMap(); //first we build the vector<Product> that are incommon for a single ean code
		for(Vector<Product> eanProds : commonProds){
		
			if(eanProds.size() > 1){
				//System.out.println("CommonProductsHelper.buildSuppierPairs::  Ean " + eanProds.get(0).getEan() );
				
				for(int i = 0; i< eanProds.size()-1; i++){
					//if(i < eanProds.size()-1){
						Product firstProd = eanProds.get(i);
						Product secondProd = eanProds.get(i+1);
						
						String firstSupl = firstProd.getSupplierName();
						String secondSupl = secondProd.getSupplierName();
						//System.out.println("CommonProductsHelper.buildSuppierPairs:: " 
							//										+ " testing pair " + firstSupl +" " + secondSupl);
						
						PairOfString suplPair = new PairOfString(firstSupl, secondSupl);
						if(!hasSupplierPair(suplPair) && !(firstSupl.equals(secondSupl))){
							suplPairs.addElement(suplPair);
					//	}//end if the pair of suppliers is not already in the Vector
					}//end if i < eanProd.size()-1
				}//end of looping through the products of this ean

			}//end if there are more than 0 products for this particullar ean

		}//end of looping through the commonProds Vector<Vector<Product>>
		
		return  suplPairs;
		
	}//end of buildSuppllierPairs
	
	
	
	
	/**
	 *  searches through the supplier name pairs
	 *  and sees if this pair exist inside the pairs
	 * @param suplPair
	 * @return true or false
	 */
	public boolean hasSupplierPair( PairOfString suplPair){
		
		System.out.println("CommonProductsHelper.hasSupplier::  checking " + suplPair.getFirst() + "   " + suplPair.getSecond());
		for(int i = 0; i < suplPairs.size(); i++){
			PairOfString temp = suplPairs.get(i);
			if( suplPair.getFirst().equals(temp.getFirst())){     
				if(suplPair.getSecond().equals(temp.getSecond())){return true;}
			}else{
				if(suplPair.getFirst().equals(temp.getSecond())){
					if(suplPair.getSecond().equals(temp.getFirst())){return true;}
				}//end if suplPair.getFirst() is  equal to temp.getSecond()
			}//end of if  suplPair.getFirst() is not equal to temp.getFirst()
		}//end of looping through the suplPairs
		return false;
	}//end of hasSupplierPair
	
	
	//TODO
	//ADD COMMENTS
	/**
	 *
	 * @param badSupl
	 * @param goodSupl
	 */
	public void setSuplProdsUnavailable(String badSupl, String goodSupl){
		
		Vector<Product> replaceProds = new Vector<Product>();
		
		
		for(Vector<Product> prods: commonProds){
			
			if(hasBothSuppliers(prods, goodSupl, badSupl)){
				
				for(Product prod : prods){
					
					if(prod.getSupplierName().equals(badSupl)){ 
						//System.out.println("CommonProductsHelper.setSuplPRodsUnavailable:: UPDATED PRODUCT "  
						//		+prod.getModel()); 
						prod.setStatus("0");
						replaceProds.add(prod);
					}//end if the name of the product supplier is the badSupl
					else{
					//System.out.println("CommonProductsHelper.setSuplPRodsUnavailable:: Checked PRODUCT "  
					//		+prod.getModel());
					} 
				}//end of looping through the products for one EAN number 
				//	System.out.println("CommonProductsHelper.setSuplPRodsUnavailable:: "
				//		+ "checked all products of ean" );
			}//end of if the Vector<Products> has both suppliers
		
		
	
		//After finding all the products that are presented by both suppliers
		// supl and badSupl, (and store the updated versions o replaceProds)
		//then we can overwrite the file of the badSupplier
		// to update the appearnce of these produces
		
		
		}//end of looping through the Vector<Vector> products
		
		//System.out.println("CommonProductsHelper.setSuplPRodsUnavailable:: "
			//	+ "checked all common products" );

		
		if(replaceProds.size() > 0){
			File savedProdsFile = new File(FileHelper.getExecFolder() +fileSep +"config"+fileSep
																			+ badSupl.trim() +".prods");
			
			System.out.println("CommonProductsHelper.setSuplPRodsUnavailable:: will UPDATED FILE "  
					+ savedProdsFile.getName()); 
			for(Product prod: replaceProds){
				System.out.println("CommonProductsHelper.setSuplPRodsUnavailable:: Supplier "
							+prod.getSupplierName()+" ||| "+ prod.toCsv() );
			}
			
			CsvWriter.updateProdCsvLine(2,  savedProdsFile, replaceProds, ";");
			System.out.println("CommonProductsHelper.setSuplPRodsUnavailable:: UPDATED FILE "  
													+ savedProdsFile.getName()); 
		}//end of if replacePRods.size >0
		
	}//end of setSuplProdsUnavailable
	
	
	/**
	 * loops the vector of products and returns whether or not they
	 * contain products of both given suppliers
	 * @return
	 */
	private boolean hasBothSuppliers(Vector<Product> products, String supplier1, String supplier2){
		
		boolean hasFirst = false;
		boolean hasSecond = false;
		
		for(Product prod: products){
			if(prod.getSupplierName().equals(supplier1)) hasFirst = true;
			if(prod.getSupplierName().equals(supplier2)) hasSecond = true;
		}//end of looping through the products
		
		return (hasFirst && hasSecond);
	}//end of hasBothSuppliers
	
	
	
		/**
		 * inner class defining pair of strings 
		 * @author nikos
		 *
	 */
	public class  PairOfString{
		private String s1;
		private String s2;
		
		PairOfString(String s1, String s2){
		   this.s1  = s1;
		   this.s2 = s2;
		}//end of constructor
		
		public String getFirst(){return s1;}
		public String getSecond(){return s2;}
	}//end of private class
	
	
	
}//end of class
