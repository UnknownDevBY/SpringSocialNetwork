package com.network.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.MatchesPattern;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView
    private int id;

    @NotBlank
    @Size(min = 5, max = 127)
    private String email;

    @NotBlank
    @Size(min = 2, max = 31)
    @MatchesPattern("^[а-яА-Я\\p{Cyrillic}a-zA-Z]+$")
    @JsonView
    private String name;

    @NotBlank
    @Size(min = 3, max = 31)
    @MatchesPattern("^[а-яА-Я\\p{Cyrillic}a-zA-Z]+$")
    @JsonView
    private String surname;

    @NotNull
    @JsonView
    private char sex;

    @NotNull
    @MatchesPattern("^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$"
            + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
            + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
            + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$")
    @JsonView
    private String dateOfBirth;

    @NotBlank
    @Size(min = 2, max = 31)
    @MatchesPattern("^[а-яА-Я\\p{Cyrillic}a-zA-Z]+$")
    @JsonView
    private String city;

    private String password;

    @JsonView
    private String bio;

    @JsonView
    private String interests;

    private String status;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Photo> photos;

    @OneToMany(mappedBy = "from", fetch = FetchType.EAGER)
    private Set<Friendship> fromRequests;

    @OneToMany(mappedBy = "to", fetch = FetchType.EAGER)
    private Set<Friendship> toRequests;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private Set<Post> authors;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<Post> owners;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private PrivacySettings privacySettings;

    @OneToMany(mappedBy = "from", fetch = FetchType.EAGER)
    private Set<Message> fromMessages;

    @OneToMany(mappedBy = "to", fetch = FetchType.EAGER)
    private Set<Message> toMessages;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "admin", fetch = FetchType.EAGER)
    private Set<Community> adminIn;

    @ManyToMany(mappedBy = "subscribers", fetch = FetchType.EAGER)
    private Set<Community> communities;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<PhotoAlbum> photoAlbums;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(Role.USER);
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

    public enum Role implements GrantedAuthority {

        USER;

        @Override
        public String getAuthority() {
            return name();
        }
    }
}
