package model;

import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;


public class StringHelper {

	/**
	 * 
	 * @param s a Strings
	 * @return a string that does not contain any of the 
	 * special characters that can be used as a separator in 
	 * a Csv file
	 */
	public static String cleanString(String s){
		s  = s.replaceAll("(\\r|\\n|\\r\\n)+", " ");
		s = s.replace(";", "");
		return s.replace(",", "");
	}//end of cleanString
	
	/**
	 * 	" bread" & "butter"
	 *  becomes:
	*   &quot;bread&quot; &amp; &quot;butter&quot;. 
	 * @param s
	 * @return
	 */
	public static String escapeSpecialChars(String s){
		return escapeHtml(s);
	}//end of escapeSpecialChars
	
	
	
	
	
	
	
	
	
}//end of StringHelper
