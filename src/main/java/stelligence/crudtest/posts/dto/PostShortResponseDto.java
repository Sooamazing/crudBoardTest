package stelligence.crudtest.posts.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import stelligence.crudtest.posts.entity.Posts;

@Getter // TODO 직렬화할 때 필요함!!!!
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class PostShortResponseDto {

	private Long id;
	private String title;
	private LocalDateTime createdDate;

	public static PostShortResponseDto from(Posts posts) {
		return new PostShortResponseDto(posts.getId(), posts.getTitle(), posts.getCreatedDate());
	}

}
