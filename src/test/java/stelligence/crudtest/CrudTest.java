package stelligence.crudtest;

// Test code

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import stelligence.crudtest.posts.repository.PostRepository;

@SpringBootTest
public class CrudTest {

	@Autowired
	private PostRepository postRepository;

	@Test
	void save() {

		// Post post1 = new Post("title", "content");
		// postRepository.save(post1);

	}
}
