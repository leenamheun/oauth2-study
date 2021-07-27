package com.study.security.auth.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass //BaseTimeEntity을 상속받는 하위클래스에게 createdDate, modifiedDate도 인식
@EntityListeners(AuditingEntityListener.class) //해당 entity에 Auditing을 설정,
public abstract class BaseTimeEntity {

    @CreatedDate
    public LocalDateTime createdDate;

    @LastModifiedDate
    public LocalDateTime modifiedDate;
}
