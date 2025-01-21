package com.authsphere.authhub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.authsphere.authhub.service.CustomAccessDeniedHandler;
import com.authsphere.authhub.service.CustomAuthenticationEntryPoint;
import com.authsphere.authhub.service.CustomAuthenticationFailureHandler;
import com.authsphere.authhub.service.CustomAuthenticationSuccessHandler;
import com.authsphere.authhub.service.CustomLogoutSuccessHandler;
import com.authsphere.authhub.service.JwtAuthenticationFilter;
import com.authsphere.authhub.service.JwtTokenProvider;
import com.authsphere.authhub.service.application.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain securityFilterChain(
        HttpSecurity http, 
        AuthenticationManager authenticationManager
    ) throws Exception {
        return http
            .httpBasic(AbstractHttpConfigurer::disable) // 기본 인증 비활성화 -> JWT 기반 인증, 보안성이 낮은 Basic 인증 제외
            .csrf(AbstractHttpConfigurer::disable) // 기본 CSRF 보호 비활성화 -> REST API는 stateless 하므로 필요 x
            .rememberMe(AbstractHttpConfigurer::disable) // 기본 리멤버미 비활성화 -> 로그인 상태 장기 유지 필요 x refresh token 사용
            .requestCache(RequestCacheConfigurer::disable) // 기본 요청 캐시 비활성화 -> 인증 전 요청을 저장했다가 인증 후 원래 페이지로 리다이렉트 보틍은 클라이언트에서 라우팅 처리
            .sessionManagement(sessions -> sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Jwt 기반 인증이므로 세션 비활성화
            .securityContext(AbstractHttpConfigurer::disable)

            .exceptionHandling((exception) -> 
                exception.authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
            )

            .addFilterBefore(new JwtAuthenticationFilter(authenticationManager, objectMapper), UsernamePasswordAuthenticationFilter.class)

            .oauth2Login((oauth2) -> oauth2
                .authorizedClientRepository(null)
                .authorizedClientService(null)
                .userInfoEndpoint(userInfo -> userInfo.userService(null))
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
            )

            .formLogin((form) -> form
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
            )

            .logout((logout) -> logout
                .logoutUrl("/api/v1/auth/logout")
                .logoutSuccessHandler(customLogoutSuccessHandler)
                .clearAuthentication(true)
            )

            .authorizeHttpRequests((authorize) -> authorize
                .anyRequest().authenticated()
            )

            .build();
    }

    @Bean
    public PasswordEncoder customPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy);
        return expressionHandler;
    }

    @Bean
    public AuthenticationManager formAuthenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public AuthenticationManager jwtAuthenticationManager(JwtTokenProvider jwtProvider) {
        return new ProviderManager(jwtProvider);
    }
}
