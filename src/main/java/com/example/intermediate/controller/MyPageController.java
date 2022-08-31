package com.example.intermediate.controller;

import com.example.intermediate.controller.response.MyPageResponseDto;
import com.example.intermediate.domain.UserDetailsImpl;
import com.example.intermediate.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MyPageController {

    private final MyPageService mypageService;

    // 마이페이지 조회
    @GetMapping("/api/myPage")
    public MyPageResponseDto getMyContentList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mypageService.getMyPageList(userDetails);
    }
}