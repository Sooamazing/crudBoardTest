package stelligence.crudtest.comments.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import stelligence.crudtest.comments.dto.CommentRequestDto;
import stelligence.crudtest.comments.service.CommentService;
import stelligence.crudtest.posts.dto.PostResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
	private final CommentService commentService;

	@DeleteMapping("/comments/{id}")
	public void deleteComment(@PathVariable("id") Long id) {
		commentService.delete(id);
	}

	@PostMapping("/posts/{postId}/comments")
	public PostResponseDto save(@PathVariable("postId") Long postId, @RequestBody CommentRequestDto commentRequestDto) {
		return commentService.save(postId, commentRequestDto);
	}

	@PutMapping("/comments/{id}")
	public PostResponseDto update(@PathVariable("id") Long id, @RequestBody CommentRequestDto commentRequestDto) {
		return commentService.update(id, commentRequestDto);
	}

}
