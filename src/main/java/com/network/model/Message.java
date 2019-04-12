package com.network.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@NoArgsConstructor
@JsonView
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 511)
    private String content;

    @JsonIgnore
    private Timestamp sendingTime;

    @ManyToOne
    @JoinColumn(name = "from_id")
    private User from;

    @ManyToOne
    @JoinColumn(name = "to_id")
    private User to;
}
