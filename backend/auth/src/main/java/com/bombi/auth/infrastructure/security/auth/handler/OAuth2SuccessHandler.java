package com.bombi.auth.infrastructure.security.auth.handler;

import com.bombi.auth.application.exception.e401.InvalidTokenException;
import com.bombi.auth.application.jwt.TokenProvider;
import com.bombi.auth.domain.member.Member;
import com.bombi.auth.domain.member.repository.MemberRepository;
import com.bombi.auth.infrastructure.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.bombi.auth.application.jwt.TokenProvider.ACCESS_EXP;
import static com.bombi.auth.application.jwt.TokenProvider.REFRESH_EXP;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

	private final TokenProvider tokenProvider;
	private final MemberRepository memberRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
										Authentication authentication) throws IOException {
		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
		Map<String, Object> attributes = oAuth2User.getAttributes();
		String email;

		if (attributes.containsKey("kakao_account")) {
			Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");

			// "email" 키가 있는지 확인 후 가져오기
			if (account.containsKey("email")) {
				email = (String) account.get("email");
			} else {
				throw new InvalidTokenException("카카오 이메일 정보가 없습니다.");
			}
		} else {
			throw new InvalidTokenException("카카오 계정 정보가 없습니다.");
		}

		// DB에서 사용자 조회 (loadUser()에서 이미 저장된 상태)
		Member member = memberRepository.findByAuthEmail(email)
				.orElseThrow(() -> new InvalidTokenException("사용자 정보를 찾을 수 없습니다."));

		// CustomUserDetails로 변환
		CustomUserDetails userDetails =
				new CustomUserDetails(member, List.of(new SimpleGrantedAuthority("ROLE_USER")));

		String accessToken = tokenProvider.generateAccessToken(userDetails);
		String refreshToken = tokenProvider.generateRefreshToken(userDetails);

		// 쿠키에 토큰 저장
		long accessTokenCookieExp = ACCESS_EXP / 1000;
		ResponseCookie cookie = ResponseCookie.from("access_token", accessToken)
				.secure(true)
				.httpOnly(true)
				.path("/")
				.sameSite("Strict")
				.maxAge(accessTokenCookieExp)
				.build();

		response.addHeader("Set-Cookie", cookie.toString());

		long refreshTokenCookieExp = REFRESH_EXP / 1000;
		cookie = ResponseCookie.from("refresh_token", refreshToken)
				.secure(true)
				.httpOnly(true)
				.path("/")
				.sameSite("Strict")
				.maxAge(refreshTokenCookieExp)
				.build();

		response.addHeader("Set-Cookie", cookie.toString());

		// 등록되지 않은 계정이면 등록 페이지로 이동. 이미 등록된 계정이면 홈으로 이동
		String redirectUri = member.getIsEnabled().equals("F") ?
				"https://bomnet.shop/signup" :
				"https://bomnet.shop";
		response.sendRedirect(redirectUri);
	}
}
