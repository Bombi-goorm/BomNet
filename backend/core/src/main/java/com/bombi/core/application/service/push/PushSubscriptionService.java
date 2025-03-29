package com.bombi.core.application.service.push;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bombi.core.application.dto.pushsubscription.UserAgentInfo;
import com.bombi.core.domain.member.model.Member;
import com.bombi.core.domain.member.model.PushSubscription;
import com.bombi.core.domain.member.repository.MemberRepository;
import com.bombi.core.presentation.dto.member.SubscriptionRegisterRequestDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PushSubscriptionService {

	private final MemberRepository memberRepository;
	private final CustomUserAgentParser parser;

	@Transactional
	public void register(String userAgent, SubscriptionRegisterRequestDto subscriptionRegisterRequestDto, String memberId) {
		UserAgentInfo userAgentInfo = parser.extractUserAgentInfo(userAgent);

		Member member = memberRepository.findMemberAndInfoById(UUID.fromString(memberId))
			.orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

		// 구독정보가 있다면
		if(member.hasSubscription(userAgentInfo.getDeviceType())) {
			member.updateSubscription(
				userAgentInfo.getOsName(),
				userAgentInfo.getBrowserName(),
				userAgentInfo.getDeviceType(),
				subscriptionRegisterRequestDto.getAuth(),
				subscriptionRegisterRequestDto.getP256dh(),
				subscriptionRegisterRequestDto.getEndpoint()
			);
			return;
		}

		PushSubscription pushSubscription = PushSubscription.of(
			subscriptionRegisterRequestDto.getEndpoint(),
			subscriptionRegisterRequestDto.getP256dh(),
			subscriptionRegisterRequestDto.getAuth(),
			userAgentInfo.getDeviceType(),
			userAgentInfo.getOsName(),
			userAgentInfo.getBrowserName(),
			member);
		member.registerSubscription(pushSubscription);
	}

}
