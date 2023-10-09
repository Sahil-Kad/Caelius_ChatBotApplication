package com.caeliusconsulting.chatbot;

/**
 * Following are all the imports
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The HomeFrame program implements an application 
 * which starts the ChatBot's Home Frame.
 * 
 * @author Sahil
 */

public class HomeFrame extends JFrame implements ActionListener {
	
	/**
	 * Following are all the variables
	 */
	
	JLabel label;
	JButton button;
	JPanel mainPanel;
	JTextField textField;
	
	/**
	 * This is the constructor method of HomeFrame which 
	 * creates a Home Frame having its functionalities
	 * 
	 * @param None.
	 * @return Nothing.
	 */
	
	public HomeFrame(){
		
		/**
		 * Following are all the functionalities of label
		 */
		
		label = new JLabel();
		label.setText("Welcome to the chatbot");
		ImageIcon image1 = new ImageIcon("chatbot logo.png");
		label.setIcon(image1);
		label.setForeground(new Color(0,0,0));
		label.setFont(new Font("MV Boli",Font.PLAIN,28));
		label.setVerticalAlignment(JLabel.CENTER);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBounds(550,220,750,550);
		
		
		/**
		 * Following are all the functionalities of textField
		 */
		
		textField = new JTextField("Enter Name");
		textField.setSize(150,40);
		textField.setLocation(1050,525);
		textField.addActionListener(this);
		textField.setFont(new Font("MV Boli",Font.PLAIN,20));
		
		/**
		 * Following are all the functionalities of button
		 */
		
		button = new JButton();
		button.setBounds(1050,580,150,50);
		button.addActionListener(this);
		button.setText("Start ChatBot");
		button.setFocusable(false);
		button.setFont(new Font("MV Boli",Font.PLAIN,17));
		
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
		this.getContentPane().setBackground(new Color(255,255,255));
		this.add(label);
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
			String userName = textField.getText();
			if(!userName.isEmpty() && !userName.equals("Enter Name")) {
				this.dispose();
				new ChatFrame(userName);
			}
		}
		
	}
}
