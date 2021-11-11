package com.sparta.item01.controller;

import com.sparta.item01.domain.Lecture;
import com.sparta.item01.domain.LectureRepository;
import com.sparta.item01.dto.PersonRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PersonController {

    private final LectureRepository lectureRepository;

    @GetMapping("/myinfo")
    public PersonRequestDto getinfo(){
        PersonRequestDto personRequestDto = new PersonRequestDto();
        personRequestDto.setName("손흥민");
        personRequestDto.setAddress("런던");
        personRequestDto.setAge(28);
        personRequestDto.setJob("대한민국 축구선수");
        return personRequestDto;
    }
}
