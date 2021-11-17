package com.example.springcore.repository;

import com.example.springcore.domain.Folder;
import com.example.springcore.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findAllByUser(User user);
}