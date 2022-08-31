package com.example.intermediate.repository;

import com.example.intermediate.domain.Post;
import com.example.intermediate.domain.Subcomment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SubcommentRepository extends JpaRepository<Subcomment, Long> {
    List<Subcomment> findAllByPost(Post post);
}
