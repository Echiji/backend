package com.example.emailservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.emailservice.dto.ProfileDTO;
import com.example.emailservice.mapper.ProfileMapper;



@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private ProfileMapper profileMapper;

    @GetMapping("/profile")
    public ResponseEntity<ProfileDTO> getProfile() {
        ProfileDTO profileDTO = profileMapper.toProfileDTO();
        return ResponseEntity.ok(profileDTO);
    }
}
