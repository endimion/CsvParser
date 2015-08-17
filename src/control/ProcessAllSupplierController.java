package control;

import java.io.File;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.FileHelper;
import model.ProductProcessor;
import model.SupplierCol;
import model.SupplierXmlDownloader;
import view.ProcessAllSuppliersView;
import view.ProgressBarPopWindow;

public class ProcessAllSupplierController {

	Vector<SupplierCol>            suppliers = new Vector<SupplierCol>();
	 Vector<CheckBox>    			checkBoxes = new Vector<CheckBox>(); 
	 Vector<Button>           			selectFileButtons = new Vector<Button>();
	 Vector<Text>             				filepaths = new Vector<Text>();
	
	 Vector<SupplierCol> selectedSuppliers;
	 Vector<String> selectedSupplierFiles;
	 
	 ProcessAllSuppliersView pasv;
	 ProgressBarPopWindow pbw;
	 File f;
	 
	 public ProcessAllSupplierController(Stage st, Button addSupplier, Button addCategory, 
				Button editCategories, Button editSup, 	Button processFiles, Button editPrice, 
				Button commonProd){
		 
		 FileHelper fh = new FileHelper();
		 Vector<SupplierCol> supCol = fh.getSuppliersCol();
		
		 
		selectedSuppliers = new Vector<SupplierCol>();
		selectedSupplierFiles = new Vector<String>();
		 
		 //store the suppliers which are ticked
		 for(int i = 0; i < supCol.size() ; i++){
			 String supName = supCol.get(i).getName().trim();
			 if(supName  != null && !supName.equals("")){
				 suppliers.add(supCol.get(i));
				 checkBoxes.add(new CheckBox());
				 Button browserB = new Button("Browse " );
				 selectFileButtons.add(browserB);
				 
				 if(supCol.get(i).getXmlURL()!= null &&  !supCol.get(i).getXmlURL().equals("") )
				 {
					 filepaths.add(new Text(supCol.get(i).getXmlURL()));
					 browserB.setDisable(true);
					 
				 } else{
					 Text na = new Text("n/a");
					 filepaths.add(na);
					 configBrowseButton(browserB,na, st);
				 }
			 }
		 }//end of looping through the supCol items
		 
		 Button processSuppliers = new Button("Process Selected");
		 processSuppliers.setOnAction(event ->{
			 
			 for(int i = 0; i < checkBoxes.size(); i++){
				 if(checkBoxes.get(i).isSelected()){
					 selectedSuppliers.add(suppliers.get(i));
					 selectedSupplierFiles.add(filepaths.get(i).getText());
				 }
			 }//end of looping through the checkBoxes
			 
			
			//this vector stores all the Task that have to be executred to process all the supplier files
			 Vector<Task<Void>> processProductsTasks = new Vector<Task<Void>>();
			 
			 for(int i = 0 ; i < selectedSuppliers.size();i++){
				 SupplierCol supl = selectedSuppliers.get(i);
				 
				 if(!supl.getXmlURL().equals("")){
					 System.out.println("ProcessAllSuppliersController:: "
					 		+ "starting to process suplier with xml feed"); 
					 String url = supl.getXmlURL();
					 String pass = supl.getXmlPass();
					String user = supl.getXmlUser();
					SupplierXmlDownloader sxd = 
							new SupplierXmlDownloader(user, pass, url);
					
					//String xmlFileString = sxd.getUrlAsString();
					
            		ProductProcessor pp = new ProductProcessor(f, supl.getName());	
					processProductsTasks.add(pp.getMainTask(sxd));
				 
				 }else{
					File supF = new File(selectedSupplierFiles.get(i));
					 System.out.println("ProcessAllSuppliersController:: "
						 		+ "starting to process suplier" + supl.getName() +" using file " + supF.getName()); 
					 ProductProcessor pp = new ProductProcessor(supF, supl.getName());	
					 processProductsTasks.add(pp.getMainTask(null));
					 
				 }//end if the selected supplier does not specify an xml url
				 
			 }//end of looping through the selected suppliers
			 
			 //next we create a new thread that will contain the vector of threads
			 // this will enable us to join each thread of the vector with it 
			 //so that the threads are executed sequentially
			ExecutorService executor = Executors.newFixedThreadPool(1);
			for(Task<Void> tsk: processProductsTasks){
				System.out.println("STarting thread " + tsk.getMessage());
				executor.submit(tsk);
			}
			executor.shutdown();
			//after processing we clean, i.e. remove the selections
			selectedSuppliers = new Vector<SupplierCol>();
			 selectedSupplierFiles = new Vector<String>();
			 for(int i = 0; i < checkBoxes.size(); i++){
				 checkBoxes.get(i).setSelected(false);
			 }
		 });//end of processing the supplier filesevent
		 
		//  Button processButton, 
		//	Button addSupplier, Button addCategory, Button editCategories, 
		//	Button editSupplier,
	//		Button processFiles,
	//		Button editPrice, Button commonProd, Vector<SupplierCol> suppliers, 
	//		Vector<CheckBox> checkBoxes, 
	//		Vector<Button>     selectFileButtons, Vector<Text> filesPaths
		 
			pasv = new ProcessAllSuppliersView(st,
				  processSuppliers, addSupplier, addCategory, editCategories, editSup, 
				  processFiles, editPrice, commonProd, suppliers, 
				 checkBoxes, selectFileButtons,filepaths);
		 
		 
		 
	 }//end of constructor


	 public void display(){
		 pasv.display();
	 }//end of display


	 /**
	  *  sets the buttons setonAction and so on
	  * @param b
	  */
	 private void configBrowseButton(Button b, Text filePath, Stage st){
		 
		 b.setOnAction(event ->{
			 FileChooser fc = new FileChooser();
			 fc.setTitle("Open Resource File");
				File f = fc.showOpenDialog(st);
				if(f != null) filePath.setText(f.getAbsolutePath());
		 });
		 
	 }//end of configBrowseButton
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
}//end of class
