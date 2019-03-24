package com.network.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private int id;

    private String email;

    private String name;

    private String surname;

    private char sex;

    private String dateOfBirth;

    private String city;

    private String password;

    private String bio;

    private String interests;

    private String status;

    @OneToMany(mappedBy = "user")
    private Set<Photo> photos;

    @OneToMany(mappedBy = "from")
    private Set<Friendship> fromRequests;

    @OneToMany(mappedBy = "to")
    private Set<Friendship> toRequests;

    @OneToMany(mappedBy = "author")
    private Set<Post> authors;

    @OneToMany(mappedBy = "owner")
    private Set<Post> owners;

    @OneToOne(mappedBy = "user")
    private PrivacySettings privacySettings;

    @OneToMany(mappedBy = "from")
    private Set<Message> fromMessages;

    @OneToMany(mappedBy = "to")
    private Set<Message> toMessages;

    @OneToMany(mappedBy = "user")
    private Set<Likes> likes;

    @OneToMany(mappedBy = "user")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "admin")
    private Set<Community> adminIn;

    @ManyToMany(mappedBy = "subscribers", fetch = FetchType.EAGER)
    private Set<Community> communities;

    @OneToMany(mappedBy = "user")
    private Set<PhotoAlbum> photoAlbums;


    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public Set<Friendship> getFromRequests() {
        return fromRequests;
    }

    public void setFromRequests(Set<Friendship> fromRequests) {
        this.fromRequests = fromRequests;
    }

    public Set<Friendship> getToRequests() {
        return toRequests;
    }

    public void setToRequests(Set<Friendship> toRequests) {
        this.toRequests = toRequests;
    }

    public Set<Post> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Post> authors) {
        this.authors = authors;
    }

    public Set<Post> getOwners() {
        return owners;
    }

    public void setOwners(Set<Post> owners) {
        this.owners = owners;
    }

    public PrivacySettings getPrivacySettings() {
        return privacySettings;
    }

    public void setPrivacySettings(PrivacySettings privacySettings) {
        this.privacySettings = privacySettings;
    }

    public Set<Message> getFromMessages() {
        return fromMessages;
    }

    public void setFromMessages(Set<Message> fromMessages) {
        this.fromMessages = fromMessages;
    }

    public Set<Message> getToMessages() {
        return toMessages;
    }

    public void setToMessages(Set<Message> toMessages) {
        this.toMessages = toMessages;
    }

    public Set<Likes> getLikes() {
        return likes;
    }

    public void setLikes(Set<Likes> likes) {
        this.likes = likes;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Community> getAdminIn() {
        return adminIn;
    }

    public void setAdminIn(Set<Community> adminIn) {
        this.adminIn = adminIn;
    }

    public Set<Community> getCommunities() {
        return communities;
    }

    public void setCommunities(Set<Community> communities) {
        this.communities = communities;
    }

    public Set<PhotoAlbum> getPhotoAlbums() {
        return photoAlbums;
    }

    public void setPhotoAlbums(Set<PhotoAlbum> photoAlbums) {
        this.photoAlbums = photoAlbums;
    }

    public Set<Role> getRoles() {
        return Collections.singleton(Role.USER);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass())
            return false;
        else {
            User user = (User) obj;
            return id == user.id;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", sex=" + sex +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", password=" + password +
                ", bio='" + bio + '\'' +
                ", interests='" + interests + '\'' +
                '}';
    }

    public enum Role implements GrantedAuthority {

        USER;

        @Override
        public String getAuthority() {
            return name();
        }
    }
}
