package com.example.springcore.controller;

import com.example.springcore.domain.Folder;
import com.example.springcore.security.UserDetailsImpl;
import com.example.springcore.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class HomeController {
    private final FolderService folderService;

    @GetMapping("/")
    //AuthenticationPrincipal : 로그인한 사람의 정보(userDetails)를 가져온다? 주입한다?
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<Folder> folders = folderService.getFolders(userDetails.getUser());

        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("folders", folders);
        return "index";
    }

    @Secured("ROLE_ADMIN")  //admin 페이지 접근하는 유저 정보에 Role admin이 있는지 체크
    @GetMapping("/admin")
    public String admin(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("admin", true);
        return "index";
    }
}