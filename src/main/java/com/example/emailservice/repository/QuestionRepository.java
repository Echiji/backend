package com.example.emailservice.repository;

import com.example.emailservice.model.Question;
import com.example.emailservice.model.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByQuestionnaire(Questionnaire questionnaire);
    List<Question> findByQuestionnaire_Id(Long questionnaireId);
    
    // Charger les questions avec leurs relations
    @Query("SELECT DISTINCT q FROM Question q LEFT JOIN FETCH q.questionnaire LEFT JOIN FETCH q.possibilities WHERE q.questionnaire.id = :questionnaireId")
    List<Question> findByQuestionnaireIdWithRelations(@Param("questionnaireId") Long questionnaireId);
}
