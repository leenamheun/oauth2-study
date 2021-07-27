package com.study.security.domain.member.domain;

import com.study.security.auth.dto.OAuthAttributes;
import com.study.security.auth.entity.BaseTimeEntity;
import com.study.security.auth.entity.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity //entity명시
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //식별자 할당 전략, IDENTITY는 db에게 식별자를 위
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING) //Enum값의 타입 (default number)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String email, String picture, Role role){
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public String getRoleKey() {
       return this.role.getKey();
    }

    //entity의 데이터를 변경하면 실제 db정보가 변경
    public User update(String name, String picture){
        this.name = name;
        this.picture = picture;
        this.createdDate = getCreatedDate();
        this.modifiedDate = getModifiedDate();
        return this;
    }
}


