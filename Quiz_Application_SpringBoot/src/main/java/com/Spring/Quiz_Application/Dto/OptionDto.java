package com.Spring.Quiz_Application.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class OptionDto {
    private String text;
    public OptionDto(String text) {
        this.text = text;
    }
}
