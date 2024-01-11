package stelligence.crudtest.posts.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import stelligence.crudtest.posts.dto.PostRequestDto;
import stelligence.crudtest.posts.dto.PostResponseDto;
import stelligence.crudtest.posts.dto.PostShortResponseDto;
import stelligence.crudtest.posts.entity.Post;
import stelligence.crudtest.posts.repository.PostRepository;

@Slf4j
@Service
// @Transactional
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	@Transactional(readOnly = true)
	public List<PostShortResponseDto> findAllPosts(Pageable pageable) {
		return postRepository.findAll(pageable)
			.stream()
			.map(PostShortResponseDto::from)
			.toList();
		// return postRepository.findAll()
		// 	.stream()
		// 	// .filter(post -> !post.isDeleted())
		// 	.map(PostShortResponseDto::from)
		// 	.toList();
	}

	@Transactional(readOnly = true)
	public PostResponseDto findPost(Long id, int page, int size) {
		// PageRequest pageRequest = PageRequest.of(page, size);
		Post post = postRepository.findById(id)
			.orElseThrow(
				() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
		if (post.isDeleted()) {
			throw new IllegalArgumentException("해당 게시글이 삭제됐습니다. id=" + id);
		}
		return PostResponseDto.from(post);
	}

	// save
	@Transactional
	public PostResponseDto savePost(PostRequestDto postRequestDto) {

		Post post = new Post(postRequestDto.getTitle(), postRequestDto.getContents());
		Post savedPost = postRepository.save(post);
		return PostResponseDto.from(savedPost);

	}

	// update
	@Transactional
	public PostResponseDto update(Long id, PostRequestDto postRequestDto) {

		Post post = postRepository.findById(id)
			.orElseThrow(
				() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

		PostResponseDto.from(postRepository.findById(id)
			.orElseThrow(
				() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id)));

		// PostResponseDto postResponseDto = new PostResponseDto(post);
		// PostResponseDto postResponseDto1 = postResponseDto.of(post);

		if (post.isDeleted()) {
			throw new IllegalArgumentException("해당 게시글이 삭제됐습니다. id=" + id);
		}

		post.update(postRequestDto.getTitle(), postRequestDto.getContents());

		return PostResponseDto.from(post);

	}

	// delete
	@Transactional
	public void deletePost(Long id) {
		log.info("id: {}", id);
		postRepository.deleteById(id);
	}

	@Transactional
	public void test() {
		
	}

}

