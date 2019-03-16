package com.network.controller;

import com.network.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    @Autowired private PhotoRepository repository;

    @GetMapping("/images/{id}")
    public byte[] getImage(@PathVariable int id) {
        return repository.getById(id).getImg();
    }
}
