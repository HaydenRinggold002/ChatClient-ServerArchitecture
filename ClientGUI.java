package lab3out;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

public class ClientGUI extends JFrame {
	
	//private data fields
	private ChatClient client;
	private JPanel north;
	private JPanel center;
	private JPanel south;
	private JLabel status;	//From Lab 1 In
	private JLabel clientID;
	private JLabel serverURL;
	private JLabel serverPort;
	private JLabel clientData;
	private JLabel serverData;
	private JButton connect;	//From Lab 1 In
	private JButton submit;	//From Lab 1 In
	private JButton stop;	//From Lab 1 In
	private String[] labels = {"Client ID", "Server URL", "Server Port"};
	private JTextField[] textFields = new JTextField[labels.length];
	private JTextArea clientArea;
	private JTextArea serverArea;
	private JScrollPane clientScroll;
	private JScrollPane serverScroll;
	
	public ClientGUI(String title) {
		int i = 0;
		
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//My Code starts here
		this.setSize(400,500);
		this.setVisible(true);
		
		// setters for private data entries
		this.north = new JPanel();
		this.center = new JPanel();
		this.south = new JPanel();
		this.status = new JLabel();
		this.clientID = new JLabel();
		this.serverURL = new JLabel();
		this.serverPort = new JLabel();
		this.clientData = new JLabel();
		this.serverData = new JLabel();
		this.connect = new JButton();
		this.submit = new JButton();
		this.stop = new JButton();
		this.clientArea = new JTextArea(7,22);
		this.serverArea = new JTextArea(7, 22);
		this.clientScroll = new JScrollPane(clientArea);
		this.serverScroll = new JScrollPane(serverArea);
		JTextField textField = new JTextField(12);
		this.textFields[0] = textField;
		JTextField textField_1 = new JTextField(12);
		this.textFields[1] = textField_1;
		JTextField textField_2 = new JTextField(12);
		this.textFields[2] = textField_2;
		
		// sets the text for the status label
		this.status.setText("<html>Status: <font color=red>not connected</font></html>");
		
		// sets the text for the buttons
		this.connect.setText("Connect");
		this.submit.setText("Submit");
		this.stop.setText("Stop");
		
		// sets the text for the center labels
		this.clientID.setText(labels[0]);
		this.serverURL.setText(labels[1]);
		this.serverPort.setText(labels[2]);
		this.clientData.setText("Enter Client Data Below");
		this.serverData.setText("Received Server Data");
		
		//makes clientID textField and serverArea textArea uneditable, adds scroll bars
		this.textFields[0].setEditable(false);
		this.serverArea.setEditable(false);
		// sets the scroll bars to always be active
		this.clientScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.serverScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		// layout stuff to make it look like the example in the doc (made with window builder)
		SpringLayout sl_center = new SpringLayout();
		sl_center.putConstraint(SpringLayout.NORTH, clientScroll, -134, SpringLayout.SOUTH, serverData);
		sl_center.putConstraint(SpringLayout.EAST, clientScroll, -82, SpringLayout.EAST, center);
		sl_center.putConstraint(SpringLayout.NORTH, serverScroll, 4, SpringLayout.SOUTH, serverData);
		sl_center.putConstraint(SpringLayout.EAST, serverScroll, -82, SpringLayout.EAST, center);
		sl_center.putConstraint(SpringLayout.SOUTH, clientID, -377, SpringLayout.SOUTH, center);
		sl_center.putConstraint(SpringLayout.NORTH, textField_1, 33, SpringLayout.NORTH, center);
		sl_center.putConstraint(SpringLayout.WEST, textField, 171, SpringLayout.WEST, center);
		sl_center.putConstraint(SpringLayout.EAST, clientID, -6, SpringLayout.WEST, textField);
		sl_center.putConstraint(SpringLayout.NORTH, serverURL, 3, SpringLayout.NORTH, textField_1);
		sl_center.putConstraint(SpringLayout.EAST, serverURL, 0, SpringLayout.EAST, clientID);
		sl_center.putConstraint(SpringLayout.NORTH, clientData, 83, SpringLayout.NORTH, center);
		sl_center.putConstraint(SpringLayout.NORTH, serverPort, 3, SpringLayout.NORTH, textField_2);
		sl_center.putConstraint(SpringLayout.EAST, serverPort, 0, SpringLayout.EAST, clientID);
		sl_center.putConstraint(SpringLayout.SOUTH, textField_2, -6, SpringLayout.NORTH, clientData);
		sl_center.putConstraint(SpringLayout.EAST, textField_2, 0, SpringLayout.EAST, textField);
		sl_center.putConstraint(SpringLayout.EAST, textField_1, 0, SpringLayout.EAST, textField);
		sl_center.putConstraint(SpringLayout.NORTH, textField, -3, SpringLayout.NORTH, clientID);
		sl_center.putConstraint(SpringLayout.EAST, clientData, 0, SpringLayout.EAST, serverData);
		sl_center.putConstraint(SpringLayout.SOUTH, serverData, -164, SpringLayout.SOUTH, center);
		sl_center.putConstraint(SpringLayout.EAST, serverData, -125, SpringLayout.EAST, center);
		center.setLayout(sl_center);
		// actually adding the components to the panel
		this.center.add(clientID);
		this.center.add(textFields[0]);
		this.center.add(serverURL);
		this.center.add(textFields[1]);
		this.center.add(serverPort);
		this.center.add(textFields[2]);
		this.center.add(clientData);
		this.center.add(serverData);
		this.center.add(clientScroll);
		this.center.add(serverScroll);
		getContentPane().add(center,BorderLayout.CENTER);
		
		// north layout is set to flow layout, status label is added to the panel
		this.north.setLayout(new FlowLayout());
		this.north.add(status);
		getContentPane().add(north,BorderLayout.NORTH);
		
		
		// south layout is set to flow layout, all buttons are added to the panel
		this.south.setLayout(new FlowLayout());
		this.south.add(connect);
		this.south.add(submit);
		this.south.add(stop);
		getContentPane().add(south,BorderLayout.SOUTH);
		
		this.client = new ChatClient("localhost",8300);
		this.client.setStatus(status);
		this.client.setServerMsg(serverArea);
		this.client.setClientID(textFields[0]);
		
		// adds the eventhandler for the buttons
		EventHandler listener = new EventHandler();
		submit.addActionListener(listener);
		connect.addActionListener(listener);
		stop.addActionListener(listener);
	}
	
	
	public static void main(String args[]) {
		new ClientGUI(args[0]); //args[0] represents the title of the GUI
	}
	
	class EventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String ButtonText = e.getActionCommand();
			if(ButtonText.toLowerCase().equals("connect")) {
				if(textFields[1].getText().equals("")) {
					System.out.println("Please enter a server URL");
				}
				else {
				try {
					client.openConnection();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
			}
			else if(ButtonText.toLowerCase().equals("submit")) {
				if(clientArea.getText().equals("")) {
					System.out.println("Please enter client data");
				}
				else {
					String msg = clientArea.getText();
					try {
						client.sendToServer(msg);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			else if(ButtonText.toLowerCase().equals("stop")) {
				if(client.isConnected()){
					try {
						client.closeConnection();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else {
					System.out.println("Client not connected");
				}
			}
		}
	}
}