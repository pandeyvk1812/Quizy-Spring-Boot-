package com.Spring.Quiz_Application.controller;

import com.Spring.Quiz_Application.entity.Question;
import com.Spring.Quiz_Application.entity.Quiz;
import com.Spring.Quiz_Application.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.time.Duration;
import java.time.Instant;
import java.util.List;


@RestController
@RequestMapping("/candidate")
public class CandidateController {
    @Autowired
    private QuizService quizService;
    @GetMapping("/attemptQuiz")
    public ModelAndView showAttemptQuizForm(Model model){
        model.addAttribute("quizKey","");
        return new ModelAndView("AttemptQuiz");
    }
    @GetMapping("/show-questions")
    public ModelAndView showQuestions(Model model){
        return new ModelAndView("Attempt_quiz_form");
    }

    @PostMapping("/attemptingQuiz")
    public ModelAndView showNextQuestion(
            @ModelAttribute("currentQuestion") Question currentQuestion,
            @RequestParam("quizKey") String quizKey,
            @RequestParam(value = "currentQuestionIndex", defaultValue = "0") int currentQuestionIndex,
            Model model) {

        Quiz quiz = quizService.getQuizByQuizKey(quizKey);
        if (quiz == null) {
            model.addAttribute("errorMessage", "Quiz key not found. Please enter a valid quiz key.");
            return new ModelAndView("AttemptQuiz");
        }

        model.addAttribute("quizKey", quizKey);
        model.addAttribute("quiz", quiz);

        // Calculate the time taken
        int timeTaken = 60 - currentQuestion.getTimeTaken();
        currentQuestion.setTimeTaken(timeTaken);

        // Save the selected answer and time
        quizService.saveSelectedAnswerAndTime(currentQuestion);

        model.addAttribute("questions", quiz.getQuestions());
        model.addAttribute("currentQuestionIndex", currentQuestionIndex > 0 ? currentQuestionIndex - 1 : 0);

        return new ModelAndView("Attempt_quiz_form");
    }


    @PostMapping("/submit-quiz")
    public ModelAndView submitQuiz(@ModelAttribute("quiz") Quiz quiz) {
        //quizService.submitQuiz(quiz);
        return new ModelAndView("quiz_submitted");
    }
}

