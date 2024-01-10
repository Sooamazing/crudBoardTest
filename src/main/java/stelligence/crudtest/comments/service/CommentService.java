package stelligence.crudtest.comments.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import stelligence.crudtest.comments.dto.CommentRequestDto;
import stelligence.crudtest.comments.entity.Comment;
import stelligence.crudtest.comments.repository.CommentRepository;
import stelligence.crudtest.posts.dto.PostResponseDto;
import stelligence.crudtest.posts.entity.Post;
import stelligence.crudtest.posts.repository.PostRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final PostRepository postRepository;

	public PostResponseDto delete(Long id) {

		Comment comment = commentRepository.findById(id)
			.orElseThrow(
				() -> new IllegalArgumentException("해당 댓글이 없습니다. id=" + id));
		commentRepository.deleteById(id);

		Post post = postRepository.findById(comment.getPost().getId())
			.orElseThrow(
				() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

		// hard delete이면, list에서 comment를 삭제.
		// 조회 시점에 이미 최신이니까! -> List<Comment>에도 반영된
		// comment.deleteFrom(post);
		return PostResponseDto.from(post);
	}

	public PostResponseDto save(Long postId, CommentRequestDto commentRequestDto) {
		log.info("postId: {}", postId);
		log.info("commentRequestDto: {}", commentRequestDto.getContent());
		Post post = postRepository.findById(postId)
			.orElseThrow(
				() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId));
		Comment comment = new Comment(commentRequestDto.getContent(), post);
		// 이 트랜잭션이 끝나고, 다시 post를 조회하면 post의 새로운 comment가 추가된 list<Comment>가 반환됨. -> 다른 곳에서 절!대! 사용할 일이 없!다면! 안 넣어도 된다,,,

		commentRepository.save(comment);
		// 댓글의 게시글이 게시글 A -> 게시글 B 바뀌는 경우 어떻게 처리할 건가..?
		// comment.saveFrom(post);
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
