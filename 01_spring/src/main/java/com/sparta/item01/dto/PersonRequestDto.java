package com.sparta.item01.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PersonRequestDto {
    private String name;
    private int age;
    private String address;
    private String job;

    public PersonRequestDto(String name, int age, String address, String job) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.job = job;
    }
}
