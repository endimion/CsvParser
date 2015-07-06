package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

public class CsvWriter {

	public CsvWriter(){
		
	}//end of constructor
	
	/**
	 * 
	 * @param path, the path of the file we will save the products to
	 * @param prods, a Vector<Products> containing the products we want to save
	 */
	public void writeProdVectToCsv(String path, Vector<Product> prods){
		
		File csv = new File(FileHelper.getExecFolder() +"/output.csv"); 
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(csv, true)))) {
		   if(!csv.exists() || csv.length() <= 0)
			   out.println("model;	sku;ean;mpn;	name;description;"
			   		+ "category;quantity;stock_status;status;image;"
			   		+ "additional_image;manufacturer;	price;	tax_class;weight");
			for(Product pro: prods){
		    	//System.out.println("CSvWriter.writeProdToCsv:: writing " + pro.toCsv());
		    	out.println(pro.toCsv());
			}
		}catch (IOException e) { e.printStackTrace();}
		
	}//end of writeProdVectToCsv
	
	
	
	
}//end of class
