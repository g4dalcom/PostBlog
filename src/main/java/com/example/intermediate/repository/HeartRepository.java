package com.example.intermediate.repository;

import com.example.intermediate.domain.Heart;

import java.util.Arrays;
import java.util.List;

public interface HeartRepository {
    Arrays findAllByUserIdAndCommentCommentId(Long userid, Object o);

    Arrays findAllByUserIdAndContentsId(Long userid, Object o);

    List<Heart> findAllByUserIdAndParent(Long userid, Object o);
}
