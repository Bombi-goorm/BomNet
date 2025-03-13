package com.bombi.auth.application.jwt;

import com.bombi.auth.application.exception.e401.InvalidTokenException;
import com.bombi.auth.application.exception.e500.RedisSessionException;
import com.bombi.auth.application.util.GenerateCodeUtil;
import com.bombi.auth.infrastructure.security.CustomUserDetails;
import com.bombi.auth.infrastructure.RedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {
	private Key key;
	private final RedisService redisService;

	@Value("${jwt.secret}")
	private String secretKey;

	// 인증토큰 만료시간 - 1시간
	public static long ACCESS_EXP = 3600000L;

	// 갱신토큰 만료시간 - 60일
	public static long REFRESH_EXP = 5184000000L;

	private static final String ISSUER = "https://bomnet.co.kr";

	@PostConstruct
	public void init() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	private Key getSigningKey() {
		return key;
	}

	/**
	 * JWT 토큰에서 사용자 ID 추출.
	 * @param token JWT 토큰
	 * @return 사용자 ID (Member ID)
	 */
	public String extractMemberId(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	/**
	 * JWT 토큰에서 만료 시간 추출.
	 * @param token JWT 토큰
	 * @return 만료 시간
	 */
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	/**
	 * JWT 토큰에서 발행자 정보 추출.
	 * @param token JWT 토큰
	 * @return 발행자 (Issuer)
	 */
	private String extractIssuer(String token) { return extractClaim(token, Claims::getIssuer); }

	/**
	 * JWT 토큰에서 특정 Claim을 추출.
	 * @param token JWT 토큰
	 * @param claimsResolver Claim 변환 함수
	 * @param <T> Claim의 타입
	 * @return 변환된 Claim
	 */
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}


	/**
	 * JWT 토큰에서 모든 Claim을 추출.
	 * @param token JWT 토큰
	 * @return Claims 객체
	 */
	public Claims extractAllClaims(String token) {
		try {
			return Jwts.parserBuilder()
					.setSigningKey(getSigningKey())
					.build()
					.parseClaimsJws(token)
					.getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}


	/**
	 * Access 토큰 검증.
	 * 사용자 ID, 토큰 만료 여부, 발행자를 확인하여 유효성 검증 수행.
	 * @param token JWT Access 토큰
	 * @param userDetails 사용자 정보
	 * @return 토큰이 유효한 경우 true, 그렇지 않으면 false
	 */
	public boolean validateAccessToken(String token, CustomUserDetails userDetails) throws NoSuchAlgorithmException, InvalidKeySpecException {
		final String memberId = extractMemberId(token);
		return memberId.equals(userDetails.getMember().getId()) &&
				!isTokenExpired(token) &&
				ISSUER.equals(extractIssuer(token));
	}


	/**
	 * Refresh 토큰 검증.
	 * Redis에 저장된 검증 문자열과 대조하여 유효성 검증 수행.
	 * @param token JWT Refresh 토큰
	 */
	public void validateRefreshToken(String token) {
		try {
			String memberId = extractMemberId(token);
			String tokenVerifyString = extractClaim(token, claims -> claims.get("vfs", String.class));

			String storedVerifyString = redisService.getRefreshTokenVerification(
					memberId
			);

			if (tokenVerifyString == null ||
					!tokenVerifyString.equals(storedVerifyString) ||
					isTokenExpired(token) ||
					!ISSUER.equals(extractIssuer(token))) {
				throw new IllegalArgumentException("인증 요청이 유효하지 않습니다.");
			}
		} catch (Exception e) {
			log.error("Access 토큰 검증 실패: {}", e.getMessage());
			throw new IllegalArgumentException("인증 요청 중 오류가 발생했습니다.");
		}
	}

	/**
	 * JWT 토큰 만료 여부 확인.
	 * @param token JWT 토큰
	 * @return 만료된 경우 true, 그렇지 않으면 false
	 */
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}


	/**
	 * Access 토큰 생성.
	 * @param userDetails 사용자 정보
	 * @return 생성된 Access 토큰
	 */
	public String generateAccessToken(CustomUserDetails userDetails) {
		return generateAccessToken(new HashMap<>(), userDetails);
	}

	/**
	 * Refresh 토큰 생성.
	 * @param userDetails 사용자 정보
	 * @return 생성된 Refresh 토큰
	 */
	public String generateRefreshToken(CustomUserDetails userDetails)  {
		return generateRefreshToken(new HashMap<>(), userDetails);
	}


	/**
	 * Access 토큰 생성 (추가 Claims 포함).
	 * @param extraClaims 추가 Claims
	 * @param userDetails 사용자 정보
	 * @return 생성된 Access 토큰
	 */
	public String generateAccessToken(
			Map<String, Object> extraClaims,
			CustomUserDetails userDetails
	) {
		try {
			return buildAccessToken(extraClaims, userDetails, ACCESS_EXP);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			log.error("Access 토큰 생성 실패: memberId={}", userDetails.getMember().getId(), e);
			throw new InvalidTokenException("인증과정에서 오류가 발생했습니다.");
		}
	}

	/**
	 * Refresh 토큰 생성 (추가 Claims 포함).
	 * Redis에 검증 문자열 저장 후 생성.
	 * @param extraClaims 추가 Claims
	 * @param userDetails 사용자 정보
	 * @return 생성된 Refresh 토큰
	 */
	public String generateRefreshToken(
			Map<String, Object> extraClaims,
			CustomUserDetails userDetails
	)  {
		// 토큰 검증용 문자열 생성
		try {
			String verifyString = GenerateCodeUtil.generateTokenVerifyString();

			System.out.println(REFRESH_EXP);
			// Redis에 검증용 문자열 저장
			redisService.setRefreshTokenVerification(
					String.valueOf(userDetails.getMember().getId()),
					verifyString,
					REFRESH_EXP
			);
			return buildRefreshToken(extraClaims, userDetails, REFRESH_EXP, verifyString);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			log.error("Refresh 토큰 생성 실패: memberId={}", userDetails.getMember().getId(), e);
			throw new InvalidTokenException("인증과정에서 오류가 발생했습니다.");
		} catch (Exception e) {
			log.error("Refresh 토큰 검증 정보 저장 실패: memberId={}", userDetails.getMember().getId(), e);
			throw new RedisSessionException("인증정보 저장 중 오류가 발생했습니다.");
		}
	}

	/**
	 * Access 토큰 빌드.
	 * @param extraClaims 추가 Claims
	 * @param userDetails 사용자 정보
	 * @param accessExpiration 만료 시간
	 * @return 생성된 Access 토큰
	 */
	private String buildAccessToken(
			Map<String, Object> extraClaims,
			CustomUserDetails userDetails,
			long accessExpiration
	) throws NoSuchAlgorithmException, InvalidKeySpecException{
		return Jwts
				.builder()
				.setIssuer(ISSUER)// Issuer
				.setSubject(String.valueOf(userDetails.getMember().getId()))// memberId 설정. userDetails에서 가져온다.
				.setIssuedAt(new Date(System.currentTimeMillis()))//현재시간
				.setExpiration(new Date(System.currentTimeMillis() + accessExpiration))//만료시간 밀리초 * 초 * 분 * 시(1일)
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	/**
	 * Refresh 토큰 빌드.
	 * 검증 문자열을 Claim에 추가.
	 * @param extraClaims 추가 Claims
	 * @param userDetails 사용자 정보
	 * @param refreshExpiration 만료 시간
	 * @param verifyString 검증 문자열
	 * @return 생성된 Refresh 토큰
	 */
	public String buildRefreshToken(
			Map<String, Object> extraClaims,
			CustomUserDetails userDetails,
			long refreshExpiration,
			String verifyString
	) throws NoSuchAlgorithmException, InvalidKeySpecException {
		return Jwts
				.builder()
				.setIssuer(ISSUER)//발행주체
				.setSubject(String.valueOf(userDetails.getMember().getId()))// memberId 설정. userDetails에서 가져온다.
				.setIssuedAt(new Date(System.currentTimeMillis()))//현재시간
				.setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))//만료시간 밀리초 * 초 * 분 * 시(1일)
				.claim("vfs", verifyString) // 검증용 문자열 추가
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}
}
