package tests;

import java.io.File;
import java.util.Vector;

import model.GetProdFromFileTask;
import model.Product;

import org.junit.Before;
import org.junit.Test;

public class TestDifox {

	

	@Before
	public void setUpTests(){
		
	}// setUpTests
	
	
	@Test
	public void test(){
		File  f = new File("/home/nikos/Downloads/DIFOX-pricelist-16673325-fix.csv");
		String supplier = "DIFOX";
		GetProdFromFileTask getTask = new GetProdFromFileTask(f, supplier);
		
		getTask.setOnSucceeded(event ->{
			System.out.println("Succeded!!");
		});
		
		Thread th = new Thread(getTask);
		try{
			
			th.run();
			th.setDaemon(true);
			th.join();
			//Vector<Product> prods = getTask.get();
			
			System.out.println("Finished getting prods");
			
			
		}catch(Exception e){e.printStackTrace();}
		
	}
	
}
