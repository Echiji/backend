package com.example.emailservice.mapper;

import org.springframework.stereotype.Service;
import com.example.emailservice.dto.TestControleDTO;
import com.example.emailservice.model.TestControle;
import java.util.List;
import java.util.stream.Collectors;
import com.example.emailservice.model.Questionnaire;
@Service
public class TestControleMapper {

    public TestControleDTO toTestControleDTO(TestControle testControle) {
        TestControleDTO dto = new TestControleDTO();
        dto.setId(testControle.getId());
        dto.setNbBonneReponse(testControle.getNbBonneReponse());
        dto.setNbQuestion(testControle.getNbQuestion());
        dto.setDateCreation(testControle.getDateCreation());
        dto.setQuestionnaireId(testControle.getQuestionnaire() != null ? testControle.getQuestionnaire().getId() : null);
        dto.setQuestionnaireTitle(testControle.getQuestionnaire() != null ? testControle.getQuestionnaire().getTitle() : null);
        dto.setUserId(testControle.getUser() != null ? testControle.getUser().getId() : null);
        dto.setUsername(testControle.getUser() != null ? testControle.getUser().getUsername() : null);
        dto.setPourcentageReussite(testControle.getPourcentageReussite());
        
        return dto;
    }

    public List<TestControleDTO> toTestControleDTOList(List<TestControle> testControles) {
        return testControles.stream()
                .map(this::toTestControleDTO)
                .collect(Collectors.toList());
    }
}
