package com.demo.springai.ai;

import com.demo.springai.request.GetAnswerRequest;
import com.demo.springai.response.Answer;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class OpenAIService {

    private final ChatModel chatModel;
    private final ImageModel imageModel;

    public OpenAIService(ChatModel chatModel, ImageModel imageModel) {
        this.chatModel = chatModel;
        this.imageModel = imageModel;
    }

    public Answer getAnswer(GetAnswerRequest request) {
        PromptTemplate template = new PromptTemplate(request.question());
        Prompt prompt = template.create();

        ChatResponse response = chatModel.call(prompt);
        return new Answer(response.getResult().getOutput().getText());
    }

    public byte[] getImage(GetAnswerRequest request) {

        OpenAiImageOptions options = OpenAiImageOptions.builder()
                .width(1024)
                .height(1024)
                .quality("hd")
                .style("vivid")
                .responseFormat("b64_json")
                .model("dall-e-3")
                .build();

        ImagePrompt imagePrompt = new ImagePrompt(request.question(), options);
        ImageResponse imageResponse = imageModel.call(imagePrompt);

        return Base64.getDecoder().decode(imageResponse.getResult().getOutput().getB64Json());
    }
}
