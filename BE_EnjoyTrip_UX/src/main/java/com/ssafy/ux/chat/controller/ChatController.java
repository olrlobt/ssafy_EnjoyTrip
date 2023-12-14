package com.ssafy.ux.chat.controller;

import com.ssafy.ux.chat.model.ChatRequest;
import com.ssafy.ux.chat.model.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@Slf4j
public class ChatController {

    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @GetMapping("/chat")
    public String chat(@RequestParam String prompt) {
        // create a request
        log.info("prompt: {}", prompt);
        ChatRequest request = new ChatRequest(model, prompt);
        log.info("chat request: {}",request.getMessages().get(0).toString());
        // call the API
        try {
            ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);
            assert response != null;

            log.info("chat response: {}", response.getChoices().get(0).getMessage().getContent());
            if (response.getChoices() == null || response.getChoices().isEmpty()) {
                return "No response";
            }

            // return the first response
            return response.getChoices().get(0).getMessage().getContent();

        } catch (RestClientException e) {
            log.error("Error occurred while making the API request: {}", e.getMessage());
        }

        return prompt;
    }
}