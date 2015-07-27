package view;

import java.util.Vector;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PriceFormulaView {

	Stage stage;
	
	//60;10;150;5;4;1
	TextField lessF;
	TextField betweenF;
	TextField kiloPriceF;
	TextField lessExtraF;
	TextField betweenPercentF;
	TextField morePercentF;
	
	public PriceFormulaView(Stage st, Vector<Double> priceVars, TextField lessF,
	TextField betweenF, TextField kiloPriceF, 	TextField lessExtraF, 	
	TextField betweenPercentF,	TextField morePercentF, Button save, ChoiceBox<String> cBox, 
	CheckBox removeVAT){
		this.stage = st;
		
		this.lessF = lessF;
		this.betweenF = betweenF;
		this.kiloPriceF = kiloPriceF;
		this.lessExtraF= lessExtraF;
		this.lessF = lessF;
		this.betweenPercentF = betweenPercentF;
		this.morePercentF = morePercentF;
		
		//60;10;150;5;4;1
		if(priceVars!= null && priceVars.size() == 6){
			this.lessF.setText(priceVars.get(0).toString());
			this.lessExtraF.setText(priceVars.get(1).toString());
			this.betweenF.setText(priceVars.get(2).toString());
			this.betweenPercentF.setText(priceVars.get(3).toString());
			this.morePercentF.setText(priceVars.get(4).toString());
			this.kiloPriceF.setText(priceVars.get(5).toString());
		}//end if the priceVar vector has six elements, i.e. all that is required
		
		BorderPane MaineBorder = new BorderPane();
		MaineBorder.setId("mainPane");
		
		VBox leftBox = new VBox(15);
		VBox rightBox = new VBox(5);
		
		Text lesstxt = new Text("Item price less than: ");
		Text lessExtratxt = new Text("then additional charge: ");
		Text betweentxt = new Text("Else, if it is less than: ");
		Text betweenPercenttxt = new Text("then additional charge: ");
		Text morePercentTxt = new Text("else, additional charge: ");
		Text kiloTxt = new Text("finally charge each kilo: ");
		
		
		HBox lessBox = new HBox(5);
		lessBox.getChildren().addAll(lessF, new Text("Euro"));
		
		HBox lessExtraBox = new HBox(5);
		lessExtraBox.getChildren().addAll(lessExtraF, new Text("Euro"));
		
		HBox betweenBox = new HBox(5);
		betweenBox.getChildren().addAll(betweenF, new Text("Euro"));
		
		HBox betweenPercentBox = new HBox(5);
		betweenPercentBox.getChildren().addAll(betweenPercentF, new Text("%"));
		
		HBox morePercentBox = new HBox(5);
		morePercentBox.getChildren().addAll(morePercentF, new Text("%"));
		
		HBox kiloPriceBox = new HBox(5);
		kiloPriceBox.getChildren().addAll(kiloPriceF, new Text("Euro"));
		
		leftBox.getChildren().addAll(new Text("Supplier: "),lesstxt,lessExtratxt,
				betweentxt, betweenPercenttxt, morePercentTxt, kiloTxt, new Text("Remove VAT from supplier's price: "));
		
		rightBox.getChildren().addAll(cBox,lessBox, lessExtraBox,betweenBox,
				betweenPercentBox, morePercentBox,kiloPriceBox, removeVAT);
		
	
		
		
		HBox in = new HBox();
		in.getChildren().addAll(leftBox,rightBox);
		ScrollPane msp = new ScrollPane(in);
		
		save.setText("Save Changes");
		
		MaineBorder.setCenter(msp);
		MaineBorder.setTop(new Text("Edit price formula"));
		MaineBorder.setBottom(save);
		
		Scene scene = new Scene(MaineBorder, stage.getWidth(),stage.getHeight());
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		
		this.stage.setScene(scene);
		this.stage.sizeToScene();	
		
	} //end of PriceFormulaView
	
	
	
	/**
	 * displays the view
	 */
	public void display(){
		stage.show();
	}//end of display

	
	
	
	
	
	
	
	
}//end of class
