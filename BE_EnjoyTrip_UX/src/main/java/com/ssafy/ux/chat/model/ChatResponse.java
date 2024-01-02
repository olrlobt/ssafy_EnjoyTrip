package com.ssafy.ux.chat.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class ChatResponse {

    @JsonProperty("choices")
    private List<Choice> choices;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Choice {
        @JsonProperty("finish_reason")
        private String finishReason;

        @JsonProperty("index")
        private int index;

        @JsonProperty("message")
        private Message message;

    }
    // constructors, getters and setters

}
