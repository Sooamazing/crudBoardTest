package stelligence.crudtest.posts.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import stelligence.crudtest.comments.dto.CommentResponseDto;
import stelligence.crudtest.posts.entity.Post;

@Getter
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class PostResponseDto {

	private Long id;
	private String title;
	private String contents;
	private List<CommentResponseDto> comments = new ArrayList<>();
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;

	public PostResponseDto(Post post) {
		this.id = post.getId();
		this.title = post.getTitle();
		this.contents = post.getContents();
		this.comments = post.getComments()
			.stream().map(CommentResponseDto::from)
			.toList();
		// map은 stream의 주체를 바꿈.
		// forEach는 각각에 대해 수행하는 것 뿐.
		this.createdDate = post.getCreatedDate();
		this.lastModifiedDate = post.getLastModifiedDate();
	}

	public static PostResponseDto from(Post post) {

		return new PostResponseDto(
			post.getId(),
			post.getTitle(),
			post.getContents(),
			post.getComments()
				.stream().map(CommentResponseDto::from)
				.toList(),
			post.getCreatedDate(),
			post.getLastModifiedDate());
	}

	public PostResponseDto of(Post post) {

		return new PostResponseDto(
			post.getId(),
			post.getTitle(),
			post.getContents(),
			post.getComments()
				.stream().map(CommentResponseDto::from)
				.toList(),
			post.getCreatedDate(),
			post.getLastModifiedDate());
	}

}
