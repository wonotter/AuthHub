package com.authsphere.authhub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.authsphere.authhub.service.CustomAccessDeniedHandler;
import com.authsphere.authhub.service.CustomAuthenticationEntryPoint;
import com.authsphere.authhub.service.CustomAuthenticationFailureHandler;
import com.authsphere.authhub.service.CustomAuthenticationSuccessHandler;
import com.authsphere.authhub.service.JwtAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain securityFilterChain(
        HttpSecurity http
    ) throws Exception {
        return http
            .httpBasic(AbstractHttpConfigurer::disable) // 기본 인증 비활성화 -> JWT 기반 인증, 보안성이 낮은 Basic 인증 제외
            .csrf(AbstractHttpConfigurer::disable) // 기본 CSRF 보호 비활성화 -> REST API는 stateless 하므로 필요 x
            .formLogin(AbstractHttpConfigurer::disable)
            .logout(AbstractHttpConfigurer::disable)
            .rememberMe(AbstractHttpConfigurer::disable) // 기본 리멤버미 비활성화 -> 로그인 상태 장기 유지 필요 x refresh token 사용
            .requestCache(RequestCacheConfigurer::disable) // 기본 요청 캐시 비활성화 -> 인증 전 요청을 저장했다가 인증 후 원래 페이지로 리다이렉트 보틍은 클라이언트에서 라우팅 처리
            .sessionManagement(sessions -> sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Jwt 기반 인증이므로 세션 비활성화
            .securityContext(AbstractHttpConfigurer::disable)

            .exceptionHandling((exception) -> 
                exception.authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
            )

            .addFilterBefore(jwtAuthenticationFilter(http), UsernamePasswordAuthenticationFilter.class)

            // .oauth2Login((oauth2) -> oauth2
            //     .authorizedClientRepository(null)
            //     .authorizedClientService(null)
            //     .userInfoEndpoint(userInfo -> userInfo.userService(null))
            //     .successHandler(customAuthenticationSuccessHandler)
            //     .failureHandler(customAuthenticationFailureHandler)
            // )

            .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll()
                .anyRequest().authenticated()
            )

            .build();
    }

    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy); // 동적으로 권한 계층을 설정할 수 있도록 추후 수정
        return expressionHandler;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.fromHierarchy("ROLE_ADMIN > ROLE_NORMAL > ROLE_FREE");
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(HttpSecurity http) {
        return new JwtAuthenticationFilter(
            http.getSharedObject(AuthenticationManager.class),
            objectMapper
        );
    }
}
