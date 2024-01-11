package stelligence.crudtest.posts.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import stelligence.crudtest.posts.dto.PostRequestDto;
import stelligence.crudtest.posts.dto.PostResponseDto;
import stelligence.crudtest.posts.dto.PostShortResponseDto;
import stelligence.crudtest.posts.service.PostService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

	private final PostService postService;

	@PostMapping("/posts")
	public PostResponseDto save(@RequestBody PostRequestDto postRequestDto) {
		return postService.savePost(postRequestDto);
	}

	// TODO update와 조회는 별개로 분리해야 함! - lastModifiedDate는 그렇게. 이와 별개로 바로 반영되게 하는 방법 존재?
	// TODO update 시 조회를 하면 기능이 두 개가 된다.
	// TODO PATCH하면 들어오자마자 insert 쿼리 나감...
	@PatchMapping("/posts/{id}")
	public PostResponseDto update1(@PathVariable("id") Long id, @RequestBody PostRequestDto postRequestDto) {
		log.info("id: {}, postRequestDto: {}", id, postRequestDto);
		return postService.update(id, postRequestDto);
	}

	@PutMapping("/posts/{id}")
	public PostResponseDto update2(@PathVariable("id") Long id, @RequestBody PostRequestDto postRequestDto) {
		log.info("id: {}, postRequestDto: {}", id, postRequestDto);
		return postService.update(id, postRequestDto);
	}

	@GetMapping("/posts/{id}")
	public PostResponseDto findPost(@PathVariable("id") Long id,
		@RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "size", defaultValue = "5") int size) {
		return postService.findPost(id, page, size);
	}

	@GetMapping("/posts")
	public List<PostShortResponseDto> findAllPosts(Pageable pageable) {
		return postService.findAllPosts(pageable);
	}

	@DeleteMapping("/posts/{id}")
	public String delete(@PathVariable("id") Long id) {

		Pageable pageable = null;
		return "success";
	}

}
