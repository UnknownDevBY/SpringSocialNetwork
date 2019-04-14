package com.network.dto;

import com.network.model.Photo;
import com.network.model.Post;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class NewsDto implements Comparable<NewsDto> {

    private Photo photo;
    private Post post;
    private DateTime publicationTime;

    private DateTimeFormatter dateTimeFormatter =
            DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getPublicationTime() {
        return dateTimeFormatter.print(publicationTime);
    }

    public void setPublicationTime(String publicationTime) {
        this.publicationTime = DateTime.parse(publicationTime, dateTimeFormatter);
    }

    @Override
    public int compareTo(NewsDto o) {
        return o.publicationTime.compareTo(publicationTime);
    }
}
