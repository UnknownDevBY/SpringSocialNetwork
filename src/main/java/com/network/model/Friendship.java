package com.network.model;

import javax.persistence.*;

@Entity
public class Friendship {

    @Id
    @GeneratedValue
    private int id;

    private boolean isConfirmed;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_id")
    private User from;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_id")
    private User to;

    public Friendship() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }
}
