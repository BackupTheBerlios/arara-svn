/**
 * 
 */
package net.indrix.arara.maps;

/**
 * @author rafael
 *
 */
public class FindCityInKml {
	String cityName;
	String cityInfo1;
	String cityInfo2;
	String XMLFile = "cidades_brasil.kml";
	
	public boolean find(String Name) //throws IOException, SAXException
	  {
		return false;    
/*		  SAXBuilder myBuilder = new SAXBuilder(true);
		  Document promolist = myBuilder.build(new File(getServletContext().getRealPath(XMLFile)));
		  Element promolistRoot = promolist.getRootElement();
		  
		  List promolistContents;
		  Iterator promolistContentsIterator;
		  Element promolistElement;
		  promolistContents = promolistRoot.getChildren();	
		  promolistContentsIterator = promolistContents.iterator();	
		 
		  while(promolistContentsIterator.hasNext()){
		    promolistElement = (Element) promolistContentsIterator.next();
		    
		    //use an if statement to see if an attribute value matches a String value
		    
		    if(promolistElement.getAttributeValue("<name>").equals("<some string>"))
		      //do somthing
		      
		    //use an if statement to see if the text of the node/element matches a String value
		    
		    if(promolistElement.getText().equals("<some string>"))
		      //do somthing
		 		    
		  }
		  */		
	  }
	
}
