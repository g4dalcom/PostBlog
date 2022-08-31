package com.example.intermediate.repository;

import com.example.intermediate.domain.Comment;
import com.example.intermediate.domain.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findAllByPost(Post post);

/*  public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Override
    List<Comment> findCommentByTicketId(Long ticketId) {
      return queryFactory.selectFrom(comment)
              .leftJoin(comment.parent)
              .fetchJoin()
              .where(comment.ticket.id.eq(ticketId))
              .orderBy(
                      comment.parent.id.asc().nullsFirst(),
                      comment.createdAt.asc()
              ).fetch();
    }
*/
}
