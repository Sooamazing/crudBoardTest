package stelligence.crudtest.posts.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import stelligence.crudtest.comments.entity.Comment;
import stelligence.crudtest.util.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE posts SET deleted = true WHERE id = ?")
public class Post extends BaseEntity {

	// Entity는 순수하게 유지하고, 유효성 검증 등은 Dto에서 수행하도록 하자.
	// 비즈니스 로직이 Entity에 들어가야 하는 경우가 많다고 함. 응집성이 높아지는 경우가 있음. -> update 메서드 사용하는 게 Entity에 수정할 책임을 넘기는 거라 이것도 하나의 예시가 될 수 있음.

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long id;

	// columnDefinition = "varchar(50)"을 붙이지 않으면?
	// Column은 유효성 검증을 수행하지 않음. -> DDL, Validate에만 영향을 줌.
	@Column(length = 50)
	@NotNull
	private String title;

	// @Lob // Lob을 붙이지 않으면? + CLOB, TEXT 차이점?
	// JPA 방언 설정에 따라 CLOB, TEXT로 매핑됨
	// @Column(columnDefinition = "TEXT")
	@NotNull
	private String contents;

	// columnDefinition은 DDL 생성되는 시점에 적용됨.
	// 오직 create, create-drop, validate에 사용되는 정보.
	// update, none에는 적용되지 않음.
	// 운영에서는 validate, none를 사용.
	// JPA가 DDL할 때 사용. 프로덕션 코드에서는 딱히 사용 X -> JPA를 통해 만들 일이 없을 것. 테이블 주고 하니까 ?
	// @Column(columnDefinition = "tinyint default 0")
	// @ColumnDefault("false")
	@NotNull // TODO 이거 nullable로 하면 안 되나?
	private boolean deleted;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();

	public Post(String title, String contents) {
		this.title = title;
		this.contents = contents;
		this.deleted = false;
	}

	// TODO Entity를 생성할 때는 생성자를 사용하나?
	// public static Post of(String title, String contents) {
	// 	// TODO class 내라서 private 생성자를 호출할 수 있나?
	// 	// TODO 하기 패턴이 set 과 다른지?
	// 	Post post = new Post();
	// 	post.title = title;
	// 	post.contents = contents;
	// 	return post;
	// }

	// TODO 어떤 걸 update하는지 모르니까 명확하게.
	// TODO return this 왜 안 되냐?
	public Post update(String title, String contents) {
		// TODO this는 인스턴스 자신을 가리키는 참조 변수인 거겠지?
		this.title = title;
		this.contents = contents;
		return this;
	}

}
