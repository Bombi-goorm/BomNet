package com.bombi.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.bombi.auth.domain.auth.filter.TokenAuthenticationFilter;
import com.bombi.auth.domain.auth.filter.TokenExceptionFilter;
import com.bombi.auth.domain.auth.handler.OAuth2SuccessHandler;
import com.bombi.auth.domain.auth.service.CustomOAuth2UserService;
import com.bombi.auth.domain.auth.service.TokenService;
import com.bombi.auth.domain.auth.util.TokenProvider;
import com.bombi.auth.domain.member.MemberRole;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
//@EnableWebSecurity(debug = true)
public class SecurityConfig {

    private final CustomOAuth2UserService oauth2UserService;
    private final TokenProvider tokenProvider;
    private final TokenService tokenService;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
            .requestMatchers("/error", "/favicon.ico");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .formLogin(AbstractHttpConfigurer::disable)
            .logout(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화
            .cors((cors) -> cors.configurationSource(CorsConfig.corsConfigurationSource())) // CORS 설정
            .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 비활성화
            .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers(
                    new AntPathRequestMatcher("/home"),
                    new AntPathRequestMatcher("/home/info")).hasRole("USER")
                .anyRequest().permitAll())
        ;

        http
            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo.userService(oauth2UserService))
                .successHandler(oauth2SuccessHandler())
            );

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(tokenExceptionFilter(), TokenAuthenticationFilter.class);

        http.exceptionHandling((exceptions) -> exceptions
            .authenticationEntryPoint(customAuthenticationEntryPoint())
            .accessDeniedHandler(customAccessDeniedHandler())
        );


        return http.build();
    }

    @Bean
    public AccessDeniedHandler customAccessDeniedHandler() {
        return null;
    }

    @Bean
    public AuthenticationEntryPoint customAuthenticationEntryPoint() {
        return null;
    }

    @Bean
    public OAuth2SuccessHandler oauth2SuccessHandler() {
        return new OAuth2SuccessHandler(tokenProvider, tokenService);
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    @Bean
    public TokenExceptionFilter tokenExceptionFilter() {
        return new TokenExceptionFilter();
    }




}
