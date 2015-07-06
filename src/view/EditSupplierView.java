package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.SupplierCol;

public class EditSupplierView {

	Stage stage;
	Button saveB;
	
	
	public EditSupplierView(Stage st, ChoiceBox<String> cbox, SupplierCol sup, 
			Button save, TextField nameF, CheckBox xml, TextField catF, 
			TextField itemNumberF, TextField descF , TextField supINF,  TextField eanF
			, TextField avF,  TextField avdF,  TextField sepF, TextField rPrice, TextField modelF,
			
			TextField prodNameF, TextField stock_statF, TextField statusF, TextField addImgF,
			TextField manufF, TextField tax_classF, TextField mpnF,
			TextField imgF,
			
			Button addSupplier, Button addCategory, Button editCategories, 
			Button processFiles){
		
		this.stage = st;
		this.saveB = save;
		
		
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
	
		
		BorderPane border = new BorderPane();
		border.setId("mainPane");
		
		HBox topBox = new HBox(8);
		topBox.setId("topBox");
		
		Text tabTitle = new Text("Edit Supplier");
		tabTitle.setId("title");
		
		topBox.getChildren().addAll(tabTitle,cbox);
		cbox.setId("cBox");
		
		
		
		
		
		border.setTop(topBox);
		
	    //Left column containing only the names
	    VBox leftCol = new VBox();
	   Text stxt =  new Text("Supplier Name Column: ");
	   stxt.setId("simpleText");
	    Text ctxt = new 	 Text("Category Column: ");
	    ctxt.setId("simpleText");
	    Text intxt = new Text("Item Number Column: ");
	    intxt.setId("simpleText");
	    Text dtxt = new Text("Description Column: ");
	    dtxt.setId("simpleText");
	    Text sintxt = new Text("Supplier Item Number Column:");
	    sintxt.setId("simpleText");
	    Text eantxt = new Text("EAN Column:");
	    eantxt.setId("simpleText");
	    Text rptxt = new Text("Retail Price Column:");
	    rptxt.setId("simpleText");
	    Text xmltxt = new Text("XML Column:");
	    xmltxt.setId("simpleText");
	    Text avtxt = new Text("Availability Column: ");
	    avtxt.setId("simpleText");
	    Text davtxt = new Text("Denotation of Availability Column: ");
	    davtxt.setId("simpleText");
	    Text septxt = new Text("CSV separator: ");
	    septxt.setId("simpleText");
	    Text modtxt = new Text("Model Number Prefix: ");
	    modtxt.setId("simpleText");
	    Text imgtxt = new Text("Image Column ");
	    imgtxt.setId("simpleText");
	
	    Text prodNameTxt = new Text("Product Name Column: ");
	    prodNameTxt.setId("simpleText");
	    Text stockStatTxt = new Text("Stock Status Column: ");
	    stockStatTxt.setId("simpleText");
	    Text statusTxt = new Text("Status Column: ");
	    statusTxt.setId("simpleText");
	    Text addImgTxt = new Text("Additional Image Column: ");
	    addImgTxt.setId("simpleText");
	    Text manufTxt = new Text("Manufacturer Column: ");
	    manufTxt.setId("simpleText");
	    Text taxClassTxt = new Text("Tax Class Column: ");
	    taxClassTxt.setId("simpleText");
	   Text mpnTxt = new Text("MPN: ");
	    mpnTxt.setId("simpleText");
	    
	    
	    
	
	    
	  
	    if(sup!= null){
	    	nameF.setText(sup.getName());
	    	catF.setText(sup.getCategory());
	    	itemNumberF.setText(sup.getItemNumber());
	    	descF.setText(sup.getDescription());
	    	supINF.setText(sup.getSupItemNumber());
	    	eanF.setText(sup.getEan());
	    	rPrice.setText(sup.getRetailPrice());
	    	xml.setSelected(sup.isXml());
	    	avF.setText(sup.getAvailability());
	    	avdF.setText(sup.getIsAvailable());
	    	sepF.setText(sup.getSeparator());
	    	modelF.setText(sup.getModelPrefix());
	    	prodNameF.setText(sup.getItemName());
	    	stock_statF.setText(sup.getStockStat());
	    	statusF.setText(sup.getStatus());
	    	addImgF.setText(sup.getAddImg());
	    	manufF.setText(sup.getManuf());
	    	tax_classF.setText(sup.getTaxClass());
	    	mpnF.setText(sup.getMpn());
	    	imgF.setText(sup.getImg());
	    }//end if the supplier is not null
	  
	    VBox centerCol = new VBox();
	    centerCol.getChildren().addAll(nameF, catF, itemNumberF, descF, supINF, eanF, rPrice, xml, 
	    		avF, avdF,sepF,modelF, prodNameF, stock_statF,statusF,addImgF,manufF, tax_classF,mpnF,imgF);
	    centerCol.setSpacing(8);
	    
	    leftCol.getChildren().addAll( stxt,ctxt,intxt, dtxt, sintxt,
		    	eantxt, rptxt, xmltxt ,avtxt, davtxt, septxt,modtxt, prodNameTxt, stockStatTxt, 
		    	statusTxt, addImgTxt, manufTxt, taxClassTxt,mpnTxt,imgtxt);
	    leftCol.setSpacing(10);
	
		
		
		
		
		border.setCenter(centerCol);
		border.setLeft(leftCol);
		border.setBottom(saveB);
		
		ScrollPane sp = new ScrollPane(border);
		
		MaineBorder.setLeft(buttonBox);
		MaineBorder.setCenter(sp);
		
		Scene scene = new Scene(MaineBorder, stage.getWidth(),stage.getHeight());
	   scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
	    this.stage.setScene(scene);
		
		
		
	}//end of constructor
	
	
	
	public void display(){
		stage.show();
	}
	
	
	
	
	
	

	
	
	
	
}//end of class
