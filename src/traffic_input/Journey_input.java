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
	//This creates an array of objects that use the roadworks class
	 allJourneytime = new ArrayList<Journeytime>();
	 try {
		 //This class will read the location of journey time data and then will collect the data
		 //From a different xml file that has the information on the time based of sector numbers 
		Document journeytimeloc_doc = readdata("Test-Data/Journeytime-loc.xml");
		Element journeytimeloc_all = journeytimeloc_doc.getDocumentElement();
		NodeList journeytimeloc_nl = journeytimeloc_all.getElementsByTagName("predefinedLocation");
		NodeList journeytimeloc_to = journeytimeloc_all.getElementsByTagName("to");
		NodeList journeytimeloc_from = journeytimeloc_all.getElementsByTagName("from");
		NodeList journeytimeloc_to_desc = journeytimeloc_all.getElementsByTagName("descriptor");
		
		
		int count = -1;	
		int idcount = 0;
		for (int i = 1; i < journeytimeloc_nl.getLength();i++)
		{
				int itempos = i + count;
				//Header gets the string
				Element el = (Element)journeytimeloc_nl.item(idcount);
				if(el.hasAttribute("id"))
				{
					idcount = idcount + 2;
				String section = el.getAttribute("id").substring(7);
				System.out.println(section);
				}
		
				Element pointsto = (Element) journeytimeloc_to.item(i);
				System.out.println("To Info");
				System.out.println(gettextvalue(pointsto,"latitude"));
				System.out.println(gettextvalue(pointsto,"longitude"));
				Element juctionto = (Element) journeytimeloc_to_desc.item(itempos);
				System.out.println(gettextvalue(juctionto,"value"));
				Element roadonto = (Element) journeytimeloc_to_desc.item(itempos+1);
				System.out.println(gettextvalue(roadonto,"value"));
				Element roadcomingto = (Element) journeytimeloc_to_desc.item(itempos+2);
				System.out.println(gettextvalue(roadcomingto,"value"));
				Element pointsfrom = (Element) journeytimeloc_from.item(i);
				System.out.println("From Info");
				System.out.println(gettextvalue(pointsfrom,"latitude"));
				System.out.println(gettextvalue(pointsfrom,"longitude"));
				Element juctionfrom = (Element) journeytimeloc_to_desc.item(itempos+3);
				System.out.println(gettextvalue(juctionfrom,"value"));
				Element roadonfrom = (Element) journeytimeloc_to_desc.item(itempos+4);
				System.out.println(gettextvalue(roadonfrom,"value"));
				Element roadcomingfrom = (Element) journeytimeloc_to_desc.item(itempos+5);
				System.out.println(gettextvalue(roadcomingfrom,"value"));
				count = count + 5;
				System.out.println("");
			
			
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
		 try{
		NodeList n = el.getElementsByTagName(tag);
		Element temp = (Element)n.item(0);
		return temp.getFirstChild().getNodeValue();
		 }
		 catch(Exception e)
		 {
			 return "error";
		 }
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
