package com.network.controller;

import com.network.model.User;
import com.network.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

@Controller
public class AlbumController {

    @Autowired private AlbumService albumService;

    @Value("${s3.bucket}")
    private String bucketName;

    @GetMapping("/albums/{ownerId}")
    public String showAlbum(@PathVariable int ownerId,
                            @AuthenticationPrincipal User currentUser,
                            HttpServletRequest request,
                            Model model) {
        if(!albumService.allowAccessToAlbums(ownerId, currentUser))
            return "redirect:" + request.getHeader("Referer");
        model.addAttribute("id", ownerId);
        model.addAttribute("bucketName", bucketName);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("defaultAlbum", albumService.getDefaultAlbum(ownerId));
        model.addAttribute("albums", albumService.getUserAlbums(ownerId));
        return "albums";
    }


    @GetMapping("/albums/add")
    public String openAlbumCreator(@AuthenticationPrincipal User currentUser,
                               Model model) {
        model.addAttribute("currentUser", currentUser);
        return "addAlbum";
    }

    @GetMapping("/albums/{userId}/{albumId}")
    public String showAlbum(@PathVariable int userId,
                            @PathVariable int albumId,
                            @AuthenticationPrincipal User currentUser,
                            HttpServletRequest request,
                            Model model) {
        if(albumService.allowAccessToAlbums(userId, currentUser)) {
            model.addAttribute("bucketName", bucketName);
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("photos", albumService.getPhotos(userId, albumId));
            return "albumsPhotos";
        }
        else return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping("/albums/add")
    public String saveNewAlbum(@NotBlank @RequestParam String title,
                               @AuthenticationPrincipal User currentUser) {
        albumService.addAlbum(title, currentUser);
        return "redirect:/albums/" + currentUser.getId();
    }
}
