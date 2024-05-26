package net.lanet.screensound.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationProperties {

    @Value("${ai.gemini.project-id}")
    public String aiGeminiProjetId;
    @Value("${ai.gemini.location}")
    public String aiGeminiLocation;
    @Value("${ai.gemini.model-name}")
    public String aiGeminiModelName;

    @Value("${ai.openai.api-key}")
    public String aiOpenAiApiKey;

}
