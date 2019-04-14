package com.network.dto;

public class MessageDto {

    private UserDto friend;
    private int authorId;
    private String lastMessage;

    public MessageDto() {

    }

    public UserDto getFriend() {
        return friend;
    }

    public void setFriend(UserDto friend) {
        this.friend = friend;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

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
