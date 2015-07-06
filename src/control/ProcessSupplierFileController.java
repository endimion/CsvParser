package control;

import java.io.File;
import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.FileHelper;
import model.Product;
import model.ProductProcessor;
import model.SupplierCol;
import view.ProcessSupplierFileView;

public class ProcessSupplierFileController {

		private ChoiceBox<String> cBox;
		private ProcessSupplierFileView psfv ;
		private Button process;
		private Button findFile;
		
		private Button editSup;
		
		private FileChooser fc;
		private Text path;
		private File f;
		
		
	public ProcessSupplierFileController(Stage st, Button addSupplier, Button addCategory, 
																					Button editCategories, Button editSup, 	Button processFiles, Button editPrice){
		fc = new FileChooser();
		//Vector<String> supplierNames = new Vector<String>();	
		FileHelper fh = new FileHelper();
		cBox = new ChoiceBox<String>();
		path = new Text();
		this.editSup = editSup;
		
		
		Vector<SupplierCol> supCol = fh.getSuppliersCol();
		ObservableList<String> boxItems = FXCollections.observableArrayList();
		
		for(SupplierCol sc: supCol){
			boxItems.add(sc.getName());
		}//end of looping through the supplierColumn configuration
		cBox.setItems(boxItems);
		
		findFile = new Button();
		findFile.setText("Browse Supplier File");
		findFile.setOnAction(event ->{
			fc.setTitle("Open Resource File");
			f = fc.showOpenDialog(st);
			if(f != null) path.setText(f.getAbsolutePath());
		});
		
		process = new Button();
		process.setOnAction(event ->{
			if(f != null && cBox.getSelectionModel().getSelectedItem() != null ){
				 String sup = cBox.getSelectionModel().getSelectedItem();
				 
				Vector<Product> prods =  new Vector<Product>();  //ph.getProdFromFile(f, cBox.getSelectionModel().getSelectedItem() );
				ProductProcessor pp = new ProductProcessor(f, sup, prods);
				pp.process();
			}
		});
		
		
		
		
		psfv = new ProcessSupplierFileView(st, cBox, findFile,process, addSupplier,addCategory,
																						editCategories,editSup,processFiles, editPrice,path);
		
	}//end of constructor
	
	

	public void displayView(){
		psfv.display();
	}
	
	
	
	
	
	
}//end of class
