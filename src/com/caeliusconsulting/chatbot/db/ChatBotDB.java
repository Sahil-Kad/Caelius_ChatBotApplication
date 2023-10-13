package com.caeliusconsulting.chatbot.db;

/**
 * Following are all the imports
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The ChatBotDB program implements an application 
 * which contains the functionalities of database
 * 
 * @author Sahil
 */

public class ChatBotDB {
	
	static Connection conn;
	
	/**
	 * This is the initializeDatabase method which will start the JDBC connectivity.
	 * 
	 * @param None.
	 * @return Nothing.
	 */
	
	public static void initializeDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/Chatbot";
            String username = "root";
            String password = "rootadmin";
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * This is the queryDatabase method which will look for questions and answers inside database.
	 * and gives output according to the output received from database
	 * 
	 * @param String input.
	 * @return String.
	 */
	
	public static String queryDatabase(String input) {
        try {
            PreparedStatement preparedStatementSelect = conn.prepareStatement("SELECT Answer FROM ChatQuestions WHERE Question=?");
            preparedStatementSelect.setString(1, input);
            ResultSet result = preparedStatementSelect.executeQuery();

            if (result.next()) {
                return result.getString("Answer");
            } else {
            	try {
            		String answer = ChatGPT.openAI(input);
            		PreparedStatement preparedStatementInsert = conn.prepareStatement("INSERT INTO ChatQuestions (Question,Answer) VALUES (?,?)");
            		preparedStatementInsert.setString(1, input);
            		preparedStatementInsert.setString(2, answer);
            		preparedStatementInsert.executeUpdate();
            		return answer;
                } catch (Exception e) {
                    return "Please connect to the internet for results.";
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return new String("An error occurred while processing your request");
        }
	}
}
