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
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.ai.openai.audio.speech.SpeechResponse;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class OpenAIService {

    private final ChatModel chatModel;
    private final ImageModel imageModel;
    private final OpenAiAudioSpeechModel speechModel;

    public OpenAIService(ChatModel chatModel, ImageModel imageModel, OpenAiAudioSpeechModel speechModel) {
        this.chatModel = chatModel;
        this.imageModel = imageModel;
        this.speechModel = speechModel;
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

    public byte[] talk(GetAnswerRequest request) {
        OpenAiAudioSpeechOptions options = OpenAiAudioSpeechOptions.builder()
                .voice(OpenAiAudioApi.SpeechRequest.Voice.ALLOY)
                .speed(1.0f)
                .responseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                .model(OpenAiAudioApi.TtsModel.TTS_1.value)
                .build();

        SpeechPrompt speechPrompt = new SpeechPrompt(request.question(), options);
        SpeechResponse speechResponse = speechModel.call(speechPrompt);

        return speechResponse.getResult().getOutput();
    }
}
