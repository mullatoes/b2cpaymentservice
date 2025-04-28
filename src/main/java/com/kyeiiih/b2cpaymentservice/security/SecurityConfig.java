package com.kyeiiih.b2cpaymentservice.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    // Default to 'test' profile for testing
    @Value("${spring.profiles.active:test}")
    private String activeProfile;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        if ("test".equals(activeProfile)) {
            http.authorizeHttpRequests(authz -> authz
                    .requestMatchers("/authentication/**").permitAll()
                    .requestMatchers("/swagger-ui/**", "/api-docs/**", "/h2-console/**","/h2/**").permitAll()
                    .anyRequest().authenticated());
        } else {


            http
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/api/v1/payments/**").authenticated()
                            .requestMatchers("/swagger-ui/**", "/api-docs/**", "/h2-console/**").permitAll()
                            .anyRequest().authenticated()
                    )
                    .oauth2ResourceServer(oauth2 -> oauth2
                            .jwt(jwt -> jwt
                                    .jwtAuthenticationConverter(jwtAuthenticationConverter())
                            )
                    )
                    .csrf(AbstractHttpConfigurer::disable)
                    .headers(headers -> headers
                            .xssProtection(xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK))
                            .contentSecurityPolicy(cps -> cps.policyDirectives("script-src 'self'; frame-ancestors 'self' http://localhost:8080;"))
                    );

        }

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix("ROLE_");
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return converter;
    }
}
