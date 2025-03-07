package com.bombi.core.infrastructure.config;

import com.bombi.core.infrastructure.security.authentication.JwtAuthenticationFilter;
import com.bombi.core.infrastructure.security.handler.CustomAccessDeniedHandler;
import com.bombi.core.infrastructure.security.handler.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private static final String[] PUBLIC_API_URL = { // 인증 없이도 접근 가능한 경로
            "/core/health", "/bigquery/data", "/gcs/data", "/weather/special", "/naver/news", "/best/price", "/core/home", "/weather/forecast", "/soil/character", "/soil/chemical", "/farm"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf((csrf) -> csrf.disable()) // CSRF 보호 비활성화
                .cors((cors) -> cors.configurationSource(CorsConfig.corsConfigurationSource())) // CORS 설정
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 비활성화
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(PUBLIC_API_URL).permitAll() // 인증 없이 접근 가능한 경로
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // OPTIONS 요청 허용
                        .anyRequest().authenticated()) // 이외 요청은 모두 인증 확인
                .exceptionHandling((e) -> e
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 인증되지 않은 사용자 접근 혹은 유효한 인증정보 부족한 경우(401 Unauthorized)
                        .accessDeniedHandler(new CustomAccessDeniedHandler()) // 403 Forbidden
                )
                .anonymous((anonymous) -> anonymous.disable()) // 익명 인증 비활성화
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }
}
