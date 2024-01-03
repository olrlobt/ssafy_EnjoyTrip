package com.ssafy.ux.chat.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public String getConcatenatedCommentsByArticleNo(int articleNo) {
        List<String> commentsContent = chatRepository.findCommentsContentByArticleNo(articleNo);

        // 댓글 내용을 추출하여 하나의 문자열로 연결
        String concatenatedComments = String.join(", ", commentsContent);

        return concatenatedComments;
    }

    public String getConcatenatedCommentsBySharedTravelRouteNo(int sharedTravelRouteNo) {
        List<String> commentsContent = chatRepository.findCommentsContentBySharedTravelRouteNo(sharedTravelRouteNo);

        // 댓글 내용을 추출하여 하나의 문자열로 연결
        String concatenatedComments = String.join(", ", commentsContent);

        return concatenatedComments;
    }
}
