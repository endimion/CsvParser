package model;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.FileSystems;
import java.util.HashMap;
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
	public synchronized void writeProdVectToCsv(String path, Vector<Product> prods){
		
		//File csv = new File(FileHelper.getExecFolder() +fileSep+"output.csv"); 
		File csv = new File(path);
		
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
	
	
	
	
	/**
	 * Replaces the occurance of the products in the csv file with 
	 * the products given as input as Vector<Products> prods
	 * 
	 * @param eanCol, col number indicating the eanCol
	 * @param file, the csv file
	 * @param prod, the prod we want to ovewrite
	 */
	public static void updateProdCsvLine(int eanCol, File file, Vector<Product> prods, String sep){
		//TODO
		// write a new outputfile
		
		HashMap<String,Product> replaceProdMap = new HashMap<String,Product>();
		//First we store the products to be replaced in a HashMap
		for(Product prod: prods){
			replaceProdMap.put(prod.getEan(), prod);
		}//end of looping through the replace prods
		
		long startTime = System.nanoTime();
		
		Vector<Product> oldProd = ProductHelper.getOldProdFromFile(file);
		Vector<Product> updatedProd = new Vector<Product>();
		Product candidate;
		
		for(Product prod: oldProd){
			candidate = replaceProdMap.get(prod.getEan().trim());
			
			if(candidate != null){
				updatedProd.add(candidate);
				System.out.println("CsvWriter.updatProdCsvLine:: UPDATED " 
						+ candidate.getEan() + " --" + candidate.getModel());
			}else{//end if the old product is contained in the products to replace
				updatedProd.add(prod);
			}//end if old prod is not to be replaced
		}//end of looping through the old products
		long endTime = System.nanoTime();
		
		
		//old execution time 525698694734
		// new                                     1893044425
		
		
		
			System.out.println("CsvWriter.updatProdCsvLine::  read ALL line in "+ (endTime - startTime )/1000 +"milliseconds" );
		try{	
			FileOutputStream fos = new FileOutputStream(file,false);
			OutputStreamWriter out = new OutputStreamWriter(fos,"utf-8");
			int linesWritten  = 0;
			for(Product prod: updatedProd){
				if(linesWritten == 0){
					out.append(prod.toCsv() +"\n");
				}else{
					out.flush();
					out.close();
					fos = new FileOutputStream(file,true);
					 out = new OutputStreamWriter(fos,"utf-8");
					 out.append(prod.toCsv() +"\n");
				}//end if  linesWritten is not 0
				
				linesWritten++;
			}
			out.flush();
			out.close();
		
		}catch(IOException e){e.printStackTrace();}
	
	}//end of updateProdCsvLine
	
	
}//end of class
