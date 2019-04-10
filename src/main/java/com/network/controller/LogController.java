package com.network.controller;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
public class LogController {

    @Autowired private Path path;

    @SneakyThrows
    @GetMapping("/logs")
    public List<String> getLogs() {
        return Files.readAllLines(path);
    }
}
