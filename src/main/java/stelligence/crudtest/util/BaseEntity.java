package stelligence.crudtest.util;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass // 상속받는 엔티티에게 매핑 정보만 제공하는 역할
@EntityListeners(AuditingEntityListener.class) // Auditing 기능(자동 값 매핑)을 사용하기 위해 추가
public class BaseEntity {

	@CreatedDate
	@Column(updatable = false, nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdDate;

	// TODO lastModifiedDate는 언제 반영되는가? put으로 보냈을 때는 내용만 반영되고, lastModifiedDate는 반영되지 않음.
	@LastModifiedDate
	// @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP") // current_timestamp()가 jpa에서 자동으로 해 주는 건가?
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime lastModifiedDate;

	// @CreatedBy
	// @Column(updatable = false, nullable = false, columnDefinition = "VARCHAR(50) DEFAULT 'SYSTEM'")
	// private String createdBy;
	//
	// @LastModifiedBy
	// @Column(nullable = false, columnDefinition = "VARCHAR(50) DEFAULT 'SYSTEM'")
	// private String modifiedBy;

}
