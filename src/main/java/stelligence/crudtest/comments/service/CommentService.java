package stelligence.crudtest.comments.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import stelligence.crudtest.comments.dto.CommentRequestDto;
import stelligence.crudtest.comments.entity.Comment;
import stelligence.crudtest.comments.repository.CommentRepository;
import stelligence.crudtest.posts.dto.PostResponseDto;
import stelligence.crudtest.posts.entity.Post;
import stelligence.crudtest.posts.repository.PostRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final PostRepository postRepository;

	public PostResponseDto delete(Long id) {
		commentRepository.deleteById(id);
		Post post = postRepository.findById(id)
			.orElseThrow(
				() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
		return PostResponseDto.from(post);
	}

	public PostResponseDto save(Long postId, CommentRequestDto commentRequestDto) {
		Post post = postRepository.findById(postId)
			.orElseThrow(
				() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId));
		Comment comment = new Comment(commentRequestDto.getContent(), post);
		commentRepository.save(comment);
		return PostResponseDto.from(post);
	}

	public PostResponseDto update(Long id, CommentRequestDto commentRequestDto) {
		Comment comment = commentRepository.findById(id)
			.orElseThrow(
				() -> new IllegalArgumentException("해당 댓글이 없습니다. id=" + id));
		comment.update(commentRequestDto.getContent());
		return PostResponseDto.from(comment.getPost());
	}

}
