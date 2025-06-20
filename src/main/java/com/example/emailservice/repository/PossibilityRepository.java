package com.example.emailservice.repository;

import com.example.emailservice.model.Possibility;
import com.example.emailservice.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PossibilityRepository extends JpaRepository<Possibility, Long> {
    List<Possibility> findByQuestion(Question question);
} 