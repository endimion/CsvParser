package model;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XmlParser {

	DocumentBuilder db ;
	DocumentBuilderFactory dbf ;
	

	public XmlParser(){
		 dbf = DocumentBuilderFactory.newInstance();
		try {
			db = dbf.newDocumentBuilder();
		}catch(Exception e){e.printStackTrace();}
	}//end of constructor
	
	
	/**
	 * 
	 * @param f an xml file
	 * @param tagName the name of a node tag we want to retrieve
	 * @return a NodeList object containing all the nodes with the given tag
	 */
	public NodeList getNode(File f, String tagName){
		Document doc = readyDocument(f);
		
		NodeList nList = doc.getElementsByTagName(tagName);
		if(nList.getLength() == 0){
			System.out.println("XmlParser.getAttribute no tag with name " + tagName +" found in  "+f.getName() );
		}else{
			if(nList.item(0).getNodeType() != Node.ELEMENT_NODE ){
				System.out.println("XmlParser.getAttribute tag" + tagName +" not a node in  "+f.getName() );
			}else{
				return nList;
			}
		}//end if there exists nodes in the file with the given tagName
		return null;
	}//end of getAttribute
	
	
	
	/**
	 * 
	 * @param nNode, a Node element 
	 * @param attribute, the attribute whose value we want to retrieve
	 * @return a String denotation of the value of the attribute  or null
	 * if the given nNode is not an element
	 */
	public String getAttribute(Node nNode, String attribute){
		if(nNode != null){
			Element el = (Element) nNode;
			return el.getAttribute(attribute);
		}
		return null;
	}//end of getAttribute
	
	
	/**
	 * 
	 * @param parent, a Node whose children we are looking into
	 * @param child, the name of a Nod we want to find in the children of the parent 
	 * @return the Node Object that is a child of the parent and has the given name
	 */
	public Node getChild(Node parent, String child){
		
		NodeList children = parent.getChildNodes();
		if(children != null){
			for(int i = 0 ; i <children.getLength();i++){
				if(children.item(i).getNodeName().equals(child)){
					return children.item(i);
				}//end if the child node has the name we are looking for
			}//end of looping through the children of the node
		}//end if it has children
		return null;
	}//end of getChild
	
	
	
	/**
	 * @param name, the name of the xml tag or attribute we want to retrieve
	 * @param node, the nod which contains the tag or attribute
	 * @return a String denoting the value which matches the parameter name
	 *  regardless if that denotes an attribute of a child node in teh xml file of the parameter node
	 */
	public String getValue(String name, Node node){
		if(node != null){
			if(getAttribute(node, name).equals("") && getChild(node, name)!= null ){
				return getChild(node, name).getTextContent();
			}else{
				return getAttribute(node, name);
			}
		}else{
			return null;
		}
		//return   (node != null)?((getAttribute(node, name).equals(""))?
		//		getChild(node, name).getTextContent(): getAttribute(node, name)): null;
	}//end of getValue
	
	
	/**
	 * 
	 * @param Parent a Node of an xml file
	 * @param name the name of a child node we are looking for
	 * @return the child node with the name 
	 * if the parent node has as  descendant (maybe not as child)
	 *  a node with the given name 
	 */
	public Node hasDescendant(Node parent, String name){
		Node result = null;
		
		NodeList childs = parent.getChildNodes();
		for(int i = 0; i < childs.getLength(); i++){
			Node child = childs.item(i);
			if(child.getNodeName().equals(name)){
				//System.out.println("hasDescendant:: " + " result is " + child.getNodeName());
				return child;
			}else{
				result = hasDescendant(childs.item(i), name);
				if(result != null){ 
					//System.out.println("hasDescendant:: " + " result is " + result.getNodeName());
					break; 
				}
			}
		}//end of looping through the children of the parent
		
		return result;
	}//end of hasInnerNode
	
	
	
	
	
	

	/**
	 * 
	 * @param xml, an Xml file
	 * @param parentN, the String name of parent node
	 * @param attr, String denoting the attribute of the parent node we want to retrieve
	 * @param childN, String denoting the name of the child node we want the parent to have
	 * @param cAttr,  String denoting the attribute the child must have
	 * @param cAttrVal, String denoting the value of the attribute the child must have
	 * @return the value of the attribute attr, of the parent node, if that parent node has
	 * a descendant called childN, which has an attribute cAttr with value cAttrVal. Else null is returned
	 */
	public String getParAtt(String stringFile,File xml, String parentN, String attr, 
			String childN, String cAttr, String cAttrVal){

		NodeList pList = getNode( xml, parentN);
		Node c = null;
		Node p = null;
		boolean found = false;
		
		if(pList != null){
			for(int i = 0; i < pList.getLength(); i++){
				if(pList.item(i).hasChildNodes())
				{	
					//System.out.println("checking Parent node " + pList.item(i).getNodeName());
					NodeList cList = pList.item(i).getChildNodes();
					
					for(int j = 0; j < cList.getLength(); j++){
						c =cList.item(j); 	
						//System.out.println("checking node " + c.getNodeName());
						Node offspring = hasDescendant(c, childN);
						if( offspring != null){
							//System.out.println("found child with name " + offspring.getNodeName());
							String attVal = getAttribute(offspring,cAttr);
							//System.out.println("child has attribut  " +cAttr + " with val"
							//		+ attVal);
							
							if(attVal.equals(cAttrVal)){ 
								found = true;
								break;
							}
						}
					}//end of looping throught the child elements
					if(found){
						p = pList.item(i);
						break;
					}
				}//end if the parent node has  children
	
			}//end of looping throught the nodes matching the parent name																							
																										
			return  getValue(attr, p);
		}//end if pList is not null
		else{
			
			return null;
		}
		//return "parent "+ p + "child " + c + " attribute " + attr + " value " + getValue(attr, p);
	}//getAttrValParChild
	
	
	
	
	/**
	 * 
	 * @param f a File which is supposed to be an xml file
	 * @return an xml Document object that can be used to retrieve that various attributes of the
	 * xml file
	 */
	private Document readyDocument(File f){
	
		try{
				FileInputStream fis = new FileInputStream(f);
				InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
				InputSource inputSource = new InputSource(isr);
				inputSource.setEncoding("UTF-8");
				
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			    DocumentBuilder builder = factory.newDocumentBuilder();
			  
			    Document doc =  builder.parse(inputSource);
				doc.getDocumentElement().normalize();
				return doc;
			
		}catch(Exception e){e.printStackTrace(); return null;}
	}//end of readyDocument
	
	
	//TODO
	public  Vector<ParsedRow> getFileToVect(File f, String rowTag, 
			Vector<String> keys){
		Vector<ParsedRow> res = new Vector<ParsedRow>();
		
		NodeList  rows = getNode(f,rowTag);
		Node row = null;
		ParsedRow pr ;
		Vector<String> rowData ;
		
		System.out.println(" XmlParser.getFileToVect:: size of rows " 
												+ rows.getLength() );
		
		for(int i = 0; i < rows.getLength(); i++){
			row = rows.item(i);
			rowData = new Vector<String>();
			 pr = new ParsedRow(keys);
			 String v = "";
			 //start looking for the values of the keys
			 for(String key: keys){
				
				 if ( ( v = getValue(key,row)) != null){
					rowData.add(v);
				}else{rowData.add("");}

			 }//end of looping through the keys
			pr.setRowData(rowData);
			if(rowData.size() > 0) res.add(pr);
		}//end of looping throw the "rows"
		
		System.out.println(" XmlParser.getFileToVect:: size of rows " 
				+ res.size());
		return res;
	}//end of getFileToVect
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}//end of class



