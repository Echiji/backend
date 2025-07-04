package com.example.emailservice.controller;

import com.example.emailservice.service.PossibilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/possibilities")
@CrossOrigin(origins = "*")
public class PossibilityController {
    @Autowired
    private PossibilityService possibilityService;


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePossibility(@PathVariable("id") Long id) {
        possibilityService.deletePossibility(id);
        return ResponseEntity.ok().build();
    }
} 