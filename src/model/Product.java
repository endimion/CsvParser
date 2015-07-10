package model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang.StringUtils;

public class Product {

	private String category;
	private String number;
	private String description; 
	private String supNumber;
	private String ean;
	private String rPrice;
	private int quantity;
	private String model;
	
	//private double weight;
	private double price;
	private String weight;
	private String pic;
	
	private String mpn;
	private String pName;
	private String stStatus;
	private String status;
	private String addPic;
	private String manufact;
	private String tax_class;
	
	
	public Product(){
		category= "";
		number = "";
		description = "";
		supNumber = "";
		ean = "";
		rPrice = "";
		quantity = 0;
		weight  = "";
		pic = "";
		model = "";
		price= -1;
		
		mpn = "";
		pName = "";
		stStatus = "";
		status = "";
		addPic = "";
		 manufact = "";
		tax_class = "";
	}//end of constructor
	
	public Product(String c, String n, String d, String sup, String e, String rp, int q, String m){
		this.category = c;
		this.number = n;
		this.description = d;
		this.supNumber = sup;
		this.ean = e;
		this.rPrice = rp;
		this.quantity = q;
		this.weight = null;
		this.pic = null;
		this.model = m;
		price = -1;
	}//end of constructor
	
	
	public String getCategory(){return this.category;}
	public void setCategory(String cat){this.category = cat;}
	
	public String getNumber(){return this.number;}
	public void setNumber(String n){this.number =n;}
	
	public String getDescription(){return this.description;}
	public void setDescription(String desc){
		this.description = cleanString(desc);
	}//end of setDescription
	
	public String getSupNum(){return this.supNumber;}
	public void setSupNum(String sn){this.supNumber = sn;}
	
	public String getEan(){return this.ean;}
	public void setEan(String e){
		e = e.trim();
		if(e.contains(",")|| e.contains(";") ){
			if(e.contains(",")) this.ean  = e.split(",")[0].trim();
			else  this.ean  = e.split(";")[0].trim();
		}else{
			this.ean = e;
		}
	}//end of setEan
	
	public String getRPrice(){return this.rPrice;}
	public void  setRPrice(String p){this.rPrice = p;}
	
	public int getQuantity(){return this.quantity;}
	public void setQuantity(int q){this.quantity = q;}
	
	public String getWeight(){return this.weight;}
	public void setWeight(String w){this.weight = w;}
	
	public String getModel(){return this.model;}
	public void setModel(String model){this.model = model;}
	
	/**
	 * 
	 * @return a double version of the weight (converted to kg, i.e. if it is i grams it gets divided by 1000)
	 */
	public double getWeightDouble(){
		String w = this.weight;
		double res = 10.0;
		
		if(w != null && (w.contains("kg") || w.contains("g"))){
			if(w.contains("kg")){ 
				w = w.trim().split("kg")[0].replace(",", ".");
				res = Double.parseDouble(w);
			}
			if(w.contains("g")){
				w = w.trim().split("g")[0].replace(",", ".");
				res = Double.parseDouble(w) / 1000;
			}
		}
		
		return res;
	}//end of getWeightDouble
	
	public String getPic(){return this.pic;}
	public void setPic(String picN){this.pic = picN;}
	

