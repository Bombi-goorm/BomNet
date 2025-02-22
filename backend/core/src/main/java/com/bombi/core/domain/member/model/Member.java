package com.bombi.core.domain.member.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Column(columnDefinition = "VARCHAR(10) NOT NULL")
    @Comment("소셜로그인")
    private String platform;

    @Column(columnDefinition = "VARCHAR(100) NOT NULL")
    @Comment("이메일")
    private String authEmail;

    // 인증 후 등록 전
    private String isEnabled;

    // 탈퇴, 이용정지
    private String isBanned;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", columnDefinition = "BIGINT COMMENT '권한 ID'")
    private Role role;

    @Builder
    private Member(String platform, String authEmail, Role role) {
        this.platform = platform;
        this.authEmail = authEmail;
        this.role = role;
    }

    public static Member of(String platform, String authEmail, Role role) {
        return new Member(platform, authEmail, role);
    }

}
