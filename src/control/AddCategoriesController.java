package control;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.CategoryMap;
import model.FileHelper;
import view.AddCategoryView;

public class AddCategoriesController {

	private Stage stage;
	private AddCategoryView acv;
	private Button save;
	private TextArea foreign;
	private TextField local;
	
	public AddCategoriesController(Stage st,Button addSupplier, Button addCategory, Button editCategories, 
			Button processFiles){
		stage = st;
		FileHelper fh = new FileHelper();
		CategoryMap cm = new CategoryMap();
		local = new TextField();
		foreign = new TextArea();
		
		save = new Button();
		save.setOnAction(event->{
			cm.setName(local.getText());
			String[] foreignArray = foreign.getText().trim().split("\n");
			cm.setMatchesArray(foreignArray);
			fh.saveCategory(cm);
		});
		
		acv = new AddCategoryView(stage,save, local, foreign,  addSupplier,addCategory,
				editCategories,processFiles );
	}//end of constructor
	
	
	

	
	public void displayView(){
		acv.display();
	}
	
	
	
	
	
	
	
}//end of class
