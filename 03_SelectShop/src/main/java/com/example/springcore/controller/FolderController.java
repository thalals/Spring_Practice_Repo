package com.example.springcore.controller;

import com.example.springcore.domain.Folder;
import com.example.springcore.dto.FolderCreateRequestDto;
import com.example.springcore.exeception.ApiException;
import com.example.springcore.security.UserDetailsImpl;
import com.example.springcore.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FolderController {
    // 멤버 변수 선언
    private final FolderService folderService;

    // 회원이 등록한 모든 폴더 조회
    @GetMapping("/api/folders")
    public List<Folder> getFolders(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return folderService.getFolders(userDetails.getUser());
    }

    // 회원이 폴더 추가
    @PostMapping("/api/folders")
    public List<Folder> addFolders(@RequestBody FolderCreateRequestDto folderCreateRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<String> folderNames = folderCreateRequestDto.getFolderNames();
        return folderService.createFolders(folderNames, userDetails.getUser());
    }

    //폴더 저장시 에러 처리
    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<Object> handle(IllegalArgumentException ex) {
        ApiException apiException = new ApiException(
                ex.getMessage(),
                // HTTP 400 -> Client Error
                HttpStatus.BAD_REQUEST
        );

        return new ResponseEntity<>(
                apiException,
                HttpStatus.BAD_REQUEST
        );
    }
}