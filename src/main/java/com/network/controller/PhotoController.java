package com.network.controller;

import com.network.dto.PhotoDto;
import com.network.model.User;
import com.network.repository.CommentRepository;
import com.network.repository.PhotoRepository;
import com.network.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PhotoController {

    @Autowired private PhotoService photoService;
    @Autowired private PhotoRepository photoRepository;
    @Autowired private CommentRepository commentRepository;

    @Value("${s3.bucket}")
    private String bucketName;

    @GetMapping("/photos/{id}")
    public String showPhoto(@PathVariable int id,
                            @AuthenticationPrincipal User currentUser,
                            HttpServletRequest request,
                            Model model) {
        if(!photoRepository.existsById(id))
            return "redirect:" + request.getHeader("Referer");
        PhotoDto photoDto = photoService.getPhoto(id, currentUser);
        model.addAttribute("photo", photoDto);
        model.addAttribute("id", id);
        model.addAttribute("bucketName", bucketName);
        model.addAttribute("nextPhoto", photoRepository.getNextPhotoId(id));
        model.addAttribute("prevPhoto", photoRepository.getPreviousPhotoId(id));
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("comments", commentRepository.getAllByPhoto(photoDto.getPhoto()));
        return "photo";
    }
}
