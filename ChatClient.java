package lab3out;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import ocsf.client.*;
import ocsf.server.*;

public class ChatClient extends AbstractClient {

	// private data fields
	private JLabel status;
	private JTextArea serverMsg;
	private JTextField clientID;
	
	// methods
	public ChatClient(String host, int port) {
		super(host, port);
		this.status = new JLabel();
		this.serverMsg = new JTextArea();
		this.clientID = new JTextField();
	}
	
	public void setStatus(JLabel status) {
		this.status = status;
	}
	
	public void setServerMsg(JTextArea serverMsg) {
		this.serverMsg = serverMsg;
	}
	
	public void setClientID(JTextField clientID) {
		this.clientID = clientID;
	}
	
	public void connectionEstablished() {
		status.setText("<html>Status: <font color=green>Connected</html>");
		setStatus(status);
		setServerMsg(serverMsg);
		setClientID(clientID);
	}
	
	protected void handleMessageFromServer(Object arg0) {
		String msg = (String)arg0;
		if(msg.contains("username:")) {
			String temp = msg.substring(msg.lastIndexOf("-")+1);
			clientID.setText(temp);
			serverMsg.append("Server: " + msg + "\n");
			setClientID(clientID);
			setServerMsg(serverMsg);
			setStatus(status);
		}
		else {
		serverMsg.append("Server: " + msg + "\n");
		setServerMsg(serverMsg);
		setStatus(status);
		setClientID(clientID);
		}
	}
	
	public void connectionClosed() {
		status.setText("<html>Status: <font color=red>Not Connected</html>");
		setStatus(status);
		setServerMsg(serverMsg);
		setClientID(clientID);
	}
}
