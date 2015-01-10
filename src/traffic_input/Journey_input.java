package traffic_input;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import traffic_analyze.Journeytime;

public class Journey_input {
	public static ArrayList<Journeytime> allJourneytime;
	public static double tolong,tolat,fromlat,fromlong;
	public static String toroadname,fromroadname,toroadjuction,fromroadjuction,sectionid,direction;
	public static ArrayList<Journeytime> getJourneys(boolean online){
	//This creates an array of objects that use the roadworks class
	 allJourneytime = new ArrayList<Journeytime>();
	 try {
		 //This class will read the location of journey time data and then will collect the data
		 //From a different xml file that has the information on the time based of sector numbers 
		Document journeytimeloc_doc = readdata("Test-Data/Journeytime-loc.xml");
		Element journeytimeloc_all = journeytimeloc_doc.getDocumentElement();
		NodeList journeytimeloc_nl = journeytimeloc_all.getElementsByTagName("predefinedLocation");
		//Working with a new input system
		for(int i = 0; i < journeytimeloc_nl.getLength(); i = i + 2 )
		{
			
			Element idel = (Element)journeytimeloc_nl.item(i);
			sectionid = idel.getAttribute("id").substring(7);
			System.out.println("This is " + idel.getAttribute("id").substring(7));
			Element Direction = (Element)journeytimeloc_nl.item(i+1).getFirstChild();
			direction = gettextvalue(Direction,"tpegDirection");
			Node to = journeytimeloc_nl.item(i+1).getFirstChild().getChildNodes().item(2);
			Element toel = (Element) to;
			System.out.println(toel.getAttribute("xsi:type"));
			if(toel.getAttribute("xsi:type").equalsIgnoreCase("TPEGJunction"))
			{
				//
				Node points = to.getChildNodes().item(0);
				Element pointsto = (Element)points;
				tolat = Double.parseDouble(gettextvalue(pointsto,"latitude"));
				tolong = Double.parseDouble(gettextvalue(pointsto,"longitude"));
				Node Juction = to.getChildNodes().item(1).getChildNodes().item(0);
				Element Juctionto = (Element)Juction;
				toroadjuction = gettextvalue(Juctionto,"value");
				Node Road = to.getChildNodes().item(2).getChildNodes().item(0);
				Element Roadto = (Element)Road;		
				toroadname = gettextvalue(Roadto,"value");
				Node from = journeytimeloc_nl.item(i+1).getFirstChild().getChildNodes().item(3);
				Element fromel = (Element) from;
				if(fromel.getAttribute("xsi:type").equalsIgnoreCase("TPEGJunction"))
				{
					Node fpoints = from.getChildNodes().item(0);
					Element pointsfrom = (Element)fpoints;
					fromlat = Double.parseDouble(gettextvalue(pointsfrom,"latitude"));
					fromlong = Double.parseDouble(gettextvalue(pointsfrom,"longitude"));
					Node fJuction = from.getChildNodes().item(1).getChildNodes().item(0);
					Element Juctionfrom = (Element)fJuction;
					fromroadjuction = gettextvalue(Juctionfrom,"value");
					Node fRoad = from.getChildNodes().item(2).getChildNodes().item(0);
					Element Roadfrom = (Element)fRoad;		
					fromroadjuction = gettextvalue(Roadfrom,"value");
				}
			}else
			{
				Node from = journeytimeloc_nl.item(i+1).getFirstChild().getChildNodes().item(3);
				Element fromel = (Element) from;
				if(fromel.getAttribute("xsi:type").equalsIgnoreCase("TPEGJunction"))
				{
					Node fpoints = from.getChildNodes().item(0);
					Element pointsfrom = (Element)fpoints;
					fromlat = Double.parseDouble(gettextvalue(pointsfrom,"latitude"));
					fromlong = Double.parseDouble(gettextvalue(pointsfrom,"longitude"));
					Node fJuction = from.getChildNodes().item(1).getChildNodes().item(0);
					Element Juctionfrom = (Element)fJuction;
					fromroadjuction = gettextvalue(Juctionfrom,"value");
					Node fRoad = from.getChildNodes().item(2).getChildNodes().item(0);
					Element Roadfrom = (Element)fRoad;		
					fromroadjuction = gettextvalue(Roadfrom,"value");
				}
			}
			
			//This saves the journey info as a journey object then adds to the array 
			Journeytime temp = new Journeytime(tolat,fromlat,tolong,tolat,toroadname,toroadjuction,fromroadname,fromroadjuction,direction,sectionid);
			allJourneytime.add(temp);
		}
		
		System.out.println(allJourneytime.size());
		return allJourneytime;
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
	return allJourneytime;

	 
	 
	 
	 
	 
	 
	 
	 
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
