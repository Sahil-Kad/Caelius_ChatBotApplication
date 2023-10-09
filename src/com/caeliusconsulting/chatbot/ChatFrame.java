package com.caeliusconsulting.chatbot;

/**
 * Following are all the imports
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.caeliusconsulting.chatbot.db.ChatBotDB;

/**
 * The ChatFrame program implements an application 
 * which starts the ChatBot's Chat Frame.
 * 
 * @author Sahil
 */

public class ChatFrame extends JFrame implements ActionListener {
	
	/**
	 * Following are all the variables
	 */
	
	JTextArea textArea;
	JTextField textField;
	JButton button;
	String userName;
	
	/**
	 * This is the constructor method of ChatFrame which 
	 * creates a Chat Frame having its functionalities
	 * 
	 * @param String userName.
	 * @return Nothing.
	 */
	
	public ChatFrame(String userName){
		
		this.userName = userName;
		ChatBotDB.initializeDatabase();
		Border border = BorderFactory.createLineBorder(new Color(25,84,116),3);
		
		JPanel textAreaPanel = new JPanel();
		
		/**
		 * Following are all the functionalities of textArea
		 */
		
		textArea = new JTextArea(23,80);
		textArea.setBorder(border);
		textArea.setFont(new Font("MV Boli",Font.PLAIN,25));
		textArea.setEditable(false);
		textArea.append("Musk Bot -> Hi "+userName+" , I'm Musk Bot. How can I assist you? \n");
		
		JScrollPane areaScroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		textAreaPanel.add(areaScroll);
		
		/**
		 * Following are all the functionalities of textField
		 */
		
		textField = new JTextField(75);
		textField.addActionListener(this);
		textField.setBorder(border);
		textField.setFont(new Font("MV Boli",Font.PLAIN,25));
		
		/**
		 * Following are all the functionalities of button
		 */
		
		button = new JButton("Submit");
		button.setFocusable(false);
		button.addActionListener(this);
		button.setFont(new Font("MV Boli",Font.PLAIN,20));
		
		/**
		 * Following are all the functionalities of frame (this)
		 */
		
		this.setTitle("My ChatBot");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		ImageIcon image = new ImageIcon("chatbot logo.png");
		this.setIconImage(image.getImage());
		this.setContentPane(textAreaPanel);
		this.add(textField);
		this.add(button);
		this.setVisible(true);
	}
	
	/**
	 * This is the actionPerformed method which is an abstract method of ActionListener interface.
	 * This method runs operations on mentioned actions disposes the current Frame and makes use an instance of ChatFrame.
	 * 
	 * @param ActionEvent e.
	 * @return Nothing.
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==textField || e.getSource()==button) {
			String gText = textField.getText();
			if(!gText.isEmpty()) {
				textArea.append(userName+" -> "+gText+"\n");
				String gResponse = ChatBotDB.queryDatabase(gText);
				textArea.append("Musk Bot -> "+gResponse+"\n");
				textField.setText("");
			}
		}
	}
}
