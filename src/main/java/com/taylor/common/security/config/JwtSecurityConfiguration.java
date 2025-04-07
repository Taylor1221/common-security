package com.taylor.common.security.config;

import com.taylor.common.jwt.JwtProvider;
import com.taylor.common.security.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * JWT相关配置类
 *
 * @author loveCamille
 * @date 2025-04-07 14:14:46
 */
@Configuration
public class JwtSecurityConfiguration {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtProvider jwtProvider,
                                                           UserDetailsService userDetailsService) {
        return new JwtAuthenticationFilter(jwtProvider, userDetailsService);
    }

}
