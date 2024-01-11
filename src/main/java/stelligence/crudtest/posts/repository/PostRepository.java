package stelligence.crudtest.posts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import stelligence.crudtest.posts.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	// @EntityGraph(attributePaths = {"comments"}, type = EntityGraph.EntityGraphType.FETCH)
	@Query(value = "select * from post where deleted = false", nativeQuery = true)
	List<Post> findAll();

	// @EntityGraph(attributePaths = {"comments"}, type = EntityGraph.EntityGraphType.FETCH)
	// @Query("select p from Post p JOIN fetch p.comments where p.id = :id and p.deleted = false")
	// Optional<Post> findById(@Param("id") Long id);

}
