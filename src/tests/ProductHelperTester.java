package tests;

import java.io.File;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import model.CsvWriter;
import model.FileHelper;
import model.GetProdFromFileTask;
import model.Product;
import model.ProductHelper;

import org.junit.Before;
import org.junit.Test;

public class ProductHelperTester {

	@Before
	public void setUpTests(){
	}// setUpTests
	
	
	
	public void testGetProdFromFile(){
		ProductHelper ph = new ProductHelper();
		Vector<Product> vec = ph.getProdFromFile(new File("difox2.CSV"), "difox");
		System.out.println(vec.size());
		
		System.out.println(vec.get(0).toString());
		System.out.println(vec.get(1).toString());
		System.out.println("size "+ vec.size());
		
		Vector<Product> expanded = ph.expandProducts(vec,"difox");
		for(int i = 0 ; i <expanded.size(); i ++){
			System.out.println("price for product " + expanded.get(i).getEan() 
					+ " is "  + expanded.get(i).getDoublePrice()  + " it has " 
					+ expanded.get(i).getRPrice() + " retail price and weights " 
					+ expanded.get(i).getWeight()  + " and weights  in kilos " 
					+ expanded.get(i).getWeightDouble());
			
		}//end of loop
		
		
		CsvWriter csvw = new CsvWriter();
	 	csvw.writeProdVectToCsv(FileHelper.getExecFolder()+"/out.csv", expanded);
	
		
		 vec = ph.getProdFromFile(new File("telepart.csv"), 
								"telepart");
		System.out.println(vec.size());
		System.out.println("size "+ vec.size());
		
		expanded = ph.expandProducts(vec,"telepart");
		
		 vec = ph.getProdFromFile(new File("orico.xml"), 
					"orico");
		 
		 System.out.println(vec.size());
		 System.out.println("orico.xml size "+ vec.size());

		
	}//end of testGetProdFromFile
	
	
	
	public void test() throws InterruptedException{
		//THIS TEST DOES NOT WORK
		// because JavaFx taks can only be run from inside a JAvafx application!!!!!
		GetProdFromFileTask task =  new GetProdFromFileTask(new File("difox2.CSV"), "difox");
		  Thread th = new Thread(task);
		  th.setDaemon(true);
		  th.run();
	}//end of test
	
	
	
	
	
}//end of test class
