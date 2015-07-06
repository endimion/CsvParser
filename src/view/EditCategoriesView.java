package view;

import java.util.Vector;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EditCategoriesView {

	private Stage stage;
	private Button save;
	
	public EditCategoriesView(Stage st, Button save, Vector<Text> catNames, Vector<TextArea> catContents,
			Button addSupplier, Button addCategory, Button editCategories, 
			Button processFiles){
		stage = st;
		
		this.save = save;
		save.setText("save");
		
		BorderPane MaineBorder = new BorderPane();
		MaineBorder.setId("mainPane");
		
		//Build the buttons pane
		
		//Build the menu title
		Text menuTitle = new Text("Menu");
		menuTitle.setId("leftTitle");
		
		VBox buttonBox = new VBox(8);
		buttonBox.getChildren().addAll(menuTitle, addSupplier, addCategory, editCategories, processFiles);
		addSupplier.setId("leftButtons");
		addCategory.setId("leftButtons");
		editCategories.setId("leftButtons");
		processFiles.setId("leftButtons");
		
		ScrollPane sp = new ScrollPane();
		BorderPane border = new BorderPane();
		border.setId("mainPane");
		
		Text tabTitle = new Text("Edit Categories");
		tabTitle.setId("title");
		
		VBox center = new VBox();
		for(int i=0 ; i < catNames.size(); i++){
			center.getChildren().addAll(catNames.get(i), catContents.get(i));
		}//
		
		border.setTop(tabTitle);
		border.setCenter(center);
		border.setBottom(this.save);
		
		sp.setContent(border);
		
		MaineBorder.setLeft(buttonBox);
		MaineBorder.setCenter(sp);
		
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
