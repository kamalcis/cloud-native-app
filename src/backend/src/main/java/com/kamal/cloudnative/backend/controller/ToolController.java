package com.kamal.cloudnative.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.kamal.cloudnative.backend.models.Tool;
import com.kamal.cloudnative.backend.services.IToolService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class ToolController {

    private final IToolService iToolService;

    public ToolController(IToolService iToolService) {
        this.iToolService = iToolService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Tool>> getTools() {
        return ResponseEntity.ok(iToolService.getTools());
    }
}
