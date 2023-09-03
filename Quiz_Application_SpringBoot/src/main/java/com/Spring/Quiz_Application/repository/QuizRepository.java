package com.Spring.Quiz_Application.repository;

import com.Spring.Quiz_Application.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz,String> {
    Quiz findByQuizKey(String quizKey);
}
