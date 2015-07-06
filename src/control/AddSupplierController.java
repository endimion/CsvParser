package control;



import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.FileHelper;
import model.SupplierCol;
import view.AddSupplierView;

public class AddSupplierController {

	Stage st; 
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
	
	AddSupplierView asv;
	
	public AddSupplierController(Stage st, Button addSupplier, Button addCategory, Button editCategories, 
			Button processFiles){
		this.st = st;
		
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
		 
		 save = new Button();
			save.setOnAction(event->{
				String supNameCol = nameF.getText();
				String categoryCol = catF.getText();
				String intNumberCol = itemNumberF.getText();
				String descriptionCol = descF.getText();
				String supItemNumberCol = supINF.getText();
				String eanCol = eanF.getText();
				String availabilityCol = avF.getText();
				String availabilityDenotCol = avdF.getText();
				String retailPriceCol = rPrice.getText();
				String separator = sepF.getText();
				
				FileHelper fh = new FileHelper();
				SupplierCol sup = new SupplierCol();
				sup.setName(supNameCol);
				sup.setCategory(categoryCol);
				sup.setItemNumber(intNumberCol);
				sup.setDescription(descriptionCol);
				sup.setSupItemNumber(supItemNumberCol);
				sup.setEan(eanCol);
				sup.setAvailability(availabilityCol);
				sup.setIsAvailable(availabilityDenotCol);
				sup.setRetailPrice(retailPriceCol);
				sup.setSeparator(separator);
				sup.setIsXml(xml.selectedProperty().get());
				sup.setModelPrefix(modF.getText());
				
				sup.setItemName(prodNameF.getText());
				sup.setStockStat(stock_statF.getText());
				sup.setStatus(statusF.getText())	;
				sup.setAddImg(addImgF.getText());
				sup.setManuf(manufF.getText());
				sup.setTaxClass(tax_classF.getText());
				sup.setMpn(mpnF.getText());
				sup.setImg(imgF.getText());
				
				fh.saveSupplier(sup);
			});
			
			
			asv = new AddSupplierView(st, save, nameF, xml, catF, 
					itemNumberF, descF, supINF, eanF, avF, avdF, sepF, rPrice, modF,   prodNameF,  stock_statF,  statusF,  addImgF,
					 manufF,  tax_classF, mpnF, imgF,addSupplier,addCategory,
					editCategories,processFiles);
	}//end of constructor
	
	public void displayView(){
		
		asv.display();
	}//end of displayView
	
	
	
	
	
	
	
	
	
	
}//end of class
