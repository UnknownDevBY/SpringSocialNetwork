package com.network.controller;

import com.network.dto.PhotoDto;
import com.network.model.Photo;
import com.network.model.User;
import com.network.repository.CommentRepository;
import com.network.repository.PhotoAlbumRepository;
import com.network.repository.PhotoRepository;
import com.network.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class PhotoController {

    @Autowired private PhotoService photoService;
    @Autowired private PhotoRepository photoRepository;
    @Autowired private CommentRepository commentRepository;
    @Autowired private PhotoAlbumRepository albumRepository;

    @Value("${s3.bucket}")
    private String bucketName;

    @GetMapping("/photos/{id}")
    public Map<String, Object> showPhoto(@PathVariable int id,
                            @AuthenticationPrincipal User currentUser) {
        Photo photo = photoRepository.getById(id);
        if(photo == null)
            return null;
        Map<String, Object> map = new LinkedHashMap<>();
        User owner = photo.getUser();
        PhotoDto photoDto = photoService.getPhoto(id, currentUser);
        map.put("photo", photoDto);
        map.put("id", id);
        map.put("bucketName", bucketName);
        map.put("nextPhoto", photoRepository.getPreviousPhotoId(id, owner.getId()));
        map.put("prevPhoto", photoRepository.getNextPhotoId(id, owner.getId()));
        map.put("owner", owner);
        map.put("currentUserId", currentUser != null ? currentUser.getId() : 0);
        map.put("comments", commentRepository.getAllByPhoto(photoDto.getPhoto()));
        return map;
    }

    @GetMapping("/photos/add")
    public Map<String, Object> openAddPhoto(@AuthenticationPrincipal User currentUser) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("currentUserId", currentUser.getId());
        map.put("albums", albumRepository.getAllTitlesByUserId(currentUser.getId()));
        return map;
    }

    @PostMapping("/photos/add")
    @ResponseStatus(HttpStatus.OK)
    public void addPhoto(@RequestParam(required = false) Boolean makeAvatar,
                           @RequestParam MultipartFile newPhoto,
                           @RequestParam(required = false) String album,
                           @AuthenticationPrincipal User currentUser) throws IOException {
        photoService.savePhoto(makeAvatar, newPhoto, currentUser, null, album);
    }
}
