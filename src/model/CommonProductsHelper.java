package model;

import java.io.File;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;

public class CommonProductsHelper{


	private final static String fileSep = FileSystems.getDefault().getSeparator();
	private int noEanProds;
	HashMap<String,Vector<Product>> map ;
	Vector<PairOfString> suplPairs ;
	
	/**
	 * defines a HashMap of products by different suppliers
	 * which share the same key
	 */
	public CommonProductsHelper(){
		this.map =null;
		noEanProds = 0;
		suplPairs = new Vector<PairOfString>();
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
			noEanProds++;
		}else{
			//System.out.println("CoomonProducts.addProductToMap:: EAN " + ean);
			Vector<Product> existingProds = 
				getProdsForEan(ean);
			System.out.println("CoomonProducts.addProductToMap:: EAN " + ean + " has " 
				+ existingProds.size() + " other matches");
			existingProds.add(prod);
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
			System.out.println("CommonProducts.getProdsForEan::"
					+ " FOUND PRODUCTS FOR for " + ean);
			return retrievedProds;
		}else{
			Vector<Product> newProd = new Vector<Product>();
			
			//The products with empty ean are not added to the map
			if(!ean.trim().equals("")) this.map.put(ean, newProd);
			return new Vector<Product>();
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
			System.out.println("CommonProductsMap.build:: created config dir" );
			confDir.mkdir();
			this.map = new HashMap<String, Vector<Product>>();
		}else{
			
			this.map = new HashMap<String, Vector<Product>>();
			ArrayList<File> files = 
					new ArrayList<File>(Arrays.asList(confDir.listFiles()));
			
			for(File f: files){
				//in the next skip the . char has to be escaped because it denotes any character in a regular expression
				if(f.getName().split("\\.")[1].equals("prods")){
					System.out.println("CommonProductsMap.build:: "
							+ "found a supplier product file " + f.getName() );
					
					String supplierName = f.getName().split("\\.")[0].trim();
					
					Vector<Product> oldProds = 
							ProductHelper.getOldProdFromFile(f);
					System.out.println("CommonProductsMap.build:: "
							+ "file contains " + oldProds.size() +" items");
					//after retrieving all the old products from the file
					for(Product prod : oldProds){
						prod.setSupplierName(supplierName);
						addProductToMap(prod);
					}//end of looping through the old products of the supplier file
				}//end if the extension of the file is prods, i.e. a supplier parsed product file
			}//end of looping through the files of the config directory
			
			System.out.println("CommonProductsMap.build:: "
					+ "after reading all supplier files if found map size of " + map.size()
		+" and no EAN in " + noEanProds);
		
		}//end if the configDir exists in the filesystem
		
	}//end of buildMap
	

	/**
	 *  builds the Vector<PairOfString> that contain the name 
	 *  of the suppliers that have products in common. 
	 */
	public Vector<PairOfString> buildSuppierPairs(){
		
		//HashMap<String,Vector<Product>> mp = ;
		
		Iterator<Entry<String, Vector<Product>>> it = getMap().entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Vector<Product>> pair = (Map.Entry<String, Vector<Product>>)it.next();
			//String ean = pair.getKey();
			Vector<Product> eanProds = pair.getValue();
			if(eanProds.size() > 0){
				System.out.println("CommonProductsHelper.buildSuppierPairs::  eanProds " +pair.getKey() +" s ize "  + eanProds.size());
				System.out.println("----------------");
				for(Product prod : eanProds){
					System.out.println("CommonProductsHelper.buildSuppierPairs::  eanProds "  + prod.getSupplierName());
				}
				System.out.println("----------------");
			}//end 
			
			if(eanProds.size() > 0){
				
				for(int i = 0; i< eanProds.size() -1; i++){
					Product firstProd = eanProds.get(i);
					Product secondProd = eanProds.get(i+1);
					
					String firstSupl = firstProd.getSupplierName();
					String secondSupl = secondProd.getSupplierName();
					System.out.println("CommonProductsHelper.buildSuppierPairs:: " 
																+ " testing pair " + firstSupl +" " + secondSupl);
					
					PairOfString suplPair = new PairOfString(firstSupl, secondSupl);
					if(!hasSupplierPair(suplPair) && !(firstSupl.equals(secondSupl))){
						suplPairs.addElement(suplPair);
					}//end if the pair of suppliers is not already in the Vector
					
				}//end of looping through the products of this ean

				
			}//end if there are more than 0 products for this particullar ean

			it.remove();
		}//end of looping through the elements  of the mapm
		
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
