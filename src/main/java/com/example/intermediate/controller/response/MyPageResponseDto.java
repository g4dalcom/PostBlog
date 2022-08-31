package com.example.intermediate.controller.response;

import com.example.intermediate.domain.Comment;
import com.example.intermediate.domain.Heart;
import com.example.intermediate.domain.Post;
import com.example.intermediate.domain.Subcomment;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MyPageResponseDto {

    private final List<Post> postList;
    private final List<Comment> commentList;
    private final List<Subcomment> subcommentList;
    private final List<Heart> heartList;

    @Builder
    public MyPageResponseDto(List<Post> postList, List<Comment> commentList, List<Subcomment> subcommentList, List<Heart> heartList) {
        this.postList = postList;
        this.commentList = commentList;
        this.subcommentList = subcommentList;
        this.heartList = heartList;
    }
}
