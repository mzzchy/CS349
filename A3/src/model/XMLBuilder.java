package model;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * TODO list
 * 1. Parse Stroke(just point data)
 * 2. Parse Animation/Frame data
 * 3. All data in a file
 */
public class XMLBuilder {

	/**
	 * @param args
	 */
	Document doc; 
	
	public XMLBuilder() throws ParserConfigurationException{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
	}
	
	public void addStroke(){
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			 
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("KSketch");
			doc.appendChild(rootElement);
	 
			Element animation = doc.createElement("Animation");
			rootElement.appendChild(animation);
			
			// staff elements
			Element stroke = doc.createElement("Stroke");
			animation.appendChild(stroke);
	 
			// firstname elements
			Element firstname = doc.createElement("point");
			firstname.appendChild(doc.createTextNode("1,2"));
			stroke.appendChild(firstname);
	 
			// lastname elements
			Element lastname = doc.createElement("point");
			lastname.appendChild(doc.createTextNode("3,4"));
			stroke.appendChild(lastname);
	 
			// nickname elements
			Element nickname = doc.createElement("point");
			nickname.appendChild(doc.createTextNode("5,6"));
			stroke.appendChild(nickname);
			
			//
			Element frame = doc.createElement("frame");
			animation.appendChild(frame);
			
			//Init
			Element initFrame = doc.createElement("start");
			initFrame.appendChild(doc.createTextNode("0"));
			frame.appendChild(initFrame);
			
			//End
			Element endFrame = doc.createElement("end");
			endFrame.appendChild(doc.createTextNode("10"));
			frame.appendChild(endFrame);
			
			//Displacement
			Element displace = doc.createElement("displace");
			frame.appendChild(displace);
			
			//All the points for each frame
			for(int i = 0; i< 5; i++){
				Element dis = doc.createElement("point");
				dis.appendChild(doc.createTextNode(""+i+","+(i+1)));
				displace.appendChild(dis);
			}
			
			
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
//			StreamResult result = new StreamResult(new File("C:\\file.xml"));
	 
			// Output to console for testing
			StreamResult result = new StreamResult(System.out);
	 
			transformer.transform(source, result);
	 
			System.out.println("File saved!");
	 
		  } catch (ParserConfigurationException pce) {
			System.out.print(pce);
		  } catch (TransformerException tfe) {
			System.out.print(tfe);
		  }
		
	}

	public void saveFile() {
		// TODO Auto-generated method stub
		
	}

}
