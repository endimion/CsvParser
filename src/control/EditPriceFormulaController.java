package control;

import java.util.Vector;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.FileHelper;
import model.SupplierCol;
import model.SupplierPriceNotFound;
import view.PriceFormulaView;



public class EditPriceFormulaController {

	PriceFormulaView pfv;
	
	
	public EditPriceFormulaController(Stage st, Button addSupplier, Button addCategory, 
			Button editCategories, Button editSup, 	Button processFiles, Button editPrice, String supplier){
		
		try{
			Vector<Double> prices = FileHelper.getPriceConfig(supplier); 
			ChoiceBox<String> cBox = new ChoiceBox<String>();
			CheckBox remVatBox = new CheckBox();
			
			
			TextField lessF = new TextField();
			TextField betweenF = new TextField();
			TextField kiloPriceF = new TextField();
			TextField lessExtraF = new TextField();
			TextField betweenPercentF = new TextField();
			TextField morePercentF = new TextField();
			
			if(supplier!= null && !supplier.equals("")){
				//System.out.println("EditPriceFormulaControlelr:: setSelected" + FileHelper.getRemoveVAT(supplier));
				remVatBox.setSelected(FileHelper.getRemoveVAT(supplier));
			}//end if supplier is not null and not the empty String
			
			Button save = new Button();
			save.setOnAction(event ->{
				Vector<Double> nPrices = new Vector<Double>();
				
				if(supplier!= null && !supplier.equals("")){
					try{
						//60;10;150;5;4;1
						nPrices.add(getDoubleFromText(lessF));
						nPrices.add(getDoubleFromText(lessExtraF));
						nPrices.add(getDoubleFromText(betweenF));
						nPrices.add(getDoubleFromText(betweenPercentF));
						nPrices.add(getDoubleFromText(morePercentF));
						nPrices.add(getDoubleFromText(kiloPriceF));
					}catch(Exception e){e.printStackTrace();}
	
					boolean remVat = remVatBox.selectedProperty().get();
					FileHelper.savePriceFormula(nPrices,supplier,remVat);
					
					ProcessSupplierFileController psc = new 
							ProcessSupplierFileController(st, addSupplier, addCategory, editCategories, editSup, processFiles, editPrice);
					psc.displayView();
				}//end if a supplier is selected to store the values
			
			});
			
			FileHelper fh = new FileHelper();
			Vector<SupplierCol> supCol = fh.getSuppliersCol();
			ObservableList<String> boxItems = FXCollections.observableArrayList();
			int selected = -1;
			int i =0;
			for(SupplierCol sc: supCol){
				boxItems.add(sc.getName());
				if(sc.getName().equals(supplier))selected = i;
					i++;
			}//end of looping through the supplierColumn configuration
			cBox.setItems(boxItems);
			if(selected!= -1){
				cBox.getSelectionModel().select(selected);;
			}//sets the selected it in the choicebox
			
			cBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			      @Override
			      public void changed(ObservableValue<? extends Number> observableValue, Number oldV, Number newV) {
			    	  ObservableList<String> boxItems = cBox.getItems(); 
			    	  String selection =  boxItems.get(newV.intValue());
			    	  EditPriceFormulaController epfc = 
			    			  new EditPriceFormulaController(st, addSupplier, addCategory, editCategories, 
			    					  editSup, processFiles, editPrice,selection);
			    	 epfc.displayView(); 
			      }
			  });
			
			
			
			
			
			pfv = 
					new PriceFormulaView(st, prices, lessF, betweenF, 
							kiloPriceF, lessExtraF, betweenPercentF, morePercentF, save,cBox,remVatBox);
		}catch(SupplierPriceNotFound e){
			e.printStackTrace();
		}//end of catching the exception
	
		
	}//end of constructor
	
	

	public void displayView(){
		pfv.display();
	}//end displayView
	
	
	private double getDoubleFromText(TextField f) throws IllegalArgumentException{
		return Double.parseDouble(f.getText().trim());
	}//end of getDoubleFromText
	
	
	
	
	
	
}//end of class
