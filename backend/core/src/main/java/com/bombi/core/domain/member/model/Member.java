package com.bombi.core.domain.member.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.annotations.Comment;

import com.bombi.core.domain.base.model.BaseEntity;
import com.bombi.core.domain.notificationcondition.model.NotificationCondition;

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
    @Column(columnDefinition = "VARCHAR(1) NOT NULL")
    @Comment("인증 등록 여부")
    private String isEnabled;

    // 탈퇴, 이용정지
    @Column(columnDefinition = "VARCHAR(1) NOT NULL")
    @Comment("탈퇴/정지 여부")
    private String isBanned;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", columnDefinition = "BIGINT COMMENT '권한 ID'")
    private Role role;

    @OneToOne(mappedBy = "member")
    private MemberInfo memberInfo;

    @OneToMany(mappedBy = "member")
    private List<NotificationCondition> notificationConditions = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
    @MapKey(name = "deviceType")
    private Map<String, PushSubscription> pushSubscriptions = new HashMap<>();

    @Builder
    private Member(String platform, String authEmail, Role role) {
        this.platform = platform;
        this.authEmail = authEmail;
        this.role = role;
        this.isBanned = "F";
        this.isEnabled = "F";
    }

    public static Member of(String platform, String authEmail, Role role) {
        return new Member(platform, authEmail, role);
    }

    public void updatePnu(String pnu) {
        memberInfo.updatePnu(pnu);
        role.changeToFarmer();
        activateEnableStatus();
    }

    private void activateEnableStatus() {
        this.isEnabled = "T";
    }

    public boolean hasSubscription(String deviceType) {
        return pushSubscriptions.containsKey(deviceType);
    }

    public void updateSubscription(String osName, String browserName, String deviceType, String auth, String p256dh,
        String endpoint) {
        PushSubscription pushSubscription = pushSubscriptions.get(deviceType);
        pushSubscription.updateOs(osName);
        pushSubscription.updateBrowser(browserName);
        pushSubscription.updateAuth(auth);
        pushSubscription.updateP256dh(p256dh);
        pushSubscription.updateEndpoint(endpoint);
    }

    public void registerSubscription(PushSubscription pushSubscription) {
        this.pushSubscriptions.put(pushSubscription.getDeviceType(), pushSubscription);
    }

    public String getSidoCode() {
        return memberInfo.getSiDoCode();
    }

}
