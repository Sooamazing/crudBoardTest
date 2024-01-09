package stelligence.crudtest.comments.entity;

import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import stelligence.crudtest.posts.entity.Post;
import stelligence.crudtest.util.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE comment SET deleted = true WHERE id = ?")
public class Comment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long id;

	@NotNull
	@Column(columnDefinition = "TEXT")
	private String contents;

	@NotNull
	private boolean deleted;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id") // comment table에서 Post entity의 PK를 참고할 건데, 그 PK를 여기서는 name으로 지정할 거야!
	private Post post;

	public Comment(String contents, Post post) {
		this.contents = contents;
		this.post = post;
		this.deleted = false;
	}

	// 생성 시 Post List에 넣어줘야 함.
	public void saveFrom(Post post) {
		post.getComments().add(this);
	}

	// TODO post Entity에는 이미 같은 객체가 들어가 있어서 update 시에는 post 안 넣어도 상관 없음.
	public void update(String content) {
		this.contents = content;
	}

	// 삭제 시 Post List에서 빼줘야 함.
	public void deleteFrom(Post post) {
		post.getComments().remove(this);
	}

}
