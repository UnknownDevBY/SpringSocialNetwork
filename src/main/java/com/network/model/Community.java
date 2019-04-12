package com.network.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Community implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView
    private int id;

    @Column(length = 63)
    @JsonView
    private String title;

    @Column(length = 127)
    @JsonView
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
