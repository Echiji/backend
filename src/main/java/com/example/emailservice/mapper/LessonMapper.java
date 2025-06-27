package com.example.emailservice.mapper;

import org.springframework.stereotype.Service;
import com.example.emailservice.dto.LessonDTO;
import com.example.emailservice.model.Lesson;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonMapper {

    public LessonDTO toLessonDTO(Lesson lesson) {
        LessonDTO dto = new LessonDTO();
        dto.setId(lesson.getId());
        dto.setTitle(lesson.getTitle());
        dto.setDescription(lesson.getDescription());
        dto.setUserId(lesson.getUser() != null ? lesson.getUser().getId() : null);
        dto.setCourseId(lesson.getCourse() != null ? lesson.getCourse().getId() : null);
        dto.setCourseTitle(lesson.getCourse() != null ? lesson.getCourse().getTitle() : null);
        
        // Calculer le nombre de questions
        Integer questionCount = lesson.getQuestions() != null ? lesson.getQuestions().size() : 0;
        dto.setQuestionCount(questionCount);
        
        return dto;
    }

    public List<LessonDTO> toLessonDTOList(List<Lesson> lessons) {
        return lessons.stream()
                .map(this::toLessonDTO)
                .collect(Collectors.toList());
    }
}
