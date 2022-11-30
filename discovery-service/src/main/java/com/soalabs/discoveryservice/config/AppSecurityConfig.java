package com.soalabs.discoveryservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class AppSecurityConfig {
    @Value("${app.eureka.username}")
    private String username;

    @Value("${app.eureka.password}")
    private String password;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder, PasswordEncoder passwordEncoder) throws Exception {
        authenticationManagerBuilder.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder)
                .withUser(username)
                .password(passwordEncoder.encode(password))
                .authorities("USER");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests().anyRequest()
                .authenticated()
                .and()
                .httpBasic();

        return httpSecurity.build();
    }
}
