package control;

import java.util.Vector;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.CategoriesSet;
import model.CategoryMap;
import model.FileHelper;
import view.EditCategoriesView;

public class EditCategoriesController {

	Vector<Text> catNameLabels;
	Vector<TextArea> catMatches;
	Stage stage ;
	Button save;
	EditCategoriesView ecv;
	
	public EditCategoriesController(Stage st, Button addSupplier, Button addCategory, Button editCategories, 
			Button processFiles){
		
		FileHelper fh = new FileHelper();
		CategoriesSet cs = fh.getCategories();
		
		catNameLabels = new Vector<Text>();
		catMatches = new Vector<TextArea>();
		stage = st;
		
		for(CategoryMap cm : cs){
			String catName = cm.getName();
			Vector<String> matches = cm.getMatches();
			
			catNameLabels.add(new Text(catName));
			TextArea txtA = new TextArea();
			
			for(String match : matches){
				txtA.appendText(match +"\n");
			}//end of looping through the foreign categories
			catMatches.add(txtA);
		}//end of looping through the categories set elements

	
		save = new Button();
		save.setOnAction(event ->{
			int i = 0;
			for(Text ctxt: catNameLabels){
				String catName = ctxt.getText();
				CategoryMap cm = new CategoryMap();
				cm.setName(catName);
				String[] categories = catMatches.get(i).getText().split("\n");
				cm.setMatchesArray(categories);
				
				fh.saveCategory(cm);
				i++;
			}//end of looping through the name of the categories
		});
	
		ecv = new EditCategoriesView(stage,save, catNameLabels, catMatches, addSupplier,addCategory,
				editCategories,processFiles);
	}//end of constructor
	
	
	public void displayView(){

		ecv.display();
	}
	
}//end of class
