package com.example.emailservice.controller;

import com.example.emailservice.model.Question;
import com.example.emailservice.model.Lesson;
import com.example.emailservice.service.QuestionService;
import com.example.emailservice.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "*")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private LessonService lessonService;

    @GetMapping("/lessons/{lessonId}")
    public ResponseEntity<List<Question>> getQuestionsByLesson(@PathVariable Long lessonId) {
        Lesson lesson = lessonService.getLessonById(lessonId);
        List<Question> questions = questionService.getQuestionsByLesson(lesson);
        return ResponseEntity.ok(questions);
    }

    @PostMapping
    public ResponseEntity<Question> createQuestion(@RequestBody Question question) {
        System.out.println("Received question creation request: " + question);
        System.out.println("Question details: " + question.getQuestion() + ", Lesson ID: " + (question.getLesson() != null ? question.getLesson().getId() : "null"));
        Question created = questionService.createQuestion(question);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestion(@PathVariable Long id) {
        return questionService.getQuestion(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question question) {
        System.out.println("updateQuestionid" + id);
        System.out.println("updateQuestion" + question.getLesson().getId());

        Question updated = questionService.updateQuestion(id, question);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.ok().build();
    }
}
