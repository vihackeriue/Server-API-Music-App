package com.musicapp.serverapimusicapp.cofig;

import com.musicapp.serverapimusicapp.dto.BaseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.net.http.HttpRequest;
import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Autowired
    JWTConfig jwtConfig;

    @Bean
    public AuditorAware<String> auditorProvider(HttpServletRequest httpRequest){
//        return new AuditorAwareImpl(session);
        return new AuditorAwareImpl(httpRequest,jwtConfig);
    }
    public static class AuditorAwareImpl implements AuditorAware<String> {
        private final HttpServletRequest httpRequest;
        private final JWTConfig jwtConfig;

        public AuditorAwareImpl(HttpServletRequest httpRequest, JWTConfig jwtConfig) {
            this.httpRequest = httpRequest;
            this.jwtConfig = jwtConfig;
        }

        @Override
        public Optional<String> getCurrentAuditor() {
           String token = httpRequest.getHeader("token");


            if(token == null || jwtConfig.isTokenExpired(token)){
                return null;
            }
            return Optional.of(jwtConfig.extractUsername(token));
        }
    }



}
