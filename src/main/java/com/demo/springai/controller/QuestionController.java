package com.demo.springai.controller;

import com.demo.springai.ai.OpenAIService;
import com.demo.springai.request.GetAnswerRequest;
import com.demo.springai.response.Answer;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {

    private final OpenAIService openAIService;

    public QuestionController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping
    public ResponseEntity<Answer> getAnswer(@RequestBody GetAnswerRequest getAnswerRequest) {
        Answer answer = openAIService.getAnswer(getAnswerRequest);
        return ResponseEntity.ok(answer);
    }

    @PostMapping(value = "/images", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage(@RequestBody GetAnswerRequest request) {
        return openAIService.getImage(request);
    }

    @PostMapping(value = "/talk", produces = "audio/mpeg")
    public byte[] talk(@RequestBody GetAnswerRequest request) {
        return openAIService.talk(request);
    }
}