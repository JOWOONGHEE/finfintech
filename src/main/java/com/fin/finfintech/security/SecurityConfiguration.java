package com.fin.finfintech.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter authenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/auth/**", "/account", "/account/**",
                                "/transaction/**", "/transfer", "/autotransfer",
                                "/investment/**")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }


}


//    private final OAuth2Service oAuth2Service;
//
//    public SecurityConfig(OAuth2Service oAuth2Service) {
//        this.oAuth2Service = oAuth2Service;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http.csrf(csrf -> csrf.disable()); // CSRF 보안 설정 비활성화
//        http.authorizeHttpRequests((request) -> request
//                .requestMatchers("/").permitAll() // 홈 화면은 인증 필요 X
//                .anyRequest().authenticated() // 그 외는 모두 인증 필요
//        );
//
//        http.formLogin(formLogin -> formLogin.disable());// 폼 로그인 비활성화
//
//        http.logout(logout -> logout // 로그아웃 설정
//                .logoutUrl("/logout") // 로그아웃 URL 설정
//                .logoutSuccessUrl("/") // 로그아웃 성공 시 이동할 URL 설정
//                .permitAll() // 로그아웃 요청은 인증 없이 접근 가능
//        );
//
//        http.oauth2Login(oauth2 -> oauth2
//                .defaultSuccessUrl("/oauth/loginInfo", true) // 로그인 성공시 이동할 URL
//                .userInfoEndpoint(userInfo -> userInfo
//                        .userService(oAuth2Service)
//                ));
//
//
//        return http.build();
//
//
//    }