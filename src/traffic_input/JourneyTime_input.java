package traffic_input;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
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
	// This class is used to add the time to the journey infomation
	// This will get the information from the xml file and will also be able to
	// get
	// The data from online without needing to download a new Journey input
	public static ArrayList<Journeytime> allJourneytime;
	public static double travelTime, freeFlowTravelTime,
	normallyExpectedTravelTime;

	public static ArrayList<Journeytime> getJourneys(boolean online) {
		// Will call the journey_input to get an array of journey sections
		// Then will add the information to the journey input

		// This will see if the user is online and will pass the info on
		try {
			if (online == true) {
				allJourneytime = Journey_input.getJourneys(false);
				Document journeytimeloc_doc = readonlinedata("http://hatrafficinfo.dft.gov.uk/feeds/datex/England/JourneyTimeData/content.xml");
				Element journeytimeloc_all = journeytimeloc_doc
						.getDocumentElement();
				NodeList journeytimeloc_nl = journeytimeloc_all
						.getElementsByTagName("elaboratedData");
				System.out.println(journeytimeloc_nl.getLength());
				for (int i = 0; i < journeytimeloc_nl.getLength(); i++) {
					Element idel = (Element) journeytimeloc_nl.item(i);
					Node alldata = idel.getChildNodes().item(1);
					Element alldatael = (Element) alldata;
					// Goes thought the nodes to get to the time section
					travelTime = phasedouble(gettextvalue(alldatael,
							"travelTime"));
					freeFlowTravelTime = phasedouble(gettextvalue(alldatael,
							"freeFlowTravelTime"));
					normallyExpectedTravelTime = phasedouble(gettextvalue(
							alldatael, "normallyExpectedTravelTime"));
					for (int i2 = 0; i2 < allJourneytime.size(); i2++) {

						if (allJourneytime.get(i2).getid()
								.equals(idel.getAttribute("id").substring(13))) {
							allJourneytime.get(i2).setalltime(travelTime,
									freeFlowTravelTime,
									normallyExpectedTravelTime);
						}
					}
				}

				return allJourneytime;
			} else {
				allJourneytime = Journey_input.getJourneys(false);
				Document journeytimeloc_doc = readdata("Test-Data/Journeytime.xml");
				Element journeytimeloc_all = journeytimeloc_doc
						.getDocumentElement();
				NodeList journeytimeloc_nl = journeytimeloc_all
						.getElementsByTagName("elaboratedData");
				for (int i = 0; i < journeytimeloc_nl.getLength(); i++) {
					Element idel = (Element) journeytimeloc_nl.item(i);
					Node alldata = idel.getChildNodes().item(1);
					Element alldatael = (Element) alldata;
					// Goes thought the nodes to get to the time section
					travelTime = phasedouble(gettextvalue(alldatael,
							"travelTime"));
					freeFlowTravelTime = phasedouble(gettextvalue(alldatael,
							"freeFlowTravelTime"));
					normallyExpectedTravelTime = phasedouble(gettextvalue(
							alldatael, "normallyExpectedTravelTime"));
					for (int i2 = 0; i2 < allJourneytime.size(); i2++) {
						if (allJourneytime.get(i2).getid()
								.equals(idel.getAttribute("id").substring(13))) {
							allJourneytime.get(i2).setalltime(travelTime,
									freeFlowTravelTime,
									normallyExpectedTravelTime);
						}
					}

				}

				return allJourneytime;
			}
		} catch (ParserConfigurationException e) {
			// Catch Error
			e.printStackTrace();
		} catch (SAXException e) {
			// Catch Error
			e.printStackTrace();
		} catch (IOException e) {
			// Catch Error
			e.printStackTrace();
		}
		return null;
	}

	public static String gettextvalue(Element el, String tag) {
		// Will return a string based on the element and tag given.
		try {
			NodeList n = el.getElementsByTagName(tag);
			Element temp = (Element) n.item(0);
			return temp.getFirstChild().getNodeValue();
		} catch (Exception e) {
			return "error";
		}
	}

	public static double phasedouble(String input) {
		// This will deal with making sure that if there is an error string it
		// returns 0 instead of crashing the system.
		if (input.equalsIgnoreCase("error")) {
			return 0;
		} else {
			return Double.parseDouble(input);
		}

	}

	public static Document readdata(String filepath)
			throws ParserConfigurationException, SAXException, IOException {
		// This class will read the data from the file and return it in a way to
		// be used in other classes
		File file = new File(filepath);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		return db.parse(file);

	}

	public static Document readonlinedata(String inputurl)
			throws ParserConfigurationException, SAXException, IOException {
		// This class will read the data from the file and return it in a way to
		// be used in other classes
		URL url = new URL(inputurl);
		URLConnection connection = url.openConnection();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		return db.parse(connection.getInputStream());
	}
}
