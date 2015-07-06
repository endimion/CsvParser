package control;

import java.util.Vector;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.FileHelper;
import view.PriceFormulaView;



public class EditPriceFormulaController {

	PriceFormulaView pfv;
	
	
	public EditPriceFormulaController(Stage st, Button addSupplier, Button addCategory, 
			Button editCategories, Button editSup, 	Button processFiles, Button editPrice){
		//TODO
		Vector<Double> prices = FileHelper.getPriceConfig(""); 
		
		TextField lessF = new TextField();
		TextField betweenF = new TextField();
		TextField kiloPriceF = new TextField();
		TextField lessExtraF = new TextField();
		TextField betweenPercentF = new TextField();
		TextField morePercentF = new TextField();
		
		Button save = new Button();
		save.setOnAction(event ->{
			Vector<Double> nPrices = new Vector<Double>();
			
			try{
				//60;10;150;5;4;1
				nPrices.add(getDoubleFromText(lessF));
				nPrices.add(getDoubleFromText(lessExtraF));
				nPrices.add(getDoubleFromText(betweenF));
				nPrices.add(getDoubleFromText(betweenPercentF));
				nPrices.add(getDoubleFromText(morePercentF));
				nPrices.add(getDoubleFromText(kiloPriceF));
				
			}catch(Exception e){e.printStackTrace();}
			
			//TODO
			FileHelper.savePriceFormula(nPrices,"");
			
			ProcessSupplierFileController psc = new 
					ProcessSupplierFileController(st, addSupplier, addCategory, editCategories, editSup, processFiles, editPrice);
			psc.displayView();
		
		});
		
		
		pfv = 
				new PriceFormulaView(st, prices, lessF, betweenF, 
						kiloPriceF, lessExtraF, betweenPercentF, morePercentF, save);
	
	
		
	}//end of constructor
	
	

	public void displayView(){
		pfv.display();
	}//end displayView
	
	
	private double getDoubleFromText(TextField f) throws IllegalArgumentException{
		return Double.parseDouble(f.getText().trim());
	}//end of getDoubleFromText
	
	
	
	
	
	
}//end of class
