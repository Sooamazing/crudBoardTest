package stelligence.crudtest.comments.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import stelligence.crudtest.comments.entity.Comment;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor
public class CommentResponseDto {
	private Long id;
	private String contents;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;

	public static CommentResponseDto from(Comment comment) {
		return new CommentResponseDto(
			comment.getId(),
			comment.getContents(),
			comment.getCreatedDate(),
			comment.getLastModifiedDate());
	}

}
