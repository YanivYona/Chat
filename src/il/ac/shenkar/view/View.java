package il.ac.shenkar.view;

import javax.swing.*;
import test.pack.Log4J;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;
import java.awt.Dimension;

public class View implements Runnable {

	private Controller controller;
	private JFrame frame_1;
	private JFrame frame_2;
	private JTable table;
	private JPanel panel;
	private JPanel panel_2;
	private JTextField textFrom;
	private JTextField textTo;
	private JList<String> listFrom;
	private JList<String> listTo;
	private JButton converter;
	private JButton calc;
	private JButton refresh;
	private JLabel amountLabel;
	private JLabel resultLabel;
	private JLabel fromLabel;
	private JLabel toLabel;
	String[] rates = { "Israel - NIS", "USA", "Great Britain", "Japan", "EMU", "Australia", "Canada", "Denmark",
			"Norway", "South Africa", "Sweden", "Switzerland", "Jordan", "Lebanon", "Egypt" };
	Dimension size;

	public void setTextTo(String t) {
		this.textTo.setText(t);
	}

	public void CreateTable(Vector<Currency> arr) { // initializing the table
		Object[] head = new Object[] { "", "", "", "", "", "" };
		Object[][] matrix = new Object[15][6];

		matrix[0][0] = "Name";
		matrix[0][1] = "Unit";
		matrix[0][2] = "CurrencyCode";
		matrix[0][3] = "Country";
		matrix[0][4] = "Rate";
		matrix[0][5] = "Change";

		for (int i = 1; i < 15; i++) {
			matrix[i][0] = arr.get(i - 1).getName();
			matrix[i][1] = Integer.toString(arr.get(i - 1).getUnit());
			matrix[i][2] = arr.get(i - 1).getCurrencyCode();
			matrix[i][3] = arr.get(i - 1).getCountry();
			matrix[i][4] = Double.toString(arr.get(i - 1).getRate());
			matrix[i][5] = Double.toString(arr.get(i - 1).getChange());
		}

		table = new JTable(matrix, head);
		Log4J.getInstance().info("The Table information has updated");
	}

	public View(Controller c, Vector<Currency> arr) { // view constructor

		frame_1 = new JFrame("Table");
		frame_2 = new JFrame("Currency converter");
		controller = c;
		CreateTable(arr);
		panel = new JPanel();
		panel_2 = new JPanel();
		textFrom = new JTextField(10);
		textTo = new JTextField(10);
		listFrom = new JList<String>(rates);
		listTo = new JList<String>(rates);
		converter = new JButton("Convert");
		refresh = new JButton("Refresh");
		calc = new JButton("calculate / hide");
		resultLabel = new JLabel("Result:");
		amountLabel = new JLabel("Amount:");
		fromLabel = new JLabel("From:");
		toLabel = new JLabel("To:");

	}

	@Override
	public void run() { // GUI thread
		frame_2.setContentPane(panel);
		frame_1.setContentPane(panel_2);
		panel.setLayout(null);
		Insets insets = panel.getInsets();
		frame_2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame_2.setSize(800, 600);
		frame_1.setSize(500, 600);
		frame_1.setLocation(770, 0);
		frame_2.setLocation(0, 0);

		size = fromLabel.getPreferredSize(); // positioning the components...
		fromLabel.setBounds(100 + insets.left, 30 + insets.top, size.width, size.height);
		size = listFrom.getPreferredSize();
		listFrom.setBounds(100 + insets.left, 60 + insets.top, 10 + size.width, size.height);

		size = toLabel.getPreferredSize();
		toLabel.setBounds(600 + insets.left, 30 + insets.top, size.width, size.height);
		size = listTo.getPreferredSize();
		listTo.setBounds(600 + insets.left, 60 + insets.top, 10 + size.width, size.height);

		textFrom.setText("1");
		size = textFrom.getPreferredSize();
		textFrom.setBounds(380 + insets.left, 30 + insets.top, size.width, size.height);
		size = amountLabel.getPreferredSize();
		amountLabel.setBounds(325 + insets.left, 30 + insets.top, size.width, size.height);

		size = textTo.getPreferredSize();
		textTo.setBounds(380 + insets.left, 300 + insets.top, size.width, size.height);
		size = resultLabel.getPreferredSize();
		resultLabel.setBounds(335 + insets.left, 300 + insets.top, size.width, size.height);
		textTo.setEditable(false);

		size = converter.getPreferredSize();
		converter.setBounds(100 + insets.left, 400 + insets.top, size.width, size.height);
		size = refresh.getPreferredSize();
		refresh.setBounds(190 + insets.left, 400 + insets.top, size.width, size.height);

		size = table.getPreferredSize();
		table.setBounds(150 + insets.left, 450 + insets.top, size.width, size.height);
		table.setEnabled(false);

		size = calc.getPreferredSize();
		calc.setBounds(150 + insets.left, 450 + insets.top, size.width, size.height);

		panel.add(amountLabel);
		panel.add(textFrom);
		panel.add(resultLabel);
		panel.add(textTo);
		panel.add(fromLabel);
		panel.add(listFrom);
		panel.add(toLabel);
		panel.add(listTo);
		panel.add(converter);
		panel.add(refresh);
		panel_2.add(table);
		panel_2.add(calc);
		frame_1.setVisible(true);

		converter.addActionListener(new ActionListener() { // the converter
			public void actionPerformed(ActionEvent arg0) {
				String from = listFrom.getSelectedValue();
				String to = listTo.getSelectedValue();

				try {
					if (textFrom.getText().isEmpty()) // if the text-box is
														// empty
					{
						Log4J.getInstance().error("empty word");
						throw new IOException("empty");
					}

					if (!textFrom.getText().matches("[0-9]+")) // if there is
																// not digit
					{
						Log4J.getInstance().error("not a digit");
						throw new IOException("not digit");
					}

					if (from == null || to == null) {
						Log4J.getInstance().error("non selected currency"); // if
																			// non
																			// selected
																			// currency
						throw new IOException("non currency");
					}

					double amount = Double.parseDouble(textFrom.getText());
					controller.calc(from, to, amount);

				} catch (IOException e) {
				}

			}
		});

		refresh.addActionListener(new ActionListener() { // the refresher button
			public void actionPerformed(ActionEvent arg0) {
				try {
					controller.getParsi().ParsingXM();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				CreateTable(controller.getParsi().getVec()); // create the new
																// refreshed
																// table
				Log4J.getInstance().info("refreshed");
			}

		});

		calc.addActionListener(new ActionListener() { // the actual calculation
			public void actionPerformed(ActionEvent arg0) {
				if (frame_2.isVisible() == true)
					frame_2.setVisible(false);
				else
					frame_2.setVisible(true);
			}
		});

	}

}
