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

public class JourneyTime_input {
	//This class is used to add the time to the journey infomation 
	//This will get the information from the xml file and will also be able to get
	//The data from online without needing to download a new Journey input
	public static ArrayList<Journeytime> allJourneytime;
	public static double travelTime,freeFlowTravelTime,normallyExpectedTravelTime;
	public static ArrayList<Journeytime> getJourneys(boolean online) {
		//Will call the journey_input to get an array of journey sections
		//Then will add the information to the journey input 
		
		//This will see if the user is online and will pass the info on 
		try
		{
			if(online == true)
			{
				allJourneytime = Journey_input.getJourneys(true);
				Document journeytimeloc_doc = readdata("Test-Data/Journeytime.xml");
				Element journeytimeloc_all = journeytimeloc_doc.getDocumentElement();
				NodeList journeytimeloc_nl = journeytimeloc_all.getElementsByTagName("elaboratedData");
				for(int i = 0; i < journeytimeloc_nl.getLength(); i++)
				{
					Element idel = (Element)journeytimeloc_nl.item(i);	
					System.out.println(idel.getAttribute("id").substring(13));
					Node alldata = idel.getChildNodes().item(1);
					Element alldatael = (Element) alldata;
					System.out.println("Travel time: " + gettextvalue(alldatael,"travelTime"));
					System.out.println("Freeflow time: " + gettextvalue(alldatael,"freeFlowTravelTime"));
					System.out.println("Normally Travel Time: " + gettextvalue(alldatael,"normallyExpectedTravelTime"));
					System.out.println("   ");
				}

				 getroads(allJourneytime);
				 return allJourneytime;
			}else
			{
				allJourneytime = Journey_input.getJourneys(false);
				Document journeytimeloc_doc = readdata("Test-Data/Journeytime.xml");
				Element journeytimeloc_all = journeytimeloc_doc.getDocumentElement();
				NodeList journeytimeloc_nl = journeytimeloc_all.getElementsByTagName("elaboratedData");
				for(int i = 0; i < journeytimeloc_nl.getLength(); i++)
				{
					Element idel = (Element)journeytimeloc_nl.item(i);	
					Node alldata = idel.getChildNodes().item(1);
					Element alldatael = (Element) alldata;
					
					travelTime = phasedouble(gettextvalue(alldatael,"travelTime"));
					freeFlowTravelTime = phasedouble(gettextvalue(alldatael,"freeFlowTravelTime"));
					normallyExpectedTravelTime = phasedouble(gettextvalue(alldatael,"normallyExpectedTravelTime"));
					for(int i2 = 0; i2 < allJourneytime.size(); i2++ )
					{
						if(allJourneytime.get(i2).getid().equals(idel.getAttribute("id").substring(13)))
						{
							allJourneytime.get(i2).setalltime(travelTime, freeFlowTravelTime, normallyExpectedTravelTime);
						}
					}
					
					
				}
				
				return allJourneytime;
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
		return null;
	}
	public static void getroads(ArrayList<Journeytime> Journeys)
	{
		//This will collect the journey times on the spades.
		ArrayList<String> roads = new ArrayList<String>();
		for(int i = 0; i < Journeys.size(); i++)
		{
			for(int i2 = i+1;  i2 < Journeys.size(); i2++)
			{
				if(Journeys.get(i).getroad().equals(Journeys.get(i2).getroad()) & i != i2)
				{
					
					
					if(checkifroadadded(roads,Journeys.get(i).getroad()) == false)
					{
						//Adding Road 
						roads.add(Journeys.get(i).getroad());

						System.out.println("Road Added:" + Journeys.get(i).getroad());
					}
					
				}
			}
		}
		
		
	}
	public static boolean checkifroadadded(ArrayList<String>Road, String Roadname)
	{
		for(int i = 0; i < Road.size(); i++)
		{
			if(Road.get(i).equalsIgnoreCase(Roadname))
			{
				return true;
			}
		}
		return false;
		
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
	 public static double phasedouble(String input)
	 {
		 if(input.equalsIgnoreCase("error"))
		 {
			 return 0;
		 }
		 else
		 {
			 return Double.parseDouble(input);
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
