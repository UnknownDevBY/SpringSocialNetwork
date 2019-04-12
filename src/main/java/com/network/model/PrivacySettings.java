package com.network.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class PrivacySettings implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 1)
    @JsonView
    private Character messages;

    @Column(length = 1)
    @JsonView
    private Character fullInfo;

    @Column(length = 1)
    @JsonView
    private Character photos;

    @Column(length = 1)
    @JsonView
    private Character friends;

    @Column(length = 1)
    @JsonView
    private Character postAuthors;

    @Column(length = 1)
    @JsonView
    private Character comments;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void updateSettings(PrivacySettings privacySettings) {
        messages = privacySettings.messages;
        fullInfo = privacySettings.fullInfo;
        photos = privacySettings.photos;
        friends = privacySettings.friends;
        postAuthors = privacySettings.postAuthors;
        comments = privacySettings.comments;
    }
}
