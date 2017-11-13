package il.ac.shenkar.view;

import test.pack.Log4J;

public class Controller {
	private View UI;
	private ParsingXML parsi;

	public Controller() { // kind of "proxy" that connect between the GUI and
							// the parser
		parsi = new ParsingXML(); // parsing the XML

		try {
			parsi.ParsingXM();
		} catch (Exception e) {
			e.printStackTrace();
		}

		UI = new View(this, parsi.getVec()); // creates the GUI
	}

	public ParsingXML getParsi() {
		return parsi;
	}

	public void calc(String from, String to, double amount) { // converting
																// between
																// currencies
		double result = 0;

		if (from != "Israel - NIS") { // "Shekel" case
			for (Currency cur : parsi.getVec()) {
				if (cur.getCountry().equals(from)) {
					double d = (double) cur.getUnit();
					result = (cur.getRate() / d) * amount;
					break;
				}
			}
		} else
			result = amount;

		if (to != "Israel - NIS") {

			for (Currency cur : parsi.getVec()) {
				if (cur.getCountry().equals(to)) {
					double d = (double) cur.getUnit();
					result /= (cur.getRate() / d);
					break;
				}
			}
		}

		UI.setTextTo(Double.toString(result));
		Log4J.getInstance().info(amount + " units of " + from + " = " + result + " units of " + to);
	}

	public static void main(String args[]) { // main func'

		Controller proxy = new Controller();
		Thread t1 = new Thread(proxy.UI);
		t1.start();

	}

}
