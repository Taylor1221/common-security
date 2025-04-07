package com.taylor.common.security.config;

import com.taylor.common.security.handler.RestfulAccessDeniedHandler;
import com.taylor.common.security.handler.RestfulAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 异常处理配置类
 *
 * @author loveCamille
 * @date 2025-04-07 14:17:06
 */
@Configuration(proxyBeanMethods = false)
public class SecurityExceptionHandlerConfiguration {

    @Bean
    public RestfulAccessDeniedHandler restfulAccessDeniedHandler() {
        return new RestfulAccessDeniedHandler();
    }

    @Bean
    public RestfulAuthenticationEntryPoint restfulAuthenticationEntryPoint() {
        return new RestfulAuthenticationEntryPoint();
    }

}
