package com.example.emailservice.mapper;

import org.springframework.stereotype.Service;
import com.example.emailservice.dto.QuestionDTO;
import com.example.emailservice.model.Question;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionMapper {

    public QuestionDTO toQuestionDTO(Question question) {
        QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setQuestion(question.getQuestion());
        dto.setType(question.getType());
        dto.setAnswer(question.getAnswer());
        dto.setLessonId(question.getLesson() != null ? question.getLesson().getId() : null);
        dto.setPossibilities(question.getPossibilities().stream()
            .map(PossibilityMapper::toPossibilityDTO)
            .collect(Collectors.toList()));
        return dto;
    }

    public List<QuestionDTO> toQuestionDTOList(List<Question> questions) {
        return questions.stream()
                .map(this::toQuestionDTO)
                .collect(Collectors.toList());
    }
}
