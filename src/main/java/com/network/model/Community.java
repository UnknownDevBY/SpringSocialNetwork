package com.network.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 63)
    private String title;

    @Column(length = 127)
    private String description;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin;

    @ManyToMany
    @JoinTable(
            name = "community_subscribers",
            joinColumns = {@JoinColumn(name = "community_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<User> subscribers;

    @OneToMany(mappedBy = "community")
    private Set<Photo> photos;

    @OneToMany(mappedBy = "community", fetch = FetchType.EAGER)
    private Set<Post> posts;

    public Community() {

    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public List<User> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<User> subscribers) {
        this.subscribers = subscribers;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass())
            return false;
        else {
            Community community = (Community) obj;
            return id == community.id;
        }
    }
}
