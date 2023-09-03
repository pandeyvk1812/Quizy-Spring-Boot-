package com.Spring.Quiz_Application.service;

import com.Spring.Quiz_Application.entity.Quiz;
import com.Spring.Quiz_Application.repository.QuizRepository;
import com.Spring.Quiz_Application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostService {
    @Autowired
    private UserRepository userRepository;
    @Autowired

    private QuizRepository quizRepository;

    public void createQuiz(Quiz quiz) {
        quizRepository.save(quiz);
    }

    public Quiz getQuizByQuizKey(String key) {
        return quizRepository.findById(key).orElse(null);
    }


}
