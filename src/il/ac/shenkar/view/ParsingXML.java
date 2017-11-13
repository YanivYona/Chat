package il.ac.shenkar.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.NodeList;

import test.pack.Log4J;

public class ParsingXML {

	private Vector<Currency> vec;

	public Vector<Currency> getVec() {
		return vec;
	}

	public ParsingXML() { // c'tor
		vec = new Vector<Currency>();
	}

	public void ParsingXM() throws Exception { // function that parsing the XML
												// file
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		org.w3c.dom.Document doc = null;
		vec.clear();

		URL website = null;
		try {
			website = new URL("http://www.boi.org.il/currency.xml");
		} catch (MalformedURLException e) {
			Log4J.getInstance().error("MalformedURL error");
			e.printStackTrace();
		}
		ReadableByteChannel rbc = null;
		try {
			rbc = Channels.newChannel(website.openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("information.xml");
		} catch (FileNotFoundException e) {
			Log4J.getInstance().error("FileNotFound error");
			e.printStackTrace();
		}
		try {
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE); // copy to
																	// local
																	// file
		} catch (IOException e) {
			e.printStackTrace();
		}

		File fXmlFile = new File("information.xml"); // creating new file
		factory = DocumentBuilderFactory.newInstance();
		builder = factory.newDocumentBuilder();
		doc = builder.parse(fXmlFile);
		NodeList list = ((org.w3c.dom.Document) doc).getElementsByTagName("CURRENCY");
		int length = list.getLength();

		for (int i = 0; i < length; i++) { // insert to "vec" item by item
			NodeList innerNodes = list.item(i).getChildNodes();
			String name = innerNodes.item(1).getTextContent();
			int unit = Integer.parseInt(innerNodes.item(3).getTextContent());
			String currencyCode = innerNodes.item(5).getTextContent();
			String country = innerNodes.item(7).getTextContent();
			double rate = Double.parseDouble(innerNodes.item(9).getTextContent());
			double change = Double.parseDouble(innerNodes.item(11).getTextContent());
			vec.add(new Currency(name, currencyCode, country, unit, rate, change));

		}
		Log4J.getInstance().info("The XML has parsed");

	}
}
