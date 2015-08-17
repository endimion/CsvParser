package control;

import java.util.Vector;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.CommonProductsHelper;
import model.CommonProductsHelper.PairOfString;
import view.CommonProductsView;



public class CommonProductController {

	Vector<LabelCheckBox> checkBoxes;
	
	Button save;
	Stage stage;
	CommonProductsView cpv;
	
	
	
	
	
	public CommonProductController(Stage st,Button addSupplier, Button addCategory, Button editCategories, 
																												Button editSup, Button processFiles, Button editPriceForm,
																												Button commonProd){
		stage = st;
		save = new Button("Save");
		
		checkBoxes = new Vector<LabelCheckBox>();
		
		CommonProductsHelper  cph = new CommonProductsHelper();
		Vector<PairOfString> suppliers = cph.getSuppierPairs();
		
		Vector<HBox> hBoxes = new Vector<HBox>();
		
		for(PairOfString pair : suppliers){
			LabelCheckBox box1 = new LabelCheckBox(pair.getFirst(), new CheckBox());
			LabelCheckBox box2 = new LabelCheckBox(pair.getSecond(), new CheckBox());
			HBox hbox = new HBox(10);
			hbox.getChildren().addAll(new Label(box1.getLabel()), box1.getBox(), 
																	new Label(box2.getLabel()), box2.getBox()); 
			hBoxes.add(hbox);
			
		}//end of looping through the suppliers with common products
		
		save.setOnAction(event -> {
			for(HBox box : hBoxes){
				CheckBox box1  = (CheckBox)box.getChildren().get(1);
				CheckBox box2  = (CheckBox)box.getChildren().get(3);
				String supl=null;
				
				String badSupl = null; 
				
				
				if(box1.isSelected()){
					supl = ((Label) box.getChildren().get(0)).getText();
					badSupl = ((Label) box.getChildren().get(2)).getText();
					
				}else{
					if(box2.isSelected()){
						supl = ((Label) box.getChildren().get(2)).getText();
						badSupl = ((Label) box.getChildren().get(0)).getText();
					}//end if box2 is selected
				}//end of if box1 is not selected
				
				if(supl != null){
					System.out.println("CommonProductController:: supplier was selected" 
							+ supl + " and supplier " + badSupl  + " must have common products set to 0");
					
					cph.setSuplProdsUnavailable(badSupl, supl);
					
					ProcessSupplierFileController psfc = new ProcessSupplierFileController(st, 
							addSupplier, addCategory,editCategories, editSup,processFiles,editPriceForm,commonProd);
					psfc.displayView();
					
				}//end if supl was not null
			}//loop through the lines
			
		});//end of setOnAction
		
		Button cancel = new Button("Cancel");
		cancel.setOnAction(event ->{
			ProcessSupplierFileController psfc = new ProcessSupplierFileController(st, 
					addSupplier, addCategory,editCategories, editSup,processFiles,editPriceForm,commonProd);
			psfc.displayView();
		});
		
		
		Button revalidate = new Button("reCreate Output");
		revalidate.setOnAction(event ->{
			cph.reWriteOutput();
			ProcessSupplierFileController psfc = new ProcessSupplierFileController(st, 
					addSupplier, addCategory,editCategories, editSup,processFiles,editPriceForm,commonProd);
			psfc.displayView();
		});
		
		cpv = new CommonProductsView(st, hBoxes,save, cancel, revalidate);
		
		
	}//end of constructor
	

	
	public void displayView(){
		cpv.display();
	}
	
	
	
	
	
	
	/**
	 *  innerClass
	 * @author nikos
	 *
	 */
	public class LabelCheckBox{
		Label label ;
		CheckBox cBox;
		
		public LabelCheckBox(String label, CheckBox box){
			this.label = new Label(label);
			this.cBox = new CheckBox();
		}//end of constructor
		
		public CheckBox getBox() {return this.cBox;}
		public String 		getLabel(){return this.label.getText();}
	}//end of inner class
	
	
	
	
	
	
	
	
}//end of class
