package com.demo.springai;

import com.demo.springai.ai.OpenAIService;
import com.demo.springai.request.GetAnswerRequest;
import com.demo.springai.response.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringAiApplication implements CommandLineRunner {

	private final OpenAIService openAIService;
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringAiApplication.class);

	public SpringAiApplication(OpenAIService openAIService) {
		this.openAIService = openAIService;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringAiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		GetAnswerRequest request = new GetAnswerRequest("tell me a dad joke");
		Answer answer = openAIService.getAnswer(request);
		LOGGER.info(answer.toString());
	}
}
