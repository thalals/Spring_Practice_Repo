package com.sparta.item01.domain;

import com.sparta.item01.dto.LectureRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor  //매개변수가 없는 생성자 코드 삭제(자동 생성)
@Getter
@Setter
@Entity // 테이블임을 나타냅니다.
public class Lecture extends Timestamped{

//    public Lecture() {}

    @Id // ID 값, Primary Key로 사용하겠다는 뜻입니다.
    @GeneratedValue(strategy = GenerationType.AUTO) // 자동 증가 명령입니다.
    private Long id;

    @Column(nullable = false) // 컬럼 값이고 반드시 값이 존재해야 함을 나타냅니다.
    private String title;

    @Column(nullable = false)
    private String tutor;

    public Lecture(LectureRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.tutor = requestDto.getTutor();
    }

    public Lecture(String title, String tutor) {
        this.title = title;
        this.tutor = tutor;
    }

    public void update(LectureRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.tutor = requestDto.getTutor();
    }
}