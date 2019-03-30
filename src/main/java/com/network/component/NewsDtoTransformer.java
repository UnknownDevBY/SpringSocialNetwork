package com.network.component;

import com.network.dto.NewsDto;
import com.network.model.Photo;
import com.network.model.Post;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class NewsDtoTransformer {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public NewsDto toNewsDto(Photo photo, Post post) {
        NewsDto newsDto = new NewsDto();
        newsDto.setPhoto(photo);
        newsDto.setPost(post);
        newsDto.setPublicationTime(simpleDateFormat.format(photo.getDateOfPost()));
        return newsDto;
    }
}
