package model;

import java.io.File;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

public class CommonProductsMap{


	private final static String fileSep = FileSystems.getDefault().getSeparator();
	private int noEanProds;
	HashMap<String,Vector<Product>> map ;
	
	/**
	 * defines a HashMap of products by different suppliers
	 * which share the same key
	 */
	public CommonProductsMap(){
		this.map =null;
		noEanProds = 0;
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
			System.out.println("CoomonProducts.addProductToMap:: EAN " + ean);
			Vector<Product> existingProds = 
				getProdsForEan(ean);
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
					Vector<Product> oldProds = 
							ProductHelper.getOldProdFromFile(f);
					System.out.println("CommonProductsMap.build:: "
							+ "file contains " + oldProds.size() +" items");
					//after retrieving all the old products from the file
					for(Product prod : oldProds){
						addProductToMap(prod);
					}//end of looping through the old products of the supplier file
				}//end if the extension of the file is prods, i.e. a supplier parsed product file
			}//end of looping through the files of the config directory
			
			System.out.println("CommonProductsMap.build:: "
					+ "after reading all supplier files if found map size of " + map.size()
		+" and no EAN in " + noEanProds);
		
			
			
		}//end if the configDir exists in the filesystem
		
	}//end of buildMap
	
	
	
}//end of class
