package com.teamsparta.restudy.util.security

import com.teamsparta.restudy.util.jwt.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val authenticationEntryPoint: CustomAuthenticationEntryPoint
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain{
        return http
            .httpBasic { it.disable() } // BasicAuthenticationFilter, DefaultLoginPageGeneratingFilter, DefaultLogoutPageGeneratingFilter 제외
            .formLogin { it.disable() } // UsernamePassworedAuthenticationFilter, DefaultLoginPageGeneratingFilter, DefaultLogoutPageGeneratingFilter 제외
            .csrf { it.disable() } // CsrfFilter 제외
            .authorizeHttpRequests {
                it.requestMatchers(
                    "/signin",
                    "/signup",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/getPostList",
                    "/getPost",
                    "/posts/**" //확인용 임시 권한
                ).permitAll()
                    // 위 URI를 제외하곤 모두 인증이 되어야 함.
                    .anyRequest().authenticated()
            }
            // 기존 UsernamePasswordAuthenticationFilter 가 존재하던 자리에 JwtAuthenticationFilter 적용
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling {
                it.authenticationEntryPoint(authenticationEntryPoint)
            }
            .build()
    }
}