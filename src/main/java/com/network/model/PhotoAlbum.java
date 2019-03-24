package com.network.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class PhotoAlbum {

    @Id
    @GeneratedValue
    private int id;

    @Column(length = 63)
    private String title;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "photoAlbum")
    private List<Photo> photos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass())
            return false;
        else {
            PhotoAlbum photoAlbum = (PhotoAlbum) obj;
            if(id == photoAlbum.id)
                return true;
            return user.getId() == photoAlbum.user.getId() && title.equals(photoAlbum.title);
        }
    }
}
