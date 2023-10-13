package com.caeliusconsulting.chatbot;

/**
 * Following are all the imports
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

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
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.append("Musk Bot -> Hi "+userName+" , I'm Musk Bot. How can I assist you? \n");
		
		JScrollPane areaScroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
	 * This is the isMathematicalExpression method which will check that mentioned 
	 * message is mathematical expression or not
	 * 
	 * @param String message.
	 * @return boolean.
	 */
	
	public boolean isMathematicalExpression(String message) {
        return message.matches("[0-9+\\-*/()\\s]+");
    }
	
	/**
	 * This is the evaluateMathExpression method which will evaluate our expression
	 * and converts the string expression into mathematical expression
	 * 
	 * @param String message.
	 * @return double.
	 */
	
	public double evaluateMathExpression(String expression) {
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();
        
        char[] charArr = expression.toCharArray();
        int i=0;
        while (i<charArr.length) {
            if (charArr[i] == ' ') {
                i++;
                
            }

            if (Character.isDigit(charArr[i])) {
                StringBuilder sb = new StringBuilder();
                while (i<charArr.length && (Character.isDigit(charArr[i]) || charArr[i] == '.')) {
                    sb.append(charArr[i]);
                    i++;
                }
                i--;

                numbers.push(Double.parseDouble(sb.toString()));
            } else if (charArr[i] == '+' || charArr[i] == '-' || charArr[i] == '*' || charArr[i] == '/') {
                while (!operators.isEmpty() && hasPrecedence(operators.peek(), charArr[i])) {
                    performCalculation(numbers, operators);
                }
                operators.push(charArr[i]);
            } else if (charArr[i] == '(') {
                operators.push(charArr[i]);
            } else if (charArr[i] == ')') {
                while (operators.peek() != '(') {
                    performCalculation(numbers, operators);
                }
                operators.pop();  // Pop '('
            }
            i++;
        }

        while (!operators.isEmpty()) {
            performCalculation(numbers, operators);
        }

        return numbers.pop();
    }
	
	/**
	 * This is the hasPrecedence method which will check for the Precedence of the operators
	 * 
	 * @param char op1, char op2.
	 * @return boolean.
	 */

    private boolean hasPrecedence(char op1, char op2) {
        return (op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-');
    }
    
    /**
	 * This is the performCalculation method which will calculate the mathematical expression
	 * 
	 * @param Stack<Double> numbers, Stack<Character> operators.
	 * @return void.
	 */

    private void performCalculation(Stack<Double> numbers, Stack<Character> operators) {
        char operator = operators.pop();
        double b = numbers.pop();
        double a = numbers.pop();
        double result = 0.0;

        switch (operator) {
            case '+':
                result = a + b;
                break;
            case '-':
                result = a - b;
                break;
            case '*':
                result = a * b;
                break;
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("Exception: Division by zero");
                }
                result = a / b;
                break;
        }

        numbers.push(result);
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
				if(isMathematicalExpression(gText)) {
					try {
						double value = evaluateMathExpression(gText);
						textArea.append("Musk Bot -> "+value+"\n");
					}
					catch(Exception e1) {
						textArea.append("Musk Bot -> "+e1.getMessage()+"\n");
					}
					
				}else {
					String gResponse = ChatBotDB.queryDatabase(gText);
					textArea.append("Musk Bot -> "+gResponse+"\n");
				}
				textField.setText("");
			}
		}
	}
}
