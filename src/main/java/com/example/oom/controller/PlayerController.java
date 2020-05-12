package com.example.oom.controller;

import com.example.oom.service.BackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {

    @Autowired
    private BackupService service;

    @GetMapping("/backup")
    public String backup() {
        service.backup();
        return "backup has been started";
    }
}
