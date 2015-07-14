package control;

import model.SupplierCol;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainControl extends Application{

	private Button addSupplier;
	private Button addCategory;
	private Button editCategories;
	private Button processFiles;
	private Button editSup;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//AddSupplierController asc = new AddSupplierController(primaryStage);
		//asc.displayView();
		//EditCategoriesController ecc = new EditCategoriesController(primaryStage);
		//ecc.displayView();
		//AddCategoriesController acc = new AddCategoriesController(primaryStage);
		//acc.displayView();
		
		
		
		Button editPriceForm = new Button("Edit Price Formula");
		editPriceForm.setOnAction(event ->{
			EditPriceFormulaController epfc = 
					new EditPriceFormulaController(primaryStage,addSupplier, addCategory,editCategories, editSup,processFiles,editPriceForm,"");
			epfc.displayView();
		});
		
		addSupplier = new Button("Add Supplier");
		addSupplier.setOnAction(event ->{
			AddSupplierController asc = new AddSupplierController(primaryStage,addSupplier, addCategory,editCategories,processFiles);
			asc.displayView();
		});
		
		addCategory = new Button("Add Category");
		addCategory.setOnAction(event ->{
			AddCategoriesController acc = new AddCategoriesController(primaryStage,addSupplier, addCategory,editCategories,processFiles);
			acc.displayView();
		});
		
		editCategories = new Button("Edit Categories");
		editCategories.setOnAction(event ->{
			EditCategoriesController ecc = new EditCategoriesController(primaryStage,addSupplier, addCategory,editCategories,processFiles);
			ecc.displayView();
		});
		
		editSup = new Button("Edit Suppliers");
		editSup.setOnAction(event ->{
			EditSupplierController esc = new EditSupplierController(primaryStage, 
					addSupplier, addCategory, editCategories, processFiles, new SupplierCol());
			esc.displayView();
		});
		
		
		processFiles = new Button("Process Supplier Files");
		processFiles.setOnAction(event ->{
			ProcessSupplierFileController psfc = new ProcessSupplierFileController(primaryStage, 
					addSupplier, addCategory,editCategories, editSup,processFiles,editPriceForm);
			psfc.displayView();
		});
		
		

		
		
		ProcessSupplierFileController psfc = new ProcessSupplierFileController(primaryStage, 
				addSupplier, addCategory,editCategories, editSup, 
				processFiles , editPriceForm);
		psfc.displayView();
		
	}

	
	
	public static void main(String[] args) {
		launch(args);
	}

	
	
	
	
	
	
	
	
	
}//end of class