	/**
	 * @param var1 lessThan 
	 * @param var2 additional price if less than var1
	 * @param var3 between 
	 * @param var4 additional price if less than var3
	 * @param var5 additional price if more than var3
	 * @return the price of the product
	 */
	public double getPrice(double var1, double var2, double var3,  double var4, 
			double var5, double kiloPrice){

		String retailS = (getRPrice().trim());
		double res = 0;
		try{
			if(StringUtils.countMatches(retailS,".") == 0){
				retailS = (getRPrice().trim()).replace(",",".");
			}else{
				retailS = (getRPrice().trim()).replace(",","");
			}

			while(!retailS.matches("(.*)\\d") && retailS.length() > 0){
				retailS = retailS.substring(0, retailS.length()-1);
			}//end of while
			
			double retailP = Double.parseDouble(retailS);

			if(retailP <= var1){
				res =  retailP +  var2 + getWeightDouble()*kiloPrice;
			}//end of if
			else{
				if(retailP <= var3) {
					//System.out.println("less than var3");
					res =  retailP  + (retailP * var4)/100 + getWeightDouble()*kiloPrice;
				
				}else res =  retailP  + (retailP * var5)/100 + getWeightDouble()*kiloPrice;
			}
		}catch(NumberFormatException e){
				e.printStackTrace();
				return -1;
			}

		res = round(res,2);
		return res;
	}//end of getPrice
	
	
	public void setDoublePrice(double p){ this.price = p;	}
	public double getDoublePrice(){return this.price;}
	
	
	public String toString(String supplier){
		return "product category "+ getCategory() +", description " + getDescription() + ", ean " + getEan()
				+ ", number" + getNumber() + ", retail price "+ getRPrice() + ", supplier number " + getSupNum() +
				", quantity " + getQuantity() 
				+", price"+ getPrice(FileHelper.getPriceConfig(supplier).get(0), FileHelper.getPriceConfig(supplier).get(1), 
						FileHelper.getPriceConfig(supplier).get(2),FileHelper.getPriceConfig(supplier).get(3),
						FileHelper.getPriceConfig(supplier).get(4),FileHelper.getPriceConfig(supplier).get(5))
						+  ", status " + getStatus() 
				+ ", stock_status " + getStStatus() + " tax class " + getTax_class() + ", name " + getpName()
				+" mpn " + getMpn() + ", manufacturer  " + getManufact(); 
	}//end toString
	
	
	/**
	 * 
	 * @return a String repersentation as a CSV line of the product
	 */
	public String toCsv( ){
		
		return trimmer(getModel()).replace(";",",") + ";"
					   +" "+";"
					   	+ trimmer(getEan())+";" 
					   	+ trimmer(getMpn()).replace(";",",")+";"
					   	+  trimmer(getpName()).replace(";",",")+";"
					   	+  trimmer(getDescription()).replace(";",",")+";"
					   	+  trimmer(getCategory()).replace(";",",") +";"
					   	+  getQuantity()+";"
					   	+ trimmer(getStStatus()).replace(";",",")+";"
					   	+ trimmer(getStatus()).replace(";",",")+";"
					   	+ trimmer(getPic()).replace(";",",")+";"
					   	+trimmer(getAddPic()).replace(";",",")+";"
					   	+trimmer(getManufact()).replace(";",",")+";"
					   	+ getDoublePrice() +";"
					   	+trimmer(getTax_class()).replace(";",",")+";"
					   	+  getWeightDouble()+"kg"+";";
		
		
	}//end toCsv

	/**
	 * 
	 * @param s an input string
	 * @return the string trimmed if it is not null, else the empty string is returned
	 */
	public String trimmer(String s){
		return (s != null)?s.trim():"";
	}

	//ADDitional Information
	
	public String getSupNumber() {	return supNumber;}
	public void setSupNumber(String supNumber) {this.supNumber = supNumber;}

	public String getMpn() {	return mpn;}
	public void setMpn(String mpn) {this.mpn = mpn;}

	public String getpName() {return pName;}
	public void setpName(String pName) {	this.pName = pName;}

	public String getStatus() {return status;	}
	public void setStatus(String status) {this.status = status;}

	public String getStStatus() {return stStatus;	}
	public void setStStatus(String stStatus) {this.stStatus = stStatus;}
	
	public String getAddPic() {return addPic;	}
	public void setAddPic(String addPic) {this.addPic = addPic;}

	public String getManufact() {return manufact;}
	public void setManufact(String manufact) {	this.manufact = manufact;	}

	public String getTax_class() {return tax_class;}
	public void setTax_class(String tax_class) {this.tax_class = tax_class;}
	
	
	
	/**
	 * 
	 * @param value
	 * @param places
	 * @return
	 */
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();
	    	BigDecimal bd = new BigDecimal(value);
	    	bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}//end of round
	

	
	/**
	 * 
	 * @param s a Strings
	 * @return a string that does not contain any of the 
	 * special characters that can be used as a separator in 
	 * a Csv file
	 */
	private String cleanString(String s){
		s  = s.replaceAll("(\\r|\\n|\\r\\n)+", " ");
		s = s.replace(";", "");
		return s.replace(",", "");
	}
	
	/**
	 * Compares the given product with a foreign one and returns true if they match in all attributes
	 * except from the weight, doublePrice, Description, pic, addPic,  
	 * @param foreign
	 * @return
	 */
	public boolean compareProduct(Product foreign){
		/*
		boolean res = this.getModel().equals(foreign.getModel()) &&this.getRPrice().equals(foreign.getRPrice());
		if(!res){
			System.out.println("Product.compareProduct :: " + this.getModel() + " =/= " + foreign.getModel() 
					+ " OR "+ this.getDoublePrice()  +"=/="+ foreign.getDoublePrice() 
					+ this.getNumber() + " =/="+ (foreign.getNumber()));
		}*/
		
		return this.getModel().equals(foreign.getModel()) &&
						this.getCategory().equals(foreign.getCategory()) &&
						this.getEan().equals(foreign.getEan())&&
						this.getManufact().equals(foreign.getManufact())&&
						this.getMpn().equals(foreign.getMpn())&&
						//this.getNumber().equals(foreign.getNumber())&&
						this.getpName().equals(foreign.getpName())&&
						this.getQuantity() == foreign.getQuantity()&&
						this.getDoublePrice() == foreign.getDoublePrice();
	}//end compareProduct
	
	
}//end of class
