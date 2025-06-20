package com.example.emailservice.service;

import com.example.emailservice.model.Question;
import com.example.emailservice.model.Lesson;
import com.example.emailservice.model.User;
import com.example.emailservice.model.Possibility;
import com.example.emailservice.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private LessonService lessonService;

    public List<Question> getQuestionsByLesson(Lesson lesson) {
        return questionRepository.findByLesson(lesson);
    }

    public Question createQuestion(Question question) {
        Lesson lesson = lessonService.getLessonById(question.getLesson().getId());
        User currentUser = userService.getCurrentUser();
        if (!lesson.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Vous n'êtes pas autorisé à ajouter une question à cette leçon");
        }
        question.setLesson(lesson);

        if (question.getPossibilities() != null) {
            for (Possibility p : question.getPossibilities()) {
                p.setQuestion(question);
            }
        }

        return questionRepository.save(question);
    }

    public Optional<Question> getQuestion(Long id) {
        return questionRepository.findById(id);
    }

    public Question updateQuestion(Long id, Question question) {
        Optional<Question> existing = questionRepository.findById(id);
        if (existing.isPresent()) {
            Question q = existing.get();
            Lesson lesson = lessonService.getLessonById(question.getLesson().getId());
            User currentUser = userService.getCurrentUser();
            if (!lesson.getUser().getId().equals(currentUser.getId())) {
                throw new RuntimeException("Vous n'êtes pas autorisé à modifier cette question");
            }
            q.setQuestion(question.getQuestion());
            q.setType(question.getType());
            q.setAnswer(question.getAnswer());
            q.setPossibilities(question.getPossibilities());
            q.setLesson(lesson);
            return questionRepository.save(q);
        } else {
            throw new RuntimeException("Question not found");
        }
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
}
