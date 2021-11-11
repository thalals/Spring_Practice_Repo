package com.sparta.item01;

import com.sparta.item01.domain.Lecture;
import com.sparta.item01.domain.LectureRepository;
import com.sparta.item01.dto.LectureRequestDto;
import com.sparta.item01.service.LectureService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@EnableJpaAuditing
@SpringBootApplication
public class Item01Application {

    public static void main(String[] args) {
        System.out.println("hi~");
        SpringApplication.run(Item01Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(LectureRepository lectureRepository, LectureService lectureService) {
        return (args) -> {
            lectureRepository.save(new Lecture("프론트엔드의 꽃, 리액트", "임민영"));

            System.out.println("데이터 인쇄");
            List<Lecture> lectureList = lectureRepository.findAll();
            for (int i = 0; i < lectureList.size(); i++) {
                Lecture lecture = lectureList.get(i);
                System.out.println(lecture.getId());
                System.out.println(lecture.getTitle());
                System.out.println(lecture.getTutor());
            }

            LectureRequestDto requestDto = new LectureRequestDto("웹개발의 봄, Spring", "임민영");
            lectureService.update(1L, requestDto);
            lectureList = lectureRepository.findAll();
            for (int i = 0 ; i < lectureList.size() ; i++) {
                Lecture lecture = lectureList.get(i);
                System.out.println(lecture.getId());
                System.out.println(lecture.getTitle());
                System.out.println(lecture.getTutor());
            }
//            }
        };
    }
}
