package model;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.FileSystems;
import java.util.Vector;

public class CsvWriter {
	private final static String fileSep = FileSystems.getDefault().getSeparator();
	
	public CsvWriter(){
		
	}//end of constructor
	
	/**
	 * 
	 * @param path, the path of the file we will save the products to
	 * @param prods, a Vector<Products> containing the products we want to save
	 */
	public void writeProdVectToCsv(String path, Vector<Product> prods){
		
		File csv = new File(FileHelper.getExecFolder() +fileSep+"output.csv"); 
		
		
		try {
		   
			//PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(csv, true)));
			
			FileOutputStream fos = new FileOutputStream(csv,true);
			OutputStreamWriter out = new OutputStreamWriter(fos,"utf-8");
			
			if(!csv.exists() || csv.length() <= 0)
			   out.write("model;	sku;ean;mpn;	name;description;"
			   		+ "category;quantity;stock_status;status;image;"
			   		+ "additional_image;manufacturer;	price;	tax_class;weight "
			   		+ "\n");
			for(Product pro: prods){
		    	//System.out.println("CSvWriter.writeProdToCsv:: writing " + pro.toCsv());
		    	out.append(pro.toCsv()+ "\n");
			}
			out.flush();
			out.close();
		}catch (IOException e) { e.printStackTrace();}
		
	}//end of writeProdVectToCsv
	
	
	
	
}//end of class
