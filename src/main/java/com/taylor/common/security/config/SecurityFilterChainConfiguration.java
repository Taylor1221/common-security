package com.taylor.common.security.config;

import com.taylor.common.security.filter.JwtAuthenticationFilter;
import com.taylor.common.security.handler.RestfulAccessDeniedHandler;
import com.taylor.common.security.handler.RestfulAuthenticationEntryPoint;
import com.taylor.common.security.properties.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 安全规则配置
 *
 * @author loveCamille
 * @date 2025-04-07 14:13:15
 */
@Configuration(proxyBeanMethods = false)
public class SecurityFilterChainConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, JwtAuthenticationFilter jwtAuthenticationFilter,
                                           RestfulAccessDeniedHandler restfulAccessDeniedHandler,
                                           RestfulAuthenticationEntryPoint restfulAuthenticationEntryPoint,
                                           FilterSecurityInterceptor dynamicSecurityFilter,
                                           SecurityProperties securityProperties) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity
                .authorizeRequests();

        registry.antMatchers(securityProperties.getIgnoreUrls().toArray(new String[0])) // 白名单的资源路径允许访问
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS) // 允许跨域请求的OPTIONS请求
                .permitAll()

                // 任何请求需要身份认证
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()

                // 关闭跨站请求防护及禁用session
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 登出行为由自己实现
                .and()
                .logout()
                .disable()

                // 自定义权限拒绝处理类,返回权限处理结果
                .exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restfulAuthenticationEntryPoint)

                // 自定义权限拦截器JWT过滤器
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // 登录验证
                .addFilterBefore(dynamicSecurityFilter, FilterSecurityInterceptor.class); //动态权限校验
        return httpSecurity.build();
    }

}
