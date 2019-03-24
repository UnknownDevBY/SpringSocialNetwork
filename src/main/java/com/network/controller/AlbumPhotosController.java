package com.network.controller;

import com.network.model.User;
import com.network.repository.PhotoAlbumRepository;
import com.network.repository.PhotoRepository;
import com.network.repository.UserRepository;
import com.network.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AlbumPhotosController {

    @Autowired private UserRepository userRepository;
    @Autowired private PhotoAlbumRepository albumRepository;
    @Autowired private AlbumService albumService;
    @Autowired private PhotoRepository photoRepository;

    @Value("${s3.bucket}")
    private String bucketName;

    @GetMapping("/albums/{userId}/{albumId}")
    public String showAlbum(@PathVariable int userId,
                            @PathVariable int albumId,
                            @AuthenticationPrincipal User currentUser,
                            HttpServletRequest request,
                            Model model) {
        if(!userRepository.existsById(userId) || albumRepository.existsById(albumId))
            return "redirect:" + request.getHeader("Referer");
        model.addAttribute("bucketName", bucketName);
        model.addAttribute("currentUser", currentUser);
        if(albumId == 0)
            model.addAttribute("photos", photoRepository.getAllByUser_Id(userId));
        else model.addAttribute("photos", photoRepository.getAllByPhotoAlbum_Id(albumId));
        return "albumsPhotos";
    }
}
