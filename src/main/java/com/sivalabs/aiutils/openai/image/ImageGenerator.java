package com.sivalabs.aiutils.openai.image;

import com.sivalabs.aiutils.common.CommonUtils;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImageOptionsBuilder;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.api.OpenAiImageApi;

public class ImageGenerator {
    private final ImageModel imageModel;

    public ImageGenerator(String apiKey) {
        OpenAiImageApi openAiImageApi =
                OpenAiImageApi.builder()
                        .apiKey(apiKey)
                        .build();
        this.imageModel = new OpenAiImageModel(openAiImageApi);
    }

    public String generate(String prompt) {
        var options = ImageOptionsBuilder.builder()
                .height(1024).width(1024)
                //.responseFormat("url")
                .responseFormat("b64_json")
                .model("dall-e-3")
                .build();
        ImagePrompt imagePrompt = new ImagePrompt(prompt, options);
        ImageResponse imageResponse = imageModel.call(imagePrompt);
        //return imageResponse.getResult().getOutput().getUrl();
        String b64Json = imageResponse.getResult().getOutput().getB64Json();
        return CommonUtils.writeImageToFile(b64Json);
    }
}
