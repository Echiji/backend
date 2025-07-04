package com.example.emailservice.controller;

import com.example.emailservice.model.Question;
import com.example.emailservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.emailservice.dto.QuestionDTO;
import com.example.emailservice.mapper.QuestionMapper;
import com.example.emailservice.model.Possibility;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "*")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
@Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/questionnaire/{questionnaireId}")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByQuestionnaireId(@PathVariable Long questionnaireId) {
        List<Question> questions = questionService.getQuestionsByQuestionnaireId(questionnaireId);
        List<QuestionDTO> questionDTOs = questionMapper.toQuestionDTOList(questions);
        return ResponseEntity.ok(questionDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable Long id) {
        Question question = questionService.getQuestionById(id);
        if (question != null) {
            QuestionDTO questionDTO = questionMapper.toQuestionDTO(question);
            return ResponseEntity.ok(questionDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<QuestionDTO> createQuestion(@RequestBody Question question) {
        Question savedQuestion = questionService.createQuestion(question);
        QuestionDTO questionDTO = questionMapper.toQuestionDTO(savedQuestion);
        return ResponseEntity.ok(questionDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionDTO> updateQuestion(@PathVariable Long id, @RequestBody Question question) {
        System.out.println("updateQuestionid" + id);
        System.out.println("updateQuestion" + question.getQuestionnaire().getId());

        Question updatedQuestion = questionService.updateQuestion(id, question);
        if (updatedQuestion != null) {
            QuestionDTO questionDTO = questionMapper.toQuestionDTO(updatedQuestion);
            return ResponseEntity.ok(questionDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.ok().build();
    }
}
