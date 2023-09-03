package com.Spring.Quiz_Application.repository;

import com.Spring.Quiz_Application.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question,Long> {
}
