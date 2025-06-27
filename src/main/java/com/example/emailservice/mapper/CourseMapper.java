package com.example.emailservice.mapper;

import org.springframework.stereotype.Service;
import com.example.emailservice.dto.CourseDTO;
import com.example.emailservice.model.Course;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseMapper {

    public CourseDTO toCourseDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setTypeCourse(course.getTypeCourse());
        dto.setUserId(course.getUser() != null ? course.getUser().getId() : null);
        
        // Calculer le nombre de leçons
        Integer lessonCount = course.getLessons() != null ? course.getLessons().size() : 0;
        dto.setLessonCount(lessonCount);
        
        return dto;
    }

    public List<CourseDTO> toCourseDTOList(List<Course> courses) {
        return courses.stream()
                .map(this::toCourseDTO)
                .collect(Collectors.toList());
    }
}
