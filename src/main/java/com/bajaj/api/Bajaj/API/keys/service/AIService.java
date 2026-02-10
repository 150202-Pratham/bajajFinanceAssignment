package com.bajaj.api.Bajaj.API.keys.service;

import org.springframework.stereotype.Service;
import okhttp3.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

@Service
public class AIService {
    private final String API_KEY;
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public AIService() {
        this.API_KEY = System.getenv("GOOGLE_API_KEY");
        if (this.API_KEY == null || this.API_KEY.isBlank()) {
            throw new IllegalStateException("GOOGLE_API_KEY environment variable not set");
        }
    }
    
    public String askAI(String question) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
            .build();
        
        String body = """
        {
          "contents":[{"parts":[{"text":"Answer in one word: %s"}]}]
        }
        """.formatted(question.replace("\"", "\\\""));
        
        Request request = new Request.Builder()
            .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=" + API_KEY)
            .post(RequestBody.create(body, MediaType.parse("application/json")))
            .build();
        
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("AI API call failed with status: " + response.code());
            }
            
            String responseBody = response.body().string();
            String answer = parseGeminiResponse(responseBody);
            return answer.isEmpty() ? "Unable to answer" : answer;
        } catch (IOException e) {
            throw new IOException("Failed to call AI service: " + e.getMessage());
        }
    }
    
    private String parseGeminiResponse(String jsonResponse) {
        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode candidates = root.get("candidates");
            
            if (candidates != null && candidates.isArray() && candidates.size() > 0) {
                JsonNode content = candidates.get(0).get("content");
                if (content != null) {
                    JsonNode parts = content.get("parts");
                    if (parts != null && parts.isArray() && parts.size() > 0) {
                        JsonNode text = parts.get(0).get("text");
                        if (text != null) {
                            String answer = text.asText().trim();
                            return answer.split("\\s+")[0]; // Return first word
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Return default on parse error
        }
        return "Unable";
    }
}