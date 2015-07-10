package model;

import java.io.File;
import java.util.Vector;
import javafx.concurrent.Task;




public class GetProdFromFileTask extends Task<Vector<Product>>{
		
	File f; 
	String supplier;
	Vector<Product> products ;
	
		public GetProdFromFileTask(File f, String supplier){
			this.f = f;
			this.supplier= supplier;
		}//end of constructor
	
	
	
		@Override
		protected Vector<Product> call() throws Exception {
			//System.out.println("GetProdFromFileTask.call :: Thread Started");
			
			products = new Vector<Product>();
			FileHelper fh = new FileHelper();
			Vector<SupplierCol> suppliers = fh.getSuppliersCol();
			SupplierCol sup = null;
			
			for(SupplierCol s: suppliers){
				if(s.getName().equals(supplier)) sup = s;
			}//end of looping through the suppliers
			
			//after retrieving the supplier we know what columns to retrieve from the csv
			if(sup != null){
				Vector<ParsedRow> rows = null;
				CategoriesSet	catSet = fh.getCategories();
				String supAvail = sup.getIsAvailable();
				Vector<String> keys = sup.getColVector();
				
				if(!sup.isXml()){ //If the supplier gives a csv file
					 rows = CsvParser.fileToVect(f, sup.getSeparator());
				}//end if the supplier does not send info as xml
				else{
					XmlParser xmlp = new XmlParser();
					String sep = sup.getSeparator();
					rows = xmlp.getFileToVect(f, sep, keys);
				}//end if supplier info is xml
					
					if(rows.size() >= 1)
	
						for(int i=0; i< rows.size();i++){
							ParsedRow r = rows.get(i);
							Product prod = new Product();
							updateProgress(i, rows.size());
							updateMessage("Reading product " + i + " from "+ rows.size());
							
							// if the string denoting item availability is indeed a word and not a number
							if(!supAvail.contains(">=")){
								//if in this product the column denoting availability is equal
								// to what the supplier.config file denotes as available for this supplier
								// then the quantity of the product is set to 28 else it is set to 0
								if(sup.getAvailability()!= null && 
										r.getElement(sup.getAvailability()) != null &&
										r.getElement(sup.getAvailability()).equals(supAvail) ){
									
									prod.setQuantity(28);
								}else{
									prod.setQuantity(0);
								}
							}else{
								String rest = supAvail.split(">=")[1].trim();
								//find the number part in the expression
								try{
									Double threshold = Double.parseDouble(rest);
										Double rowQuantity = Double.parseDouble(r.getElement(sup.getAvailability()));
										
										if(rowQuantity >= threshold ){
											prod.setQuantity(28);
										}else{
											prod.setQuantity(0);
										}
								}catch(Exception e){e.printStackTrace();}
							}//end if the supplier expression for available products is a numerical representation
							
							//
							prod.setPic(r.getElement(sup.getImg().trim()));
							prod.setAddPic(r.getElement(sup.getAddImg().trim()));
							
							//Converting the foreign category read from the file to a native category
							String foreignCat = r.getElement(sup.getCategory());
							String nativeCat = catSet.getContainingCat(foreignCat);
							
							//if the nativeCategory is not null then the product is added to the output csv file
							if(nativeCat != null){
								//System.out.println("GetProdFromFile.call :: matching category found");
								prod.setCategory(nativeCat);
								prod.setDescription(r.getElement(sup.getDescription()));
								if(r.getElement(sup.getEan()).equals("")){
									System.out.println("GetprodfromFiletask.call:: no ean found!!" );
								}
								prod.setEan(r.getElement(sup.getEan()));
								prod.setNumber(r.getElement(sup.getItemNumber()));
								prod.setRPrice(r.getElement(sup.getRetailPrice()));
								prod.setSupNum(r.getElement(sup.getSupItemNumber()));
								
								//set the model number of the product
								prod.setModel(sup.getModelPrefix().trim()
										+	r.getElement(sup.getItemNumber()).trim()   );
								
								
								
								//Additional information
								if(sup.getMpn()!=null 
										&&sup.getManuf()!=null && sup.getItemName()!= null){
									prod.setMpn(r.getElement(sup.getMpn()));
									prod.setManufact(r.getElement(sup.getManuf()));
									prod.setpName(r.getElement(sup.getItemName()));
								}

								prod.setDoublePrice(-1);
								//System.out.println("got it");
								
								products.add(prod);
								//System.out.println("added product");
							}//end if the nativeCategory is not null
							else{
								// write and error log that there was no matching category for the product
								prod.setCategory(foreignCat);
								prod.setDescription(r.getElement(sup.getDescription()));
								prod.setEan(r.getElement(sup.getEan()));
								prod.setNumber(r.getElement(sup.getItemNumber()));
								prod.setRPrice(r.getElement(sup.getRetailPrice()));
								prod.setSupNum(r.getElement(sup.getSupItemNumber()));
								
								fh.saveNotFoundCategory(prod, supplier);
							
							}//end if the nativeCategory was null, i.e. the product does not fit into any category of our site
						}//end of looping through the parsed rows of the csv file
			}//if we found a supplier matching the requested one to read the file
			
		
		return products;
	}//end of call

	
	
	
}//end of class
