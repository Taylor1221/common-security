package com.taylor.common.security;

import com.taylor.common.security.config.*;
import com.taylor.common.security.white.SecurityProperties;
import com.taylor.common.security.service.PermissionSourceService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 动态权限主配置类，仅作为入口，开启自动配置和条件判断
 *
 * @author loveCamille
 * @date 2025-04-07 14:12:12
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnBean(PermissionSourceService.class)
@EnableConfigurationProperties(SecurityProperties.class)
@Import({
        SecurityFilterChainConfiguration.class,
        JwtSecurityConfiguration.class,
        SecurityExceptionHandlerConfiguration.class,
        DynamicPermissionConfiguration.class,
        PermissionChangeListenerConfiguration.class
})
public class DynamicSecurityAutoConfiguration {
}
