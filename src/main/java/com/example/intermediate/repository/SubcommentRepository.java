package com.example.intermediate.repository;

import com.example.intermediate.domain.Subcomment;

import java.util.List;

public interface SubcommentRepository {
    List<Subcomment> findAllByUserIdAndParent(Long userid, Object o);
}
