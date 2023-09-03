package com.Spring.Quiz_Application.controller;

import com.Spring.Quiz_Application.Dto.OptionDto;
import com.Spring.Quiz_Application.Dto.QuestionDto;
import com.Spring.Quiz_Application.Dto.QuizWithQuestionsDto;
import com.Spring.Quiz_Application.entity.Question;
import com.Spring.Quiz_Application.entity.Quiz;
import com.Spring.Quiz_Application.service.HostService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

import java.util.List;



@RestController
@RequestMapping("/host")
public class HostController {
    @Autowired
    private HostService hostService;
    @GetMapping("/create-quiz")
    
    public ModelAndView showCreateQuizForm(Model model) {
        Quiz quiz = new Quiz();
        for (int i = 0; i < 10; i++) {
            quiz.getQuestions().add(new Question());
        }
        model.addAttribute("quiz", quiz);
        return new ModelAndView("create_quiz_form");
    }

    @PostMapping("/create-quiz")
    public ModelAndView createQuiz(Quiz quiz) {
        for (Question question : quiz.getQuestions()) {
            question.setQuiz(quiz);

        }

        hostService.createQuiz(quiz);
        return new ModelAndView("redirect:/host/create-quiz?success");
    }

    @GetMapping("/fetch-quiz-form")
    public ModelAndView showGetQuizByIdForm(Model model) {
        model.addAttribute("quizDto", null);
        model.addAttribute("error", false);
        return new ModelAndView("get_quiz_by_id_form");
    }

    @GetMapping("/fetch-quiz-result")
    public ModelAndView getQuizById(@RequestParam String quizId, Model model) {
        Quiz quiz = hostService.getQuizByQuizKey(quizId);

        if (quiz != null) {
            List<QuestionDto> questionDtos = new ArrayList<>();
            for (Question question : quiz.getQuestions()) {
                List<OptionDto> optionDtos = new ArrayList<>();
                optionDtos.add(new OptionDto(question.getOption1()));
                optionDtos.add(new OptionDto(question.getOption2()));
                optionDtos.add(new OptionDto(question.getOption3()));
                optionDtos.add(new OptionDto(question.getOption4()));

                questionDtos.add(new QuestionDto(question.getQuestion(), optionDtos, question.getAns()));
            }


            model.addAttribute("quizDto", new QuizWithQuestionsDto(quiz.getQuizKey(), questionDtos));
            model.addAttribute("error", false);
        } else {
            model.addAttribute("quizDto", null);
            model.addAttribute("error", true);
        }

        return new ModelAndView("get_quiz_by_id_form");
    }
}
