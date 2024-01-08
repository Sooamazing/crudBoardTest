package stelligence.crudtest.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import stelligence.crudtest.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
