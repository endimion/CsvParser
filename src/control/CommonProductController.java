package control;

import java.util.HashMap;
import java.util.Vector;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.CommonProductsHelper;
import model.CommonProductsHelper.PairOfString;
import model.Product;
import view.CommonProductsView;

public class CommonProductController {

	Vector<LabelCheckBox> checkBoxes;
	
	Button save;
	Stage stage;
	CommonProductsView cpv;
	
	
	
	
	
	public CommonProductController(Stage st){
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
					//TODO 
					// now we must make the products of the bad supplier 0
					cph.setSuplProdsUnavailable(badSupl, supl);
					
				}//end if supl was not null
				
				
				
			}//loop through the lines
			
			
		});//end of setOnAction
		
		
		
		
		cpv = new CommonProductsView(st, hBoxes,save);
		cpv.display();
		
	}//end of constructor
	

	

	
	
	
	
	
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
