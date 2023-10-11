package study.datajpa.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass // 속성들을 상속 관계로 다 내려서 테이블에 같이 쓸 수 있게 해주는 어노테이션
@Getter
public class JpaBaseEntity {

    @Column(updatable = false) // 값 변경 불가 옵션
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @PrePersist // persist 하기 전에 실행
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now;
    }

    @PreUpdate // update 하기 전에 실행
    public void preUpdate() {
        updatedDate = LocalDateTime.now();
    }
}
