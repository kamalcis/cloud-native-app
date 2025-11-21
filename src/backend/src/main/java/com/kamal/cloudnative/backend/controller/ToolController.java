package com.kamal.cloudnative.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class ToolController {

    @GetMapping("/")
    public String getTools() {
        return "List of Tools";
    }
}
