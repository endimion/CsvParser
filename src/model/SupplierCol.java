package model;

import java.util.Vector;


/**
 * This class contains the information about the name of the columns
 * of the CSV or Xml files that the suppliers send
 * and match the various column/attribute names with the set get methods it provides
 * 
 *  additionally in the case of Xml files optional 
 *  methods allow to set/get if a field is a Nod or an Attribute in the XML file
 * @author nikos
 *
 */
public class SupplierCol {

	private String category;
	private String itemNumber;
	private String description;
	private String supItemNumber;
	private String ean;
	private String retailPrice;
	private String availability;
	private String name;
	private String separ;
	private boolean xml;
	private String modelPrefix;
	
	private String productXmlTag;
	
	private String mpn;
	private String itemName;
	private String manuf;
	
	private String stockStat;
	private String status;
	
	private String addImg;
	private String img;
	
	private String taxClass;
	
	// this  string stores how a supplier denotes a product is available
	private String isAvailable;
	
	private String xmlURL;
	private String xmlPass;
	private String xmlUser;
	
	
	private String weight;
	
	public SupplierCol(){
		category="";
		itemNumber = "";
		description = "";
		supItemNumber = "";
		ean = "";
		retailPrice = "";
		availability= "";
		name = "";
		
		this.separ ="";
		this.productXmlTag ="";
		img = "";
		this.xmlURL ="";
		this.xmlPass ="";
		this.xmlUser="";
		
		this.weight = "";
	}//end of Supplier
	
	
	
	
	public void setCategory(String cat){this.category=cat.trim();}
	public String getCategory(){return this.category;}
	
	public void setItemNumber(String it){this.itemNumber=it.trim();}
	public String getItemNumber(){return this.itemNumber;}
	
	public void setDescription(String desc){this.description = desc.trim();}
	public String getDescription( ){return this.description;}
	
	public void setSupItemNumber(String nm){this.supItemNumber=nm.trim();;}
	public String getSupItemNumber(){return this.supItemNumber;}
	
	public void setEan(String ean){this.ean=ean.trim();;}
	public String getEan(){return this.ean;}
	
	public void setRetailPrice(String p){this.retailPrice=p.trim();;}
	public String getRetailPrice(){return this.retailPrice;}
	
	public void setAvailability(String avail){this.availability=avail.trim();;}
	public String getAvailability(){return this.availability;}
	
	public void setIsXml(boolean xml){this.xml = xml;}
	public boolean isXml(){return xml;}
	
	public void setName(String n){this.name = n.trim();;}
	public String getName(){return this.name;}
	
	public void setIsAvailable(String a){this.isAvailable = a.trim();;}
	public String getIsAvailable(){return this.isAvailable;}
	
	
	public void setModelPrefix(String pre){this.modelPrefix = pre; }
	public String getModelPrefix(){return this.modelPrefix;}
	
	public void setImg(String img){this.img = img;}
	public String getImg(){return this.img;}
	
	
	//the separator of the files received by the suppliers in case 
	// they are csv files
	public void setSeparator(String sep){
		this.separ = sep;
	}//end of 
	
	public String getSeparator(){
		return this.separ;
	}
	
	public void setProductXmlTag(String t){
		this.productXmlTag = t;
	}//end
	public String getProductXmlTag(){return this.productXmlTag;}
	
	
	public void setXmlURL(String url){this.xmlURL = url;}
	public String getXmlURL(){return this.xmlURL;}
	
	public void setXmlPass(String pass){this.xmlPass = pass;}
	public String getXmlPass(){return this.xmlPass;}
	
	public void setXmlUser(String user){this.xmlUser = user;}
	public String getXmlUser(){return this.xmlUser;}
	
	public void setWeight(String w){this.weight = w;}
	public String getWeight(){return this.weight;}
	
	

	//Additional Information
	public String getMpn() {	return mpn;}
	public void setMpn(String mpn) {	this.mpn = mpn;}
	
	public String getItemName() {	return itemName;}
	public void setItemName(String itemName) {	this.itemName = itemName;	}

	public String getManuf() {	return manuf;}
	public void setManuf(String manuf) {this.manuf = manuf;}
	//end of additional info




	public String getStockStat() {return stockStat;}
	public void setStockStat(String stockStat) {	this.stockStat = stockStat;}

	public String getStatus() {return status;}
	public void setStatus(String status) {	this.status = status;}

	public String getAddImg() {	return addImg;}
	public void setAddImg(String addImg) {	this.addImg = addImg;	}

	public String getTaxClass() {	return taxClass;}
	public void setTaxClass(String taxClass) {	this.taxClass = taxClass;	}
	
	
	/**
	 * 
	 * @return a Vector<String> containing the 
	 *  names the supplier uses for the required information, 
	 *  i.e. description, ean, etc.
	 */
	public Vector<String> getColVector(){
		Vector<String> res = new Vector<String>();
		if(this.getCategory() != null && !this.getCategory().equals("")){
			res.add(this.getCategory());
		}
		if(this.getAvailability() != null && !this.getAvailability().equals("")){
			res.add(this.getAvailability());
		}
		if(this.getDescription() != null && !this.getDescription().equals("")){
			res.add(this.getDescription());
		}
		if(this.getEan()!= null && !this.getEan().equals("")){
			res.add(this.getEan());
		}
		if(this.getIsAvailable()!= null && !this.getIsAvailable().equals("")){
			res.add(this.getIsAvailable());
		}
		if(this.getItemNumber()!= null && !this.getItemNumber().equals("")){
			res.add(this.getItemNumber());
		}
		if(this.getRetailPrice()!= null && !this.getRetailPrice().equals("")){
			res.add(this.getRetailPrice());
		}
		if(this.getImg()!= null && !this.getImg().equals("")){
			res.add(this.getImg());
		}
		
		if(this.getItemName()!= null && !this.getItemName().equals("")){
			res.add(this.getItemName());
		}
		
		if(this.getAddImg()!= null && !this.getAddImg().equals("")){
			res.add(this.getAddImg());
		}
		
		if(this.getXmlPass() != null && !getXmlPass().equals("")){
			res.add(getXmlPass());
		}
		
		if(this.getXmlUser()!= null && ! getXmlUser().equals("")){res.add(getXmlUser());}
		if(this.getXmlURL() != null && !this.getXmlURL().equals("")){res.add(getXmlURL());}
		
		if(this.getWeight()!= null && !getWeight().equals("")){res.add(getWeight()) ;}
		return res;
	}//end of getColVector



	
	
}//end of Supplier
