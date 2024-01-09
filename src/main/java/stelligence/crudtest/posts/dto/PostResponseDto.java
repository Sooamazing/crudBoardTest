package stelligence.crudtest.posts.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import stelligence.crudtest.posts.entity.Post;

@Getter
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class PostResponseDto {

	private Long id;
	private String title;
	private String contents;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;

	public static PostResponseDto from(Post post) {

		return new PostResponseDto(
			post.getId(),
			post.getTitle(),
			post.getContents(),
			post.getCreatedDate(),
			post.getLastModifiedDate());
	}

}
