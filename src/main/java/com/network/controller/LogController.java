package com.network.controller;

import com.network.model.Log;
import com.network.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;

@Controller
public class LogController {

    @Autowired private LogRepository logRepository;

    @GetMapping("/log")
    public String getLog(Model model) {
        List<Log> logs = logRepository.findAll();
        Collections.reverse(logs);
        model.addAttribute("logs", logs);
        return "log";
    }
}
