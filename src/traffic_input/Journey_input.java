package traffic_input;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import traffic_analyze.Journeytime;

public class Journey_input {
	public static ArrayList<Journeytime> allJourneytime;
	public static void getJourneys(boolean test){
	//This creates an array of objects that use the roadwork class
	 allJourneytime = new ArrayList<Journeytime>();
	 try {
	 //Now will collect the infomation from the xml file
	 //Scotland Current Incidents
		Document journeytimeloc_doc = readdata("Test-Data/Journeytime-loc.xml");
		Element journeytimeloc_all = journeytimeloc_doc.getDocumentElement();
		NodeList journeytimeloc_nl = journeytimeloc_all.getElementsByTagName("predefinedLocation");
		NodeList journeytimeloc_to = journeytimeloc_all.getElementsByTagName("to");
		NodeList journeytimeloc_from = journeytimeloc_all.getElementsByTagName("from");
		NodeList journeytimeloc_to_desc = journeytimeloc_all.getElementsByTagName("descriptor");
		
		
		int count = 0;
		for (int i = 0; i < journeytimeloc_nl.getLength();i++)
		{
			count = count + 1;
			if(count == 1)
			{
				//Header
				Element el = (Element)journeytimeloc_nl.item(i);
				String section = el.getAttribute("id").substring(7);
				
			}else
			{
				//Body
				count = 0;
				Element el = (Element)journeytimeloc_nl.item(i);
				String direction = gettextvalue(el,"tpegDirection");
				System.out.println(direction);
				
				Element pointsto = (Element) journeytimeloc_to.item(i/2);
				System.out.println(gettextvalue(pointsto,"latitude"));
				System.out.println(gettextvalue(pointsto,"longitude"));
			
				
				Element juction_el_to_1 = (Element) journeytimeloc_to_desc.item(i/2);
				System.out.println(gettextvalue(juction_el_to_1,"value"));
				
				
				Element pointsfrom = (Element) journeytimeloc_from.item(i/2);
				System.out.println(gettextvalue(pointsfrom,"latitude"));
				System.out.println(gettextvalue(pointsfrom,"longitude"));
				
				
			}
			
			//Gets title
			//String[] cords = gettextvalue(el,"georss:point").split(" ");
			//double lat = Double.parseDouble(cords[0]);
			//double longitude = Double.parseDouble(cords[1]);
			//Journeytime temp = new Journeytime(gettextvalue(el,"title"),gettextvalue(el,"description"),lat,longitude,"Incident"); 
			//allJourneytime.add(temp);
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
		//<predefinedLocation id="Section11117">

	 
	 
	 
	 
	 
	 
	 
	 
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
