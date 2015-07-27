package view;

import java.util.Vector;

import model.SupplierCol;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ProcessAllSuppliersView {

	private Stage stage;

	
	
	
	
	public ProcessAllSuppliersView(Stage st,  Button processButton, 
			Button addSupplier, Button addCategory, Button editCategories, 
			Button editSupplier,
			Button processFiles,
			Button editPrice, Button commonProd, Vector<SupplierCol> suppliers, 
			Vector<CheckBox> checkBoxes, 
			Vector<Button>     selectFileButtons, Vector<Text> filesPaths){
		
		this.stage = st;
		
		
		BorderPane MaineBorder = new BorderPane();
		MaineBorder.setId("mainPane");
		
		//Build the horizontal pane
		Vector<HBox> horizontal = new Vector<HBox>();
		for(int i = 0 ; i < suppliers.size(); i++){
			HBox hbox = new HBox(15);
			hbox.getChildren().addAll(new Label(suppliers.get(i).getName()), 
					checkBoxes.get(i),
					selectFileButtons.get(i),
					filesPaths.get(i));
					
			horizontal.add(hbox);
		}//end of looping through the suppliers vector
		
		
		
		
		
		//Build the menu title
		Text menuTitle = new Text("Menu");
		menuTitle.setId("leftTitle");
		
		VBox buttonBox = new VBox(8);
		buttonBox.getChildren().addAll(menuTitle, addSupplier, addCategory, editCategories, 
						processFiles,editSupplier,editPrice,commonProd);
		addSupplier.setId("leftButtons");
		addCategory.setId("leftButtons");
		editCategories.setId("leftButtons");
		processFiles.setId("leftButtons");
		editSupplier.setId("leftButtons");
		editPrice.setId("leftButtons");
		
		//build the central pane
		VBox centerVertical = new VBox(10);
		centerVertical.getChildren().addAll(horizontal);
		centerVertical.getChildren().add(processButton);
		
		ScrollPane border = new ScrollPane(centerVertical);
		border.setId("mainPane");
		
		
		
		//
		MaineBorder.setCenter(border);
		MaineBorder.setLeft(buttonBox);
		
		Scene scene = new Scene(MaineBorder, stage.getWidth(),stage.getHeight());
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		
		this.stage.setScene(scene);
		this.stage.sizeToScene();
		
	}//end of constructor
		
		
	/**
		* displays the view
	*/
	public void display(){
		stage.show();
	}//end of display
			
			
			
	
	
	
	
	
}//end of class
