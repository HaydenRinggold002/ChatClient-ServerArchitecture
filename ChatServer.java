package lab3out;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import ocsf.server.*;
import ocsf.client.*;

public class ChatServer extends AbstractServer {
	private JTextArea log;
	private JLabel status;
	
	// methods
	
	// constructor
	public ChatServer() {
		super(12435);
		setTimeout(500);
		this.log = new JTextArea();
		this.status = new JLabel();
	}
	
	public void setLog(JTextArea log) {
		this.log = log;
	}
	
	public void setStatus(JLabel status) {
		this.status = status;
	}
	
	public void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {
		log.append("Client-" + arg1.getId() + ": " + arg0 + "\n");
		try {
			arg1.sendToClient((String)arg0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void listeningException(Throwable exception) {
		status.setText("<html>Status: <font color=red>Exception Occured When Listening</font></html>");
		log.append(exception.getLocalizedMessage());
		log.append("Press Listen to Restart Server\n");
		setStatus(status);
		setLog(log);
	}
	
	public void serverStarted() {
		status.setText("<html>Status: <font color=green>Listening</font></html>");
		log.append("Server Started\n");
		setStatus(status);
		setLog(log);
	}
	
	public void serverStopped() {
		status.setText("<html>Status: <font color=red>Stopped</font></html>");
		log.append("Server Stopped Accepting New Clients - Press Listen to Start Accepting New Clients\n");
		setStatus(status);
		setLog(log);
	}
	
	public void serverClosed() {
		status.setText("<html>Status: <font color=red>Closed</font></html>");
		log.append("Server And All Current Clients Are Closed - Press Listen To Restart\n");
		setStatus(status);
		setLog(log);
	}
	
	public void clientConnected(ConnectionToClient client) {
		try {
			client.sendToClient("username:Client-"+client.getId());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.append("Client-" + client.getId() + " Connected\n");
		setStatus(status);
		setLog(log);
	}
	
}