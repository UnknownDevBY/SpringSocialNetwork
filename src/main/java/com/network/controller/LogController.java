package com.network.controller;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class LogController {

    @SneakyThrows
    @GetMapping("/logs")
    public List<String> getLogs() {
        return Files.readAllLines(Paths.get("log.txt"));
    }
}
