package com.network.component;

import com.network.dto.PostDto;
import com.network.model.Post;
import com.network.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostDtoTransformer {

    @Autowired private CommentRepository commentRepository;
    @Autowired private LikesListTransformer likesListTransformer;

    public PostDto toPostDto(Post post, int currentUserId) {
        PostDto postDto = new PostDto();
        postDto.setPost(post);
        postDto.setComments(commentRepository.getAllByPost(post));
        List<Integer> likes = likesListTransformer.deserialize(post.getLikes());
        postDto.setLikedByCurrentUser(likes.contains(currentUserId));
        postDto.setLikesCount(likes.size());
        return postDto;
    }
}
