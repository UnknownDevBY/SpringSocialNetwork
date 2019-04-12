package com.network.controller;

import com.network.model.User;
import com.network.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class AlbumController {

    @Autowired private AlbumService albumService;

    @Value("${s3.bucket}")
    private String bucketName;

    @GetMapping("/albums/{ownerId}")
    public Map<String, Object> showAlbum(@PathVariable int ownerId,
                                         @AuthenticationPrincipal User currentUser) {
        if(!albumService.allowAccessToAlbums(ownerId, currentUser))
            return null;
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", ownerId);
        map.put("bucketName", bucketName);
        map.put("currentUserId", currentUser != null ? currentUser.getId() : 0);
        map.put("defaultAlbum", albumService.getDefaultAlbum(ownerId));
        map.put("albums", albumService.getUserAlbums(ownerId));
        return map;
    }


    @GetMapping("/albums/add")
    public Map<String, Object> openAlbumCreator(@AuthenticationPrincipal User currentUser) {
        return Map.of("currentUserId", currentUser != null ? currentUser.getId() : 0);
    }

    @GetMapping("/albums/{userId}/{albumId}")
    public Map<String, Object> showAlbum(@PathVariable int userId,
                               @PathVariable int albumId,
                               @AuthenticationPrincipal User currentUser) {
        if(!albumService.allowAccessToAlbumContent(userId, albumId, currentUser))
            return null;
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("bucketName", bucketName);
        map.put("currentUserId", currentUser != null ? currentUser.getId() : 0);
        map.put("photos", albumService.getPhotos(userId, albumId));
        return map;
    }

    @PostMapping("/albums/add")
    public void saveNewAlbum(@NotBlank @RequestParam String title,
                             @AuthenticationPrincipal User currentUser) {
        albumService.addAlbum(title, currentUser);
    }
}
