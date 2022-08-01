package com.shop.shop.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 상속했을 때, 컬럼으로 인식하게 함
@EntityListeners(AuditingEntityListener.class) // 생성/수정 시간을 자동으로 업데이트 함
public abstract class Timestamped { // abstract - 직접 생성은 불가하고 상속만 가능함
    @CreatedDate
    private LocalDateTime createdAt; // 생성 시간

    @LastModifiedDate
    private LocalDateTime modifiedAt; // 수정 시간
}
