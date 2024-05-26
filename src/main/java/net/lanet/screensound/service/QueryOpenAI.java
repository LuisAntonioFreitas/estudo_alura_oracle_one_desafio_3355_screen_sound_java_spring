package net.lanet.screensound.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

import net.lanet.screensound.configs.ApplicationProperties;

public class QueryOpenAI implements IQueryAI {

    @Override
    public String getInformation(String text,
                                 ApplicationProperties applicationProperties) {
        try {
            OpenAiService service = new OpenAiService(applicationProperties.aiOpenAiApiKey);

            CompletionRequest request = CompletionRequest.builder()
                    .model("gpt-3.5-turbo-instruct")
                    .prompt("me fale sobre o artista: " + text)
                    .maxTokens(1000)
                    .temperature(0.7)
                    .build();

            var response = service.createCompletion(request);
            if (!response.getChoices().get(0).getText().isEmpty()) {
                return response.getChoices().get(0).getText();
            }
            return "";
        } catch (RuntimeException e) {
//            throw new RuntimeException(e);
            System.out.println("Error:\n" + e.getMessage());
            return "";
        }
    }

}
