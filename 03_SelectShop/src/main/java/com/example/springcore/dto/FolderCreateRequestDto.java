package com.example.springcore.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class FolderCreateRequestDto {
    List<String> folderNames;
}