package com.example.emailservice.mapper;


import org.springframework.beans.factory.annotation.Autowired;

import com.example.emailservice.dto.ProfileDTO;
import com.example.emailservice.model.TestControle;
import com.example.emailservice.model.User;
import com.example.emailservice.service.TestControleService;
import com.example.emailservice.service.UserService;
import java.util.List;


import org.springframework.stereotype.Service;


@Service
public class ProfileMapper {

    @Autowired
    private UserService userService;
    @Autowired
    private TestControleService testControleService;

    public ProfileDTO toProfileDTO(){
        User user = userService.getCurrentUser();
        List<TestControle> testControles = testControleService.getTestControlesByUserId(user.getId());

        Integer nbTestControle = testControles.size();
        Long nbTestControlePerfect = testControles.stream()
                .filter(testControle -> testControle.getPourcentageReussite() == 100)
                .count();
        Long nbTestControleFailed = testControles.stream()
                .filter(testControle -> testControle.getPourcentageReussite() < 100)
                .count();
        Long nbTestControlePassed = testControles.stream()
                .filter(testControle -> testControle.getPourcentageReussite() >= 80)
                .count();

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setUsername(user.getUsername());
        profileDTO.setNbTestControle(nbTestControle);
        profileDTO.setNbTestControlePerfect(nbTestControlePerfect);
        profileDTO.setNbTestControleFailed(nbTestControleFailed);
        profileDTO.setNbTestControlePassed(nbTestControlePassed);
        return profileDTO;
        //return new ProfileDTO(user.getUsername(), nbTestControle, nbTestControlePerfect, nbTestControleFailed, nbTestControlePassed);
    }
    
}
