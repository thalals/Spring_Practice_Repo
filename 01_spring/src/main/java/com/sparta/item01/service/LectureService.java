package com.sparta.item01.service;

import com.sparta.item01.domain.Lecture;
import com.sparta.item01.domain.LectureRepository;
import com.sparta.item01.dto.LectureRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor    //기본 생성자 자동 추가(
@Service // 스프링에게 이 클래스는 서비스임을 명시
public class LectureService {

    // final: 서비스에게 꼭 필요한 녀석임을 명시
    private final LectureRepository lectureRepository;

    // 생성자를 통해, Service 클래스를 만들 때 꼭 Repository를 넣어주도록
    // 스프링에게 알려줌
//    public LectureService(LectureRepository lectureRepository) {
//        this.lectureRepository = lectureRepository;
//    }

    @Transactional // SQL 쿼리가 일어나야 함을 스프링에게 알려줌 -> lectureRepository.save(lecture1) 의 기능을 함(자동)
    public Long update(Long id, LectureRequestDto requestDto) {
        Lecture lecture1 = lectureRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다.")
        );
        lecture1.update(requestDto);
        //lectureRepository.save(lecture1)
        return lecture1.getId();
    }
}