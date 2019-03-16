package com.network.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Photo {

    @Id
    @GeneratedValue
    private int id;

    @Lob
    @NotNull
    @Column(length = 10_000_000)
    private byte[] img;

    private boolean isAvatar;

    private String dateOfPost;

    private boolean wasAvatar;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Photo() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public boolean isAvatar() {
        return isAvatar;
    }

    public void setAvatar(boolean avatar) {
        isAvatar = avatar;
    }

    public String getDateOfPost() {
        return dateOfPost;
    }

    public void setDateOfPost(String dateOfPost) {
        this.dateOfPost = dateOfPost;
    }

    public boolean isWasAvatar() {
        return wasAvatar;
    }

    public void setWasAvatar(boolean wasAvatar) {
        this.wasAvatar = wasAvatar;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
