package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddCategoryView {

		private Stage stage;
		private Button save;
	
		public AddCategoryView(Stage st, Button save,TextField catName ,TextArea catMatches,
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
		
			
			
			//
			BorderPane border = new BorderPane();
			border.setId("mainPane");
			
			Text tabTitle = new Text("Add Category");
			tabTitle.setId("title");
			VBox left = new VBox();
			Text localtxt = new Text("Local Category Name: ");
			localtxt.setId("simpleText");
			Text foreigntxt = new Text("Foreign Categories: ");
			foreigntxt.setId("simpleText");
			left.getChildren().addAll(localtxt,foreigntxt);
			
			VBox center = new VBox();
			center.getChildren().addAll(catName,catMatches);
			
			border.setTop(tabTitle);
			border.setCenter(center);
			border.setLeft(left);
			border.setBottom(this.save);
			
			MaineBorder.setLeft(buttonBox);
			MaineBorder.setCenter(border);
			
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
