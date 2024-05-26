package net.lanet.screensound.service;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.ChatSession;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.ResponseHandler;
import net.lanet.screensound.configs.ApplicationProperties;

public class QueryGeminiAI implements IQueryAI {

    @Override
    public String getInformation(String text,
                                 ApplicationProperties applicationProperties) {
        String projectId = applicationProperties.aiGeminiProjetId;
        String location = applicationProperties.aiGeminiLocation;
        String modelName = applicationProperties.aiGeminiModelName;

        return chatPrompt(projectId, location, modelName,
                "me fale sobre o artista: " + text);
    }

    public static String chatPrompt(String projectId, String location, String modelName,
                                    String text) {
        try {
            // Initialize client that will be used to send requests. This client only needs
            // to be created once, and can be reused for multiple requests.
            VertexAI vertexAI = new VertexAI(projectId, location);
            GenerateContentResponse response;

            GenerativeModel model = new GenerativeModel(modelName, vertexAI);
            // Create a chat session to be used for interactive conversation.
            ChatSession chatSession = new ChatSession(model);

            response = chatSession.sendMessage(text);
            return ResponseHandler.getText(response);

        } catch (Exception e) {
//            throw new RuntimeException(e);
            System.out.println("Error:\n" + e.getMessage());
            return "";
        }
    }

}
