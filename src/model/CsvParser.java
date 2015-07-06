package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;

/**
 *  Provides methods to parse a csv file and get its contents
 *  as a vector of ParsedRow objects with teh fileToVector method
 * @author nikos
 *
 */
public class CsvParser {

	public CsvParser(){}
	
	
	
	/*
	public static Vector<ParsedRow> fileToVector(File csv, String separator){
		Vector<ParsedRow> res = new Vector<ParsedRow>();

		try {
			FileInputStream fis = new FileInputStream(csv);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr); 
			String line = (br.readLine()).trim();
			
			while(line != null){
				res.add(parseLine(line,separator)); 
				 line = br.readLine();
			 }
			 br.close();
		}catch(Exception e){e.printStackTrace();}
		
		return res;
	}//end of fileToVector
	*/
	
	
	/**
	 * 
	 * @param csv a File in CSV format
	 * @return a vector containing the contents of the file as
	 *  ParsedRowMap objects 
	 */
	public static Vector<ParsedRow>  fileToVect(File csv, String separator){
		Vector<ParsedRow> res = new Vector<ParsedRow>();

		try {
			FileInputStream fis = new FileInputStream(csv);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr); 
			String line = (br.readLine()).trim();
			
			//the first line contains the names of the columns
			Vector<String> keys =new Vector<String>();
			for(String s: line.split(separator)){
				keys.add(s.trim());
			}//end of for loop
			
			line = br.readLine();
			
			while(line != null){
				line = line.trim();
				res.add(parseLine(line, keys, separator)); 
				 line = br.readLine();
			 }
			 br.close();
		}catch(Exception e){e.printStackTrace();}
		
		return res;
	}//end of fileToVectMap
	
	
	
	/*
	private static ParsedRow parseLine(String row,String separator){
		Vector<String> res = new Vector<String>();
		ParsedRow pr = new ParsedRow();
		
		String[] lineArray = row.trim().split(separator);
		for(String s: lineArray){
			res.add(s);
		}
		pr.setRow(res);
		return pr;
	}//end of ParsedRow
	*/
	
	
	
	
	/**
	 * 
	 * @param row a String representation of a row
	 */
	private static ParsedRow parseLine(String row,Vector<String> keys, String separator){
		ParsedRow pr = new ParsedRow(keys);
		Vector<String> data = new Vector<String>();
		String[] lineArray = row.trim().split(separator);
		for(String s: lineArray){
			data.add(s.trim());
		}
		pr.setRowData(data);
		
		return pr; 
	}//end of ParsedRowMap
	
	
	
	
	
	
	
}//end of Class
