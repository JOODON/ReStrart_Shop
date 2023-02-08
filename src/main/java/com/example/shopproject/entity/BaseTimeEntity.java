package com.example.shopproject.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass//부모클래스를 상속받는 자식에 대하여만 맵핑 정보를 제공함
@Getter @Setter
public abstract class BaseTimeEntity {

    @CreatedDate//저장될떄 시작 자동 저장
    @Column(updatable = false)
    private LocalDateTime regTime;

    @LastModifiedDate//값을 변경 할떄 시간 자동 저장
    private LocalDateTime upDateTime;
}
