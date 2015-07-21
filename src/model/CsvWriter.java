package model;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr); 
			String line = (br.readLine()).trim();
			String fileContents="";
			
			while(line != null){
				String toAppend = line +"\n";
				String[] lineArr = line.split(sep);
				
				if(lineArr.length > 0){
					for(Product prod : prods){
						if(lineArr[eanCol].equals(prod.getEan())){
							prod.setStatus("0");
							toAppend = prod.toCsv() +"\n";
							System.out.println("CsvWriter.updatProdCsvLine:: UPDATED " 
																			+ prod.getEan() + " --" + prod.getModel());
						}//end if the lineArr is an item we have to replace
					}//end of looping through the products we have to replace
					
					fileContents += toAppend;
				}//end if line is not the empty string
				line = br.readLine();
			}//end of looping through the lines of the file
			br.close();
	
			FileOutputStream fos = new FileOutputStream(file,false);
			OutputStreamWriter out = new OutputStreamWriter(fos,"utf-8");
			out.write(fileContents);
			out.close();
		
		}catch(IOException e){e.printStackTrace();}
	
	}//end of updateProdCsvLine
	
	
}//end of class
