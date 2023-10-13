package com.caeliusconsulting.chatbot.db;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class ChatGPT {
	
	/**
	 * This is the openAI method which will look for the questions through
	 * OpenAI API and sends back the answer to GUI of the Chatbot
	 * 
	 * @param String question.
	 * @return String.
	 */
	
	public static String openAI(String question) throws Exception
    {
        String api_uri = "https://api.openai.com/v1/chat/completions";
        String api_key = "Bearer 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";
        
        HttpClient cl = HttpClient.newHttpClient();
        
        JSONObject reqBody = new JSONObject();
        reqBody.put("model", "gpt-3.5-turbo");
        
        JSONObject messageBody = new JSONObject();
        messageBody.put("role", "user");
        messageBody.put("content", question+" in 50 words");
        
        JSONArray messageArray = new JSONArray();
        messageArray.add(messageBody);
        
        reqBody.put("messages",messageArray);
        reqBody.put("temperature",0.7);
        reqBody.put("max_tokens", 100);
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(api_uri))
                .header("Authorization", api_key)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(reqBody.toString(), StandardCharsets.UTF_8))
                .build();
        
        HttpResponse<String> resp = cl.send(request, HttpResponse.BodyHandlers.ofString());
        String respBody = resp.body();
        Object file = JSONValue.parse(respBody);
        JSONObject jsonObjectdecode = (JSONObject)file;
        JSONArray choices = (JSONArray) jsonObjectdecode.get("choices");
        JSONObject firstObj = (JSONObject) choices.get(0);
        JSONObject messages = (JSONObject) firstObj.get("message");
        String content = (String) messages.get("content");
        return content;
    }
}
