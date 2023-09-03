package com.Spring.Quiz_Application.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
    private String text;
    private List<OptionDto> options;
    private String answer;
}
