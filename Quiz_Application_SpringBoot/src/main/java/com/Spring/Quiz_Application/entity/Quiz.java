package com.Spring.Quiz_Application.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Quiz")
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class Quiz {
    @Id
    private String quizKey;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Question> questions=new ArrayList<>();

}
