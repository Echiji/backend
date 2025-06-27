package com.example.emailservice.repository;

import com.example.emailservice.model.Question;
import com.example.emailservice.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByLesson(Lesson lesson);
    List<Question> findByLesson_Id(Long lessonId);
}
