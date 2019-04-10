package com.network.service.impl;

import com.network.model.Comment;
import com.network.model.Photo;
import com.network.model.Post;
import com.network.model.User;
import com.network.repository.CommentRepository;
import com.network.repository.PhotoRepository;
import com.network.repository.PostRepository;
import com.network.service.DeleteService;
import com.network.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteServiceImpl implements DeleteService {

    @Autowired private PhotoRepository photoRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private CommentRepository commentRepository;
    @Autowired private S3Service s3Service;

    @Override
    public void deleteObject(String type, int id, User currentUser) {
        switch (type) {
            case "photo":
                deletePhoto(id, currentUser);
                break;
            case "post":
                deletePost(id, currentUser);
                break;
            case "comment":
                deleteComment(id, currentUser);
                break;
        }
    }

    private void deletePhoto(int id, User currentUser) {
        Photo photo = photoRepository.getById(id);
        if(photo.getUser().equals(currentUser)) {
            s3Service.deleteFile(photo.getTitle());
            photoRepository.delete(photo);
            if(photo.isAvatar())
                photoRepository.setPreviousAvatar(currentUser.getId());
        }
    }

    private void deletePost(int id, User currentUser) {
        Post post = postRepository.getById(id);
        if(post.getAuthor().equals(currentUser))
            postRepository.delete(post);
    }

    private void deleteComment(int id, User currentUser) {
        Comment comment = commentRepository.getById(id);
        if(comment.getUser().equals(currentUser))
            commentRepository.delete(comment);
    }
}
