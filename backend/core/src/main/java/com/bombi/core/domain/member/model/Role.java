package com.bombi.core.domain.member.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import org.hibernate.annotations.Comment;

@Entity
@Table(name = "role")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20) NOT NULL")
    @Comment("권한 이름")
    private MemberRole roleName;

    private Role(MemberRole memberRole) {
        this.roleName = memberRole;
    }

    public static Role ofUser() {
        return new Role(MemberRole.USER);
    }

    public static Role ofFarmer() {
        return new Role(MemberRole.FARMER);
    }
}
