package com.network.model;

import javax.persistence.*;

@Entity
public class PrivacySettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 1)
    private Character messages;

    @Column(length = 1)
    private Character fullInfo;

    @Column(length = 1)
    private Character photos;

    @Column(length = 1)
    private Character friends;

    @Column(length = 1)
    private Character postAuthors;

    @Column(length = 1)
    private Character comments;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public PrivacySettings() {

    }

    public void updateSettings(PrivacySettings privacySettings) {
        messages = privacySettings.messages;
        fullInfo = privacySettings.fullInfo;
        photos = privacySettings.photos;
        friends = privacySettings.friends;
        postAuthors = privacySettings.postAuthors;
        comments = privacySettings.comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Character getMessages() {
        return messages;
    }

    public void setMessages(Character messages) {
        this.messages = messages;
    }

    public Character getFullInfo() {
        return fullInfo;
    }

    public void setFullInfo(Character fullInfo) {
        this.fullInfo = fullInfo;
    }

    public Character getPhotos() {
        return photos;
    }

    public void setPhotos(Character photos) {
        this.photos = photos;
    }

    public Character getFriends() {
        return friends;
    }

    public void setFriends(Character friends) {
        this.friends = friends;
    }

    public Character getPostAuthors() {
        return postAuthors;
    }

    public void setPostAuthors(Character postAuthors) {
        this.postAuthors = postAuthors;
    }

    public Character getComments() {
        return comments;
    }

    public void setComments(Character comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PrivacySettings{" +
                "id=" + id +
                ", messages=" + messages +
                ", fullInfo=" + fullInfo +
                ", photos=" + photos +
                ", friends=" + friends +
                ", postAuthors=" + postAuthors +
                ", user=" + user +
                '}';
    }
}
