package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class AddSupplierView {

	private Stage stage;
	private Button saveB;
	
	
	/**
	 * 
	 * @param st, the stage
	 * @param save, a button s that save the info to the supplier file
	 * @param nameF,  the name of the suppliser textfield
	 * @param xml, checkbox denoting if the info is given as an xml file or not
	 * @param catF, the textfield for the category
	 * @param itemNumberF, the textfield for the itemNumber
	 * @param descF, the textField for the description
	 * @param supINF, the textField for hte supplier item number
	 * @param eanF, the textField for the ean number
	 * @param avF, the textfield for the available 
	 * @param avdF, the textFiled for the expression that denotes if an item is available
	 * @param sepF, the textfield for the file separator in the case of csv files
	 * @param rPrice, the textfield for the retail price
	 */
	public AddSupplierView(Stage st, Button save, TextField nameF, CheckBox xml, TextField catF, 
											TextField itemNumberF, TextField descF , TextField supINF,  TextField eanF
											, TextField avF,  TextField avdF,  TextField sepF, TextField rPrice, TextField modelF,
											
											TextField prodNameF, TextField stock_statF, TextField statusF, TextField addImgF,
											TextField manufF, TextField tax_classF,
											TextField mpnF, TextField imgF, TextField xmlUrlF, TextField xmlUserF, TextField xmlPassF,
											
											Button addSupplier, Button addCategory, Button editCategories, 
											Button processFiles){
		this.stage = st;
		
		this.saveB = save;
		saveB.setText("save");
		
		
		
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
		
		Text tabTitle = new Text("Add new Supplier");
		tabTitle.setId("title");
		
		border.setTop(tabTitle);
		
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
	    Text modtxt = new Text("Model  Prefix: ");
	    modtxt.setId("simpleText");
	
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
	    Text mpntxt = new Text("MPN Column: ");
	    mpntxt.setId("simpleText");
	    Text imgtxt = new Text("Image Column: ");
	    imgtxt.setId("simpleText");
	    Text xmlUrlTxt = new Text("Xml feed URL: ");
	    xmlUrlTxt.setId("simpleText");
	    
	    Text xmlUserTxt = new Text("Xml feed UserName: ");
	    xmlUserTxt.setId("simpleText");
	    
	    Text xmlPassTxt = new Text("Xml feed PassWord: ");
	    xmlPassTxt.setId("simpleText");
	    
	    leftCol.getChildren().addAll( stxt,ctxt,intxt, dtxt, sintxt,
	    	eantxt, rptxt, xmltxt ,avtxt, davtxt, septxt,modtxt, prodNameTxt, stockStatTxt, 
	    	statusTxt, addImgTxt, manufTxt, taxClassTxt, mpntxt,imgtxt,xmlUrlTxt,xmlUserTxt,xmlPassTxt);
	    
	  leftCol.setSpacing(10);
	    
	    VBox centerCol = new VBox();
	    centerCol.getChildren().addAll(nameF, catF, itemNumberF, descF, supINF, eanF, rPrice, xml, 
	    		avF, avdF,sepF,modelF, prodNameF, stock_statF,statusF,addImgF,
	    		manufF, tax_classF,mpnF, imgF,xmlUrlF,xmlUserF,xmlPassF);
	    centerCol.setSpacing(8);
		
		
		
		
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
	
	
	
	
	/**
	 * displays the view
	 */
	public void display(){
		stage.show();
	}//end of display
	
	
	
	
	
	
	
}//end of class addSupplierView
