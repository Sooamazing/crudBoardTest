package stelligence.crudtest.comments.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import stelligence.crudtest.comments.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
