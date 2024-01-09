package stelligence.crudtest.comments.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import lombok.RequiredArgsConstructor;
import stelligence.crudtest.comments.dto.CommentRequestDto;
import stelligence.crudtest.comments.service.CommentService;
import stelligence.crudtest.posts.dto.PostResponseDto;

@Controller
@RequiredArgsConstructor
public class CommentController {
	private final CommentService commentService;

	@DeleteMapping("/comments/{id}")
	public void deleteComment(Long id) {
		commentService.delete(id);
	}

	@PostMapping("/posts/{postId}/comments")
	public PostResponseDto save(Long postId, CommentRequestDto commentRequestDto) {
		return commentService.save(postId, commentRequestDto);
	}

	@PutMapping("/comments/{id}")
	public PostResponseDto update(Long id, CommentRequestDto commentRequestDto) {
		return commentService.update(id, commentRequestDto);
	}

}
