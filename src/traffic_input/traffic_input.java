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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import traffic_analyze.Incident;

public class traffic_input {
	public static ArrayList<Incident> allincidents;

	private static int getdirectioncode(String text) {
		// This will return the code for the direction the incident is.
		if (text.toLowerCase().contains("northbound")) {
			return 1;
		} else if (text.toLowerCase().contains("eastbound")) {
			return 2;
		} else if (text.toLowerCase().contains("southbound")) {
			return 3;
		} else if (text.toLowerCase().contains("westbound")) {
			return 4;
		} else {
			return 0;
		}

	}

	public static String gettextvalue(Element el, String tag) {
		// Will return the value given from the tag inputed
		try {
			NodeList n = el.getElementsByTagName(tag);
			Element temp = (Element) n.item(0);
			return temp.getFirstChild().getNodeValue();
		} catch (Exception e) {
			return "error";
		}

	}

	public static ArrayList<Incident> gettraffic(boolean online) {
		// This class will be called at the start of the program
		if (online == false) {
			// Will work with the test data.
			try {
				// This will read all the traffic xml files

				// This creates an array of objects that use the roadwork class
				allincidents = new ArrayList<Incident>();

				// Scotland Current Incidents
				Document currentincidents_sco_doc = readdata("Test-Data/currentincidents.xml");
				Element currentincidents_sco_all = currentincidents_sco_doc
						.getDocumentElement();
				NodeList currentincidents_sco_nl = currentincidents_sco_all
						.getElementsByTagName("item");
				for (int i = 0; i < currentincidents_sco_nl.getLength(); i++) {

					Element el = (Element) currentincidents_sco_nl.item(i);
					// Gets title
					String[] cords = gettextvalue(el, "georss:point")
							.split(" ");
					double lat = Double.parseDouble(cords[0]);
					double longitude = Double.parseDouble(cords[1]);
					String roadname = gettextvalue(el, "title").split(" ")[0];
					Incident temp = new Incident(gettextvalue(el, "title"),
							gettextvalue(el, "description"), lat, longitude,
							"Incident",
							getdirectioncode(gettextvalue(el, "title")
									+ gettextvalue(el, "description")),
							roadname);
					allincidents.add(temp);
				}

				// Scotland roadworks
				Document roadworks_sco_doc = readdata("Test-Data/roadworks.xml");
				Element roadworks_sco_all = roadworks_sco_doc
						.getDocumentElement();
				NodeList roadworks_sco_nl = roadworks_sco_all
						.getElementsByTagName("item");
				for (int i = 0; i < roadworks_sco_nl.getLength(); i++) {
					Element el = (Element) roadworks_sco_nl.item(i);
					// Gets title
					String[] cords = gettextvalue(el, "georss:point")
							.split(" ");
					double lat = Double.parseDouble(cords[0]);
					double longitude = Double.parseDouble(cords[1]);
					String roadname = gettextvalue(el, "title").split(" ")[0];
					Incident temp = new Incident(gettextvalue(el, "title"),
							gettextvalue(el, "description"), lat, longitude,
							"Roadworks",
							getdirectioncode(gettextvalue(el, "title")
									+ gettextvalue(el, "description")),
							roadname);
					allincidents.add(temp);
				}

				// England current Incident
				Document roadworks_elg_doc = readdata("Test-Data/CurrentAndFutureEvents.xml");
				Element roadworks_elg_all = roadworks_elg_doc
						.getDocumentElement();
				NodeList roadworks_elg_nl = roadworks_elg_all
						.getElementsByTagName("item");
				for (int i = 0; i < roadworks_elg_nl.getLength(); i++) {
					Element el = (Element) roadworks_elg_nl.item(i);
					// Getting the road
					String roadname = gettextvalue(el, "road");
					// Gets title
					double lat = Double
							.parseDouble(gettextvalue(el, "latitude"));
					double longitude = Double.parseDouble(gettextvalue(el,
							"longitude"));
					Incident temp = new Incident(gettextvalue(el, "title"),
							gettextvalue(el, "description"), lat, longitude,
							"Incident",
							getdirectioncode(gettextvalue(el, "title")
									+ gettextvalue(el, "description")),
							roadname);
					allincidents.add(temp);
				}

			} catch (ParserConfigurationException e) {
				// Catch Errors
				e.printStackTrace();
			} catch (SAXException e) {
				// Catch Errors
				e.printStackTrace();
			} catch (IOException e) {
				// Catch Errors
				e.printStackTrace();
			}

			return allincidents;
		} else {
			// Will work with the online data.
			// This will read all the traffic xml files
			try {
				// This creates an array of objects that use the roadwork class
				allincidents = new ArrayList<Incident>();

				// Scotland Current Incidents
				Document currentincidents_sco_doc = readonlinedata("https://trafficscotland.org/rss/feeds/currentincidents.aspx");
				Element currentincidents_sco_all = currentincidents_sco_doc
						.getDocumentElement();
				NodeList currentincidents_sco_nl = currentincidents_sco_all
						.getElementsByTagName("item");
				for (int i = 0; i < currentincidents_sco_nl.getLength(); i++) {

					Element el = (Element) currentincidents_sco_nl.item(i);
					// Gets title
					String[] cords = gettextvalue(el, "georss:point")
							.split(" ");
					double lat = Double.parseDouble(cords[0]);
					double longitude = Double.parseDouble(cords[1]);
					String roadname = gettextvalue(el, "title").split(" ")[0];
					Incident temp = new Incident(gettextvalue(el, "title"),
							gettextvalue(el, "description"), lat, longitude,
							"Incident",
							getdirectioncode(gettextvalue(el, "title")
									+ gettextvalue(el, "description")),
							roadname);
					allincidents.add(temp);
				}

				// Scotland roadworks
				Document roadworks_sco_doc = readonlinedata("https://trafficscotland.org/rss/feeds/roadworks.aspx");
				Element roadworks_sco_all = roadworks_sco_doc
						.getDocumentElement();
				NodeList roadworks_sco_nl = roadworks_sco_all
						.getElementsByTagName("item");
				for (int i = 0; i < roadworks_sco_nl.getLength(); i++) {
					Element el = (Element) roadworks_sco_nl.item(i);
					// Gets title
					String[] cords = gettextvalue(el, "georss:point")
							.split(" ");
					double lat = Double.parseDouble(cords[0]);
					double longitude = Double.parseDouble(cords[1]);
					String roadname = gettextvalue(el, "title").split(" ")[0];
					Incident temp = new Incident(gettextvalue(el, "title"),
							gettextvalue(el, "description"), lat, longitude,
							"Roadworks",
							getdirectioncode(gettextvalue(el, "title")
									+ gettextvalue(el, "description")),
							roadname);
					allincidents.add(temp);
				}

				// England current Incident
				Document roadworks_elg_doc = readonlinedata("http://hatrafficinfo.dft.gov.uk/feeds/rss/CurrentAndFutureEvents.xml");
				Element roadworks_elg_all = roadworks_elg_doc
						.getDocumentElement();
				NodeList roadworks_elg_nl = roadworks_elg_all
						.getElementsByTagName("item");
				for (int i = 0; i < roadworks_elg_nl.getLength(); i++) {
					Element el = (Element) roadworks_elg_nl.item(i);
					// Getting the road
					String roadname = gettextvalue(el, "road");
					// Gets title
					double lat = Double
							.parseDouble(gettextvalue(el, "latitude"));
					double longitude = Double.parseDouble(gettextvalue(el,
							"longitude"));
					Incident temp = new Incident(gettextvalue(el, "title"),
							gettextvalue(el, "description"), lat, longitude,
							"Incident",
							getdirectioncode(gettextvalue(el, "title")
									+ gettextvalue(el, "description")),
							roadname);
					allincidents.add(temp);
				}

			} catch (ParserConfigurationException e) {
				// Catch Errors
				e.printStackTrace();
			} catch (SAXException e) {
				// Catch Errors
				e.printStackTrace();
			} catch (IOException e) {
				// Catch Errors
				e.printStackTrace();
			}

			return allincidents;
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
