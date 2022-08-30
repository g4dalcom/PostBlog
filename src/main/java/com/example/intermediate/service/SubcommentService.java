package com.example.intermediate.service;

import com.example.intermediate.controller.request.SubcommentRequestDto;
import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.controller.response.SubcommentResponseDto;
import com.example.intermediate.domain.Member;
import com.example.intermediate.domain.Post;
import com.example.intermediate.domain.Subcomment;
import com.example.intermediate.jwt.TokenProvider;
import com.example.intermediate.repository.SubcommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubcommentService {

    private final SubcommentRepository subcommentRepository;

    private final TokenProvider tokenProvider;
    private final PostService postService;

    @Transactional
    public ResponseDto<?> createSubcomment(SubcommentRequestDto requestDto, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Post post = postService.isPresentPost(requestDto.getPostId());
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        Subcomment subcomment = Subcomment.builder()
                .member(member)
                .post(post)
                .content(requestDto.getContent())
                .build();
        subcommentRepository.save(subcomment);
        return ResponseDto.success(
                SubcommentResponseDto.builder()
                        .id(subcomment.getId())
                        .author(subcomment.getMember().getNickname())
                        .content(subcomment.getContent())
                        .createdAt(subcomment.getCreatedAt())
                        .modifiedAt(subcomment.getModifiedAt())
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getAllSubcommentsByPost(Long postId) {
        Post post = postService.isPresentPost(postId);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        List<Subcomment> subcommentList = subcommentRepository.findAllByPost(post);
        List<SubcommentResponseDto> subcommentResponseDtoList = new ArrayList<>();

        for (Subcomment subcomment : subcommentList) {
            subcommentResponseDtoList.add(
                    SubcommentResponseDto.builder()
                            .id(subcomment.getId())
                            .author(subcomment.getMember().getNickname())
                            .content(subcomment.getContent())
                            .createdAt(subcomment.getCreatedAt())
                            .modifiedAt(subcomment.getModifiedAt())
                            .build()
            );
        }
        return ResponseDto.success(subcommentResponseDtoList);
    }

    @Transactional
    public ResponseDto<?> updateSubcomment(Long id, SubcommentRequestDto requestDto, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Post post = postService.isPresentPost(requestDto.getPostId());
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        Subcomment subcomment = isPresentSubcomment(id);
        if (null == subcomment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        if (subcomment.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }

        subcomment.update(requestDto);
        return ResponseDto.success(
                SubcommentResponseDto.builder()
                        .id(subcomment.getId())
                        .author(subcomment.getMember().getNickname())
                        .content(subcomment.getContent())
                        .createdAt(subcomment.getCreatedAt())
                        .modifiedAt(subcomment.getModifiedAt())
                        .build()
        );
    }

    @Transactional
    public ResponseDto<?> deleteSubcomment(Long id, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Subcomment subcomment = isPresentSubcomment(id);
        if (null == subcomment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        if (subcomment.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }

        subcommentRepository.delete(subcomment);
        return ResponseDto.success("success");
    }

    @Transactional(readOnly = true)
    public Subcomment isPresentSubcomment(Long id) {
        Optional<Subcomment> optionalSubcomment = subcommentRepository.findById(id);
        return optionalSubcomment.orElse(null);
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}
