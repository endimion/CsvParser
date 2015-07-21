package control;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import view.CommonProductsView;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import model.CommonProductsHelper;
import model.Product;

public class CommonProductController {

	HashMap<String,Vector<Product>> hashMap ;
	Vector<Vector<CheckBox> > eanCheckBoxes;
	Stage stage;
	CommonProductsView cpv;
	
	public CommonProductController(Stage st){
		hashMap = new HashMap<String,Vector<Product>>();
		eanCheckBoxes = new Vector<Vector<CheckBox>>();
		stage = st;
		
		CommonProductsHelper  map = new CommonProductsHelper();
		hashMap = map.getMap();
		buildCommonMap();
		
		cpv = new CommonProductsView(st, hashMap, eanCheckBoxes);
		cpv.display();
		
	}//end of constructor
	
	
	/**
	 * Builds the HashMap of the Products 
	 */
	public void buildCommonMap(){
		
		
		// loop through the keys of the hashMap
		Iterator<Entry<String, Vector<Product>>> it = hashMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Vector<Product>> pair = (Map.Entry<String, Vector<Product>>)it.next();
			//System.out.println("CommonProductController.buildCommonMap " + pair.getKey());
			Vector<CheckBox> boxes = new Vector<CheckBox>();
			for(int i = 0; i < pair.getValue().size();i++){
				CheckBox box = new CheckBox();
				boxes.add(box);
			}//end of looping through the Vector<Product> for the specific EAN of the pair
			
			eanCheckBoxes.addElement(boxes); //we add to all the checkboxes the vector of checkboxes for this ean number
			
			it.remove(); // avoids a ConcurrentModificationException
		}//end of looping through the iterator of the hashMap
		
		System.out.println("CommonProductCONTROLLER.buildCommonMap::  " + eanCheckBoxes.size());
		
	}//end of buildCommonMap
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}//end of class
