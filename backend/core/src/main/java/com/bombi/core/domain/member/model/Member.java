package com.bombi.core.domain.member.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import org.hibernate.annotations.Comment;

import com.bombi.core.domain.base.model.BaseEntity;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "member_id")
    private UUID id;

    @Column(columnDefinition = "VARCHAR(100) NOT NULL COMMENT '이메일'")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", columnDefinition = "BIGINT COMMENT '권한 ID'")
    private Role role;

}
