package com.example.intermediate.service;

import com.example.intermediate.controller.response.MyPageResponseDto;

import com.example.intermediate.domain.Post;
import com.example.intermediate.domain.Comment;
import com.example.intermediate.domain.Subcomment;
import com.example.intermediate.domain.Heart;

import com.example.intermediate.repository.PostRepository;
import com.example.intermediate.repository.CommentRepository;
import com.example.intermediate.repository.SubcommentRepository;
import com.example.intermediate.repository.HeartRepository;

import com.example.intermediate.domain.UserDetailsImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class MyPageService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final SubcommentRepository subcommentRepository;
    private final HeartRepository heartRepository;

    public MyPageResponseDto getMyPageList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userid = userDetails.getMember().getId();

        List<Post> post = postRepository.findAllByUserId(userid);
        List<Comment> comment = commentRepository.findAllByUserIdAndParent(userid, null);
        List<Subcomment> subcomment = subcommentRepository.findAllByUserIdAndParent(userid, null);
        List<Heart> heart = heartRepository.findAllByUserIdAndParent(userid, null);

        return MyPageResponseDto.builder()
                .postList(post)
                .commentList(comment)
                .subcommentList(subcomment)
                .heartList(heart)
                .build();
    }
}