package com.bombi.auth.application.util;

import com.bombi.auth.application.exception.e500.RedisSessionException;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Component
@NoArgsConstructor
public final class GenerateCodeUtil {

    public static String generateTokenVerifyString() {
        try {
            int tokenLength = 32;
            byte[] randomBytes = new byte[tokenLength];
            SecureRandom secureRandom = SecureRandom.getInstanceStrong();
            secureRandom.nextBytes(randomBytes);
            return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RedisSessionException("토큰 생성 중 오류가 발생했습니다");
        }
    }
}
