package com.network.controller;

import com.network.model.User;
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
public class AlbumController {

    @Autowired private UserRepository userRepository;
    @Autowired private AlbumService albumService;

    @Value("${s3.bucket}")
    private String bucketName;

    @GetMapping("/albums/{id}")
    public String showAlbum(@PathVariable int id,
                            @AuthenticationPrincipal User currentUser,
                            HttpServletRequest request,
                            Model model) {
        if(!userRepository.existsById(id))
            return "redirect:" + request.getHeader("Referer");
        model.addAttribute("id", id);
        model.addAttribute("bucketName", bucketName);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("defaultAlbum", albumService.getDefaultAlbum(id));
        model.addAttribute("albums", albumService.getUserAlbums(id));
        return "albums";
    }

}
