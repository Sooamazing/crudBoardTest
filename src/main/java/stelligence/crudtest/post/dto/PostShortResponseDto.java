package stelligence.crudtest.post.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import stelligence.crudtest.post.entity.Post;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class PostShortResponseDto {

	private Long id;
	private String title;
	private LocalDateTime createdDate;

	public static PostShortResponseDto from(Post post) {
		return new PostShortResponseDto(post.getId(), post.getTitle(), post.getCreatedDate());
	}

}
