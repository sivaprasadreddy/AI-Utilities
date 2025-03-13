package com.sivalabs.aiutils.openai.audio;

import com.sivalabs.aiutils.common.CommonUtils;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.autoconfigure.openai.OpenAiAudioTranscriptionProperties;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class AudioTranscriber {
    private final OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel;
    private final OpenAiAudioTranscriptionOptions transcriptionOptions;

    public AudioTranscriber(OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel,
                            OpenAiAudioTranscriptionProperties openAiAudioTranscriptionProperties) {
        this.openAiAudioTranscriptionModel = openAiAudioTranscriptionModel;
        this.transcriptionOptions = openAiAudioTranscriptionProperties.getOptions();
    }

    public String transcribe(String fileName) {
        System.out.println("Transcribing " + fileName);
        File file = new File(fileName);

        // Collect the transcriptions of each chunk
        List<String> transcriptions = new ArrayList<>();
        List<File> chunks = WavFileSplitter.splitWavFileIntoChunks(file);
        boolean shouldDelete = chunks.size() > 1;
        for (File chunk : chunks) {
            String transcription = transcribeAudio(chunk);
            transcriptions.add(transcription);

            // After transcribing, no longer need the chunk
            if (shouldDelete && !chunk.delete()) {
                System.out.println("Failed to delete " + chunk.getName());
            }
        }
        // Join the individual transcripts and write to a file
        String transcription = String.join(" ", transcriptions);
        String fileNameWithoutPath = fileName.substring(fileName.lastIndexOf("/") + 1);
        String textFile = fileNameWithoutPath.replace(".wav", ".txt");
        CommonUtils.writeTextToFile(transcription, new File(textFile));
        return transcription;
    }

    public String transcribeAudio(File file) {
        var audioFile = new FileSystemResource(file);
        AudioTranscriptionPrompt transcriptionRequest =
                new AudioTranscriptionPrompt(audioFile, transcriptionOptions);
        AudioTranscriptionResponse response =
                openAiAudioTranscriptionModel.call(transcriptionRequest);
        return response.getResult().getOutput();
    }
}
