package com.demo.springai.ai;

import com.demo.springai.request.GetAnswerRequest;
import com.demo.springai.response.Answer;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

@Service
public class OpenAIService {

    private final ChatModel chatModel;

    public OpenAIService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public Answer getAnswer(GetAnswerRequest request) {
        PromptTemplate template = new PromptTemplate(request.question());
        Prompt prompt = template.create();

        ChatResponse response = chatModel.call(prompt);
        return new Answer(response.getResult().getOutput().getText());
    }

}
