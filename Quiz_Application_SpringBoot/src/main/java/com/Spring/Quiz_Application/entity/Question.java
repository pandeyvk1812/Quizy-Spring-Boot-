package com.Spring.Quiz_Application.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "Question")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long quesId;



    @NotBlank
    private String question;



    @NotBlank
    private String option1;



    @NotBlank
    private String option2;



    @NotBlank
    private String option3;



    @NotBlank
    private String option4;



    @NotBlank
    private String ans;


    private String selectedOption;

    private int time_taken;

    @ManyToOne
    @JoinColumn(name = "quiz_key")
    private Quiz quiz;

    public String getText() {
        return question;
    }



    public List<String> getOptions() {
        return Arrays.asList(option1, option2, option3, option4);
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }



    public int getTimeTaken() {
        return time_taken;
    }

    public void setTimeTaken(int timeTaken) {
        this.time_taken = timeTaken;
    }

}
