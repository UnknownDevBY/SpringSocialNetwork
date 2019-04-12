package com.network.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

@JsonView
@Getter
public class MessageDto {

    @Setter
    private UserDto friend;

    @Setter
    private int authorId;

    private String lastMessage;

    public void setLastMessage(String lastMessage) {
        StringBuilder builder = new StringBuilder();
        String[] firstWords = lastMessage.split(" ", 5);
        int length = firstWords.length;
        for(String i: firstWords) {
            builder.append(i);
            builder.append(" ");
        }
        if(length > 5)
            builder.append("...");
        this.lastMessage = builder.toString();
    }
}
