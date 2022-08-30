package com.example.intermediate.controller.response;

import com.example.intermediate.controller.request.SubcommentRequestDto;
import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.service.SubcommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
public class SubcommentController {

    private final SubcommentService subcommentService;

    @RequestMapping(value = "/api/auth/subcomment", method = RequestMethod.POST)
    public ResponseDto<?> createSubcomment(@RequestBody SubcommentRequestDto requestDto,
                                           HttpServletRequest request) {
        return subcommentService.createSubcomment(requestDto, request);
    }

    @RequestMapping(value = "/api/subcomment/{id}", method = RequestMethod.GET)
    public ResponseDto<?> getAllSubcomments(@PathVariable Long id) {
        return subcommentService.getAllSubcommentsByPost(id);
    }

    @RequestMapping(value = "/api/auth/subcomment/{id}", method = RequestMethod.PUT)
    public ResponseDto<?> updateSubcomment(@PathVariable Long id, @RequestBody SubcommentRequestDto requestDto,
                                           HttpServletRequest request) {
        return subcommentService.updateSubcomment(id, requestDto, request);
    }

    @RequestMapping(value = "/api/auth/subcomment/{id}", method = RequestMethod.DELETE)
    public ResponseDto<?> deleteSubcomment(@PathVariable Long id,
                                           HttpServletRequest request) {
        return subcommentService.deleteSubcomment(id, request);
    }
}
