package com.ssafy.ux.finalpj.chat.model;

import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ChatResponse {

    private List<Choice> choices;

    // constructors, getters and setters

    @Getter
    @Setter
    @Data
    public static class Choice {

        private int index;
        private Message message;

        // constructors, getters and setters
    }
}
