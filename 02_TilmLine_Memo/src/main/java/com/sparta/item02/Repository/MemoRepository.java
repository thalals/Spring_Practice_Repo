package com.sparta.item02.Repository;

import com.sparta.item02.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
public interface MemoRepository extends JpaRepository<Memo, Long> {
//    List<Memo> findAllByOrderByModifiedAtDesc();    //조회시 정렬
    List<Memo> findAllByModifiedAtBetweenOrderByModifiedAtDesc(LocalDateTime start, LocalDateTime end);

}

