package com.caeliusconsulting.chatbot;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ChatGPT {
	public static void main(String args[]) throws URISyntaxException, IOException, InterruptedException
    {
        HttpClient cl = HttpClient.newHttpClient();
        String api_uri = "https://api.openai.com/v1/chat/completions";
        String req_body = "{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\":\"user\",\"content\":\"Write me a sentence of 10 characters\"}],\"temperature\":0.7}";
        String api_key = "Bearer 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";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(api_uri))
                .header("Authorization", api_key)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(req_body, StandardCharsets.UTF_8))
                .build();
        
        HttpResponse<String> resp = cl.send(request, HttpResponse.BodyHandlers.ofString());
//        int respCode = resp.statusCode();
        String respBody = resp.body();
        System.out.println(respBody);
    }
}
