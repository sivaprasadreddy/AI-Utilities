package com.sivalabs.aiutils;

import com.sivalabs.aiutils.openai.audio.AudioTranscriber;
import com.sivalabs.aiutils.openai.image.ImageGenerator;

public class Main {

    public static void main(String[] args) {

        //transcribeAudio("intellij-runanything.wav");
        //generateImage("A cute baby sea otter");
    }

    static String getOpenAiApiKey() {
        String openaiApiKey = System.getenv("OPENAI_API_KEY");
        if (openaiApiKey == null || openaiApiKey.trim().isBlank()) {
            throw new RuntimeException("OPENAI_API_KEY environment variable not set");
        }
        return openaiApiKey;
    }

    public static void transcribeAudio(String wavFilePath) {
        String openAiApiKey = getOpenAiApiKey();
        AudioTranscriber audioTranscriber = new AudioTranscriber(openAiApiKey);

        String transcription = audioTranscriber.transcribe(wavFilePath);
        System.out.println("Transcription:\n" + transcription);
    }

    public static void generateImage(String prompt) {
        String openAiApiKey = getOpenAiApiKey();
        ImageGenerator imageGenerator = new ImageGenerator(openAiApiKey);
        String filePath = imageGenerator.generate(prompt);
        System.out.println("filePath = " + filePath);
    }
}
