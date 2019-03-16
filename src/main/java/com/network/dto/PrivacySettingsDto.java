package com.network.dto;

public class PrivacySettingsDto {

    private boolean canSendMessages = true;
    private boolean isFullInfoAllowed = true;
    private boolean arePhotosAllowed = true;
    private boolean areFriendsAllowed = true;
    private boolean canLeavePosts = true;

    public PrivacySettingsDto() {

    }

    public PrivacySettingsDto(boolean areFriends, Character messages, Character fullInfo, Character photos, Character friends, Character postAuthors) {
        if(messages != null && ((!areFriends && messages == 'f') || messages == 'm')) {
            canSendMessages = false;
        }
        if(fullInfo != null && ((!areFriends && fullInfo == 'f') || fullInfo == 'm')) {
            isFullInfoAllowed = false;
        }
        if(photos != null && ((!areFriends && photos == 'f') || photos == 'm')) {
            arePhotosAllowed = false;
        }
        if(friends != null && ((!areFriends && friends == 'f') || friends == 'm')) {
            areFriendsAllowed = false;
        }
        if(postAuthors != null && ((!areFriends && postAuthors == 'f') || postAuthors == 'm')) {
            canLeavePosts = false;
        }
    }

    public void setCanSendMessages(boolean canSendMessages) {
        this.canSendMessages = canSendMessages;
    }

    public void setFullInfoAllowed(boolean fullInfoAllowed) {
        isFullInfoAllowed = fullInfoAllowed;
    }

    public void setArePhotosAllowed(boolean arePhotosAllowed) {
        this.arePhotosAllowed = arePhotosAllowed;
    }

    public void setAreFriendsAllowed(boolean areFriendsAllowed) {
        this.areFriendsAllowed = areFriendsAllowed;
    }

    public void setCanLeavePosts(boolean canLeavePosts) {
        this.canLeavePosts = canLeavePosts;
    }

    public boolean isCanSendMessages() {
        return canSendMessages;
    }

    public boolean isFullInfoAllowed() {
        return isFullInfoAllowed;
    }

    public boolean isArePhotosAllowed() {
        return arePhotosAllowed;
    }

    public boolean isAreFriendsAllowed() {
        return areFriendsAllowed;
    }

    public boolean isCanLeavePosts() {
        return canLeavePosts;
    }

    @Override
    public String toString() {
        return "PrivacySettingsDto{" +
                "canSendMessages=" + canSendMessages +
                ", isFullInfoAllowed=" + isFullInfoAllowed +
                ", arePhotosAllowed=" + arePhotosAllowed +
                ", areFriendsAllowed=" + areFriendsAllowed +
                ", canLeavePosts=" + canLeavePosts +
                '}';
    }
}
