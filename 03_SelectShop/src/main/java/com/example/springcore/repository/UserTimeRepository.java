package com.example.springcore.repository;

import com.example.springcore.domain.User;
import com.example.springcore.domain.UserTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTimeRepository extends JpaRepository<UserTime, Long> {
    UserTime findByUser(User user);
}