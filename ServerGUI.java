package lab3out;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import ocsf.server.*;
import ocsf.client.*;

public class ServerGUI extends JFrame {
	
	// Data Fields
	private ChatServer server;
	private JPanel north;
	private JPanel center;
	private JPanel south;
	private JLabel status; //Initialized to “Not Connected”
	private JLabel portNum;
	private JLabel timeout;
	private JLabel serverLog;
	private String[] labels = {"Port #", "Timeout"};
	private JTextField[] textFields = new JTextField[labels.length];
	private JTextArea log;
	private JScrollPane logScroll;
	private JButton listen;
	private JButton close;
	private JButton stop;
	private JButton quit;
	
	// Methods
	
	// ServerGUI Constructor
	public ServerGUI(String title) {
		int i = 0;
		
		this.setTitle(title);
		this.setSize(650,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		// setters for private data entries
		this.server = new ChatServer();
		this.north = new JPanel();
		this.center = new JPanel();
		this.south = new JPanel();
		this.status = new JLabel();
		this.portNum = new JLabel();
		this.timeout = new JLabel();
		this.serverLog = new JLabel();
		JTextField textField = new JTextField();
		this.textFields[0] = textField;
		JTextField textField_1 = new JTextField();
		this.textFields[1] = textField_1;
		this.listen = new JButton();
		this.close = new JButton();
		this.stop = new JButton();
		this.quit = new JButton();
		
		// sets the text for the labels
		this.portNum.setText("Port #");
		this.timeout.setText("Timeout");
		this.serverLog.setText("Server Log Below");
		
		// set up the scroll panel
		this.log = new JTextArea(10,40);
		this.log.setEditable(false);
		this.logScroll = new JScrollPane(log);
		this.logScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		// Layout stuff to make it look like the example in the doc (made using window builder)
		SpringLayout sl_center = new SpringLayout();
		sl_center.putConstraint(SpringLayout.SOUTH, serverLog, -250, SpringLayout.SOUTH, center);
		sl_center.putConstraint(SpringLayout.NORTH, logScroll, 6, SpringLayout.SOUTH, serverLog);
		sl_center.putConstraint(SpringLayout.WEST, logScroll, 141, SpringLayout.WEST, center);
		sl_center.putConstraint(SpringLayout.WEST, serverLog, 258, SpringLayout.WEST, center);
		sl_center.putConstraint(SpringLayout.EAST, textField_1, -322, SpringLayout.EAST, center);
		sl_center.putConstraint(SpringLayout.NORTH, textField_1, 6, SpringLayout.SOUTH, textField);
		sl_center.putConstraint(SpringLayout.WEST, textField_1, 6, SpringLayout.EAST, timeout);
		sl_center.putConstraint(SpringLayout.EAST, textField, 108, SpringLayout.EAST, portNum);
		sl_center.putConstraint(SpringLayout.NORTH, timeout, 12, SpringLayout.SOUTH, portNum);
		sl_center.putConstraint(SpringLayout.EAST, timeout, 0, SpringLayout.EAST, portNum);
		sl_center.putConstraint(SpringLayout.NORTH, textField, -3, SpringLayout.NORTH, portNum);
		sl_center.putConstraint(SpringLayout.WEST, textField, 6, SpringLayout.EAST, portNum);
		sl_center.putConstraint(SpringLayout.NORTH, portNum, 10, SpringLayout.NORTH, center);
		sl_center.putConstraint(SpringLayout.WEST, portNum, 227, SpringLayout.WEST, center);
		center.setLayout(sl_center);
		
		// adds components to the panel
		this.center.add(portNum);
		this.center.add(textFields[0]);
		this.center.add(timeout);
		this.center.add(textFields[1]);
		this.center.add(serverLog);
		this.center.add(logScroll);
		getContentPane().add(center,BorderLayout.CENTER);
		
		// sets the text for the status label
		this.status.setText("<html>Status: <font color=red>Not Connected</font></html>");
		
	
		// set north panel layout and add it to the frame
		this.north.setLayout(new FlowLayout());
		this.north.add(status);
		getContentPane().add(north, BorderLayout.NORTH);
		
		
		// set the text on the buttons
		this.listen.setText("Listen");
		this.close.setText("Close");
		this.stop.setText("Stop");
		this.quit.setText("Quit");
		
		// set south panel layout and add it to the frame
		this.south.setLayout(new FlowLayout());
		this.south.add(listen);
		this.south.add(close);
		this.south.add(stop);
		this.south.add(quit);
		getContentPane().add(south, BorderLayout.SOUTH);
		
		EventHandler listener = new EventHandler();
		// add listeners to the buttons
		this.listen.addActionListener(listener);
		this.close.addActionListener(listener);
		this.stop.addActionListener(listener);
		this.quit.addActionListener(listener);
		
		
		//setters for chatserver
		this.server.setLog(log);
		this.server.setStatus(status);
	}
	
	public static void main(String args[]) {
		new ServerGUI(args[0]); //args[0] is the title for the serverGUI
	}

	
	// Event Handler Class that implements functionality for buttons
	class EventHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			String ButtonText = e.getActionCommand();
			if(ButtonText.toLowerCase().equals("listen")) {
				// check to see if port num and timeout fields have been filled
				if(textFields[0].getText().equals("")) {
					log.append("Port Number Field needs to be filled\n");
					server.setLog(log);
				}
				else if(textFields[1].getText().equals("")) {
					log.append("Timeout Field needs to be filled\n");
					server.setLog(log);
				}
				else {
					// start listen method from chatserver
					try {
						String portText = textFields[0].getText();
						String timeText = textFields[1].getText();
						int port = Integer.parseInt(portText);
						int time = Integer.parseInt(timeText);
						server.setPort(port);
						server.setTimeout(time);
						server.listen();
					} catch (IOException ioe) {
						// TODO Auto-generated catch block
						ioe.printStackTrace();
					}
				}
			}
			else if(ButtonText.toLowerCase().equals("close")) {
				if(server.isListening()) {
					try {
						server.close();
					} catch (IOException ioe1) {
						// TODO Auto-generated catch block
						ioe1.printStackTrace();
					}
				}
				else {
					log.append("Server Not currently started\n");
				}
			}
			else if(ButtonText.toLowerCase().equals("stop")) {
				if(server.isListening()){
					server.stopListening();
				}
				else {
					log.append("Server Not currently started\n");
				}
			}
			else if(ButtonText.toLowerCase().equals("quit")) {
				ServerGUI.super.dispose();
			}
		}
	}

}
