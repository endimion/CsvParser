package control;

import java.util.Vector;

import view.AddSupplierView;
import view.EditSupplierView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.FileHelper;
import model.SupplierCol;

public class EditSupplierController {

	private ChoiceBox<String> cBox;
	
	
	Button save; 
	
	TextField nameF; 
	CheckBox xml; 
	TextField catF; 
	TextField itemNumberF; 
	TextField descF; 
	TextField supINF; 
	TextField eanF;
	TextField avF;  
	TextField avdF;  
	TextField sepF; 
	TextField rPrice;
	TextField modF;
	TextField prodNameF;
	TextField stock_statF; 
	TextField statusF; 
	TextField addImgF;
	TextField manufF; 
	TextField tax_classF;
	TextField mpnF;
	TextField imgF;
	TextField xmlUrlF;
	TextField xmlUserF;
	TextField xmlPassF;
	
	AddSupplierView asv;
	EditSupplierView esv;
	 SupplierCol sup;
	
	
	public EditSupplierController(Stage st, Button addSupplier, Button addCategory, Button editCategories, 
			Button processFiles,SupplierCol sup){
		
		FileHelper fh = new FileHelper();
		cBox = new ChoiceBox<String>();
		Vector<SupplierCol> supCol = fh.getSuppliersCol();
		ObservableList<String> boxItems = FXCollections.observableArrayList();
		 this.sup = sup;
		
		
		for(SupplierCol sc: supCol){
			boxItems.add(sc.getName());
		}//end of looping through the supplierColumn configuration
		cBox.setItems(boxItems);
	
		cBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		      @Override
		      public void changed(ObservableValue<? extends Number> observableValue, Number oldV, Number newV) {
		    	  ObservableList<String> boxItems = cBox.getItems(); 
		    	  
		    	  String selection =  boxItems.get(newV.intValue());
		    	  SupplierCol nSup = fh.getSupplierByName(selection);
		    	  System.out.println("selection changed " + newV.intValue());
		    	  EditSupplierController esc = new EditSupplierController(st,addSupplier, 
		    			  													addCategory, editCategories, processFiles, nSup);
		    	  esc.displayView();
		      }
		  });
	
	
		 nameF = new TextField();
		 xml = new CheckBox(); 
		 catF = new TextField(); 
		 itemNumberF = new TextField();
		 descF= new TextField(); 
		 supINF= new TextField(); 
		 eanF= new TextField();
		 avF= new TextField();  
		 avdF= new TextField();  
		 sepF= new TextField(); 
		 rPrice= new TextField();
		 modF = new TextField();
		 prodNameF = new TextField(); 
		 stock_statF = new TextField();
		 statusF = new TextField();
		 addImgF= new TextField();
		 manufF = new TextField(); 
		 tax_classF= new TextField();
		mpnF = new TextField();
		imgF = new TextField();
		xmlUrlF = new TextField();
		xmlUserF = new TextField();
		xmlPassF = new TextField();
		
		 Button saveB = new Button("Save Changes");
		 saveB.setOnAction(event ->{
			 SupplierCol supl = new SupplierCol();
			
			 supl.setAddImg(getTextFieldText(addImgF.getText()));
			 supl.setAvailability(getTextFieldText(avF.getText()));
			 supl.setCategory(getTextFieldText(catF.getText()));
			 supl.setDescription(getTextFieldText(descF.getText()));
			 supl.setEan(getTextFieldText(eanF.getText()));
			 supl.setIsAvailable(getTextFieldText(avdF.getText()));
			 supl.setIsXml(xml.selectedProperty().get());
			 supl.setItemName(getTextFieldText(prodNameF.getText()));
			 supl.setItemNumber(getTextFieldText(itemNumberF.getText()));
			 supl.setManuf(getTextFieldText(manufF.getText()));
			 supl.setModelPrefix(getTextFieldText(modF.getText()));
			 supl.setMpn(getTextFieldText(mpnF.getText()));
			 supl.setName(getTextFieldText(nameF.getText()));
			 supl.setRetailPrice(getTextFieldText(rPrice.getText()));
			 supl.setSeparator(getTextFieldText(sepF.getText()));
			 supl.setStatus(getTextFieldText(statusF.getText()));
			 supl.setStockStat(getTextFieldText(stock_statF.getText()));
			 supl.setSupItemNumber(getTextFieldText(supINF.getText()));
			 supl.setTaxClass(getTextFieldText(tax_classF.getText()));
			 supl.setImg(getTextFieldText(imgF.getText()));
			 
			 supl.setXmlURL(getTextFieldText(xmlUrlF.getText()));
			 supl.setXmlUser(getTextFieldText(xmlUserF.getText()));
			 supl.setXmlPass(getTextFieldText(xmlPassF.getText()));
			// supl.setProductXmlTag(t);
			 
			 fh.saveSupplier(supl);
			 
		 });
		 
		/*esv = new EditSupplierView(st, cBox, sup,saveB , nameF, xml, catF,
				itemNumberF, descF, supINF, eanF, avF, avdF, sepF, rPrice, modF, prodNameF, 
				stock_statF, statusF, addImgF, 
				manufF, tax_classF, mpnF,
				addSupplier, addCategory, editCategories, processFiles);*/

		 
		esv = new EditSupplierView(st, cBox, sup, saveB, nameF, xml, catF, 
				itemNumberF, descF, supINF, eanF, avF, avdF, sepF, rPrice, 
				modF, prodNameF, stock_statF, statusF, addImgF, manufF, 
				tax_classF, mpnF,imgF, xmlUrlF, xmlUserF,xmlPassF,
				addSupplier, addCategory, editCategories, processFiles);

	}//end of constructor
	
	
	
	public void displayView(){
		esv.display();
	}
	
	
	/**
	 * 
	 * @param txt
	 * @return
	 */
	private String getTextFieldText(String txt){
		return (txt == null || txt.equals("null"))?"":txt;
	}//end of getTextFieldText
	
	
	
}//end of class
