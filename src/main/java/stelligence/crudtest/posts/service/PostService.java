package stelligence.crudtest.posts.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import stelligence.crudtest.posts.dto.PostRequestDto;
import stelligence.crudtest.posts.dto.PostResponseDto;
import stelligence.crudtest.posts.dto.PostShortResponseDto;
import stelligence.crudtest.posts.entity.Posts;
import stelligence.crudtest.posts.repository.PostRepository;

@Slf4j
@Service
// @Transactional
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	@Transactional(readOnly = true)
	public List<PostShortResponseDto> findAllPosts() {
		return postRepository.findAll()
			.stream()
			// .filter(post -> !post.isDeleted())
			.map(PostShortResponseDto::from)
			.toList();
	}

	@Transactional(readOnly = true)
	public PostResponseDto findPost(Long id) {
		Posts posts = postRepository.findById(id)
			.orElseThrow(
				() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
		if (posts.isDeleted()) {
			throw new IllegalArgumentException("해당 게시글이 삭제됐습니다. id=" + id);
		}
		return PostResponseDto.from(posts);
	}

	// save
	@Transactional
	public PostResponseDto savePost(PostRequestDto postRequestDto) {

		Posts posts = new Posts(postRequestDto.getTitle(), postRequestDto.getContents());
		Posts savedPosts = postRepository.save(posts);
		return PostResponseDto.from(savedPosts);

	}

	// update
	@Transactional
	public PostResponseDto update(Long id, PostRequestDto postRequestDto) {

		Posts posts = postRepository.findById(id)
			.orElseThrow(
				() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

		if (posts.isDeleted()) {
			throw new IllegalArgumentException("해당 게시글이 삭제됐습니다. id=" + id);
		}

		posts.update(postRequestDto.getTitle(), postRequestDto.getContents());

		return PostResponseDto.from(posts);

	}

	// delete
	@Transactional
	public void deletePost(Long id) {
		postRepository.deleteById(id);
	}

}

