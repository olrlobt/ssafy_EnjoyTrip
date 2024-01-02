package com.ssafy.ux.chat.controller;

import com.ssafy.ux.chat.model.ChatRequest;
import com.ssafy.ux.chat.model.ChatResponse;
import com.ssafy.ux.chat.model.service.ChatRepository;
import com.ssafy.ux.chat.model.service.ChatService;
import com.ssafy.ux.comment.model.Comments;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class ChatController {

    private final RestTemplate restTemplate;
    private final String model;
    private final String apiUrl;
    private final ChatService chatService;

    @Autowired
    public ChatController(
            RestTemplate restTemplate,
            @Value("${openai.model}") String model,
            @Value("${openai.api.url}") String apiUrl,
            ChatService chatService) {
        this.restTemplate = restTemplate;
        this.model = model;
        this.apiUrl = apiUrl;
        this.chatService = chatService;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/chat")
    public String chat(@RequestBody int articleNo) {
        // check commentDto.getId
//        int articleNo = comments.getArticleNo();
        log.info("articleNo: {} ", articleNo);
        String prompt = chatService.getConcatenatedCommentsByArticleNo(articleNo);
        prompt += "위의 해당 글들은 갖고 있는 데이터입니다. 데이터를 한문장으로 요약해주세요 ";

        log.info("prompt: {}", prompt);

        //create a request
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