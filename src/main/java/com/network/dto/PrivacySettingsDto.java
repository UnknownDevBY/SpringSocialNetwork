package com.network.dto;

public class PrivacySettingsDto {

    private boolean canSendMessages = true;
    private boolean isFullInfoAllowed = true;
    private boolean arePhotosAllowed = true;
    private boolean areFriendsAllowed = true;
    private boolean canLeavePosts = true;
    private boolean canLeaveComments = true;

    public void setCanSendMessages(boolean areFriends, Character messages) {
        if(messages != null && ((!areFriends && messages == 'f') || messages == 'm')) {
            canSendMessages = false;
        }
    }

    public void setFullInfoAllowed(boolean areFriends, Character fullInfo) {
        if(fullInfo != null && ((!areFriends && fullInfo == 'f') || fullInfo == 'm')) {
            isFullInfoAllowed = false;
        }
    }

    public void setArePhotosAllowed(boolean areFriends, Character photos) {
        if(photos != null && ((!areFriends && photos == 'f') || photos == 'm')) {
            arePhotosAllowed = false;
        }
    }

    public void setAreFriendsAllowed(boolean areFriends, Character friends) {
        if(friends != null && ((!areFriends && friends == 'f') || friends == 'm')) {
            areFriendsAllowed = false;
        }
    }

    public void setCanLeavePosts(boolean areFriends, Character postAuthors) {
        if(postAuthors != null && ((!areFriends && postAuthors == 'f') || postAuthors == 'm')) {
            canLeavePosts = false;
        }
    }

    public void setCanLeaveComments(boolean areFriends, Character comments) {
        if(comments != null && ((!areFriends && comments == 'f') || comments == 'm')) {
            canLeaveComments = false;
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

    public void setCanLeaveComments(boolean canLeaveComments) {
        this.canLeaveComments = canLeaveComments;
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

    public boolean isCanLeaveComments() {
        return canLeaveComments;
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
