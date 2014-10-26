package traffic_input;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import traffic_analyze.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
public class traffic_input {
	public static ArrayList<Incident> allincidents;
	
	 public static void gettraffic(boolean test){
		//This class will be called at the start of the program 
		if(test == true)
		{
			//Will work with the test data.
			try {
				//This will read all the traffic xml files
				
				//This creates an array of objects that use the roadwork class
				ArrayList<Incident> allroadworks = new ArrayList<Incident>();
				allincidents= new ArrayList<Incident>();
				
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
					Incident temp = new Incident(gettextvalue(el,"title"),gettextvalue(el,"description"),lat,longitude,"Incident"); 
					allincidents.add(temp);
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
					Incident temp = new Incident(gettextvalue(el,"title"),gettextvalue(el,"description"),lat,longitude,"Roadworks");
					allroadworks.add(temp);
				}
				
				//England current Incident
				System.out.println();
				System.out.println("_________________");
				System.out.println("	Incident    ");
				System.out.println("-----------------");
				Document roadworks_elg_doc = readdata("Test-Data/CurrentAndFutureEvents.xml");
				Element roadworks_elg_all = roadworks_elg_doc.getDocumentElement();
				NodeList roadworks_elg_nl = roadworks_elg_all.getElementsByTagName("item");
				for (int i = 0; i < roadworks_elg_nl.getLength();i++)
				{
					Element el = (Element)roadworks_elg_nl.item(i);
					//Gets title
					System.out.println();
					System.out.println(gettextvalue(el,"title"));
					System.out.println(gettextvalue(el,"description"));
					double lat = Double.parseDouble(gettextvalue(el,"latitude"));
					double longitude = Double.parseDouble(gettextvalue(el,"longitude"));
					System.out.println("Lat: " + String.valueOf(lat) + "  Long" + String.valueOf(longitude));
					Incident temp = new Incident(gettextvalue(el,"title"),gettextvalue(el,"description"),lat,longitude,"Incident");
					allincidents.add(temp);
				}
				
				
				
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
