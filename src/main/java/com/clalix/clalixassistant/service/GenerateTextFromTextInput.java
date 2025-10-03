package com.clalix.clalixassistant.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.ThinkingConfig;
import io.github.cdimascio.dotenv.Dotenv;

public class GenerateTextFromTextInput {
    /**
     * Generates content from Gemini AI using the provided prompt and returns the response text.
     *
     * @param prompt The prompt to send to Gemini AI.
     * @return The AI-generated response text, or error message if failed.
     */
    public static String generateText(String prompt) {
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("GEMINI_API_KEY");
        if (apiKey == null) {
            throw new IllegalArgumentException("GOOGLE_API_KEY not set in .env or environment");
        }
        GenerateContentResponse response;
        ThinkingConfig thinkingConfig = ThinkingConfig.builder().thinkingBudget(0).build();
        GenerateContentConfig config = GenerateContentConfig.builder().thinkingConfig(thinkingConfig).build();
        try (Client client = Client.builder().apiKey(apiKey).build()) {
            response = client.models.generateContent(
                    "gemini-2.5-flash",
                    prompt,
                    config);
            return response.text();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}