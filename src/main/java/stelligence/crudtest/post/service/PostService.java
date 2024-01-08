package stelligence.crudtest.post.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import stelligence.crudtest.post.dto.PostRequestDto;
import stelligence.crudtest.post.dto.PostResponseDto;
import stelligence.crudtest.post.dto.PostShortResponseDto;
import stelligence.crudtest.post.entity.Post;
import stelligence.crudtest.post.repository.PostRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	@Transactional(readOnly = true)
	public List<PostShortResponseDto> findAllPosts() {
		return postRepository.findAll()
			.stream()
			.map(PostShortResponseDto::from)
			.toList();
	}

	@Transactional(readOnly = true)
	public PostResponseDto findPost(Long id) {
		Post post = postRepository.findById(id)
			.orElseThrow(
				() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
		return PostResponseDto.from(post);
	}

	// save
	public PostResponseDto savePost(PostRequestDto postRequestDto) {

		Post post = new Post(postRequestDto.getTitle(), postRequestDto.getContents());
		Post savedPost = postRepository.save(post);
		return PostResponseDto.from(savedPost);

	}

	// update
	public PostResponseDto update(Long id, PostRequestDto postRequestDto) {

		Post post = postRepository.findById(id)
			.orElseThrow(
				() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id))
			.update(postRequestDto.getTitle(), postRequestDto.getContents());
		return PostResponseDto.from(post);

	}

	// delete
	public void deletePost(Long id) {
		postRepository.deleteById(id);
	}

}

