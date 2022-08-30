package com.example.intermediate.controller;


import com.example.intermediate.controller.request.HeartRequestDto;
import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor //final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성
@RestController
public class HeartController {
    private final HeartService heartService;

    @RequestMapping(value = "/api/auth/heart", method = RequestMethod.POST)
    public ResponseDto<?> heart(@RequestBody @Valid HeartRequestDto heartDto, HttpServletRequest request) {
        return heartService.heart(heartDto, request);
    }

    @RequestMapping(value = "/api/auth/heart", method = RequestMethod.DELETE)
    public ResponseDto<?> unHeart(@RequestBody @Valid HeartRequestDto heartDto, HttpServletRequest request) {
        return heartService.unHeart(heartDto, request);
    }
}
