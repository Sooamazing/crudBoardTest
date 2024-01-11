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

	// Post 자체가 1개라서 N+1 쿼리가 나갈 일이 없을 것..... Post랑 상관없이 Comment 전체만 존재한다면 N+1 쿼리가 나갈 수 있음.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id") // comment table에서 Post entity의 PK를 참고할 건데, 그 PK를 여기서는 name으로 지정할 거야!
	private Post post;

	public Comment(String contents, Post post) {
		this.contents = contents;
		this.post = post;
		this.deleted = false;
		// TODO
		post.getComments().add(this);
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
		// TODO comment list에서 remove하는 게 중요한 게 아니라 댓글의 deleted로 true로 바꾸는 게 중요함.
		// hard delete 시 delete 후 조회하면 정합성 안 깨짐.
		// 쿼리 날아가는 시점 -> insert(id값 필요해서)
		// TODO update, delete 시점에 쿼리 날아감. ?
		// 변경 감지를 이용해서 updqte가 바로 날아감 XXX

		// post를 삭제할 때에 대한 내용
		// FK 가지고 있는 것부터 지워야 함.
		// deleteAll 은 delete where (id) in(1,2,3,4,5)으로 list 한번에 지워짐. cascade는 하나씩 지워짐.
		// OnDelete는 DB에서 처리함 -> FK 걸린 게 있으면 삭제 못하게 막는데 SetNull은 삭제될 때 나를 참조하고 있는 것들의 FK를 null로 바꿔줌.OnDelete cascade는 나를 참조하고 있는 것들도 다 삭제해줌.
		// TODO cascade는 애플리케이션에서 처리, OnDelete는 DB에서 처리. ->
		// TODO comment에 대한 쿼리를 하나 하나 날리는 게 애플레이션에서 처리하는 거고, post에 대한 delete 쿼리 하나만 날리는데(post를 삭제하는 것만 구현되어 있는), 관련 연관 관계를 다 삭제하는 게 db에서 처리하한다
		// @OnDelete -> on delete cascade 로 스프링 떴을 때 이렇게 나감. DDL 생성 시점에만 영향을 줌.
		// TODO jdbcTemplate 쓰면 감이 올 수 있음. list for 돌리면서 delete 쿼리 날리는 거랑 같은 거임. -> 애플리케이션에서 처리하는 것.

		post.getComments().remove(this);
	}

}
