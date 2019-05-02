package com.network.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.MatchesPattern;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Email
    @Size(min = 5, max = 127)
    private String email;

    @NotBlank
    @Size(min = 2, max = 31)
    @MatchesPattern("^[а-яА-Я\\p{Cyrillic}a-zA-Z]+$")
    private String name;

    @NotBlank
    @Size(min = 3, max = 31)
    @MatchesPattern("^[а-яА-Я\\p{Cyrillic}a-zA-Z]+$")
    private String surname;

    @NotNull
    private char sex;

    @NotNull
    private Date dateOfBirth;

    @NotBlank
    @Size(min = 2, max = 31)
    @MatchesPattern("^[а-яА-Я\\p{Cyrillic}a-zA-Z]+$")
    private String city;

    private String activationCode;

    @NotBlank
    @Size(min = 6)
    private String password;

    private String bio;

    private String interests;

    private String status;

    @Enumerated(EnumType.STRING)
    private Role role;

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
    private Set<Comment> comments;

    @OneToMany(mappedBy = "admin")
    private Set<Community> adminIn;

    @OneToMany(mappedBy = "user")
    private Set<PhotoAlbum> photoAlbums;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Integer> blacklist;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "user")
    private List<CommunitySubscriber> subscriptions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(role);
    }

    @Override
    public String getUsername() {
        return email;
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
        return activationCode == null;
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
                ", city='" + city + '\'' +
                ", activationCode='" + activationCode + '\'' +
                ", password='" + password + '\'' +
                ", bio='" + bio + '\'' +
                ", interests='" + interests + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public enum Role implements GrantedAuthority {

        USER, ADMIN;

        @Override
        public String getAuthority() {
            return name();
        }
    }
}
