package stelligence.crudtest.posts.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import stelligence.crudtest.posts.entity.Posts;

@Getter
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class PostResponseDto {

	private Long id;
	private String title;
	private String contents;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;

	public static PostResponseDto from(Posts posts) {

		return new PostResponseDto(
			posts.getId(),
			posts.getTitle(),
			posts.getContents(),
			posts.getCreatedDate(),
			posts.getLastModifiedDate());
	}

}
