package model;

public class SupplierPriceNotFound extends Exception
	{
	      /**
	 * 
	 */
	private static final long serialVersionUID = -4814386844333705448L;

		//Parameterless Constructor
	      public SupplierPriceNotFound() {}

	      //Constructor that accepts a message
	      public SupplierPriceNotFound(String message)
	      {
	         super(message);
	      }
}//custom exception for when the price of a supplier is not found int the price.config file