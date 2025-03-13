package com.sivalabs.aiutils;

import com.sivalabs.aiutils.openai.audio.AudioTranscriber;
import com.sivalabs.aiutils.openai.image.ImageGenerator;
import com.sivalabs.aiutils.openai.text.TextGenerator;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Main {

    static void doYourStuffHere() {
        var text = """
                Spring framework provided JdbcTemplate as a higher-level abstraction
                on top of JDBC API to interact with SQL databases.
                However, JdbcTemplate API feels a bit verbose and not developer friendly.
                """;
        rephraseText(text);
        //transcribeAudio("intellij-runanything.wav");
        //generateImage("A cute baby sea otter");
    }

    static ImageGenerator imageGenerator;
    static TextGenerator textGenerator;
    static AudioTranscriber audioTranscriber;

    public static void main(String[] args) {
        verifyOpenAiApiKey();

        var springContext = new SpringApplicationBuilder(Main.class)
                .web(WebApplicationType.NONE)
                .run(args);

        imageGenerator = springContext.getBean(ImageGenerator.class);
        textGenerator = springContext.getBean(TextGenerator.class);
        audioTranscriber = springContext.getBean(AudioTranscriber.class);

        doYourStuffHere();
    }

    static void verifyOpenAiApiKey() {
        String openaiApiKey = System.getenv("OPENAI_API_KEY");
        if (openaiApiKey == null || openaiApiKey.trim().isBlank()) {
            throw new RuntimeException("OPENAI_API_KEY environment variable not set");
        }
    }

    public static void transcribeAudio(String wavFilePath) {
        String transcription = audioTranscriber.transcribe(wavFilePath);
        System.out.println("Transcription:\n" + transcription);
    }

    public static void generateImage(String prompt) {
        String filePath = imageGenerator.generate(prompt);
        System.out.println("filePath = " + filePath);
    }

    public static void rephraseText(String text) {
        String result = textGenerator.rephraseText(text);
        System.out.println("Original Text:\n " + text);
        System.out.println("Rephrased Text:\n " + result);
    }
}
