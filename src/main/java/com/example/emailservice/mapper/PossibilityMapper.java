package com.example.emailservice.mapper;

import org.springframework.stereotype.Service;
import com.example.emailservice.dto.PossibilityDTO;
import com.example.emailservice.model.Possibility;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PossibilityMapper {

    public static PossibilityDTO toPossibilityDTO(Possibility possibility) {
        PossibilityDTO dto = new PossibilityDTO();
        dto.setId(possibility.getId());
        dto.setPossibility(possibility.getValue());
        dto.setQuestionId(possibility.getQuestion() != null ? possibility.getQuestion().getId() : null);
        
        return dto;
    }

    public static List<PossibilityDTO> toPossibilityDTOList(List<Possibility> possibilities) {
        return possibilities.stream()
                .map(PossibilityMapper::toPossibilityDTO)
                .collect(Collectors.toList());
    }
} 