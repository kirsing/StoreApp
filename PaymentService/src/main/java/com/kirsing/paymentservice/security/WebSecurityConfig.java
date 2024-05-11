package com.kirsing.paymentservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .authorizeHttpRequests(authorizeRequest -> authorizeRequest.requestMatchers(HttpMethod.GET).permitAll());
        http.authorizeHttpRequests(authorizeRequest -> {
            try {
                authorizeRequest.requestMatchers(HttpMethod.POST).authenticated()
                        .and().oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
//                                .requestMatchers("/payment/**")
//                                .hasAuthority("SCOPE_internal")
//                                .anyRequest()
//                                .authenticated())
//                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        return http.build();
    }
}

