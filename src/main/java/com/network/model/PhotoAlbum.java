package com.network.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class PhotoAlbum implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 63)
    @JsonView
    private String title;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonView
    @OneToMany(mappedBy = "photoAlbum")
    private List<Photo> photos;

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
