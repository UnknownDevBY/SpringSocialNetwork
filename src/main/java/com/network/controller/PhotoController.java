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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class PhotoController {

    @Autowired private PhotoService photoService;
    @Autowired private PhotoRepository photoRepository;
    @Autowired private CommentRepository commentRepository;
    @Autowired private PhotoAlbumRepository albumRepository;

    @Value("${s3.bucket}")
    private String bucketName;

    @GetMapping("/photos/{id}")
    public String showPhoto(@PathVariable int id,
                            @AuthenticationPrincipal User currentUser,
                            HttpServletRequest request,
                            Model model) {
        Photo photo = photoRepository.getById(id);
        if(photo == null)
            return "redirect:" + request.getHeader("Referer");
        User owner = photo.getUser();
        PhotoDto photoDto = photoService.getPhoto(id, currentUser);
        model.addAttribute("photo", photoDto);
        model.addAttribute("id", id);
        model.addAttribute("bucketName", bucketName);
        model.addAttribute("nextPhoto", photoRepository.getPreviousPhotoId(id, owner.getId()));
        model.addAttribute("prevPhoto", photoRepository.getNextPhotoId(id, owner.getId()));
        model.addAttribute("owner", owner);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("comments", commentRepository.getAllByPhoto(photoDto.getPhoto()));
        return "photo";
    }

    @GetMapping("/photos/add")
    public String openAddPhoto(@AuthenticationPrincipal User currentUser,
                               Model model) {
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("albums", albumRepository.getAllTitlesByUserId(currentUser.getId()));
        return "addPhoto";
    }

    @PostMapping("/photos/add")
    public String addPhoto(@RequestParam(required = false) Boolean makeAvatar,
                           @RequestParam MultipartFile newPhoto,
                           @RequestParam(required = false) String album,
                           @AuthenticationPrincipal User currentUser) throws IOException {
        photoService.savePhoto(makeAvatar, newPhoto, currentUser, null, album);
        return "redirect:/users/" + currentUser.getId();
    }
}
