package com.sivalabs.aiutils.openai.text;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TextGenerator {
    private final ChatClient chatClient;

    public TextGenerator(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String rephraseText(String text) {
        PromptTemplate promptTemplate = new PromptTemplate("Rephrase the following text:\n\n {text}");
        Prompt prompt = promptTemplate.create(Map.of("text", text));
        return chatClient.prompt(prompt).call().content();
    }
}
