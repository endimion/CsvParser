package view;


import java.util.Vector;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CommonProductsView {

	private Stage stage;
	Vector<Vector<CheckBox> > eanCheckBoxes;
	 Vector<HBox> hBoxes ;
	
	
	public CommonProductsView(Stage st,  Vector<HBox> hBoxes, Button save ){
		this.stage = st;
		this.hBoxes = hBoxes;
		
		//Vector<HBox> vericalBoxes = new Vector<HBox>(); 
		//Vector<HBox> horizontalBoxes = new Vector<HBox>();
		VBox verticalBoxes = new VBox(10);
		verticalBoxes.getChildren().addAll(new Label("Check the name of the suppliers to Display ONLY their products \n"
				+ "for the ones in common with the other supplier"));
		for(HBox hbox : hBoxes){
			verticalBoxes.getChildren().addAll(hbox);
		}
		
		
		
		//finalizing the view
		//VBox center = new VBox();
		//center.getChildren().addAll(verticalBoxes);
		BorderPane MaineBorder = new BorderPane();
		MaineBorder.setId("mainPane");
		verticalBoxes.getChildren().addAll(save);
		MaineBorder.setCenter(verticalBoxes);
		
		Scene scene = new Scene(MaineBorder, stage.getWidth(),stage.getHeight());
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
		this.stage.setScene(scene);
		
	}//end of constructor
	
	
	
	
	

	/**
	 * displays the view
	 */
	public void display(){
		stage.show();
	}//end of display
	
	
	
	
	
}//end of class
