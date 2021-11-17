package com.example.springcore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserTimeDto {
    String username;
    long totalTime;
}