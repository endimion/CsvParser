package view;

import control.ProcessAllSupplierController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ProcessSupplierFileView {

	private Stage stage;
	private Button process;

	
	
	public ProcessSupplierFileView(Stage st, ChoiceBox<String> cbox, Button fc, Button pr, 
																			Button addSupplier, Button addCategory, Button editCategories, 
																			Button editSupplier,
																			Button processFiles,
																			Button editPrice, Button commonProd,
														Text filePath){
		this.stage = st;
		this.process = pr;
		process.setText("Process Supplier File");
		
		BorderPane MaineBorder = new BorderPane();
		MaineBorder.setId("mainPane");
		
		//Build the buttons pane
		
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
		BorderPane border = new BorderPane();
		border.setId("mainPane");
		
		Text tabTitle = new Text("Process Supplier Files");
		tabTitle.setId("title");
		VBox left = new VBox();
		Text sNametxt = new Text("Supplier Name: ");
		sNametxt.setId("simpleText");
		Text filetxt = new Text("File: ");
		filetxt.setId("simpleText");
		left.getChildren().addAll(sNametxt,filetxt);
		
		VBox center = new VBox();
		center.getChildren().addAll(cbox,filePath);
		
		Button testBtn = new Button("test");
		testBtn.setOnAction(event ->{
			ProcessAllSupplierController pasc = new ProcessAllSupplierController(st,   addSupplier,  addCategory, 
					 editCategories,  editSupplier, 	 processFiles,  editPrice, 
					 commonProd);
			pasc.display();
		});
		
		HBox bBox = new HBox();
		bBox.getChildren().addAll(process,fc,testBtn);
		
		border.setTop(tabTitle);
		border.setCenter(center);
		border.setLeft(left);
		border.setBottom(bBox);
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
