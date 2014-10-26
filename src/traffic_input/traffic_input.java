package traffic_input;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
public class traffic_input {
	 public static void gettraffic(boolean test){
		//This class will be called at the start of the program 
		if(test == true)
		{
			//Will work with the test data.
			try {
				//This will read all the traffic xml files
				
				//Scotland Current Incidents
				Document currentincidents_sco_doc = readdata("Test-Data/currentincidents.xml");
				Element currentincidents_sco_all = currentincidents_sco_doc.getDocumentElement();
				NodeList currentincidents_sco_nl = currentincidents_sco_all.getElementsByTagName("item");
				for (int i = 0; i < currentincidents_sco_nl.getLength();i++)
				{
					Element el = (Element)currentincidents_sco_nl.item(i);
					//Gets title
					System.out.println(gettextvalue(el,"title"));
					System.out.println(gettextvalue(el,"description"));
					String[] cords = gettextvalue(el,"georss:point").split(" ");
					double lat = Double.parseDouble(cords[0]);
					double longitude = Double.parseDouble(cords[1]);
					System.out.println("Lat: " + String.valueOf(lat) + "  Long" + String.valueOf(longitude));
					
				}
						
				//Scotland roadworks
				System.out.println();
				System.out.println("_________________");
				System.out.println("	ROADWORKS    ");
				System.out.println("-----------------");
				Document roadworks_sco_doc = readdata("Test-Data/roadworks.xml");
				Element roadworks_sco_all = roadworks_sco_doc.getDocumentElement();
				NodeList roadworks_sco_nl = roadworks_sco_all.getElementsByTagName("item");
				for (int i = 0; i < roadworks_sco_nl.getLength();i++)
				{
					Element el = (Element)roadworks_sco_nl.item(i);
					//Gets title
					System.out.println();
					System.out.println(gettextvalue(el,"title"));
					System.out.println(gettextvalue(el,"description"));
					String[] cords = gettextvalue(el,"georss:point").split(" ");
					double lat = Double.parseDouble(cords[0]);
					double longitude = Double.parseDouble(cords[1]);
					System.out.println("Lat: " + String.valueOf(lat) + "  Long" + String.valueOf(longitude));
					
					System.out.println();
				}
				
				
				
				/*System.out.println(roadworks_sco_doc.getNodeValue());
				System.out.println(roadworks_sco_doc.getDocumentElement().getNodeName());
				Element roadworks_sco = roadworks_sco_doc.getDocumentElement();
				NodeList sco_roadworkslist = roadworks_sco.getChildNodes();
				System.out.println(sco_roadworkslist.getLength());
				Element roadworks_sco1 = (Element) sco_roadworkslist;
				System.out.println(roadworks_sco1.getFirstChild().getAttributes().toString());
				System.out.println(roadworks_sco.getElementsByTagName("item"));
				System.out.println(roadworks_sco.getAttribute("title").toString());*/
				
				
				
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
		{
			//Will work with the online data.
		}
	 }
	 public static String gettextvalue(Element el , String tag)
	 {

		NodeList n = el.getElementsByTagName(tag);
		Element temp = (Element)n.item(0);
		return temp.getFirstChild().getNodeValue();
		 
	 }
	 
	 public static Document readdata(String filepath) throws ParserConfigurationException, SAXException, IOException
	 {
		 //This class will read the data from the file and return it in a way to be used in other classes
		 File file = new File(filepath);
		 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		 DocumentBuilder db = dbf.newDocumentBuilder();
		return db.parse(file);
		 
	 }
}
