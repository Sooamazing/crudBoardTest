package stelligence.crudtest.posts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import stelligence.crudtest.posts.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	@Query(value = "select * from post where deleted = false", nativeQuery = true)
	List<Post> findAll();

}
